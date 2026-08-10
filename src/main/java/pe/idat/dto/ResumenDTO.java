package pe.idat.dto;

import java.math.BigDecimal;
import java.util.List;

public class ResumenDTO {
    
	private BigDecimal montoCotizado;
    private BigDecimal totalVentas;
    private Long cantidadProductosBajos;
    private Long totalClientes;
    private List <TopProductoDTO> topProductos;
    
	public ResumenDTO(BigDecimal montoCotizado, BigDecimal totalVentas, Long cantidadProductosBajos, Long totalClientes,
			List<TopProductoDTO> topProductos) {
		super();
		this.montoCotizado = montoCotizado;
		this.totalVentas = totalVentas;
		this.cantidadProductosBajos = cantidadProductosBajos;
		this.totalClientes = totalClientes;
		this.topProductos = topProductos;
	}

	public BigDecimal getMontoCotizado() {
		return montoCotizado;
	}

	public void setMontoCotizado(BigDecimal montoCotizado) {
		this.montoCotizado = montoCotizado;
	}

	public BigDecimal getTotalVentas() {
		return totalVentas;
	}

	public void setTotalVentas(BigDecimal totalVentas) {
		this.totalVentas = totalVentas;
	}

	public Long getCantidadProductosBajos() {
		return cantidadProductosBajos;
	}

	public void setCantidadProductosBajos(Long cantidadProductosBajos) {
		this.cantidadProductosBajos = cantidadProductosBajos;
	}

	public Long getTotalClientes() {
		return totalClientes;
	}

	public void setTotalClientes(Long totalClientes) {
		this.totalClientes = totalClientes;
	}

	public List<TopProductoDTO> getTopProductos() {
		return topProductos;
	}

	public void setTopProductos(List<TopProductoDTO> topProductos) {
		this.topProductos = topProductos;
	}
	
	

    

    
}