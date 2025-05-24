package modelo.tiquetes;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import modelo.atraccion.Atraccion;

/**
 * Representa un tiquete normal para el parque.
 * Incluye atributos comunes como id, si fue utilizado, 
 * acceso a atracciones, descuento y el tipo de tiquete.
 */
public class TiquetesNormales {

    private String id;
    private boolean utilizado;
    private List<Atraccion> accesoAtracciones;
    private int descuento;
    private Tipo tipo;

    /**
     * Crea un nuevo tiquete normal.
     * @param id Identificador único
     * @param tipo Tipo de tiquete
     * @param accesoAtracciones Lista de atracciones accesibles
     * @param descuento Porcentaje de descuento
     */
    public TiquetesNormales(String id, Tipo tipo, List<Atraccion> accesoAtracciones, int descuento) {
        this.id = id;
        this.tipo = tipo;
        this.accesoAtracciones = accesoAtracciones;
        this.descuento = descuento;
        this.utilizado = false;
    }

    /**
     * Imprime un mensaje simulando la compra del tiquete.
     */
    public void comprar() {
        System.out.println("Se ha comprado el tiquete con ID: " + id + " de tipo " + tipo);
    }

    /**
     * Simula guardar la información del tiquete.
     */
    public void guardarCompra() {
        System.out.println("Guardando la información del tiquete " + id + " en el sistema...");
    }

    // Getters y setters
    public String getId() { return id; }
    public boolean isUtilizado() { return utilizado; }
    public void setUtilizado(boolean utilizado) { this.utilizado = utilizado; }
    public List<Atraccion> getAccesoAtracciones() { return accesoAtracciones; }
    public void setAccesoAtracciones(List<Atraccion> accesoAtracciones) { this.accesoAtracciones = accesoAtracciones; }
    public int getDescuento() { return descuento; }
    public void setDescuento(int descuento) { this.descuento = descuento; }
    public Tipo getTipo() { return tipo; }
    public void setTipo(Tipo tipo) { this.tipo = tipo; }

    /**
     * Convierte este objeto a formato JSON.
     * @return Cadena JSON del objeto
     */
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"clase\":\"TiquetesNormales\",");
        sb.append("\"id\":\"").append(id).append("\",");
        sb.append("\"utilizado\":").append(utilizado).append(",");
        sb.append("\"descuento\":").append(descuento).append(",");
        sb.append("\"tipo\":\"").append(tipo.toString()).append("\",");
        sb.append("\"accesoAtracciones\":[");
        for (int i = 0; i < accesoAtracciones.size(); i++) {
            sb.append(accesoAtracciones.get(i).toJson());
            if (i < accesoAtracciones.size() - 1) sb.append(",");
        }
        sb.append("]");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Crea un objeto TiquetesNormales desde su representación JSON.
     * @param json Cadena JSON del objeto
     * @return Instancia de TiquetesNormales
     */
    public static TiquetesNormales fromJson(String json) {
        String id = extractString(json, "id");
        boolean utilizado = Boolean.parseBoolean(extractString(json, "utilizado"));
        int descuento = Integer.parseInt(extractString(json, "descuento"));
        Tipo tipo = Tipo.valueOf(extractString(json, "tipo"));

        List<Atraccion> atracciones = new ArrayList<>();
        int inicio = json.indexOf("[");
        int fin = json.lastIndexOf("]");
        if (inicio > 0 && fin > inicio) {
            String arrayContent = json.substring(inicio + 1, fin).trim();
            if (!arrayContent.isEmpty()) {
                int contador = 0, start = 0;
                for (int i = 0; i < arrayContent.length(); i++) {
                    char c = arrayContent.charAt(i);
                    if (c == '{') contador++;
                    else if (c == '}') {
                        contador--;
                        if (contador == 0) {
                            atracciones.add(Atraccion.fromJson(arrayContent.substring(start, i + 1)));
                            start = i + 1;
                            while (start < arrayContent.length() &&
                                   (arrayContent.charAt(start) == ',' || Character.isWhitespace(arrayContent.charAt(start)))) {
                                start++;
                            }
                            i = start - 1;
                        }
                    }
                }
            }
        }

        TiquetesNormales t = new TiquetesNormales(id, tipo, atracciones, descuento);
        t.setUtilizado(utilizado);
        return t;
    }

    private static String extractString(String json, String key) {
        Pattern pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*\"?([^\"]+?)\"?(,|\\})");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) return matcher.group(1);
        throw new IllegalArgumentException("No se pudo extraer el campo: " + key);
    }
}