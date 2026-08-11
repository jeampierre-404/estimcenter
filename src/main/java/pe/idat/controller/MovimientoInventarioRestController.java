package pe.idat.controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.idat.model.MovimientoInventario;
import pe.idat.service.MovimientoInventarioService;

@RestController
@RequestMapping("/movimiento")
@CrossOrigin(origins = "*") // 🔥 Vital para conectar con Angular
public class MovimientoInventarioRestController {
	
	@Autowired
	private MovimientoInventarioService movimientoService;
	
	@GetMapping("/listar")
	public ResponseEntity<?> listar(){
		Collection<MovimientoInventario> items = movimientoService.findAll();
		return new ResponseEntity<>(items, HttpStatus.OK);
	}
	
	@GetMapping("/buscar/{idMovimiento}")
	public ResponseEntity<?> buscar(@PathVariable Long idMovimiento){
		MovimientoInventario mov = movimientoService.findById(idMovimiento);
		if (mov != null) {
			return new ResponseEntity<>(mov, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/agregar")
	public ResponseEntity<?> agregar(@RequestBody MovimientoInventario movimiento){
		// 🔥 JSON debe incluir: tipo, cantidad, motivo, producto:{idProducto:X}, usuario:{idUsuario:Y}
		movimientoService.insert(movimiento);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/editar/{idMovimiento}")
	public ResponseEntity<?> editar(@PathVariable Long idMovimiento, @RequestBody MovimientoInventario newMov){
		MovimientoInventario mov = movimientoService.findById(idMovimiento);
		if (mov != null) {
			mov.setTipo(newMov.getTipo());
			mov.setCantidad(newMov.getCantidad());
			mov.setMotivo(newMov.getMotivo());
			mov.setProducto(newMov.getProducto()); // 🔥 Corregido: Ahora es getProducto()
			mov.setUsuario(newMov.getUsuario());
			movimientoService.update(mov);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/borrar/{idMovimiento}")
	public ResponseEntity<?> borrar(@PathVariable Long idMovimiento) {
		MovimientoInventario mov = movimientoService.findById(idMovimiento);
		if (mov != null) {
			movimientoService.delete(idMovimiento);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}