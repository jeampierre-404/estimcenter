package pe.idat.controller;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.idat.model.Cotizacion;
import pe.idat.model.Cotizacion.EstadoCotizacion; 
import pe.idat.model.Venta;
import pe.idat.model.Venta.TipoComprobante;
import pe.idat.model.MetodoPago;
import pe.idat.dto.CotizacionDTO;
import pe.idat.service.CotizacionService;
import pe.idat.service.VentaService; 
import pe.idat.repository.MetodoPagoRepository;
import pe.idat.model.Despacho;
import pe.idat.model.Despacho.EstadoDespacho;
import pe.idat.service.DespachoService;
import pe.idat.model.MovimientoInventario;
import pe.idat.model.MovimientoInventario.TipoMovimiento;
import pe.idat.service.MovimientoInventarioService;

@RestController
@RequestMapping("/cotizacion")
@CrossOrigin(origins = "http://localhost:4200")
public class CotizacionRestController {

    @Autowired private pe.idat.repository.DetalleCotizacionRepository detalleRepo; 
    @Autowired private pe.idat.repository.ProductoRepository productoRepo;         
    
    @Autowired
    private CotizacionService cotizacionService;

    @Autowired
    private VentaService ventaService; 
    
    @Autowired
    private MetodoPagoRepository metodoPagoRepo;
    
    @Autowired
    private DespachoService despachoService;

    @Autowired
    private MovimientoInventarioService movimientoService;

    @GetMapping("/listar")
    public ResponseEntity<?> listar() {
        Collection<Cotizacion> items = cotizacionService.findAll();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscar(@PathVariable Integer id) {
        Cotizacion cot = cotizacionService.findById(id);
        if (cot != null) {
            return new ResponseEntity<>(cot, HttpStatus.OK);
        }
        return new ResponseEntity<>("Cotización no encontrada", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/generar")
    public ResponseEntity<?> generar(@RequestBody CotizacionDTO dto) {
        cotizacionService.guardarCotizacionCompleta(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/facturar/{id}/{idMetodo}")
    @org.springframework.transaction.annotation.Transactional
    public ResponseEntity<?> facturarCotizacion(@PathVariable Integer id, @PathVariable Integer idMetodo) {
        
        Cotizacion cot = cotizacionService.findById(id);
        if (cot == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (cot.getEstado() == EstadoCotizacion.FACTURADO) {
            return new ResponseEntity<>("Esta venta ya fue cerrada.", HttpStatus.BAD_REQUEST);
        }

        for (pe.idat.model.DetalleCotizacion det : detalleRepo.findAll()) {
            if (det.getCotizacion().getIdCotizacion().equals(id)) {
                
                pe.idat.model.Producto prod = det.getProducto();

                if ("INACTIVO".equals(prod.getEstado())) {
                    return new ResponseEntity<>(
                        "Error: El producto '" + prod.getNombre() + "' está INACTIVO/ELIMINADO. No se puede vender.", 
                        HttpStatus.BAD_REQUEST
                    );
                }

                if (det.getCantidad().compareTo(prod.getStockActual()) > 0) {
                    return new ResponseEntity<>(
                        "Error: Stock insuficiente para '" + prod.getNombre() + "'. Tienes " + prod.getStockActual() + " y quieres vender " + det.getCantidad(), 
                        HttpStatus.BAD_REQUEST
                    );
                }
            }
        }

        cot.setEstado(EstadoCotizacion.FACTURADO);
        cotizacionService.update(cot);

        Venta nuevaVenta = new Venta();
        nuevaVenta.setCotizacion(cot);
        nuevaVenta.setFechaEmision(new Date());
        nuevaVenta.setTipoComprobante(TipoComprobante.BOLETA);
        nuevaVenta.setSerieCorrelativo("B001-" + String.format("%06d", id)); 
        
        MetodoPago pago = metodoPagoRepo.findById(idMetodo).orElse(null);
        if (pago == null) return new ResponseEntity<>("Error: Falta Método de Pago", HttpStatus.INTERNAL_SERVER_ERROR);
        nuevaVenta.setMetodoPago(pago);

        ventaService.insert(nuevaVenta);

        Despacho nuevoDespacho = new Despacho();
        nuevoDespacho.setVenta(nuevaVenta);
        nuevoDespacho.setEstado(EstadoDespacho.PENDIENTE);
        despachoService.insert(nuevoDespacho);

        for (pe.idat.model.DetalleCotizacion det : detalleRepo.findAll()) {
            if (det.getCotizacion().getIdCotizacion().equals(id)) {
                pe.idat.model.Producto prod = det.getProducto();
                java.math.BigDecimal stockNuevo = prod.getStockActual().subtract(det.getCantidad());
                prod.setStockActual(stockNuevo);
                productoRepo.save(prod);

                MovimientoInventario mov = new MovimientoInventario();
                mov.setTipo(TipoMovimiento.SALIDA);
                mov.setCantidad(det.getCantidad());
                mov.setFecha(new Date());
                mov.setMotivo("Venta facturada - Cotización " + cot.getCodigo());
                mov.setProducto(prod);
                mov.setUsuario(cot.getUsuario()); 
                movimientoService.insert(mov);
            }
        }

        return new ResponseEntity<>(Map.of("mensaje", "Venta y Despacho registrados correctamente"), HttpStatus.OK);
    }

    @DeleteMapping("/borrar/{id}")
    @org.springframework.transaction.annotation.Transactional
    public ResponseEntity<?> borrar(@PathVariable Integer id) {
        Cotizacion cotizacion = cotizacionService.findById(id);
        if (cotizacion != null) {
            
            if (cotizacion.getEstado() == EstadoCotizacion.FACTURADO) {
                return new ResponseEntity<>("No se puede eliminar una cotización que ya ha sido facturada.", HttpStatus.BAD_REQUEST);
            }
            
            for (pe.idat.model.DetalleCotizacion det : detalleRepo.findAll()) {
                if (det.getCotizacion().getIdCotizacion().equals(id)) {
                    detalleRepo.delete(det);
                }
            }
            
            cotizacionService.delete(id);
            return new ResponseEntity<>("eliminado", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}