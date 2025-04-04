package modelo.tiquetes;

import java.util.List;
import modelo.atraccion.Atraccion;


/**
 * Clase base que representa un tiquete normal.
 * Incluye atributos comunes como id, si fue utilizado, 
 * acceso a atracciones, descuento y el tipo de tiquete.
 */
public class TiquetesNormales {

    private String id;                  // Identificador único del tiquete
    private boolean utilizado;          // Indica si ya se usó
    private List<Atraccion> accesoAtracciones;  // Atracciones a las que da acceso
    private int descuento;             // Descuento aplicado (por ejemplo, porcentaje)
    private Tipo tipo;                 // Tipo de tiquete (FAMILIAR, ORO, DIAMANTE, BASICO)

    /**
     * Constructor de TiquetesNormales.
     * @param id  Identificador del tiquete
     * @param tipo  Tipo del tiquete (enum Tipo)
     * @param accesoAtracciones  Lista de atracciones a las que puede acceder
     * @param descuento  Descuento aplicado (en porcentaje, por ejemplo)
     */
    public TiquetesNormales(String id, Tipo tipo, List<Atraccion> accesoAtracciones, int descuento) {
        this.id = id;
        this.tipo = tipo;
        this.accesoAtracciones = accesoAtracciones;
        this.descuento = descuento;
        this.utilizado = false;
    }

    /**
     * Realiza la "compra" del tiquete.
     * En un sistema real, podría registrar la transacción en la base de datos
     * o generar un recibo. Aquí solo imprimimos un mensaje.
     */
    public void comprar() {
        System.out.println("Se ha comprado el tiquete con ID: " + id + " de tipo " + tipo);
    }

    /**
     * Guarda la información de la compra de forma persistente (ej. JSON).
     * Aquí se muestra de manera simplificada.
     */
    public void guardarCompra() {
        System.out.println("Guardando la información del tiquete " + id + " en el sistema...");
        // Llamada a la capa de persistencia si se desea
    }

    /**
     * Aplica un descuento adicional en función de un usuario (por ejemplo, si es empleado).
     * @param user  Usuario que adquiere el tiquete.
     */
   

    // Getters y Setters
    public String getId() {
        return id;
    }

    public boolean isUtilizado() {
        return utilizado;
    }

    public void setUtilizado(boolean utilizado) {
        this.utilizado = utilizado;
    }

    public List<Atraccion> getAccesoAtracciones() {
        return accesoAtracciones;
    }

    public void setAccesoAtracciones(List<Atraccion> accesoAtracciones) {
        this.accesoAtracciones = accesoAtracciones;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}