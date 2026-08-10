package pe.idat.model;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name="categorias")
public class Categoria implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_categoria")
	private Integer idCategoria;

	@Column(nullable = false, length = 100)
	private String nombre;

	@Column(length = 255)
	private String descripcion;

	@Column(columnDefinition = "TINYINT(1) DEFAULT 1")
	private Boolean estado;

	public Categoria() {}
	
	public Categoria(Categoria categoria) {
		this(categoria.getIdCategoria(), categoria.getNombre(), categoria.getDescripcion(), categoria.getEstado());
	}

	public Categoria(Integer idCategoria, String nombre, String descripcion, Boolean estado) {
		this.idCategoria = idCategoria;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.estado = estado;
	}

	@PrePersist
	public void prePersist() {
		if(this.estado == null) {
			this.estado = true;
		}
	}

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
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

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
}