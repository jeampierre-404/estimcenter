package pe.idat.dto;

import java.util.List;
import pe.idat.model.Cotizacion;
import pe.idat.model.DetalleCotizacion;

public class CotizacionDTO {
    
    // Esta clase sirve para recibir el PAQUETE COMPLETO desde Angular
    private Cotizacion cotizacion;
    private List<DetalleCotizacion> detalles;

    public CotizacionDTO() {}

    public Cotizacion getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
    }

    public List<DetalleCotizacion> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleCotizacion> detalles) {
        this.detalles = detalles;
    }
}