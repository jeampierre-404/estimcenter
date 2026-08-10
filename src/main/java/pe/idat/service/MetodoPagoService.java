package pe.idat.service;

import java.util.Collection;
import pe.idat.model.MetodoPago;

public interface MetodoPagoService {
	
	public abstract void insert(MetodoPago metodoPago);
	public abstract void update(MetodoPago metodoPago);
	public abstract void delete(Integer idMetodo);
	public abstract MetodoPago findById(Integer idMetodo);
	public abstract Collection<MetodoPago> findAll();
}