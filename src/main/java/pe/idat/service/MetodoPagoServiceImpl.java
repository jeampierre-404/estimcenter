package pe.idat.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.idat.model.MetodoPago;
import pe.idat.repository.MetodoPagoRepository;

@Service
public class MetodoPagoServiceImpl implements MetodoPagoService {
	
	@Autowired
	private MetodoPagoRepository repository;
	
	@Override
	@Transactional
	public void insert(MetodoPago metodoPago) {
		repository.save(metodoPago);
	}
	
	@Override
	@Transactional
	public void update(MetodoPago metodoPago) {
		repository.save(metodoPago);
	}
	
	@Override
	@Transactional
	public void delete(Integer idMetodo) {
		repository.deleteById(idMetodo);
	}
	
	@Override
	@Transactional(readOnly=true)
	public MetodoPago findById(Integer idMetodo) {
		return repository.findById(idMetodo).orElse(null);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Collection<MetodoPago> findAll() {
		return (Collection<MetodoPago>)repository.findAll();
	}
}