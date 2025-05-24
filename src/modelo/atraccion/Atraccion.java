package modelo.atraccion;

import java.util.Date;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public abstract class Atraccion {
    protected int id;
    protected String nombre;
    protected int capacidad;
    protected String ubicacion;
    protected String nivelExclusividad;
    protected String estadoOperativo;
    protected Date mantenimientoProgramado;
    
    public static final String OPERATIVO = "OPERATIVO";
    public static final String MANTENIMIENTO = "MANTENIMIENTO";
    public static final String FUERA_DE_SERVICIO = "FUERA_DE_SERVICIO";
    
    public Atraccion(int id, String nombre, int capacidad, String ubicacion, String nivelExclusividad) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.ubicacion = ubicacion;
        this.nivelExclusividad = nivelExclusividad;
        this.estadoOperativo = OPERATIVO;
        this.mantenimientoProgramado = null;
    }
    
    // Constructor para deserialización desde JSON
    protected Atraccion(int id, String nombre, int capacidad, String ubicacion, 
                     String nivelExclusividad, String estadoOperativo, Date mantenimientoProgramado) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.ubicacion = ubicacion;
        this.nivelExclusividad = nivelExclusividad;
        this.estadoOperativo = estadoOperativo;
        this.mantenimientoProgramado = mantenimientoProgramado;
    }
    
    public void registrar() {
        System.out.println("Registrando atracción: " + nombre + " (ID: " + id + ")");
        this.estadoOperativo = OPERATIVO;
    }
    
    public void actualizar(String nuevoNombre, int nuevaCapacidad, String nuevaUbicacion, String nuevoNivelExclusividad) {
        this.nombre = nuevoNombre;
        this.capacidad = nuevaCapacidad;
        this.ubicacion = nuevaUbicacion;
        this.nivelExclusividad = nuevoNivelExclusividad;
        System.out.println("Atracción actualizada: " + nombre);
    }
    
    // Método abstracto para validación de acceso
    public abstract boolean validarAcceso(Map<String, Object> parametros);
    
    public void programarMantenimiento(Date fecha) {
        this.mantenimientoProgramado = fecha;
        this.estadoOperativo = MANTENIMIENTO;
        System.out.println("Mantenimiento programado para " + fecha + " en " + nombre);
    }
    
    public String obtenerEstado() {
        return estadoOperativo;
    }
    
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

    /**
     * Convierte la atracción a una cadena JSON con sus atributos básicos.
     */
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"tipo\":\"").append(getTipoAtraccion()).append("\",");
        sb.append("\"id\":").append(id).append(",");
        sb.append("\"nombre\":\"").append(nombre).append("\",");
        sb.append("\"capacidad\":").append(capacidad).append(",");
        sb.append("\"ubicacion\":\"").append(ubicacion).append("\",");
        sb.append("\"nivelExclusividad\":\"").append(nivelExclusividad).append("\",");
        sb.append("\"estadoOperativo\":\"").append(estadoOperativo).append("\"");
        
        // Incluir la fecha de mantenimiento si existe
        if (mantenimientoProgramado != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sb.append(",\"mantenimientoProgramado\":\"")
              .append(sdf.format(mantenimientoProgramado)).append("\"");
        }
        
        // Añadir propiedades específicas de la subclase
        sb.append(toJsonEspecifico());
        sb.append("}");
        return sb.toString();
    }
    
    /**
     * Debe ser implementado por cada subclase para añadir sus propiedades específicas al JSON.
     */
    protected abstract String toJsonEspecifico();
    
    /**
     * Debe ser implementado por cada subclase para identificar su tipo.
     */
    protected abstract String getTipoAtraccion();
    
    // ----------------
    // FÁBRICA DE DESERIALIZACIÓN
    // ----------------
    /**
     * Fabrica la atracción adecuada según su JSON.
     */
    public static Atraccion fromJson(String json) {
        String tipo = extractStringValue(json, "tipo");
        if ("MECANICA".equalsIgnoreCase(tipo)) {
            return AtraccionMecanica.fromJson(json);
        } else if ("CULTURAL".equalsIgnoreCase(tipo)) {
            return AtraccionCultural.fromJson(json);
        } else {
            throw new IllegalArgumentException("Tipo de atracción desconocido: " + tipo);
        }
    }
    
    /**
     * Extrae un valor int de un JSON.
     */
    protected static int extractIntValue(String json, String key) {
        String pattern = "\"" + key + "\"\\s*:\\s*(\\d+)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(json);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }
        throw new IllegalArgumentException("No se encontró el valor para: " + key);
    }
    
    /**
     * Extrae un valor string de un JSON.
     */
    protected static String extractStringValue(String json, String key) {
        String pattern = "\"" + key + "\"\\s*:\\s*\\\"([^\\\"]*)\\\"";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(json);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }
    
    /**
     * Extrae un valor Date de un JSON.
     */
    protected static Date extractDateValue(String json, String key) {
        String dateStr = extractStringValue(json, key);
        if (dateStr != null && !dateStr.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.parse(dateStr);
            } catch (ParseException e) {
                System.err.println("Error al parsear la fecha: " + e.getMessage());
            }
        }
        return null;
    }
}
