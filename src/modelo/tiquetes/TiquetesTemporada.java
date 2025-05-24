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
 * Representa un tiquete de temporada, que extiende TiquetesNormales.
 * Ofrece acceso ilimitado dentro de un rango de fechas.
 */
public class TiquetesTemporada extends TiquetesNormales {

    private Date fechaInicio;
    private Date fechaCaducidad;

    /**
     * Crea un nuevo tiquete de temporada.
     * @param id Identificador único
     * @param tipo Tipo de tiquete
     * @param accesoAtracciones Lista de atracciones a las que da acceso
     * @param descuento Descuento aplicado
     * @param fechaInicio Fecha de inicio de la temporada
     * @param fechaCaducidad Fecha de caducidad de la temporada
     */
    public TiquetesTemporada(String id, Tipo tipo, List<Atraccion> accesoAtracciones,
                             int descuento, Date fechaInicio, Date fechaCaducidad) {
        super(id, tipo, accesoAtracciones, descuento);
        this.fechaInicio = fechaInicio;
        this.fechaCaducidad = fechaCaducidad;
    }

    /**
     * Muestra por consola el rango de fechas del tiquete de temporada.
     */
    public void getTiquetesTemporada() {
        System.out.println("Tiquete de temporada válido del " + fechaInicio + " al " + fechaCaducidad);
    }

    /**
     * Configura nuevas fechas para el tiquete de temporada.
     * @param nuevaFechaInicio Nueva fecha de inicio
     * @param nuevaFechaCaducidad Nueva fecha de caducidad
     */
    public void setTiquetesTemporada(Date nuevaFechaInicio, Date nuevaFechaCaducidad) {
        this.fechaInicio = nuevaFechaInicio;
        this.fechaCaducidad = nuevaFechaCaducidad;
    }

    public Date getFechaInicio() { return fechaInicio; }

    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaCaducidad() { return fechaCaducidad; }

    public void setFechaCaducidad(Date fechaCaducidad) { this.fechaCaducidad = fechaCaducidad; }

    /**
     * Convierte este objeto en una representación JSON.
     * @return Cadena JSON del objeto
     */
    @Override
    public String toJson() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"clase\":\"TiquetesTemporada\",");
        sb.append("\"id\":\"").append(getId()).append("\",");
        sb.append("\"utilizado\":").append(isUtilizado()).append(",");
        sb.append("\"descuento\":").append(getDescuento()).append(",");
        sb.append("\"tipo\":\"").append(getTipo().toString()).append("\",");
        sb.append("\"fechaInicio\":\"").append(sdf.format(fechaInicio)).append("\",");
        sb.append("\"fechaCaducidad\":\"").append(sdf.format(fechaCaducidad)).append("\",");
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
     * Crea un objeto TiquetesTemporada desde una cadena JSON.
     * @param json Representación JSON del objeto
     * @return Instancia de TiquetesTemporada
     */
    public static TiquetesTemporada fromJson(String json) {
        String id = extractString(json, "id");
        boolean utilizado = Boolean.parseBoolean(extractString(json, "utilizado"));
        int descuento = Integer.parseInt(extractString(json, "descuento"));
        Tipo tipo = Tipo.valueOf(extractString(json, "tipo"));
        String inicioStr = extractString(json, "fechaInicio");
        String finStr = extractString(json, "fechaCaducidad");

        Date inicio, fin;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            inicio = sdf.parse(inicioStr);
            fin = sdf.parse(finStr);
        } catch (ParseException e) {
            inicio = new Date();
            fin = new Date();
        }

        List<Atraccion> atracciones = extractAtracciones(json);
        TiquetesTemporada tt = new TiquetesTemporada(id, tipo, atracciones, descuento, inicio, fin);
        tt.setUtilizado(utilizado);
        return tt;
    }

    /**
     * Extrae un valor de tipo cadena de un campo JSON.
     * @param json El JSON completo
     * @param key La clave que se desea extraer
     * @return Valor como String
     */
    private static String extractString(String json, String key) {
        Pattern pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*\"?([^\"]+?)\"?(,|\\})");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) return matcher.group(1);
        throw new IllegalArgumentException("No se pudo extraer el campo: " + key);
    }

    /**
     * Extrae la lista de atracciones embebidas en un arreglo JSON.
     * @param json JSON con la lista de atracciones
     * @return Lista de objetos Atraccion
     */
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
                            while (start < array.length() &&
                                   (array.charAt(start) == ',' || Character.isWhitespace(array.charAt(start)))) {
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