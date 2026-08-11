package pe.idat.controller;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.idat.dto.ResumenDTO;
import pe.idat.model.Cotizacion; // Importamos el Modelo
import pe.idat.model.Cotizacion.EstadoCotizacion; // Importamos el Enum
import pe.idat.repository.ClienteRepository;
import pe.idat.repository.CotizacionRepository;
import pe.idat.repository.ProductoRepository;
import pe.idat.repository.DetalleCotizacionRepository;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*")
public class DashboardRestController {

    @Autowired private CotizacionRepository cotizacionRepo;
    @Autowired private ProductoRepository productoRepo;
    @Autowired private ClienteRepository clienteRepo;
    @Autowired private DetalleCotizacionRepository detalleRepo;

    @GetMapping("/resumen")
    public ResponseEntity<ResumenDTO> obtenerResumen() {
        
        BigDecimal sumaVentas = BigDecimal.ZERO;      // Dinero real (Facturado)
        BigDecimal dineroPendiente = BigDecimal.ZERO; // Dinero esperado (Cotizaciones)

        // 1. CLASIFICAR EL DINERO (USANDO ENUMS)
        for (Cotizacion c : cotizacionRepo.findAll()) {
            
            // Verificamos que el estado no sea nulo para evitar errores
            if (c.getEstado() != null) {
                
                // CASO 1: VENTA CERRADA (CAJA AZUL)
                if (c.getEstado() == EstadoCotizacion.FACTURADO) {
                    sumaVentas = sumaVentas.add(c.getTotal());
                } 
                // CASO 2: COTIZACIÓN ABIERTA (CAJA AMARILLA)
                else if (c.getEstado() == EstadoCotizacion.PENDIENTE) {
                    dineroPendiente = dineroPendiente.add(c.getTotal());
                }
            }
        }

        // 2. CONTAR STOCK BAJO (Mantener igual)
        long productosBajos = 0;
        for (pe.idat.model.Producto p : productoRepo.findAll()) {
            if (p.getStockActual() != null && p.getStockActual().compareTo(new BigDecimal("5")) < 0) {
                productosBajos++;
            }
        }

        // 3. CONTAR CLIENTES (Mantener igual)
        long totalClientes = clienteRepo.count();

        // 4. CALCULAR RANKING (Mantener igual)
        java.util.Map<String, BigDecimal> mapaConteo = new java.util.HashMap<>();
        for (pe.idat.model.DetalleCotizacion det : detalleRepo.findAll()) {
            if (det.getCantidad() != null && det.getProducto() != null) {
                String prodNombre = det.getProducto().getNombre();
                mapaConteo.put(prodNombre, mapaConteo.getOrDefault(prodNombre, BigDecimal.ZERO).add(det.getCantidad()));
            }
        }

        java.util.List<pe.idat.dto.TopProductoDTO> ranking = mapaConteo.entrySet().stream()
            .sorted((k1, k2) -> k2.getValue().compareTo(k1.getValue())) 
            .limit(5)
            .map(e -> new pe.idat.dto.TopProductoDTO(e.getKey(), e.getValue()))
            .collect(java.util.stream.Collectors.toList());

        // 5. ENVIAR EL PAQUETE CORRECTO (ORDEN CORREGIDO)
        // El constructor pide: (Pendiente, Real, Stock, Clientes, Ranking)
        pe.idat.dto.ResumenDTO resumen = new pe.idat.dto.ResumenDTO(
            dineroPendiente,  // <--- 1. PRIMERO va lo Pendiente (Amarillo)
            sumaVentas,       // <--- 2. SEGUNDO va lo Real (Azul)
            productosBajos, 
            totalClientes, 
            ranking
        );

        return new ResponseEntity<>(resumen, HttpStatus.OK);
    }
}