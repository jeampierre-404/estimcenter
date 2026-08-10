package pe.idat.controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.idat.model.Rol;
import pe.idat.service.RolService;

@RestController
@RequestMapping("/rol")
@CrossOrigin(origins = "http://localhost:4200")
public class RolRestController {
	
	@Autowired
	private RolService rolService;
	
	@GetMapping("/listar")
	public ResponseEntity<?> listar(){
		Collection<Rol> itemsRol = rolService.findAll();
		return new ResponseEntity<>(itemsRol, HttpStatus.OK);
	}
	
	@GetMapping("/buscar/{idRol}")
	public ResponseEntity<?> buscar(@PathVariable Integer idRol){
		Rol rol = rolService.findById(idRol);
		if (rol != null) {
			return new ResponseEntity<>(rol, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/agregar")
	public ResponseEntity<?> agregar(@RequestBody Rol rol){
		rolService.insert(rol);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@PutMapping("/editar/{idRol}")
	public ResponseEntity<?> editar(@PathVariable Integer idRol, @RequestBody Rol newRol){
		Rol rol = rolService.findById(idRol);
		if (rol != null) {
			rol.setNombre(newRol.getNombre());
			rol.setDescripcion(newRol.getDescripcion());
			rolService.update(rol);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/borrar/{idRol}")
	public ResponseEntity<?> borrar(@PathVariable Integer idRol) {
		Rol rol = rolService.findById(idRol);
		if (rol != null) {
			rolService.delete(idRol);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
}