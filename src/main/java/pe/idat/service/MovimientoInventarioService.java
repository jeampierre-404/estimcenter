package pe.idat.service;

import java.util.Collection;
import pe.idat.model.MovimientoInventario;

public interface MovimientoInventarioService {
	
	public abstract void insert(MovimientoInventario movimiento);
	public abstract void update(MovimientoInventario movimiento);
	public abstract void delete(Long idMovimiento);
	public abstract MovimientoInventario findById(Long idMovimiento);
	public abstract Collection<MovimientoInventario> findAll();
}