package modelo.atraccion;

import java.util.Date;
import java.util.Map;

/**
 * Representa una atracción cultural.
 * Su validación se enfoca en la restricción de edad.
 */
public class AtraccionCultural extends Atraccion {

    // Atributos específicos para atracciones culturales
    private int restriccionEdad; // Edad mínima requerida
    private String descripcionTematica; // Descripción de la temática

    /**
     * Constructor para AtraccionCultural.
     *
     * @param id Identificador único.
     * @param nombre Nombre de la atracción.
     * @param capacidad Capacidad máxima.
     * @param ubicacion Ubicación en el parque.
     * @param nivelExclusividad Nivel de exclusividad.
     * @param restriccionEdad Edad mínima requerida.
     * @param descripcionTematica Descripción de la temática.
     */
    public AtraccionCultural(int id, String nombre, int capacidad, String ubicacion, String nivelExclusividad,
                            int restriccionEdad, String descripcionTematica) {
        super(id, nombre, capacidad, ubicacion, nivelExclusividad);
        this.restriccionEdad = restriccionEdad;
        this.descripcionTematica = descripcionTematica;
    }
    
    /**
     * Constructor para deserialización desde JSON.
     */
    private AtraccionCultural(int id, String nombre, int capacidad, String ubicacion, 
                           String nivelExclusividad, String estadoOperativo, Date mantenimientoProgramado,
                           int restriccionEdad, String descripcionTematica) {
        super(id, nombre, capacidad, ubicacion, nivelExclusividad, estadoOperativo, mantenimientoProgramado);
        this.restriccionEdad = restriccionEdad;
        this.descripcionTematica = descripcionTematica;
    }

    /**
     * Valida el acceso verificando que la edad del usuario sea mayor o igual a la restricción.
     *
     * @param parametros Mapa que debe contener la clave "edad".
     * @return true si cumple la restricción, false en caso contrario.
     */
    @Override
    public boolean validarAcceso(Map<String, Object> parametros) {
        if (parametros.containsKey("edad")) {
            int edad = ((Number) parametros.get("edad")).intValue();
            boolean cumpleEdad = edad >= restriccionEdad;
            if (!cumpleEdad) {
                System.out.println("Edad insuficiente para " + nombre + ". Se requiere mínimo " + restriccionEdad);
            }
            return cumpleEdad;
        }
        return false;
    }

    // Getters y Setters para atributos específicos
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
     * Define el tipo de atracción para la serialización/deserialización.
     */
    @Override
    protected String getTipoAtraccion() {
        return "CULTURAL";
    }
    
    /**
     * Añade los atributos específicos de AtraccionCultural al JSON.
     */
    @Override
    protected String toJsonEspecifico() {
        StringBuilder sb = new StringBuilder();
        sb.append(",\"restriccionEdad\":").append(restriccionEdad);
        sb.append(",\"descripcionTematica\":\"").append(descripcionTematica).append("\"");
        return sb.toString();
    }
    
    /**
     * Crea una AtraccionCultural a partir de un string JSON.
     *
     * @param json String en formato JSON con los datos de la atracción cultural.
     * @return Una nueva instancia de AtraccionCultural.
     */
    public static AtraccionCultural fromJson(String json) {
        // Extraer los campos básicos
        int id = extractIntValue(json, "id");
        String nombre = extractStringValue(json, "nombre");
        int capacidad = extractIntValue(json, "capacidad");
        String ubicacion = extractStringValue(json, "ubicacion");
        String nivelExclusividad = extractStringValue(json, "nivelExclusividad");
        String estadoOperativo = extractStringValue(json, "estadoOperativo");
        Date mantenimientoProgramado = extractDateValue(json, "mantenimientoProgramado");
        
        // Extraer los campos específicos
        int restriccionEdad = extractIntValue(json, "restriccionEdad");
        String descripcionTematica = extractStringValue(json, "descripcionTematica");
        
        // Crear y retornar la nueva instancia
        return new AtraccionCultural(id, nombre, capacidad, ubicacion, nivelExclusividad, 
                                  estadoOperativo, mantenimientoProgramado, restriccionEdad, descripcionTematica);
    }
}