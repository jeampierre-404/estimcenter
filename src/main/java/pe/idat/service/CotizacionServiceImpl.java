package pe.idat.service;

import java.util.Collection;
import java.util.Date; // Necesario para la fecha

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.idat.dto.CotizacionDTO; // Importamos el DTO
import pe.idat.model.Cotizacion;
import pe.idat.model.Cotizacion.EstadoCotizacion; // Importamos el Enum
import pe.idat.model.DetalleCotizacion;
import pe.idat.repository.CotizacionRepository;
import pe.idat.repository.DetalleCotizacionRepository; // Importamos repositorio de detalles

@Service
public class CotizacionServiceImpl implements CotizacionService {
	
	@Autowired
	private CotizacionRepository repository;
	
	// --- AGREGAMOS ESTE REPOSITORIO (NECESARIO PARA GUARDAR LOS PRODUCTOS) ---
	@Autowired
	private DetalleCotizacionRepository detalleRepo;
	
	@Override
	@Transactional
	public void insert(Cotizacion cotizacion) {
		repository.save(cotizacion);
	}
	
	@Override
	@Transactional
	public void update(Cotizacion cotizacion) {
		repository.save(cotizacion);
	}
	
	@Override
	@Transactional
	public void delete(Integer idCotizacion) {
		repository.deleteById(idCotizacion);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Cotizacion findById(Integer idCotizacion) {
		return repository.findById(idCotizacion).orElse(null);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Collection<Cotizacion> findAll() {
		return (Collection<Cotizacion>)repository.findAll();
	}

	// --- ESTE ES EL MÉTODO QUE TE FALTABA Y DABA ERROR ---
	@Override
	@Transactional
	public void guardarCotizacionCompleta(CotizacionDTO dto) {
		
		// 1. Preparamos la Cabecera (Cotización)
		Cotizacion cot = dto.getCotizacion();
		cot.setFechaEmision(new Date()); // Fecha de hoy
		cot.setEstado(EstadoCotizacion.PENDIENTE); // Nace como pendiente
		
		// 2. Guardamos la Cabecera primero (para generar el ID)
		repository.save(cot);
		
		// 3. Guardamos los Detalles (Productos)
		for(DetalleCotizacion det : dto.getDetalles()) {
			det.setCotizacion(cot); // Enlazamos el producto con la cotización creada
			detalleRepo.save(det);  // Guardamos cada item
		}
	}
}