package modelo.persistencia;

import modelo.tiquetes.TiquetesNormales;
import modelo.tiquetes.TiquetesTemporada;
import modelo.tiquetes.TiquetesFastPass;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Clase especializada en persistir la lista de tiquetes en un archivo JSON.
 * Maneja subclases de TiquetesNormales según el campo "clase" en el JSON.
 */
public class PersistenceTiquetes extends JSONPersistenceManager {
    private static final String ARCHIVO_TIQUETES = "tiquetes.json";
    
    public PersistenceTiquetes(String rutaAlmacenamiento) {
        super(rutaAlmacenamiento);
    }

    /**
     * Guarda la lista de tiquetes (normales, temporada, fastpass...) en JSON.
     */
    public boolean guardarListaTiquetes(List<TiquetesNormales> lista) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"tiquetes\":[");
        for (int i = 0; i < lista.size(); i++) {
            // Cada subclase implementa toJson() incluyendo "clase":"NombreDeLaClase"
            sb.append(lista.get(i).toJson());
            if (i < lista.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]}");
        return guardarArchivo(ARCHIVO_TIQUETES, sb.toString());
    }

    /**
     * Carga y reconstruye la lista de tiquetes, instanciando la subclase correcta.
     */
    public List<TiquetesNormales> cargarListaTiquetes() {
        String contenido = cargarArchivo(ARCHIVO_TIQUETES);
        List<TiquetesNormales> lista = new ArrayList<>();
        if (contenido == null || contenido.isEmpty()) {
            return lista;
        }

        // Extraer el array de objetos JSON
        int inicio = contenido.indexOf('[');
        int fin    = contenido.lastIndexOf(']');
        if (inicio < 0 || fin < 0) {
            return lista;
        }
        String arrayContent = contenido.substring(inicio + 1, fin).trim();
        if (arrayContent.isEmpty()) {
            return lista;
        }

        // Separar cada objeto JSON completo
        List<String> objetosJson = new ArrayList<>();
        int level = 0, objetoInicio = 0;
        for (int i = 0; i < arrayContent.length(); i++) {
            char c = arrayContent.charAt(i);
            if (c == '{') level++;
            else if (c == '}') {
                level--;
                if (level == 0) {
                    objetosJson.add(arrayContent.substring(objetoInicio, i + 1));
                    // Avanzar al inicio del siguiente objeto
                    objetoInicio = i + 1;
                    while (objetoInicio < arrayContent.length() &&
                          (arrayContent.charAt(objetoInicio) == ',' ||
                           Character.isWhitespace(arrayContent.charAt(objetoInicio)))) {
                        objetoInicio++;
                    }
                    i = objetoInicio - 1;
                }
            }
        }

        // Para cada JSON, instanciar la subclase adecuada
        for (String objStr : objetosJson) {
            String clase = extraerClase(objStr);
            TiquetesNormales tiquete = null;
            switch (clase) {
                case "TiquetesTemporada":
                    tiquete = TiquetesTemporada.fromJson(objStr);
                    break;
                case "TiquetesFastPass":
                    tiquete = TiquetesFastPass.fromJson(objStr);
                    break;
                case "TiquetesNormales":
                default:
                    tiquete = TiquetesNormales.fromJson(objStr);
            }
            if (tiquete != null) {
                lista.add(tiquete);
            }
        }

        return lista;
    }

    /**
     * Extrae el valor del campo "clase" de un JSONObject dado en String.
     */
    private String extraerClase(String json) {
        // Busca: "clase" : "NombreDeLaClase"
        Pattern p = Pattern.compile("\"clase\"\\s*:\\s*\"([^\"]+)\"");
        Matcher m = p.matcher(json);
        if (m.find()) {
            return m.group(1);
        }
        // Por defecto, asumimos la clase base
        return "TiquetesNormales";
    }
}