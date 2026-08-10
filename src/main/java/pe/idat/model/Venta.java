package pe.idat.model;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name="ventas")
public class Venta implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_venta")
	private Integer idVenta;
	
	@Enumerated(EnumType.STRING)
	@Column(name="tipo_comprobante", nullable=false)
	private TipoComprobante tipoComprobante;
	
	@Column(name="serie_correlativo", nullable=false, length=20)
	private String serieCorrelativo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_emision")
	private Date fechaEmision;
	
	// Relación 1 a 1 con Cotización (Unique en BD)
	@OneToOne
	@JoinColumn(name="id_cotizacion", unique=true)
	private Cotizacion cotizacion;
	
	@ManyToOne
	@JoinColumn(name="id_metodo_pago", nullable=false)
	private MetodoPago metodoPago;
	
	public enum TipoComprobante {
		BOLETA, FACTURA
	}
	
	public Venta() {}
	
	public Venta(Venta venta) {
		this(venta.getIdVenta(), venta.getTipoComprobante(), venta.getSerieCorrelativo(),
			 venta.getFechaEmision(), venta.getCotizacion(), venta.getMetodoPago());
	}
	
	public Venta(Integer idVenta, TipoComprobante tipoComprobante, String serieCorrelativo, 
			Date fechaEmision, Cotizacion cotizacion, MetodoPago metodoPago) {
		this.idVenta = idVenta;
		this.tipoComprobante = tipoComprobante;
		this.serieCorrelativo = serieCorrelativo;
		this.fechaEmision = fechaEmision;
		this.cotizacion = cotizacion;
		this.metodoPago = metodoPago;
	}
	
	@PrePersist
	public void prePersist() {
		fechaEmision = new Date();
	}

	public Integer getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(Integer idVenta) {
		this.idVenta = idVenta;
	}

	public TipoComprobante getTipoComprobante() {
		return tipoComprobante;
	}

	public void setTipoComprobante(TipoComprobante tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}

	public String getSerieCorrelativo() {
		return serieCorrelativo;
	}

	public void setSerieCorrelativo(String serieCorrelativo) {
		this.serieCorrelativo = serieCorrelativo;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Cotizacion getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(Cotizacion cotizacion) {
		this.cotizacion = cotizacion;
	}

	public MetodoPago getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(MetodoPago metodoPago) {
		this.metodoPago = metodoPago;
	}
}