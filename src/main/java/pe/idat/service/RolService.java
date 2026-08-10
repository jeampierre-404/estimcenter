package pe.idat.service;

import java.util.Collection;
import pe.idat.model.Rol;

public interface RolService {
	
	public abstract void insert(Rol rol);
	public abstract void update(Rol rol);
	public abstract void delete(Integer idRol);
	public abstract Rol findById(Integer idRol);
	public abstract Collection<Rol> findAll();
}