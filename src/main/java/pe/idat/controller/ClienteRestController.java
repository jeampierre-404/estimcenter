package pe.idat.controller;

import java.util.Collection;
import java.util.Map; // 🔥 IMPORTANTE: Necesario para el login manual
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.idat.model.Cliente;
import pe.idat.service.ClienteService;

@RestController
@RequestMapping("/cliente")
@CrossOrigin(origins = "http://localhost:4200")
public class ClienteRestController {
	
	@Autowired
	private ClienteService clienteService;
	
	// ==========================================
	// 📦 RUTAS CRUD ORIGINALES (MANTENIDAS)
	// ==========================================

	@GetMapping("/listar")
	public ResponseEntity<?> listar(){
		Collection<Cliente> items = clienteService.findAll();
		return new ResponseEntity<>(items, HttpStatus.OK);
	}
	
	@GetMapping("/buscar/{idCliente}")
	public ResponseEntity<?> buscar(@PathVariable Integer idCliente){
		Cliente cliente = clienteService.findById(idCliente);
		if (cliente != null) {
			return new ResponseEntity<>(cliente, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/agregar")
	public ResponseEntity<?> agregar(@RequestBody Cliente cliente){
		cliente.setIdCliente(null);
		
		// 🔥 SOLUCIÓN AL ERROR 500: Le asignamos su mismo número de documento como contraseña por defecto
		if (cliente.getPassword() == null || cliente.getPassword().isEmpty()) {
			cliente.setPassword(cliente.getNumDoc()); 
		}
		
		// Si se envían direcciones desde un panel admin, las vinculamos
		if(cliente.getDirecciones() != null && !cliente.getDirecciones().isEmpty()) {
			cliente.getDirecciones().forEach(dir -> dir.setCliente(cliente));
		}
		
		clienteService.insert(cliente);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@PutMapping("/editar/{idCliente}")
	public ResponseEntity<?> editar(@PathVariable Integer idCliente, @RequestBody Cliente newCliente){
		Cliente cliente = clienteService.findById(idCliente);
		if (cliente != null) {
			cliente.setTipoDoc(newCliente.getTipoDoc());
			cliente.setNumDoc(newCliente.getNumDoc());
			cliente.setNombreCompleto(newCliente.getNombreCompleto());
			cliente.setEmail(newCliente.getEmail());
			cliente.setTelefono(newCliente.getTelefono());
			
			// 🔥 SEGURO: Protegemos la contraseña
			if (newCliente.getPassword() != null && !newCliente.getPassword().isEmpty()) {
				cliente.setPassword(newCliente.getPassword());
			}
			
			// 🔥 SEGURO: Protegemos el rostro guardado
			if (newCliente.getDescriptorFacial() != null) {
				cliente.setDescriptorFacial(newCliente.getDescriptorFacial());
			}
			
			clienteService.update(cliente);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/borrar/{idCliente}")
	public ResponseEntity<?> borrar(@PathVariable Integer idCliente) {
		Cliente cliente = clienteService.findById(idCliente);
		if (cliente != null) {
			clienteService.delete(idCliente);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	// ==========================================
	// 🤖 NUEVAS RUTAS: LOGIN IA (PLAN A) Y MANUAL (PLAN B)
	// ==========================================

	// 1. REGISTRAR CLIENTE CON IA Y CONTRASEÑA
	@PostMapping("/registrar-ia")
	public ResponseEntity<?> registrarConIA(@RequestBody Cliente cliente) {
		// Verificamos que el DNI no exista para evitar duplicados
		Cliente existe = clienteService.findByNumDoc(cliente.getNumDoc());
		if (existe != null) {
			return new ResponseEntity<>("El documento ya está registrado.", HttpStatus.BAD_REQUEST);
		}
		
		// Asociamos la dirección inicial al cliente para evitar el error de llave foránea (FK)
		if(cliente.getDirecciones() != null && !cliente.getDirecciones().isEmpty()) {
			cliente.getDirecciones().forEach(dir -> dir.setCliente(cliente));
		}

		// Guardamos en la BD (incluye rostro y contraseña porque vienen en el JSON de Angular)
		clienteService.insert(cliente);
		return new ResponseEntity<>(cliente, HttpStatus.CREATED);
	}

	// 2. PLAN A: Traer SOLO el rostro de un DNI específico (Búsqueda súper rápida)
	@GetMapping("/buscar-rostro/{numDoc}")
	public ResponseEntity<?> buscarRostroPorDni(@PathVariable String numDoc) {
		Cliente cliente = clienteService.findByNumDoc(numDoc);
		if (cliente != null && cliente.getDescriptorFacial() != null) {
			return new ResponseEntity<>(cliente, HttpStatus.OK);
		}
		return new ResponseEntity<>("Cliente o rostro no encontrado", HttpStatus.NOT_FOUND);
	}

	// 3. PLAN B: Login Manual (DNI + Contraseña)
	@PostMapping("/login-manual")
	public ResponseEntity<?> loginManual(@RequestBody Map<String, String> credenciales) {
		String numDoc = credenciales.get("numDoc");
		String password = credenciales.get("password");
		
		Cliente cliente = clienteService.findByNumDoc(numDoc);
		
		// Validamos que el cliente exista y la contraseña coincida
		if (cliente != null && cliente.getPassword() != null && cliente.getPassword().equals(password)) {
			return new ResponseEntity<>(cliente, HttpStatus.OK);
		}
		return new ResponseEntity<>("DNI o contraseña incorrectos", HttpStatus.UNAUTHORIZED);
	}
	
	// ==========================================
	// 🛡️ NUEVO: VERIFICACIÓN DE DUPLICADOS PARA ANGULAR
	// ==========================================
	@GetMapping("/verificar-documento/{numDoc}")
	public ResponseEntity<Boolean> verificarDocumento(@PathVariable String numDoc) {
		// Buscamos si existe un cliente con ese documento usando el método que ya tienes
		Cliente existe = clienteService.findByNumDoc(numDoc);
			
		// Si 'existe' no es nulo, significa que el DNI ya está en la BD (retorna true)
		boolean estaDuplicado = (existe != null);
			
		return new ResponseEntity<>(estaDuplicado, HttpStatus.OK);
	}
}