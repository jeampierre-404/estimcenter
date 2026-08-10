package pe.idat.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.idat.model.Categoria;
import pe.idat.repository.CategoriaRepository;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	@Override
	@Transactional
	public void insert(Categoria categoria) {
		repository.save(categoria);
	}

	@Override
	@Transactional
	public void update(Categoria categoria) {
		repository.save(categoria);
	}

	@Override
	@Transactional
	public void delete(Integer idCategoria) {
		repository.deleteById(idCategoria);
	}

	@Override
	@Transactional(readOnly = true)
	public Categoria findById(Integer idCategoria) {
		return repository.findById(idCategoria).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Categoria> findAll() {
		return (Collection<Categoria>) repository.findAll();
	}
}