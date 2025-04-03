package modelo.atraccion;

import java.util.Map;
import java.util.Date;

/**
 * Clase que representa una atracción mecánica.
 * Extiende de Atraccion e incorpora atributos y lógica
 * específicos para atracciones que requieren validación
 * de altura, peso y otros parámetros de seguridad.
 */
public class AtraccionMecanica extends Atraccion {

    // Atributos específicos para atracciones mecánicas
    private double limiteMinAltura;
    private double limiteMaxAltura;
    private double limiteMinPeso;
    private double limiteMaxPeso;
    private String nivelRiesgo;         // Ejemplo: "Medio" o "Alto"
    private String contraindicaciones;  // Descripción de contraindicaciones
    private String sensorEstado;        // Estado de sensores (por ejemplo, "NORMAL", "ALARMA")

    /**
     * Constructor para AtraccionMecanica.
     *
     * @param id                Identificador único.
     * @param nombre            Nombre de la atracción.
     * @param capacidad         Capacidad máxima.
     * @param ubicacion         Ubicación en el parque.
     * @param nivelExclusividad Nivel de exclusividad ("Familiar", "Oro", "Diamante").
     * @param limiteMinAltura   Altura mínima permitida.
     * @param limiteMaxAltura   Altura máxima permitida.
     * @param limiteMinPeso     Peso mínimo permitido.
     * @param limiteMaxPeso     Peso máximo permitido.
     * @param nivelRiesgo       Nivel de riesgo ("Medio", "Alto").
     * @param contraindicaciones Contraindicaciones relevantes.
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
        this.sensorEstado = "NORMAL"; // Valor inicial
    }

    // Getters y Setters
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

    public String getSensorEstado() {
        return sensorEstado;
    }

    public void setSensorEstado(String sensorEstado) {
        this.sensorEstado = sensorEstado;
    }

    /**
     * Valida el acceso a la atracción mecánica verificando que la altura y el peso
     * del usuario estén dentro de los límites establecidos.
     *
     * @param parametros Mapa con claves "altura" y "peso".
     * @return true si ambos parámetros están en rango, false en caso contrario.
     */
    @Override
    public boolean validarAcceso(Map<String, Object> parametros) {
        if (parametros.containsKey("altura") && parametros.containsKey("peso")) {
            Object alturaObj = parametros.get("altura");
            Object pesoObj = parametros.get("peso");
            if (alturaObj instanceof Number && pesoObj instanceof Number) {
                double altura = ((Number) alturaObj).doubleValue();
                double peso = ((Number) pesoObj).doubleValue();
                boolean alturaValida = (altura >= limiteMinAltura && altura <= limiteMaxAltura);
                boolean pesoValido = (peso >= limiteMinPeso && peso <= limiteMaxPeso);
                if (!alturaValida) {
                    System.out.println("Altura fuera de rango para la atracción " + nombre);
                }
                if (!pesoValido) {
                    System.out.println("Peso fuera de rango para la atracción " + nombre);
                }
                return alturaValida && pesoValido;
            }
        }
        return false;
    }
}