package pe.idat.repository;

import org.springframework.data.repository.CrudRepository;
import pe.idat.model.Producto;

public interface ProductoRepository extends CrudRepository<Producto, Integer> {
}