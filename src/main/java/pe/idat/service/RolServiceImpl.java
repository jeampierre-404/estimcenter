package pe.idat.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.idat.model.Rol;
import pe.idat.repository.RolRepository;

@Service
public class RolServiceImpl implements RolService{
	
	@Autowired
	private RolRepository repository;
	
	@Override
	@Transactional
	public void insert(Rol rol) {
		repository.save(rol);
	}
	
	@Override
	@Transactional
	public void update(Rol rol) {
		repository.save(rol);
	}
	
	@Override
	@Transactional
	public void delete(Integer idRol) {
		repository.deleteById(idRol);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Rol findById(Integer idRol) {
		return repository.findById(idRol).orElse(null);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Collection<Rol> findAll() {
		return (Collection<Rol>)repository.findAll();
	}
}