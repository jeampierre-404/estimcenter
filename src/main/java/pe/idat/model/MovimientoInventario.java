package pe.idat.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name="movimientos_inventario")
public class MovimientoInventario implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_movimiento")
	private Long idMovimiento; // BIGINT en BD
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private TipoMovimiento tipo;
	
	@Column(nullable=false, precision=10, scale=2)
	private BigDecimal cantidad;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date fecha;
	
	@Column
	private String motivo;
	
	// 🔥 CAMBIADO: Ahora apunta a Producto
	@ManyToOne
	@JoinColumn(name="id_producto", nullable=false)
	private Producto producto;
	
	@ManyToOne
	@JoinColumn(name="id_usuario", nullable=false)
	private Usuario usuario;
	
	public enum TipoMovimiento {
		ENTRADA, SALIDA, AJUSTE
	}
	
	public MovimientoInventario() {}
	
	public MovimientoInventario(MovimientoInventario mov) {
		// 🔥 CAMBIADO: mov.getProducto()
		this(mov.getIdMovimiento(), mov.getTipo(), mov.getCantidad(), 
			 mov.getFecha(), mov.getMotivo(), mov.getProducto(), mov.getUsuario());
	}
	
	// 🔥 CAMBIADO: Producto producto
	public MovimientoInventario(Long idMovimiento, TipoMovimiento tipo, BigDecimal cantidad, 
			Date fecha, String motivo, Producto producto, Usuario usuario) {
		this.idMovimiento = idMovimiento;
		this.tipo = tipo;
		this.cantidad = cantidad;
		this.fecha = fecha;
		this.motivo = motivo;
		this.producto = producto; // 🔥 CAMBIADO
		this.usuario = usuario;
	}
	
	@PrePersist
	public void prePersist() {
		fecha = new Date();
	}

	public Long getIdMovimiento() {
		return idMovimiento;
	}

	public void setIdMovimiento(Long idMovimiento) {
		this.idMovimiento = idMovimiento;
	}

	public TipoMovimiento getTipo() {
		return tipo;
	}

	public void setTipo(TipoMovimiento tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	// 🔥 NUEVOS GETTER Y SETTER PARA PRODUCTO
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}