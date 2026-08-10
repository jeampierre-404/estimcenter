package pe.idat.controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.idat.model.DireccionEnvio;
import pe.idat.service.DireccionEnvioService;

@RestController
@RequestMapping("/direccionenvio")
public class DireccionEnvioRestController {
	
	@Autowired
	private DireccionEnvioService direccionEnvioService;
	
	@GetMapping("/listar")
	public ResponseEntity<?> listar(){
		Collection<DireccionEnvio> items = direccionEnvioService.findAll();
		return new ResponseEntity(items, HttpStatus.OK);
	}
	
	@GetMapping("/buscar/{idDireccion}")
	public ResponseEntity<?> buscar(@PathVariable Integer idDireccion){
		DireccionEnvio direccion = direccionEnvioService.findById(idDireccion);
		if (direccion != null) {
			return new ResponseEntity<>(direccion, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/agregar")
	public ResponseEntity<?> agregar(@RequestBody DireccionEnvio direccionEnvio){
		// Nota: El JSON debe incluir el cliente: "cliente": {"idCliente": 1}
		direccionEnvioService.insert(direccionEnvio);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@PutMapping("/editar/{idDireccion}")
	public ResponseEntity<?> editar(@PathVariable Integer idDireccion, @RequestBody DireccionEnvio newDireccion){
		DireccionEnvio direccion = direccionEnvioService.findById(idDireccion);
		if (direccion != null) {
			direccion.setDireccion(newDireccion.getDireccion());
			direccion.setReferencia(newDireccion.getReferencia());
			direccion.setCiudad(newDireccion.getCiudad());
			direccion.setCliente(newDireccion.getCliente()); // Actualiza FK cliente si es necesario
			direccionEnvioService.update(direccion);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/borrar/{idDireccion}")
	public ResponseEntity<?> borrar(@PathVariable Integer idDireccion) {
		DireccionEnvio direccion = direccionEnvioService.findById(idDireccion);
		if (direccion != null) {
			direccionEnvioService.delete(idDireccion);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
}