package pe.idat.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.idat.model.DetalleCotizacion;
import pe.idat.repository.DetalleCotizacionRepository;

@Service
public class DetalleCotizacionServiceImpl implements DetalleCotizacionService {
	
	@Autowired
	private DetalleCotizacionRepository repository;
	
	@Override
	@Transactional
	public void insert(DetalleCotizacion detalle) {
		repository.save(detalle);
	}
	
	@Override
	@Transactional
	public void update(DetalleCotizacion detalle) {
		repository.save(detalle);
	}
	
	@Override
	@Transactional
	public void delete(Integer idDetalle) {
		repository.deleteById(idDetalle);
	}
	
	@Override
	@Transactional(readOnly=true)
	public DetalleCotizacion findById(Integer idDetalle) {
		return repository.findById(idDetalle).orElse(null);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Collection<DetalleCotizacion> findAll() {
		return (Collection<DetalleCotizacion>)repository.findAll();
	}
	
	@Override
    @Transactional(readOnly=true)
    public Collection<DetalleCotizacion> listarPorCotizacion(Integer idCotizacion) {
        return (Collection<DetalleCotizacion>) repository.findByCotizacionIdCotizacion(idCotizacion);
    }
	
}