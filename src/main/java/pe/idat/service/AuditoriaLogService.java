package pe.idat.service;

import java.util.Collection;
import pe.idat.model.AuditoriaLog;

public interface AuditoriaLogService {
	
	public abstract void insert(AuditoriaLog auditoriaLog);
	public abstract void update(AuditoriaLog auditoriaLog);
	public abstract void delete(Long idLog);
	public abstract AuditoriaLog findById(Long idLog);
	public abstract Collection<AuditoriaLog> findAll();
}