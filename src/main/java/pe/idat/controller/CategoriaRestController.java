package pe.idat.controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.idat.model.Categoria;
import pe.idat.service.CategoriaService;

@RestController
@RequestMapping("/categoria")
@CrossOrigin(origins = "*")
public class CategoriaRestController {

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping("/listar")
	public ResponseEntity<?> listar() {
		Collection<Categoria> items = categoriaService.findAll();
		return new ResponseEntity(items, HttpStatus.OK);
	}

	@GetMapping("/buscar/{idCategoria}")
	public ResponseEntity<?> buscar(@PathVariable Integer idCategoria) {
		Categoria categoria = categoriaService.findById(idCategoria);
		if (categoria != null) {
			return new ResponseEntity<>(categoria, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/agregar")
	public ResponseEntity<?> agregar(@RequestBody Categoria categoria) {
		categoria.setIdCategoria(null);
		categoriaService.insert(categoria);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@PutMapping("/editar/{idCategoria}")
	public ResponseEntity<?> editar(@PathVariable Integer idCategoria, @RequestBody Categoria newCategoria) {
		Categoria categoria = categoriaService.findById(idCategoria);
		if (categoria != null) {
			categoria.setNombre(newCategoria.getNombre());
			categoria.setDescripcion(newCategoria.getDescripcion());
			categoria.setEstado(newCategoria.getEstado());
			categoriaService.update(categoria);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/borrar/{idCategoria}")
	public ResponseEntity<?> borrar(@PathVariable Integer idCategoria) {
		Categoria categoria = categoriaService.findById(idCategoria);
		if (categoria != null) {
			categoriaService.delete(idCategoria);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
}