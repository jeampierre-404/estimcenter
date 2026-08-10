package pe.idat.model;

import java.io.Serializable;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="direcciones_envio")
public class DireccionEnvio implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_direccion")
	private Integer idDireccion;
	
	@Column(nullable=false)
	private String direccion;
	
	@Column
	private String referencia;
	
	@Column(length=100)
	private String ciudad;
	
	@ManyToOne
	@JoinColumn(name="id_cliente", nullable=false)
	@JsonBackReference
	private Cliente cliente;
	
	public DireccionEnvio() {}
	
	public DireccionEnvio(DireccionEnvio direccionEnvio) {
		this(direccionEnvio.getIdDireccion(), direccionEnvio.getDireccion(), 
			 direccionEnvio.getReferencia(), direccionEnvio.getCiudad(), direccionEnvio.getCliente());
	}
	
	public DireccionEnvio(Integer idDireccion, String direccion, String referencia, 
			String ciudad, Cliente cliente) {
		this.idDireccion = idDireccion;
		this.direccion = direccion;
		this.referencia = referencia;
		this.ciudad = ciudad;
		this.cliente = cliente;
	}
	
	@PrePersist
	public void prePersist() {}

	public Integer getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(Integer idDireccion) {
		this.idDireccion = idDireccion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}