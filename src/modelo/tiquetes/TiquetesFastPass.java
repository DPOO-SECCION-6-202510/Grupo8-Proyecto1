package modelo.tiquetes;

import java.util.Date;
import java.util.List;
import modelo.atraccion.Atraccion;

/**
 * Representa un tiquete FastPass, que extiende TiquetesNormales.
 * Ofrece acceso prioritario en una fecha específica.
 */
public class TiquetesFastPass extends TiquetesNormales {

    private Date fechaEntrada;  // Fecha específica en la que se puede usar el FastPass

    /**
     * Constructor de TiquetesFastPass.
     * @param id                Identificador del tiquete
     * @param tipo              Tipo del tiquete (FAMILIAR, ORO, DIAMANTE, BASICO)
     * @param accesoAtracciones Lista de atracciones a las que da acceso
     * @param descuento         Descuento aplicado
     * @param fechaEntrada      Fecha en la que se puede usar el FastPass
     */
    public TiquetesFastPass(String id, Tipo tipo, List<Atraccion> accesoAtracciones,
                            int descuento, Date fechaEntrada) {
        super(id, tipo, accesoAtracciones, descuento);
        this.fechaEntrada = fechaEntrada;
    }

    /**
     * Método específico para "obtener" el FastPass en la fecha indicada.
     */
    public void getTiquetesFastPass() {
        System.out.println("FastPass disponible para la fecha: " + fechaEntrada);
    }

    /**
     * Método específico para "configurar" la fecha del FastPass.
     */
    public void setTiquetesFastPass(Date nuevaFecha) {
        this.fechaEntrada = nuevaFecha;
        System.out.println("Se ha actualizado la fecha de FastPass a: " + nuevaFecha);
    }

    // Getter para fechaEntrada
    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    // Ejemplo de sobreescritura de setDescuento(Usuario user) si se desea
    @Override
    public void setDescuento(Usuario user) {
        // Se podría aplicar un descuento adicional especial para FastPass
        super.setDescuento(user); // Llama la lógica base
    }
}