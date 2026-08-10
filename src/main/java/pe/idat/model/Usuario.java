package pe.idat.model;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name="usuarios")
public class Usuario implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idUsuario;
	
	@Column(nullable=false)
	private String nombre;
	
	@Column(nullable=false)
	private String apellido;
	
	@Column(unique=true, nullable=false)
	private String email;
	
	@Column(nullable=false)
	private String password;
	
	@Column(columnDefinition = "TINYINT(1) DEFAULT 1")
	private Boolean estado;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;
	
	@ManyToOne
	@JoinColumn(name="id_rol", nullable=false)
	private Rol rol;
	
	public Usuario() {}
	
	public Usuario(Usuario usuario) {
		this(usuario.getIdUsuario(), usuario.getNombre(), usuario.getApellido(), 
			 usuario.getEmail(), usuario.getPassword(), usuario.getEstado(), 
			 usuario.getFechaCreacion(), usuario.getRol());
	}
	
	public Usuario(Integer idUsuario, String nombre, String apellido, String email, 
			String password, Boolean estado, Date fechaCreacion, Rol rol) {
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.password = password;
		this.estado = estado;
		this.fechaCreacion = fechaCreacion;
		this.rol = rol;
	}
	
	@PrePersist
	public void prePersist() {
		fechaCreacion = new Date();
		if(estado == null) {
			estado = true;
		}
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
}
