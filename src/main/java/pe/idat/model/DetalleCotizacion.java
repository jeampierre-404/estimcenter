package pe.idat.model;

import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name="detalle_cotizaciones")
public class DetalleCotizacion implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_detalle")
	private Integer idDetalle;
	
	@Column(nullable=false, precision=10, scale=2)
	private BigDecimal cantidad;
	
	@Column(name="precio_historico", nullable=false, precision=10, scale=2)
	private BigDecimal precioHistorico;
	
	@Column(nullable=false, precision=10, scale=2)
	private BigDecimal importe;
	
	@ManyToOne
	@JoinColumn(name="id_cotizacion", nullable=false)
	private Cotizacion cotizacion;
	
	@ManyToOne
	@JoinColumn(name="id_producto", nullable=false)
	private Producto producto;
	
	public DetalleCotizacion() {}
	
	public DetalleCotizacion(DetalleCotizacion det) {
		this(det.getIdDetalle(), det.getCantidad(), det.getPrecioHistorico(), 
			 det.getImporte(), det.getCotizacion(), det.getProducto());
	}
	
	public DetalleCotizacion(Integer idDetalle, BigDecimal cantidad, BigDecimal precioHistorico, 
			BigDecimal importe, Cotizacion cotizacion, Producto producto) {
		this.idDetalle = idDetalle;
		this.cantidad = cantidad;
		this.precioHistorico = precioHistorico;
		this.importe = importe;
		this.cotizacion = cotizacion;
		this.producto = producto;
	}
	
	@PrePersist
	public void prePersist() {}

	public Integer getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(Integer idDetalle) {
		this.idDetalle = idDetalle;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecioHistorico() {
		return precioHistorico;
	}

	public void setPrecioHistorico(BigDecimal precioHistorico) {
		this.precioHistorico = precioHistorico;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public Cotizacion getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(Cotizacion cotizacion) {
		this.cotizacion = cotizacion;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
}
