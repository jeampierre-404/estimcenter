package pe.idat.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.idat.model.AuditoriaLog;
import pe.idat.repository.AuditoriaLogRepository;

@Service
public class AuditoriaLogServiceImpl implements AuditoriaLogService {
	
	@Autowired
	private AuditoriaLogRepository repository;
	
	@Override
	@Transactional
	public void insert(AuditoriaLog auditoriaLog) {
		repository.save(auditoriaLog);
	}
	
	@Override
	@Transactional
	public void update(AuditoriaLog auditoriaLog) {
		repository.save(auditoriaLog);
	}
	
	@Override
	@Transactional
	public void delete(Long idLog) {
		repository.deleteById(idLog);
	}
	
	@Override
	@Transactional(readOnly=true)
	public AuditoriaLog findById(Long idLog) {
		return repository.findById(idLog).orElse(null);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Collection<AuditoriaLog> findAll() {
		return (Collection<AuditoriaLog>)repository.findAll();
	}
}