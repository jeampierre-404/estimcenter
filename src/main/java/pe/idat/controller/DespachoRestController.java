package pe.idat.controller;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.idat.model.Despacho;
import pe.idat.model.Despacho.EstadoDespacho;
import pe.idat.service.DespachoService;

@RestController
@RequestMapping("/despacho")
@CrossOrigin(origins = "*")
public class DespachoRestController {
	
	@Autowired
	private DespachoService despachoService;
	
	@GetMapping("/listar")
	public ResponseEntity<?> listar(){
		Collection<Despacho> items = despachoService.findAll();
		return new ResponseEntity<>(items, HttpStatus.OK);
	}
	
	@GetMapping("/buscar/{idDespacho}")
	public ResponseEntity<?> buscar(@PathVariable Integer idDespacho){
		Despacho despacho = despachoService.findById(idDespacho);
		if (despacho != null) {
			return new ResponseEntity<>(despacho, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/agregar")
	public ResponseEntity<?> agregar(@RequestBody Despacho despacho){
		despachoService.insert(despacho);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/editar/{idDespacho}")
	public ResponseEntity<?> editar(@PathVariable Integer idDespacho, @RequestBody Despacho newDespacho){
		Despacho despacho = despachoService.findById(idDespacho);
		if (despacho != null) {
			despacho.setFechaProgramada(newDespacho.getFechaProgramada());
			despacho.setFechaEntrega(newDespacho.getFechaEntrega());
			despacho.setEstado(newDespacho.getEstado());
			despacho.setVenta(newDespacho.getVenta());
			despacho.setDireccionEnvio(newDespacho.getDireccionEnvio());
			despachoService.update(despacho);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/borrar/{idDespacho}")
	public ResponseEntity<?> borrar(@PathVariable Integer idDespacho) {
		Despacho despacho = despachoService.findById(idDespacho);
		if (despacho != null) {
			despachoService.delete(idDespacho);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/actualizar-estado/{idDespacho}")
	public ResponseEntity<?> actualizarEstado(@PathVariable Integer idDespacho, @RequestBody Map<String, String> request) {
		Despacho despacho = despachoService.findById(idDespacho);
		if (despacho != null) {
			String nuevoEstado = request.get("estado");
			
			if ("EN_RUTA".equals(nuevoEstado)) {
				despacho.setEstado(EstadoDespacho.EN_RUTA);
				despacho.setFechaProgramada(new Date()); 
			} else if ("ENTREGADO".equals(nuevoEstado)) {
				despacho.setEstado(EstadoDespacho.ENTREGADO);
				despacho.setFechaEntrega(new Date()); 
			} else {
				despacho.setEstado(EstadoDespacho.PENDIENTE);
			}
			
			despachoService.update(despacho);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}