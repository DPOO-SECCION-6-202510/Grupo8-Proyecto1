package modelo.atraccion;

import java.util.Date;
import java.util.Map;

/**
 * Clase abstracta que representa una Atracción en el parque.
 * Esta clase define los atributos y métodos básicos comunes a todas las atracciones.
 */
public abstract class Atraccion {

    // Constantes para definir estados (sin usar enum)
    public static final String OPERATIVO = "OPERATIVO";
    public static final String MANTENIMIENTO = "MANTENIMIENTO";
    public static final String FUERA_DE_SERVICIO = "FUERA_DE_SERVICIO";

    // Atributos comunes
    protected int id;
    protected String nombre;
    protected int capacidad;
    protected String ubicacion;
    protected String nivelExclusividad; // Ej.: "Familiar", "Oro", "Diamante"
    protected String estadoOperativo;
    protected Date mantenimientoProgramado;

    /**
     * Constructor de Atraccion.
     *
     * @param id                Identificador único de la atracción.
     * @param nombre            Nombre de la atracción.
     * @param capacidad         Capacidad máxima de personas.
     * @param ubicacion         Ubicación de la atracción dentro del parque.
     * @param nivelExclusividad Nivel de exclusividad (Familiar, Oro, Diamante).
     */
    public Atraccion(int id, String nombre, int capacidad, String ubicacion, String nivelExclusividad) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.ubicacion = ubicacion;
        this.nivelExclusividad = nivelExclusividad;
        this.estadoOperativo = OPERATIVO;  // Estado inicial
        this.mantenimientoProgramado = null;
    }

    /**
     * Registra la atracción en el sistema.
     */
    public void registrar() {
        System.out.println("Registrando atracción: " + nombre + " (ID: " + id + ")");
        this.estadoOperativo = OPERATIVO;
    }

    /**
     * Actualiza la información de la atracción.
     *
     * @param nuevoNombre           Nuevo nombre.
     * @param nuevaCapacidad        Nueva capacidad.
     * @param nuevaUbicacion        Nueva ubicación.
     * @param nuevoNivelExclusividad Nuevo nivel de exclusividad.
     */
    public void actualizar(String nuevoNombre, int nuevaCapacidad, String nuevaUbicacion, String nuevoNivelExclusividad) {
        this.nombre = nuevoNombre;
        this.capacidad = nuevaCapacidad;
        this.ubicacion = nuevaUbicacion;
        this.nivelExclusividad = nuevoNivelExclusividad;
        System.out.println("Atracción actualizada: " + nombre);
    }

    /**
     * Valida el acceso a la atracción según parámetros proporcionados.
     * Este método se puede sobreescribir en las subclases para validaciones específicas.
     *
     * @param parametros Un mapa que puede contener claves como "altura", "peso", "edad", etc.
     * @return true si se cumplen los requisitos, false en caso contrario.
     */
    public boolean validarAcceso(Map<String, Object> parametros) {
        // Implementación por defecto: si se pasa "edad", requiere al menos 18 años.
        if (parametros.containsKey("edad")) {
            int edad = ((Number) parametros.get("edad")).intValue();
            return edad >= 18;
        }
        // Si se pasan "altura" y "peso", simplemente retorna true (se espera sobreescritura en subclases)
        if (parametros.containsKey("altura") && parametros.containsKey("peso")) {
            return true;
        }
        return false;
    }

    /**
     * Programa el mantenimiento de la atracción.
     *
     * @param fecha Fecha en la que se realizará el mantenimiento.
     */
    public void programarMantenimiento(Date fecha) {
        this.mantenimientoProgramado = fecha;
        this.estadoOperativo = MANTENIMIENTO;
        System.out.println("Mantenimiento programado para " + fecha + " en " + nombre);
    }

    /**
     * Retorna el estado operativo actual.
     *
     * @return Estado operativo como String.
     */
    public String obtenerEstado() {
        return this.estadoOperativo;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getNivelExclusividad() {
        return nivelExclusividad;
    }

    public Date getMantenimientoProgramado() {
        return mantenimientoProgramado;
    }
}