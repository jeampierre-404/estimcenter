package pe.idat.repository;

import org.springframework.data.repository.CrudRepository;
import pe.idat.model.Categoria;

public interface CategoriaRepository extends CrudRepository<Categoria, Integer> {
}