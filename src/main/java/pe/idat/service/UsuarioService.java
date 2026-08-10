package pe.idat.service;

import java.util.Collection;
import pe.idat.model.Usuario;

public interface UsuarioService {
	
	public abstract void insert(Usuario usuario);
	public abstract void update(Usuario usuario);
	public abstract void delete(Integer idUsuario);
	public abstract Usuario findById(Integer idUsuario);
	public abstract Collection<Usuario> findAll();
	public abstract Usuario findByEmail(String email);
}