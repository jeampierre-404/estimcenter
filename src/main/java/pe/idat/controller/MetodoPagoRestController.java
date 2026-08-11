package pe.idat.controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.idat.model.MetodoPago;
import pe.idat.service.MetodoPagoService;

@RestController
@RequestMapping("/metodopago")
@CrossOrigin(origins = "*")
public class MetodoPagoRestController {
	
	@Autowired
	private MetodoPagoService metodoPagoService;
	
	@GetMapping("/listar")
	public ResponseEntity<?> listar(){
		Collection<MetodoPago> items = metodoPagoService.findAll();
		return new ResponseEntity<>(items, HttpStatus.OK);
	}
	
	@GetMapping("/buscar/{idMetodo}")
	public ResponseEntity<?> buscar(@PathVariable Integer idMetodo){
		MetodoPago metodo = metodoPagoService.findById(idMetodo);
		if (metodo != null) {
			return new ResponseEntity<>(metodo, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/agregar")
	public ResponseEntity<?> agregar(@RequestBody MetodoPago metodoPago){
		metodoPagoService.insert(metodoPago);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@PutMapping("/editar/{idMetodo}")
	public ResponseEntity<?> editar(@PathVariable Integer idMetodo, @RequestBody MetodoPago newMetodo){
		MetodoPago metodo = metodoPagoService.findById(idMetodo);
		if (metodo != null) {
			metodo.setNombre(newMetodo.getNombre());
			metodoPagoService.update(metodo);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/borrar/{idMetodo}")
	public ResponseEntity<?> borrar(@PathVariable Integer idMetodo) {
		MetodoPago metodo = metodoPagoService.findById(idMetodo);
		if (metodo != null) {
			metodoPagoService.delete(idMetodo);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
}