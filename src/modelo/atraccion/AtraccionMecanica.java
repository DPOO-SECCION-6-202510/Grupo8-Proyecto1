package modelo.atraccion;

import java.util.Date;
import java.util.Map;

/**
 * Representa una atracción mecánica.
 * Incluye validaciones específicas para altura y peso.
 */
public class AtraccionMecanica extends Atraccion {

    // Atributos específicos para atracciones mecánicas
    private double limiteMinAltura;
    private double limiteMaxAltura;
    private double limiteMinPeso;
    private double limiteMaxPeso;
    private String nivelRiesgo; // Ej.: "Medio" o "Alto"
    private String contraindicaciones; // Descripción de contraindicaciones

    /**
     * Constructor para AtraccionMecanica.
     *
     * @param id Identificador único.
     * @param nombre Nombre de la atracción.
     * @param capacidad Capacidad máxima.
     * @param ubicacion Ubicación en el parque.
     * @param nivelExclusividad Nivel de exclusividad.
     * @param limiteMinAltura Altura mínima permitida.
     * @param limiteMaxAltura Altura máxima permitida.
     * @param limiteMinPeso Peso mínimo permitido.
     * @param limiteMaxPeso Peso máximo permitido.
     * @param nivelRiesgo Nivel de riesgo ("Medio" o "Alto").
     * @param contraindicaciones Contraindicaciones de salud.
     */
    public AtraccionMecanica(int id, String nombre, int capacidad, String ubicacion, String nivelExclusividad,
                            double limiteMinAltura, double limiteMaxAltura,
                            double limiteMinPeso, double limiteMaxPeso,
                            String nivelRiesgo, String contraindicaciones) {
        super(id, nombre, capacidad, ubicacion, nivelExclusividad);
        this.limiteMinAltura = limiteMinAltura;
        this.limiteMaxAltura = limiteMaxAltura;
        this.limiteMinPeso = limiteMinPeso;
        this.limiteMaxPeso = limiteMaxPeso;
        this.nivelRiesgo = nivelRiesgo;
        this.contraindicaciones = contraindicaciones;
    }
    
    /**
     * Constructor para deserialización desde JSON.
     */
    private AtraccionMecanica(int id, String nombre, int capacidad, String ubicacion, 
                             String nivelExclusividad, String estadoOperativo, Date mantenimientoProgramado,
                             double limiteMinAltura, double limiteMaxAltura,
                             double limiteMinPeso, double limiteMaxPeso,
                             String nivelRiesgo, String contraindicaciones) {
        super(id, nombre, capacidad, ubicacion, nivelExclusividad, estadoOperativo, mantenimientoProgramado);
        this.limiteMinAltura = limiteMinAltura;
        this.limiteMaxAltura = limiteMaxAltura;
        this.limiteMinPeso = limiteMinPeso;
        this.limiteMaxPeso = limiteMaxPeso;
        this.nivelRiesgo = nivelRiesgo;
        this.contraindicaciones = contraindicaciones;
    }

    /**
     * Valida el acceso verificando que la altura y el peso del usuario
     * estén dentro de los límites permitidos.
     *
     * @param parametros Mapa con claves "altura" y "peso".
     * @return true si ambos parámetros son válidos, false en caso contrario.
     */
    @Override
    public boolean validarAcceso(Map<String, Object> parametros) {
        if (parametros.containsKey("altura") && parametros.containsKey("peso")) {
            double altura = ((Number) parametros.get("altura")).doubleValue();
            double peso = ((Number) parametros.get("peso")).doubleValue();
            boolean alturaValida = (altura >= limiteMinAltura && altura <= limiteMaxAltura);
            boolean pesoValido = (peso >= limiteMinPeso && peso <= limiteMaxPeso);
            if (!alturaValida) {
                System.out.println("Altura fuera de rango para " + nombre);
            }
            if (!pesoValido) {
                System.out.println("Peso fuera de rango para " + nombre);
            }
            return alturaValida && pesoValido;
        }
        return false;
    }

    // Getters para atributos específicos
    public double getLimiteMinAltura() {
        return limiteMinAltura;
    }

    public double getLimiteMaxAltura() {
        return limiteMaxAltura;
    }

    public double getLimiteMinPeso() {
        return limiteMinPeso;
    }

    public double getLimiteMaxPeso() {
        return limiteMaxPeso;
    }

    public String getNivelRiesgo() {
        return nivelRiesgo;
    }

    public String getContraindicaciones() {
        return contraindicaciones;
    }
    
    /**
     * Define el tipo de atracción para la serialización/deserialización.
     */
    @Override
    protected String getTipoAtraccion() {
        return "MECANICA";
    }
    
    /**
     * Añade los atributos específicos de AtraccionMecanica al JSON.
     */
    @Override
    protected String toJsonEspecifico() {
        StringBuilder sb = new StringBuilder();
        sb.append(",\"limiteMinAltura\":").append(limiteMinAltura);
        sb.append(",\"limiteMaxAltura\":").append(limiteMaxAltura);
        sb.append(",\"limiteMinPeso\":").append(limiteMinPeso);
        sb.append(",\"limiteMaxPeso\":").append(limiteMaxPeso);
        sb.append(",\"nivelRiesgo\":\"").append(nivelRiesgo).append("\"");
        sb.append(",\"contraindicaciones\":\"").append(contraindicaciones).append("\"");
        return sb.toString();
    }
    
    /**
     * Crea una AtraccionMecanica a partir de un string JSON.
     *
     * @param json String en formato JSON con los datos de la atracción mecánica.
     * @return Una nueva instancia de AtraccionMecanica.
     */
    public static AtraccionMecanica fromJson(String json) {
        // Extraer los campos básicos
        int id = extractIntValue(json, "id");
        String nombre = extractStringValue(json, "nombre");
        int capacidad = extractIntValue(json, "capacidad");
        String ubicacion = extractStringValue(json, "ubicacion");
        String nivelExclusividad = extractStringValue(json, "nivelExclusividad");
        String estadoOperativo = extractStringValue(json, "estadoOperativo");
        Date mantenimientoProgramado = extractDateValue(json, "mantenimientoProgramado");
        
        // Extraer los campos específicos
        double limiteMinAltura = extractDoubleValue(json, "limiteMinAltura");
        double limiteMaxAltura = extractDoubleValue(json, "limiteMaxAltura");
        double limiteMinPeso = extractDoubleValue(json, "limiteMinPeso");
        double limiteMaxPeso = extractDoubleValue(json, "limiteMaxPeso");
        String nivelRiesgo = extractStringValue(json, "nivelRiesgo");
        String contraindicaciones = extractStringValue(json, "contraindicaciones");
        
        // Crear y retornar la nueva instancia
        return new AtraccionMecanica(id, nombre, capacidad, ubicacion, nivelExclusividad,
                                   estadoOperativo, mantenimientoProgramado,
                                   limiteMinAltura, limiteMaxAltura,
                                   limiteMinPeso, limiteMaxPeso,
                                   nivelRiesgo, contraindicaciones);
    }
    
    /**
     * Extrae un valor double de un JSON.
     * Este método debe añadirse a la clase Atraccion o duplicarse aquí.
     */
    private static double extractDoubleValue(String json, String key) {
        String pattern = "\"" + key + "\"\\s*:\\s*([\\d.]+)";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = p.matcher(json);
        if (m.find()) {
            return Double.parseDouble(m.group(1));
        }
        throw new IllegalArgumentException("No se encontró el valor para: " + key);
    }
}