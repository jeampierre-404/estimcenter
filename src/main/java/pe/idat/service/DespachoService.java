package pe.idat.service;

import java.util.Collection;
import pe.idat.model.Despacho;

public interface DespachoService {
	
	public abstract void insert(Despacho despacho);
	public abstract void update(Despacho despacho);
	public abstract void delete(Integer idDespacho);
	public abstract Despacho findById(Integer idDespacho);
	public abstract Collection<Despacho> findAll();
}