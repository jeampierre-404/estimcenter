package pe.idat.repository;

import org.springframework.data.repository.CrudRepository;
import pe.idat.model.Venta;

public interface VentaRepository extends CrudRepository<Venta, Integer>{
	
}