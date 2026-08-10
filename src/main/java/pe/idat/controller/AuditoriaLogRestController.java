package pe.idat.controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.idat.model.AuditoriaLog;
import pe.idat.service.AuditoriaLogService;

@RestController
@RequestMapping("/auditoria")
@CrossOrigin(origins = "http://localhost:4200")
public class AuditoriaLogRestController {
	
	@Autowired
	private AuditoriaLogService auditoriaService;
	
	@GetMapping("/listar")
	public ResponseEntity<?> listar(){
		Collection<AuditoriaLog> items = auditoriaService.findAll();
		return new ResponseEntity(items, HttpStatus.OK);
	}
	
	@GetMapping("/buscar/{idLog}")
	public ResponseEntity<?> buscar(@PathVariable Long idLog){
		AuditoriaLog log = auditoriaService.findById(idLog);
		if (log != null) {
			return new ResponseEntity<>(log, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/agregar")
	public ResponseEntity<?> agregar(@RequestBody AuditoriaLog log){
		// Nota: Generalmente estos registros se crean automáticamente desde el backend,
		// pero se deja el endpoint si se requiere registro manual.
		// JSON debe incluir usuario:{idUsuario:X} si aplica.
		auditoriaService.insert(log);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	// La auditoría NO debería editarse ni borrarse por seguridad, 
	// pero incluyo los métodos para mantener el patrón CRUD completo.
	
	@PutMapping("/editar/{idLog}")
	public ResponseEntity<?> editar(@PathVariable Long idLog, @RequestBody AuditoriaLog newLog){
		AuditoriaLog log = auditoriaService.findById(idLog);
		if (log != null) {
			log.setAccion(newLog.getAccion());
			log.setTablaAfectada(newLog.getTablaAfectada());
			log.setDescripcion(newLog.getDescripcion());
			log.setIpOrigen(newLog.getIpOrigen());
			log.setUsuario(newLog.getUsuario());
			auditoriaService.update(log);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/borrar/{idLog}")
	public ResponseEntity<?> borrar(@PathVariable Long idLog) {
		AuditoriaLog log = auditoriaService.findById(idLog);
		if (log != null) {
			auditoriaService.delete(idLog);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
}
