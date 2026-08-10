package pe.idat.controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.idat.model.Venta;
import pe.idat.service.VentaService;

@RestController
@RequestMapping("/venta")
@CrossOrigin(origins = "http://localhost:4200")
public class VentaRestController {
	
	@Autowired
	private VentaService ventaService;
	
	@GetMapping("/listar")
	public ResponseEntity<?> listar(){
		Collection<Venta> items = ventaService.findAll();
		return new ResponseEntity<>(items, HttpStatus.OK);
	}
	
	@GetMapping("/buscar/{idVenta}")
	public ResponseEntity<?> buscar(@PathVariable Integer idVenta){
		Venta venta = ventaService.findById(idVenta);
		if (venta != null) {
			return new ResponseEntity<>(venta, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/por-cotizacion/{id}")
	public ResponseEntity<?> buscarPorCotizacion(@PathVariable Integer id) {
		for (Venta v : ventaService.findAll()) {
			if (v.getCotizacion().getIdCotizacion().equals(id)) {
				return new ResponseEntity<>(v, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/agregar")
	public ResponseEntity<?> agregar(@RequestBody Venta venta){
		ventaService.insert(venta);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@PutMapping("/editar/{idVenta}")
	public ResponseEntity<?> editar(@PathVariable Integer idVenta, @RequestBody Venta newVenta){
		Venta venta = ventaService.findById(idVenta);
		if (venta != null) {
			venta.setTipoComprobante(newVenta.getTipoComprobante());
			venta.setSerieCorrelativo(newVenta.getSerieCorrelativo());
			venta.setCotizacion(newVenta.getCotizacion());
			venta.setMetodoPago(newVenta.getMetodoPago());
			ventaService.update(venta);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/borrar/{idVenta}")
	public ResponseEntity<?> borrar(@PathVariable Integer idVenta) {
		Venta venta = ventaService.findById(idVenta);
		if (venta != null) {
			ventaService.delete(idVenta);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
}