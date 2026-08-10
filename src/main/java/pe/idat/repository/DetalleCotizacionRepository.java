package pe.idat.repository;

import org.springframework.data.repository.CrudRepository;
import pe.idat.model.DetalleCotizacion;

public interface DetalleCotizacionRepository extends CrudRepository<DetalleCotizacion, Integer>{
	Iterable<DetalleCotizacion> findByCotizacionIdCotizacion(Integer idCotizacion);
	
}