package modelo.atraccion;

import java.util.Date;
import java.util.Map;

/**
 * Clase abstracta que representa una Atracción en el parque de diversiones.
 * Contiene atributos y métodos básicos comunes a todo tipo de atracción.
 */
public abstract class Atraccion {

    // Constantes para el estado operativo
    public static final String OPERATIVO = "OPERATIVO";
    public static final String MANTENIMIENTO = "MANTENIMIENTO";
    public static final String FUERA_DE_SERVICIO = "FUERA_DE_SERVICIO";

    // Atributos
    protected int id;
    protected String nombre;
    protected int capacidad;
    protected String ubicacion;
    protected String nivelExclusividad; // Ejemplo: "Familiar", "Oro", "Diamante"
    protected String estadoOperativo;
    protected Date mantenimientoProgramado;

    /**
     * Constructor de la clase Atraccion.
     *
     * @param id                Identificador único de la atracción.
     * @param nombre            Nombre de la atracción.
     * @param capacidad         Capacidad máxima de personas.
     * @param ubicacion         Ubicación fija de la atracción en el parque.
     * @param nivelExclusividad Nivel de exclusividad (por ejemplo, "Familiar", "Oro", "Diamante").
     */
    public Atraccion(int id, String nombre, int capacidad, String ubicacion, String nivelExclusividad) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.ubicacion = ubicacion;
        this.nivelExclusividad = nivelExclusividad;
        this.estadoOperativo = OPERATIVO; // Estado inicial
        this.mantenimientoProgramado = null;
    }

    /**
     * Registra la atracción en el sistema.
     * Se podría utilizar para asignar un ID único, establecer el estado inicial y agregarla a un catálogo.
     */
    public void registrar() {
        System.out.println("Registrando atracción: " + nombre + " (ID: " + id + ")");
        this.estadoOperativo = OPERATIVO;
    }

    /**
     * Actualiza la información de la atracción.
     *
     * @param nuevoNombre           Nuevo nombre de la atracción.
     * @param nuevaCapacidad        Nueva capacidad máxima.
     * @param nuevaUbicacion        Nueva ubicación en el parque.
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
     * Valida el acceso a la atracción en función de ciertos parámetros.
     *
     * @param parametros Un mapa con claves como "altura", "peso", "edad", "condicionesMedicas", etc.
     * @return true si se cumplen los requisitos, false en caso contrario.
     */
    public boolean validarAcceso(Map<String, Object> parametros) {
        // Ejemplo para atracciones mecánicas: se validan altura y peso.
        if (parametros.containsKey("altura") && parametros.containsKey("peso")) {
            Object alturaObj = parametros.get("altura");
            Object pesoObj = parametros.get("peso");
            if (alturaObj instanceof Number && pesoObj instanceof Number) {
                double altura = ((Number) alturaObj).doubleValue();
                double peso = ((Number) pesoObj).doubleValue();
                System.out.println("Validando acceso para " + nombre + " con altura: " + altura + " y peso: " + peso);
                // Aquí se agregaría la lógica para comparar con límites específicos.
                return true;
            }
        }
        // Ejemplo para atracciones culturales: se valida la edad.
        if (parametros.containsKey("edad")) {
            Object edadObj = parametros.get("edad");
            if (edadObj instanceof Number) {
                int edad = ((Number) edadObj).intValue();
                System.out.println("Validando acceso para " + nombre + " con edad: " + edad);
                // Ejemplo: se requiere que la edad sea mayor o igual a 18.
                return edad >= 18;
            }
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
        System.out.println("Mantenimiento programado para " + fecha + " en la atracción: " + nombre);
    }

    /**
     * Retorna el estado operativo actual de la atracción.
     *
     * @return Estado operativo como String (OPERATIVO, MANTENIMIENTO o FUERA_DE_SERVICIO).
     */
    public String obtenerEstado() {
        return this.estadoOperativo;
    }

    // Getters (y Setters si se requieren)
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