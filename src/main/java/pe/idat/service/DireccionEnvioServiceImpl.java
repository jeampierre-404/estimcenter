package pe.idat.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.idat.model.DireccionEnvio;
import pe.idat.repository.DireccionEnvioRepository;

@Service
public class DireccionEnvioServiceImpl implements DireccionEnvioService {
	
	@Autowired
	private DireccionEnvioRepository repository;
	
	@Override
	@Transactional
	public void insert(DireccionEnvio direccionEnvio) {
		repository.save(direccionEnvio);
	}
	
	@Override
	@Transactional
	public void update(DireccionEnvio direccionEnvio) {
		repository.save(direccionEnvio);
	}
	
	@Override
	@Transactional
	public void delete(Integer idDireccion) {
		repository.deleteById(idDireccion);
	}
	
	@Override
	@Transactional(readOnly=true)
	public DireccionEnvio findById(Integer idDireccion) {
		return repository.findById(idDireccion).orElse(null);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Collection<DireccionEnvio> findAll() {
		return (Collection<DireccionEnvio>)repository.findAll();
	}
}