package pe.idat.controller;

import java.util.Collection;
import java.util.Map; // 🔥 IMPORTANTE PARA EL LOGIN
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.idat.model.Usuario;
import org.springframework.dao.DataIntegrityViolationException;
import pe.idat.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "http://localhost:4200") // 🔥 EVITA EL ERROR DE CORS EN ANGULAR
public class UsuarioRestController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/listar")
	public ResponseEntity<?> listar(){
		Collection<Usuario> itemsUsuario = usuarioService.findAll();
		return new ResponseEntity<>(itemsUsuario, HttpStatus.OK);
	}
	
	@GetMapping("/buscar/{idUsuario}")
	public ResponseEntity<?> buscar(@PathVariable Integer idUsuario){
		Usuario usuario = usuarioService.findById(idUsuario);
		if (usuario != null) {
			return new ResponseEntity<>(usuario, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/agregar")
	public ResponseEntity<?> agregar(@RequestBody Usuario usuario){
		usuarioService.insert(usuario);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@PutMapping("/editar/{idUsuario}")
	public ResponseEntity<?> editar(@PathVariable Integer idUsuario, @RequestBody Usuario newUsuario){
		Usuario usuario = usuarioService.findById(idUsuario);
		if (usuario != null) {
			usuario.setNombre(newUsuario.getNombre());
			usuario.setApellido(newUsuario.getApellido());
			usuario.setEmail(newUsuario.getEmail());
			usuario.setPassword(newUsuario.getPassword());
			usuario.setEstado(newUsuario.getEstado());
			usuario.setRol(newUsuario.getRol()); 
			usuarioService.update(usuario);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/borrar/{idUsuario}")
	public ResponseEntity<?> borrar(@PathVariable Integer idUsuario) {
		Usuario usuario = usuarioService.findById(idUsuario);
		if (usuario != null) {
			try {
				// 1. INTENTO DE BORRADO FÍSICO
				// Si el usuario es nuevo y no tiene historial, se borrará por completo.
				usuarioService.delete(idUsuario);
				return new ResponseEntity<>("eliminado", HttpStatus.OK);
				
			} catch (DataIntegrityViolationException e) {
				// 2. SI HAY ERROR DE LLAVE FORÁNEA, APLICAMOS BAJA LÓGICA
				// Significa que ya tiene cotizaciones. Lo inactivamos para proteger la BD.
				usuario.setEstado(false);
				usuarioService.update(usuario); 
				return new ResponseEntity<>("inactivado", HttpStatus.OK);
			}
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	// ==========================================
	// 🔥 NUEVA RUTA PARA EL LOGIN DE EMPLEADOS
	// ==========================================
	@PostMapping("/login")
	public ResponseEntity<?> loginAdmin(@RequestBody Map<String, String> credenciales) {
		String email = credenciales.get("email");
		String password = credenciales.get("password");
		
		Usuario usuario = usuarioService.findByEmail(email);
		
		if (usuario != null) {
			if (!usuario.getEstado()) {
				return new ResponseEntity<>("Usuario inactivo. Contacte al administrador.", HttpStatus.FORBIDDEN);
			}
			if (usuario.getPassword().equals(password)) {
				return new ResponseEntity<>(usuario, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>("Correo o contraseña incorrectos", HttpStatus.UNAUTHORIZED);
	}
}