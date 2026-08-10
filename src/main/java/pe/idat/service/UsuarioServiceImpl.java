package pe.idat.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.idat.model.Usuario;
import pe.idat.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	@Autowired
	private UsuarioRepository repository;
	
	@Override
	@Transactional
	public void insert(Usuario usuario) {
		repository.save(usuario);
	}
	
	@Override
	@Transactional
	public void update(Usuario usuario) {
		repository.save(usuario);
	}
	
	@Override
	@Transactional
	public void delete(Integer idUsuario) {
		repository.deleteById(idUsuario);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Usuario findById(Integer idUsuario) {
		return repository.findById(idUsuario).orElse(null);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Collection<Usuario> findAll() {
		return (Collection<Usuario>)repository.findAll();
	}
	
	@Override
	@Transactional(readOnly=true)
	public Usuario findByEmail(String email) {
	    return repository.findByEmail(email);
	}
}