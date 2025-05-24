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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"clase\":\"TiquetesFastPass\",");
        sb.append("\"id\":\"").append(getId()).append("\",");
        sb.append("\"utilizado\":").append(isUtilizado()).append(",");
        sb.append("\"descuento\":").append(getDescuento()).append(",");
        sb.append("\"tipo\":\"").append(getTipo().toString()).append("\",");
        sb.append("\"fechaEntrada\":\"").append(sdf.format(fechaEntrada)).append("\",");
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
        String id = extractString(json, "id");
        boolean utilizado = Boolean.parseBoolean(extractString(json, "utilizado"));
        int descuento = Integer.parseInt(extractString(json, "descuento"));
        Tipo tipo = Tipo.valueOf(extractString(json, "tipo"));
        String fechaStr = extractString(json, "fechaEntrada");

        Date fechaEntrada;
        try {
            fechaEntrada = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr);
        } catch (ParseException e) {
            fechaEntrada = new Date();
        }

        List<Atraccion> atracciones = extractAtracciones(json);
        TiquetesFastPass tfp = new TiquetesFastPass(id, tipo, atracciones, descuento, fechaEntrada);
        tfp.setUtilizado(utilizado);
        return tfp;
    }

    private static String extractString(String json, String key) {
        Pattern pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*\"?([^\"]+?)\"?(,|\\})");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) return matcher.group(1);
        throw new IllegalArgumentException("No se pudo extraer el campo: " + key);
    }

    private static List<Atraccion> extractAtracciones(String json) {
        List<Atraccion> atracciones = new ArrayList<>();
        int inicio = json.indexOf("[");
        int fin = json.lastIndexOf("]");
        if (inicio > 0 && fin > inicio) {
            String array = json.substring(inicio + 1, fin).trim();
            if (!array.isEmpty()) {
                int level = 0, start = 0;
                for (int i = 0; i < array.length(); i++) {
                    char c = array.charAt(i);
                    if (c == '{') level++;
                    else if (c == '}') {
                        level--;
                        if (level == 0) {
                            atracciones.add(Atraccion.fromJson(array.substring(start, i + 1)));
                            start = i + 1;
                            while (start < array.length() && (array.charAt(start) == ',' || Character.isWhitespace(array.charAt(start)))) {
                                start++;
                            }
                            i = start - 1;
                        }
                    }
                }
            }
        }
        return atracciones;
    }
}