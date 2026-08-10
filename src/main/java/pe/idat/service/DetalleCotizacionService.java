package pe.idat.service;

import java.util.Collection;
import pe.idat.model.DetalleCotizacion;

public interface DetalleCotizacionService {
	
	public abstract void insert(DetalleCotizacion detalle);
	public abstract void update(DetalleCotizacion detalle);
	public abstract void delete(Integer idDetalle);
	public abstract DetalleCotizacion findById(Integer idDetalle);
	public abstract Collection<DetalleCotizacion> findAll();
	public abstract Collection<DetalleCotizacion> listarPorCotizacion(Integer idCotizacion);
	
}