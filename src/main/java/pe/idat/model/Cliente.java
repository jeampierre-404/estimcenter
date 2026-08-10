package pe.idat.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="clientes")
public class Cliente implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cliente")
	private Integer idCliente;
	
	@Column(name="tipo_doc", nullable=false)
	private String tipoDoc; // Se mapea como String para coincidir con el ENUM de BD
	
	@Column(name="num_doc", nullable=false, unique=true)
	private String numDoc;
	
	@Column(name="nombre_completo", nullable=false)
	private String nombreCompleto;
	
	@Column
	private String email;
	
	@Column
	private String telefono;

	// 🔥 NUEVO: Contraseña (Plan B del Login)
	@Column(nullable=false)
	private String password;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;
	
	// El campo para guardar el rostro de la IA (Texto gigante)
	@Column(name="descriptor_facial", columnDefinition = "LONGTEXT")
	private String descriptorFacial; 

	// La lista de direcciones conectada a DireccionEnvio
	@OneToMany(mappedBy="cliente", cascade=CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<DireccionEnvio> direcciones = new ArrayList<>();
	
	public Cliente() {}
	
	// 🔥 Constructor Copia Actualizado
	public Cliente(Cliente cliente) {
		this(cliente.getIdCliente(), cliente.getTipoDoc(), cliente.getNumDoc(),
			 cliente.getNombreCompleto(), cliente.getEmail(), cliente.getTelefono(), 
			 cliente.getPassword(), cliente.getFechaRegistro(), cliente.getDescriptorFacial(),
			 cliente.getDirecciones());
	}
	
	// 🔥 Constructor Completo Actualizado
	public Cliente(Integer idCliente, String tipoDoc, String numDoc, String nombreCompleto,
			String email, String telefono, String password, Date fechaRegistro, 
			String descriptorFacial, List<DireccionEnvio> direcciones) {
		this.idCliente = idCliente;
		this.tipoDoc = tipoDoc;
		this.numDoc = numDoc;
		this.nombreCompleto = nombreCompleto;
		this.email = email;
		this.telefono = telefono;
		this.password = password;
		this.fechaRegistro = fechaRegistro;
		this.descriptorFacial = descriptorFacial;
		this.direcciones = direcciones;
	}
	
	@PrePersist
	public void prePersist() {
		fechaRegistro = new Date();
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public String getNumDoc() {
		return numDoc;
	}

	public void setNumDoc(String numDoc) {
		this.numDoc = numDoc;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	public String getDescriptorFacial() {
		return descriptorFacial;
	}
	public void setDescriptorFacial(String descriptorFacial) { 
		this.descriptorFacial = descriptorFacial; 
	}

	public List<DireccionEnvio> getDirecciones() {
		return direcciones; 
	}
	
	public void setDirecciones(List<DireccionEnvio> direcciones) {
		this.direcciones = direcciones; 
	}
	
	// Método de ayuda para que sea fácil grabar desde Angular
	public void addDireccion(DireccionEnvio direccion) {
	    direcciones.add(direccion);
	    direccion.setCliente(this);
	}
}