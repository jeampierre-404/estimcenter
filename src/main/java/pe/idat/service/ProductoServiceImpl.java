package pe.idat.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.idat.model.Producto;
import pe.idat.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	private ProductoRepository repository;

	@Override
	@Transactional
	public void insert(Producto producto) {
		repository.save(producto);
	}

	@Override
	@Transactional
	public void update(Producto producto) {
		repository.save(producto);
	}

	@Override
	@Transactional
	public void delete(Integer idProducto) {
		repository.deleteById(idProducto);
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findById(Integer idProducto) {
		return repository.findById(idProducto).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Producto> findAll() {
		return (Collection<Producto>) repository.findAll();
	}
}