package pe.idat.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name="cotizaciones")
public class Cotizacion implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cotizacion")
	private Integer idCotizacion;
	
	@Column(unique=true, length=20)
	private String codigo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_emision")
	private Date fechaEmision;
	
	@Temporal(TemporalType.DATE)
	@Column(name="fecha_vencimiento")
	private Date fechaVencimiento;
	
	@Column(nullable=false, precision=10, scale=2)
	private BigDecimal subtotal;
	
	@Column(nullable=false, precision=10, scale=2)
	private BigDecimal igv;
	
	@Column(nullable=false, precision=10, scale=2)
	private BigDecimal total;
	
	@Enumerated(EnumType.STRING)
	@Column(length=20)
	private EstadoCotizacion estado;
	
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="id_usuario", nullable=false)
	private Usuario usuario;
	
	public enum EstadoCotizacion {
		PENDIENTE, APROBADO, FACTURADO, CANCELADO
	}
	
	public Cotizacion() {}
	
	public Cotizacion(Cotizacion c) {
		this(c.getIdCotizacion(), c.getCodigo(), c.getFechaEmision(), c.getFechaVencimiento(),
			 c.getSubtotal(), c.getIgv(), c.getTotal(), c.getEstado(), c.getCliente(), c.getUsuario());
	}
	
	public Cotizacion(Integer idCotizacion, String codigo, Date fechaEmision, Date fechaVencimiento,
			BigDecimal subtotal, BigDecimal igv, BigDecimal total, EstadoCotizacion estado, 
			Cliente cliente, Usuario usuario) {
		this.idCotizacion = idCotizacion;
		this.codigo = codigo;
		this.fechaEmision = fechaEmision;
		this.fechaVencimiento = fechaVencimiento;
		this.subtotal = subtotal;
		this.igv = igv;
		this.total = total;
		this.estado = estado;
		this.cliente = cliente;
		this.usuario = usuario;
	}
	
	@PrePersist
	public void prePersist() {
		fechaEmision = new Date();
		if(estado == null) {
			estado = EstadoCotizacion.PENDIENTE;
		}
	}

	public Integer getIdCotizacion() {
		return idCotizacion;
	}

	public void setIdCotizacion(Integer idCotizacion) {
		this.idCotizacion = idCotizacion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getIgv() {
		return igv;
	}

	public void setIgv(BigDecimal igv) {
		this.igv = igv;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public EstadoCotizacion getEstado() {
		return estado;
	}

	public void setEstado(EstadoCotizacion estado) {
		this.estado = estado;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}