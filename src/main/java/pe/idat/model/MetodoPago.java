package pe.idat.model;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name="metodos_pago")
public class MetodoPago implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_metodo")
	private Integer idMetodo;
	
	@Column(nullable=false, length=50)
	private String nombre;
	
	public MetodoPago() {}
	
	public MetodoPago(MetodoPago metodo) {
		this(metodo.getIdMetodo(), metodo.getNombre());
	}
	
	public MetodoPago(Integer idMetodo, String nombre) {
		this.idMetodo = idMetodo;
		this.nombre = nombre;
	}
	
	@PrePersist
	public void prePersist() {}

	public Integer getIdMetodo() {
		return idMetodo;
	}

	public void setIdMetodo(Integer idMetodo) {
		this.idMetodo = idMetodo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}