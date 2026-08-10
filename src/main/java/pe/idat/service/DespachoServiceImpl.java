package pe.idat.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.idat.model.Despacho;
import pe.idat.repository.DespachoRepository;

@Service
public class DespachoServiceImpl implements DespachoService {
	
	@Autowired
	private DespachoRepository repository;
	
	@Override
	@Transactional
	public void insert(Despacho despacho) {
		repository.save(despacho);
	}
	
	@Override
	@Transactional
	public void update(Despacho despacho) {
		repository.save(despacho);
	}
	
	@Override
	@Transactional
	public void delete(Integer idDespacho) {
		repository.deleteById(idDespacho);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Despacho findById(Integer idDespacho) {
		return repository.findById(idDespacho).orElse(null);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Collection<Despacho> findAll() {
		return (Collection<Despacho>)repository.findAll();
	}
}