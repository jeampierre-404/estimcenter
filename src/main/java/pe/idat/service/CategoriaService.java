package pe.idat.service;

import java.util.Collection;
import pe.idat.model.Categoria;

public interface CategoriaService {
	public abstract void insert(Categoria categoria);
	public abstract void update(Categoria categoria);
	public abstract void delete(Integer idCategoria);
	public abstract Categoria findById(Integer idCategoria);
	public abstract Collection<Categoria> findAll();
}
