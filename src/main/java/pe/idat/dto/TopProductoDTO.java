package pe.idat.dto;

import java.math.BigDecimal; // <--- Importante

public class TopProductoDTO {
    private String nombre;
    private BigDecimal cantidadTotal; // <--- CAMBIADO DE Long A BigDecimal

    public TopProductoDTO(String nombre, BigDecimal cantidadTotal) {
        this.nombre = nombre;
        this.cantidadTotal = cantidadTotal;
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getCantidadTotal() {
		return cantidadTotal;
	}

	public void setCantidadTotal(BigDecimal cantidadTotal) {
		this.cantidadTotal = cantidadTotal;
	}

    
}
