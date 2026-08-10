package pe.idat.service;

import java.util.Collection;
import pe.idat.model.Cliente;

public interface ClienteService {
	
	public abstract void insert(Cliente cliente);
	public abstract void update(Cliente cliente);
	public abstract void delete(Integer idCliente);
	public abstract Cliente findById(Integer idCliente);
	public abstract Collection<Cliente> findAll();
	public abstract Cliente findByNumDoc(String numDoc);
}