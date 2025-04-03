package modelo.atraccion;

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
    private String nivelRiesgo;         // Ej.: "Medio" o "Alto"
    private String contraindicaciones;  // Descripción de contraindicaciones

    /**
     * Constructor para AtraccionMecanica.
     *
     * @param id                Identificador único.
     * @param nombre            Nombre de la atracción.
     * @param capacidad         Capacidad máxima.
     * @param ubicacion         Ubicación en el parque.
     * @param nivelExclusividad Nivel de exclusividad.
     * @param limiteMinAltura   Altura mínima permitida.
     * @param limiteMaxAltura   Altura máxima permitida.
     * @param limiteMinPeso     Peso mínimo permitido.
     * @param limiteMaxPeso     Peso máximo permitido.
     * @param nivelRiesgo       Nivel de riesgo ("Medio" o "Alto").
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
}