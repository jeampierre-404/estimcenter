package pe.idat.service;

import java.util.Collection;
import pe.idat.model.Venta;

public interface VentaService {
	
	public abstract void insert(Venta venta);
	public abstract void update(Venta venta);
	public abstract void delete(Integer idVenta);
	public abstract Venta findById(Integer idVenta);
	public abstract Collection<Venta> findAll();
}