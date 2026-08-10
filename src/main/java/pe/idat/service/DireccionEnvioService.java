package pe.idat.service;

import java.util.Collection;
import pe.idat.model.DireccionEnvio;

public interface DireccionEnvioService {
	
	public abstract void insert(DireccionEnvio direccionEnvio);
	public abstract void update(DireccionEnvio direccionEnvio);
	public abstract void delete(Integer idDireccion);
	public abstract DireccionEnvio findById(Integer idDireccion);
	public abstract Collection<DireccionEnvio> findAll();
}