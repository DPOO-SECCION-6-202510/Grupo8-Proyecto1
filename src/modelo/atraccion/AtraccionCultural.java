package modelo.atraccion;

import java.util.Map;

/**
 * Clase que representa una atracción cultural.
 * Extiende de Atraccion e incorpora atributos y lógica
 * específicos para atracciones basadas en temáticas y restricciones de edad.
 */
public class AtraccionCultural extends Atraccion {

    // Atributos específicos para atracciones culturales
    private int restriccionEdad;         // Edad mínima requerida
    private String descripcionTematica;  // Descripción de la temática de la atracción

    /**
     * Constructor para AtraccionCultural.
     *
     * @param id                Identificador único.
     * @param nombre            Nombre de la atracción.
     * @param capacidad         Capacidad máxima.
     * @param ubicacion         Ubicación en el parque.
     * @param nivelExclusividad Nivel de exclusividad ("Familiar", "Oro", "Diamante").
     * @param restriccionEdad   Edad mínima requerida para el ingreso.
     * @param descripcionTematica Descripción de la temática de la atracción.
     */
    public AtraccionCultural(int id, String nombre, int capacidad, String ubicacion, String nivelExclusividad,
                             int restriccionEdad, String descripcionTematica) {
        super(id, nombre, capacidad, ubicacion, nivelExclusividad);
        this.restriccionEdad = restriccionEdad;
        this.descripcionTematica = descripcionTematica;
    }

    // Getters y Setters
    public int getRestriccionEdad() {
        return restriccionEdad;
    }

    public String getDescripcionTematica() {
        return descripcionTematica;
    }

    public void setDescripcionTematica(String descripcionTematica) {
        this.descripcionTematica = descripcionTematica;
    }

    /**
     * Valida el acceso a la atracción cultural verificando que el usuario cumpla
     * con la restricción de edad.
     *
     * @param parametros Mapa que debe contener la clave "edad".
     * @return true si la edad del usuario es igual o mayor a la restricción, false en caso contrario.
     */
    @Override
    public boolean validarAcceso(Map<String, Object> parametros) {
        if (parametros.containsKey("edad")) {
            Object edadObj = parametros.get("edad");
            if (edadObj instanceof Number) {
                int edad = ((Number) edadObj).intValue();
                boolean cumpleEdad = edad >= restriccionEdad;
                if (!cumpleEdad) {
                    System.out.println("Edad insuficiente para la atracción " + nombre + ". Se requiere mínimo " + restriccionEdad);
                }
                return cumpleEdad;
            }
        }
        return false;
    }
}