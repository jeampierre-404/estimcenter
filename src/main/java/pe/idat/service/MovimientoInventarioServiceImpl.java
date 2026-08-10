package pe.idat.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.idat.model.MovimientoInventario;
import pe.idat.repository.MovimientoInventarioRepository;

@Service
public class MovimientoInventarioServiceImpl implements MovimientoInventarioService {
	
	@Autowired
	private MovimientoInventarioRepository repository;
	
	@Override
	@Transactional
	public void insert(MovimientoInventario movimiento) {
		// Aquí idealmente iría la lógica para actualizar el stock en Producto y Lote
		repository.save(movimiento);
	}
	
	@Override
	@Transactional
	public void update(MovimientoInventario movimiento) {
		repository.save(movimiento);
	}
	
	@Override
	@Transactional
	public void delete(Long idMovimiento) {
		repository.deleteById(idMovimiento);
	}
	
	@Override
	@Transactional(readOnly=true)
	public MovimientoInventario findById(Long idMovimiento) {
		return repository.findById(idMovimiento).orElse(null);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Collection<MovimientoInventario> findAll() {
		return (Collection<MovimientoInventario>)repository.findAll();
	}
}