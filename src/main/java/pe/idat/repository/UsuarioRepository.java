package pe.idat.repository;

import org.springframework.data.repository.CrudRepository;
import pe.idat.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
    Usuario findByEmail(String email); // 🔥 Spring Boot hace la magia aquí
}