package modelo.empleados;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cliente extends Usuario {
    private List<String> historialCompras;
    private String riesgosSalud;
    private double altura;
    private double peso;

    /**
     * Constructor principal incluyendo salud y medidas
     */
    public Cliente(int id,
                   String nombre,
                   String login,
                   String password,
                   TipoUsuario tipo,
                   String riesgosSalud,
                   double altura,
                   double peso) {
        super(id, nombre, login, password, tipo);
        this.riesgosSalud = riesgosSalud;
        this.altura = altura;
        this.peso = peso;
        this.historialCompras = new ArrayList<>();
    }

    /**
     * Constructor para deserialización desde JSON
     */
    public Cliente(int id,
                   String nombre,
                   String login,
                   String password,
                   TipoUsuario tipo,
                   String riesgosSalud,
                   double altura,
                   double peso,
                   List<String> historialCompras) {
        super(id, nombre, login, password, tipo);
        this.riesgosSalud = riesgosSalud;
        this.altura = altura;
        this.peso = peso;
        this.historialCompras = historialCompras != null
            ? historialCompras
            : new ArrayList<>();
    }

    // Getters y Setters
    public List<String> getHistorialCompras() {
        return historialCompras;
    }

    public void setHistorialCompras(List<String> historialCompras) {
        this.historialCompras = historialCompras;
    }

    public String getRiesgosSalud() {
        return riesgosSalud;
    }

    public void setRiesgosSalud(String riesgosSalud) {
        this.riesgosSalud = riesgosSalud;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    // Métodos
    @Override
    public void mostrarInformacion() {
        System.out.println("Cliente: " + getNombre());
        System.out.println("ID: " + getId());
        System.out.println("Riesgos de salud: " + riesgosSalud);
        System.out.println("Altura: " + altura + " m, Peso: " + peso + " kg");
        System.out.println("Historial de Compras: " + historialCompras);
    }

    public void consultarHistorial() {
        System.out.println("Historial de Compras de " + getNombre() + ":");
        if (historialCompras.isEmpty()) {
            System.out.println("No hay compras registradas.");
        } else {
            for (String compra : historialCompras) {
                System.out.println("- " + compra);
            }
        }
    }

    public void comprarTiquete(String descripcionCompra) {
        historialCompras.add(descripcionCompra);
        System.out.println("Compra realizada: " + descripcionCompra);
    }

    /**
     * Convierte este objeto a JSON
     */
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"id\":").append(getId()).append(",");
        sb.append("\"nombre\":\"").append(getNombre()).append("\",");
        sb.append("\"login\":\"").append(getLogin()).append("\",");
        sb.append("\"password\":\"").append(getPassword()).append("\",");
        sb.append("\"tipo\":\"").append(getTipo()).append("\",");
        sb.append("\"riesgosSalud\":\"").append(riesgosSalud).append("\",");
        sb.append("\"altura\":").append(altura).append(",");
        sb.append("\"peso\":").append(peso).append(",");
        sb.append("\"historialCompras\":[");
        for (int i = 0; i < historialCompras.size(); i++) {
            sb.append("\"").append(historialCompras.get(i)).append("\"");
            if (i < historialCompras.size() - 1) sb.append(",");
        }
        sb.append("]");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Crea un Cliente a partir de JSON
     */
    public static Cliente fromJson(String json) {
        int id = extractIntValue(json, "id");
        String nombre = extractStringValue(json, "nombre");
        String login = extractStringValue(json, "login");
        String password = extractStringValue(json, "password");
        TipoUsuario tipo = TipoUsuario.valueOf(extractStringValue(json, "tipo"));
        String riesgos = extractStringValue(json, "riesgosSalud");
        double altura = extractDoubleValue(json, "altura");
        double peso = extractDoubleValue(json, "peso");
        List<String> historial = extractStringListValue(json, "historialCompras");
        return new Cliente(id, nombre, login, password, tipo, riesgos, altura, peso, historial);
    }

    // Métodos auxiliares de extracción JSON
    private static int extractIntValue(String json, String key) {
        Pattern p = Pattern.compile("\"" + key + "\"\\s*:\\s*(\\d+)");
        Matcher m = p.matcher(json);
        if (m.find()) return Integer.parseInt(m.group(1));
        throw new IllegalArgumentException("No se encontró " + key);
    }

    private static double extractDoubleValue(String json, String key) {
        Pattern p = Pattern.compile("\"" + key + "\"\\s*:\\s*([\\d.]+)");
        Matcher m = p.matcher(json);
        if (m.find()) return Double.parseDouble(m.group(1));
        throw new IllegalArgumentException("No se encontró " + key);
    }

    private static String extractStringValue(String json, String key) {
        Pattern p = Pattern.compile("\"" + key + "\"\\s*:\\s*\"([^\"]*)\"");
        Matcher m = p.matcher(json);
        if (m.find()) return m.group(1);
        throw new IllegalArgumentException("No se encontró " + key);
    }

    private static List<String> extractStringListValue(String json, String key) {
        List<String> result = new ArrayList<>();
        String arrayRegex = "\"" + key + "\"\\s*:\\s*\\[(.*?)\\]";
        Pattern p = Pattern.compile(arrayRegex, Pattern.DOTALL);
        Matcher m = p.matcher(json);
        if (m.find()) {
            String content = m.group(1);
            Pattern itemP = Pattern.compile("\\\"([^\\\"]*)\\\"");
            Matcher itemM = itemP.matcher(content);
            while (itemM.find()) result.add(itemM.group(1));
        }
        return result;
    }
}