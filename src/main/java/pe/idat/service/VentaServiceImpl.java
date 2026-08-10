package pe.idat.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.idat.model.Venta;
import pe.idat.repository.VentaRepository;

@Service
public class VentaServiceImpl implements VentaService {
	
	@Autowired
	private VentaRepository repository;
	
	@Override
	@Transactional
	public void insert(Venta venta) {
		repository.save(venta);
	}
	
	@Override
	@Transactional
	public void update(Venta venta) {
		repository.save(venta);
	}
	
	@Override
	@Transactional
	public void delete(Integer idVenta) {
		repository.deleteById(idVenta);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Venta findById(Integer idVenta) {
		return repository.findById(idVenta).orElse(null);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Collection<Venta> findAll() {
		return (Collection<Venta>)repository.findAll();
	}
}