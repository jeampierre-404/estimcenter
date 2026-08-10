package pe.idat.model;

import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name="productos")
public class Producto implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_producto")
    private Integer idProducto;

    @Column(unique = true, length = 20)
    private String codigo;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad_medida", nullable = false)
    private UnidadMedida unidadMedida;

    @Column(name = "rendimiento_caja", precision = 10, scale = 2)
    private BigDecimal rendimientoCaja;

    @Column(name = "stock_actual", precision = 10, scale = 2)
    private BigDecimal stockActual;

    @Column(name = "stock_minimo")
    private Integer stockMinimo;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String imagen;
    
    // 🔥 NUEVO CAMPO: IMAGEN DE LA SALA INSTALADA
    @Lob
    @Column(name = "imagen_sala", columnDefinition = "LONGTEXT")
    private String imagenSala;
    // -----------------------------------------
    
    @Column(length = 20)
    private String estado; 

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    public enum UnidadMedida {
        m2, unid, caja, saco, bolsa
    }

    public Producto() {}

    // Constructor actualizado
    public Producto(String codigo, String nombre, String descripcion, BigDecimal precio,
            UnidadMedida unidadMedida, BigDecimal rendimientoCaja, BigDecimal stockActual, Integer stockMinimo,
            String imagen, String imagenSala, Categoria categoria, String estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.unidadMedida = unidadMedida;
        this.rendimientoCaja = rendimientoCaja;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.imagen = imagen;
        this.imagenSala = imagenSala; // 🔥 Añadido
        this.categoria = categoria;
        this.estado = estado;
    }

    @PrePersist
    public void prePersist() {
        if(this.stockActual == null) this.stockActual = BigDecimal.ZERO;
        if(this.rendimientoCaja == null) this.rendimientoCaja = new BigDecimal("1.00");
        if(this.stockMinimo == null) this.stockMinimo = 10;
        if(this.estado == null) this.estado = "ACTIVO";
    }

    // --- GETTERS Y SETTERS ---

    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public UnidadMedida getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(UnidadMedida unidadMedida) { this.unidadMedida = unidadMedida; }

    public BigDecimal getRendimientoCaja() { return rendimientoCaja; }
    public void setRendimientoCaja(BigDecimal rendimientoCaja) { this.rendimientoCaja = rendimientoCaja; }

    public BigDecimal getStockActual() { return stockActual; }
    public void setStockActual(BigDecimal stockActual) { this.stockActual = stockActual; }

    public Integer getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(Integer stockMinimo) { this.stockMinimo = stockMinimo; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    // 🔥 GETTER Y SETTER DE LA SALA
    public String getImagenSala() { return imagenSala; }
    public void setImagenSala(String imagenSala) { this.imagenSala = imagenSala; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}