package pe.idat.model;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name="despachos")
public class Despacho implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_despacho")
	private Integer idDespacho;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_programada")
	private Date fechaProgramada;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_entrega")
	private Date fechaEntrega;
	
	@Enumerated(EnumType.STRING)
	@Column(length=20)
	private EstadoDespacho estado;
	
	@ManyToOne
	@JoinColumn(name="id_venta", nullable=false)
	private Venta venta;
	
	@ManyToOne
	@JoinColumn(name="id_direccion")
	private DireccionEnvio direccionEnvio;
	
	public enum EstadoDespacho {
		PENDIENTE, EN_RUTA, ENTREGADO
	}
	
	public Despacho() {}
	
	public Despacho(Despacho d) {
		this(d.getIdDespacho(), d.getFechaProgramada(), d.getFechaEntrega(), 
			 d.getEstado(), d.getVenta(), d.getDireccionEnvio());
	}
	
	public Despacho(Integer idDespacho, Date fechaProgramada, Date fechaEntrega, 
			EstadoDespacho estado, Venta venta, DireccionEnvio direccionEnvio) {
		this.idDespacho = idDespacho;
		this.fechaProgramada = fechaProgramada;
		this.fechaEntrega = fechaEntrega;
		this.estado = estado;
		this.venta = venta;
		this.direccionEnvio = direccionEnvio;
	}
	
	@PrePersist
	public void prePersist() {
		if(estado == null) {
			estado = EstadoDespacho.PENDIENTE;
		}
	}

	public Integer getIdDespacho() {
		return idDespacho;
	}

	public void setIdDespacho(Integer idDespacho) {
		this.idDespacho = idDespacho;
	}

	public Date getFechaProgramada() {
		return fechaProgramada;
	}

	public void setFechaProgramada(Date fechaProgramada) {
		this.fechaProgramada = fechaProgramada;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public EstadoDespacho getEstado() {
		return estado;
	}

	public void setEstado(EstadoDespacho estado) {
		this.estado = estado;
	}

	public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}

	public DireccionEnvio getDireccionEnvio() {
		return direccionEnvio;
	}

	public void setDireccionEnvio(DireccionEnvio direccionEnvio) {
		this.direccionEnvio = direccionEnvio;
	}
}