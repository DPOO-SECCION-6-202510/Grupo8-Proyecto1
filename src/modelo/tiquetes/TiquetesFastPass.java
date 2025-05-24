package modelo.tiquetes;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import modelo.atraccion.Atraccion;

/**
 * Representa un tiquete FastPass, que extiende TiquetesNormales.
 * Ofrece acceso prioritario en una fecha específica.
 */
public class TiquetesFastPass extends TiquetesNormales {

    private Date fechaEntrada;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Crea un tiquete FastPass.
     * @param id Identificador único
     * @param tipo Tipo de tiquete
     * @param accesoAtracciones Atracciones accesibles
     * @param descuento Porcentaje de descuento
     * @param fechaEntrada Fecha única de uso
     */
    public TiquetesFastPass(String id, Tipo tipo, List<Atraccion> accesoAtracciones,
                            int descuento, Date fechaEntrada) {
        super(id, tipo, accesoAtracciones, descuento);
        this.fechaEntrada = fechaEntrada;
    }

    /**
     * Imprime información sobre la disponibilidad del FastPass.
     */
    public void getTiquetesFastPass() {
        System.out.println("FastPass disponible para la fecha: " + fechaEntrada);
    }

    /**
     * Actualiza la fecha de uso del FastPass.
     * @param nuevaFecha Nueva fecha permitida
     */
    public void setTiquetesFastPass(Date nuevaFecha) {
        this.fechaEntrada = nuevaFecha;
    }

    public Date getFechaEntrada() { return fechaEntrada; }

    /**
     * Convierte este objeto a formato JSON.
     * @return Cadena JSON
     */
    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"clase\":\"TiquetesFastPass\",");
        sb.append("\"id\":\"").append(escapeJson(getId())).append("\",");
        sb.append("\"utilizado\":").append(isUtilizado()).append(",");
        sb.append("\"descuento\":").append(getDescuento()).append(",");
        sb.append("\"tipo\":\"").append(getTipo().toString()).append("\",");
        sb.append("\"fechaEntrada\":\"").append(DATE_FORMAT.format(fechaEntrada)).append("\",");
        sb.append("\"accesoAtracciones\":[");
        List<Atraccion> atracciones = getAccesoAtracciones();
        for (int i = 0; i < atracciones.size(); i++) {
            sb.append(atracciones.get(i).toJson());
            if (i < atracciones.size() - 1) sb.append(",");
        }
        sb.append("]");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Crea un TiquetesFastPass desde JSON.
     * @param json Cadena JSON
     * @return Instancia de TiquetesFastPass
     */
    public static TiquetesFastPass fromJson(String json) {
        String id = extractStringValue(json, "id");
        boolean utilizado = extractBooleanValue(json, "utilizado");
        int descuento = extractIntValue(json, "descuento");
        Tipo tipo = Tipo.valueOf(extractStringValue(json, "tipo"));
        Date fechaEntrada = extractDateValue(json, "fechaEntrada");
        List<Atraccion> atracciones = extractAtracciones(json);
        
        TiquetesFastPass tfp = new TiquetesFastPass(id, tipo, atracciones, descuento, fechaEntrada);
        tfp.setUtilizado(utilizado);
        return tfp;
    }

    // Métodos auxiliares mejorados para extracción JSON
    private static int extractIntValue(String json, String key) {
        Pattern p = Pattern.compile("\"" + key + "\"\\s*:\\s*(\\d+)");
        Matcher m = p.matcher(json);
        if (m.find()) return Integer.parseInt(m.group(1));
        throw new IllegalArgumentException("No se encontró el campo entero: " + key);
    }

    private static boolean extractBooleanValue(String json, String key) {
        Pattern p = Pattern.compile("\"" + key + "\"\\s*:\\s*(true|false)");
        Matcher m = p.matcher(json);
        if (m.find()) return Boolean.parseBoolean(m.group(1));
        throw new IllegalArgumentException("No se encontró el campo booleano: " + key);
    }

    private static String extractStringValue(String json, String key) {
        Pattern p = Pattern.compile("\"" + key + "\"\\s*:\\s*\"([^\"]*)\"");
        Matcher m = p.matcher(json);
        if (m.find()) return m.group(1);
        throw new IllegalArgumentException("No se encontró el campo string: " + key);
    }

    private static Date extractDateValue(String json, String key) {
        String fechaStr = extractStringValue(json, key);
        try {
            return DATE_FORMAT.parse(fechaStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido para " + key + ": " + fechaStr);
        }
    }

    private static List<Atraccion> extractAtracciones(String json) {
        List<Atraccion> atracciones = new ArrayList<>();
        
        // Encontrar el array de atracciones
        String arrayRegex = "\"accesoAtracciones\"\\s*:\\s*\\[(.*?)\\]";
        Pattern arrayPattern = Pattern.compile(arrayRegex, Pattern.DOTALL);
        Matcher arrayMatcher = arrayPattern.matcher(json);
        
        if (arrayMatcher.find()) {
            String arrayContent = arrayMatcher.group(1).trim();
            
            if (!arrayContent.isEmpty()) {
                // Separar objetos JSON individuales
                List<String> jsonObjects = splitJsonObjects(arrayContent);
                for (String jsonObj : jsonObjects) {
                    try {
                        atracciones.add(Atraccion.fromJson(jsonObj.trim()));
                    } catch (Exception e) {
                        System.err.println("Error parseando atracción: " + e.getMessage());
                    }
                }
            }
        }
        
        return atracciones;
    }

    private static List<String> splitJsonObjects(String arrayContent) {
        List<String> objects = new ArrayList<>();
        int level = 0;
        int start = 0;
        
        for (int i = 0; i < arrayContent.length(); i++) {
            char c = arrayContent.charAt(i);
            
            if (c == '{') {
                if (level == 0) start = i;
                level++;
            } else if (c == '}') {
                level--;
                if (level == 0) {
                    objects.add(arrayContent.substring(start, i + 1));
                }
            }
        }
        
        return objects;
    }

    /**
     * Escapa caracteres especiales para JSON
     */
    private static String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
}