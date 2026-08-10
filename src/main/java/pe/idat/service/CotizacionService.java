package pe.idat.service;

import java.util.Collection;

import pe.idat.dto.CotizacionDTO;
import pe.idat.model.Cotizacion;

public interface CotizacionService {
	
	public abstract void insert(Cotizacion cotizacion);
	public abstract void update(Cotizacion cotizacion);
	public abstract void delete(Integer idCotizacion);
	public abstract Cotizacion findById(Integer idCotizacion);
	public abstract Collection<Cotizacion> findAll();
	public abstract void guardarCotizacionCompleta(CotizacionDTO dto);
}