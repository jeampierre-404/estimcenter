package pe.idat.controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.idat.model.DetalleCotizacion;
import pe.idat.service.DetalleCotizacionService;

@RestController
@RequestMapping("/detallecotizacion")
@CrossOrigin(origins = "*")
public class DetalleCotizacionRestController {
	
	@Autowired
	private DetalleCotizacionService detalleService;
	
	@GetMapping("/listar")
	public ResponseEntity<?> listar(){
		Collection<DetalleCotizacion> items = detalleService.findAll();
		return new ResponseEntity(items, HttpStatus.OK);
	}
	
	@GetMapping("/buscar/{idDetalle}")
	public ResponseEntity<?> buscar(@PathVariable Integer idDetalle){
		DetalleCotizacion detalle = detalleService.findById(idDetalle);
		if (detalle != null) {
			return new ResponseEntity<>(detalle, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/agregar")
	public ResponseEntity<?> agregar(@RequestBody DetalleCotizacion detalle){
		// Enviar cotizacion:{idCotizacion:X} y producto:{idProducto:Y}
		detalleService.insert(detalle);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@PutMapping("/editar/{idDetalle}")
	public ResponseEntity<?> editar(@PathVariable Integer idDetalle, @RequestBody DetalleCotizacion newDetalle){
		DetalleCotizacion det = detalleService.findById(idDetalle);
		if (det != null) {
			det.setCantidad(newDetalle.getCantidad());
			det.setPrecioHistorico(newDetalle.getPrecioHistorico());
			det.setImporte(newDetalle.getImporte());
			det.setProducto(newDetalle.getProducto());
			detalleService.update(det);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/borrar/{idDetalle}")
	public ResponseEntity<?> borrar(@PathVariable Integer idDetalle) {
		DetalleCotizacion det = detalleService.findById(idDetalle);
		if (det != null) {
			detalleService.delete(idDetalle);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	// Agrega este bloque al final, antes de la última llave }
	@GetMapping("/por-cotizacion/{idCotizacion}")
	public ResponseEntity<?> listarPorCotizacion(@PathVariable Integer idCotizacion){
	    Collection<DetalleCotizacion> items = detalleService.listarPorCotizacion(idCotizacion);
	    return new ResponseEntity<>(items, HttpStatus.OK);
	}
}