package pe.idat.model;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name="auditoria_log")
public class AuditoriaLog implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_log")
	private Long idLog; // BIGINT en BD
	
	@Column(nullable=false, length=50)
	private String accion;
	
	@Column(name="tabla_afectada", length=50)
	private String tablaAfectada;
	
	@Column(columnDefinition="TEXT")
	private String descripcion;
	
	@Column(name="ip_origen", length=45)
	private String ipOrigen;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date fecha;
	
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;
	
	public AuditoriaLog() {}
	
	public AuditoriaLog(AuditoriaLog log) {
		this(log.getIdLog(), log.getAccion(), log.getTablaAfectada(), 
			 log.getDescripcion(), log.getIpOrigen(), log.getFecha(), log.getUsuario());
	}
	
	public AuditoriaLog(Long idLog, String accion, String tablaAfectada, String descripcion, 
			String ipOrigen, Date fecha, Usuario usuario) {
		this.idLog = idLog;
		this.accion = accion;
		this.tablaAfectada = tablaAfectada;
		this.descripcion = descripcion;
		this.ipOrigen = ipOrigen;
		this.fecha = fecha;
		this.usuario = usuario;
	}
	
	@PrePersist
	public void prePersist() {
		fecha = new Date();
	}

	public Long getIdLog() {
		return idLog;
	}

	public void setIdLog(Long idLog) {
		this.idLog = idLog;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getTablaAfectada() {
		return tablaAfectada;
	}

	public void setTablaAfectada(String tablaAfectada) {
		this.tablaAfectada = tablaAfectada;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIpOrigen() {
		return ipOrigen;
	}

	public void setIpOrigen(String ipOrigen) {
		this.ipOrigen = ipOrigen;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}