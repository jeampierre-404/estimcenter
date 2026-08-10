package pe.idat.repository;

import org.springframework.data.repository.CrudRepository;
import pe.idat.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Integer>{
	
	Cliente findByNumDoc(String numDoc);
	
}