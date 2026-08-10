package pe.idat.model;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name="roles")
public class Rol implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idRol;
	
	@Column(unique=true, nullable=false)
	private String nombre;
	
	@Column
	private String descripcion;
	
	public Rol() {}
	
	public Rol(Rol rol) {
		this(rol.getIdRol(), rol.getNombre(), rol.getDescripcion());
	}
	
	public Rol(Integer idRol, String nombre, String descripcion) {
		this.idRol = idRol;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	
	// No hay fecha en la tabla roles, pero mantenemos el método vacío si se requiere por estándar
	@PrePersist
	public void prePersist() {}

	public Integer getIdRol() {
		return idRol;
	}

	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}