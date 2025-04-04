package modelo.tiquetes;

import java.util.Date;
import java.util.List;
import modelo.atraccion.Atraccion;


/**
 * Representa un tiquete de temporada, que extiende TiquetesNormales.
 * Ofrece acceso ilimitado dentro de un rango de fechas.
 */
public class TiquetesTemporada extends TiquetesNormales {

    private Date fechaInicio;
    private Date fechaCaducidad;

    /**
     * Constructor de TiquetesTemporada.
     * @param id                Identificador del tiquete
     * @param tipo              Tipo del tiquete (FAMILIAR, ORO, DIAMANTE, BASICO)
     * @param accesoAtracciones Lista de atracciones a las que da acceso
     * @param descuento         Descuento aplicado
     * @param fechaInicio       Fecha de inicio de la temporada
     * @param fechaCaducidad    Fecha de fin de la temporada
     */
    public TiquetesTemporada(String id, Tipo tipo, List<Atraccion> accesoAtracciones,
                             int descuento, Date fechaInicio, Date fechaCaducidad) {
        super(id, tipo, accesoAtracciones, descuento);
        this.fechaInicio = fechaInicio;
        this.fechaCaducidad = fechaCaducidad;
    }

    /**
     * Método para obtener la información del tiquete de temporada.
     */
    public void getTiquetesTemporada() {
        System.out.println("Tiquete de temporada válido del " + fechaInicio + " al " + fechaCaducidad);
    }

    /**
     * Método para configurar nuevas fechas de temporada.
     */
    public void setTiquetesTemporada(Date nuevaFechaInicio, Date nuevaFechaCaducidad) {
        this.fechaInicio = nuevaFechaInicio;
        this.fechaCaducidad = nuevaFechaCaducidad;
        System.out.println("Se han actualizado las fechas de temporada: " + fechaInicio + " - " + fechaCaducidad);
    }

    // Getters y Setters
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

 
    
}