package modelo.persistencia;

import modelo.atraccion.Atraccion;
import modelo.atraccion.AtraccionCultural;
import modelo.atraccion.AtraccionMecanica;
import java.util.List;
import java.util.ArrayList;

/**
 * Clase especializada en persistir la lista de atracciones en un archivo JSON.
 */
public class PersistenceAtracciones extends JSONPersistenceManager {
    private static final String ARCHIVO_ATRACCIONES = "atracciones.json";
    
    public PersistenceAtracciones(String rutaAlmacenamiento) {
        super(rutaAlmacenamiento);
    }
    
    public boolean guardarListaAtracciones(List<Atraccion> lista) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"atracciones\":[");
        for (int i = 0; i < lista.size(); i++) {
            Atraccion a = lista.get(i);
            sb.append(a.toJson());  // Se asume que cada Atraccion (o sus subclases) tiene un método toJson()
            if (i < lista.size()-1) {
                sb.append(",");
            }
        }
        sb.append("]}");
        return guardarArchivo(ARCHIVO_ATRACCIONES, sb.toString());
    }
    
    public List<Atraccion> cargarListaAtracciones() {
        String contenido = cargarArchivo(ARCHIVO_ATRACCIONES);
        List<Atraccion> lista = new ArrayList<>();
        
        if (contenido == null || contenido.isEmpty()) {
            return lista;
        }
        
        int inicio = contenido.indexOf('[');
        int fin = contenido.lastIndexOf(']');
        if (inicio < 0 || fin < 0) {
            return lista;
        }
        
        String arrayContent = contenido.substring(inicio+1, fin).trim();
        if (arrayContent.isEmpty()){
            return lista;
        }
        
        // Dividir el contenido del array en objetos individuales
        List<String> objetosJson = new ArrayList<>();
        int contador = 0;
        int objetoInicio = 0;
        
        for (int i = 0; i < arrayContent.length(); i++) {
            char c = arrayContent.charAt(i);
            if (c == '{') contador++;
            else if (c == '}') {
                contador--;
                if (contador == 0) {
                    objetosJson.add(arrayContent.substring(objetoInicio, i + 1));
                    objetoInicio = i + 1;
                    // Saltar la coma después del objeto
                    while (objetoInicio < arrayContent.length() && 
                          (arrayContent.charAt(objetoInicio) == ',' || 
                           Character.isWhitespace(arrayContent.charAt(objetoInicio)))) {
                        objetoInicio++;
                    }
                    i = objetoInicio - 1; // Ajustar el índice
                }
            }
        }
        
        // Procesar cada objeto JSON
        for (String objStr : objetosJson) {
            // Identificar el tipo de atracción
            String tipo = extraerTipoAtraccion(objStr);
            
            // Crear la instancia apropiada según el tipo
            Atraccion atraccion = null;
            if ("CULTURAL".equals(tipo)) {
                atraccion = AtraccionCultural.fromJson(objStr);
            } else if ("MECANICA".equals(tipo)) {
                atraccion = AtraccionMecanica.fromJson(objStr);
            } else {
                // Si no podemos identificar claramente el tipo, no lo añadimos a la lista
                System.err.println("Tipo de atracción desconocido: " + tipo + ". No se puede cargar el objeto: " + objStr);
            }
            
            if (atraccion != null) {
                lista.add(atraccion);
            }
        }
        
        return lista;
    }
    
    /**
     * Extrae el tipo de atracción desde un objeto JSON.
     * Basado en el campo "tipo" que debe estar incluido en el JSON de cada atracción.
     * 
     * @param json String representando un objeto JSON de atracción
     * @return El tipo de atracción ("CULTURAL", "MECANICA", etc.)
     */
    private String extraerTipoAtraccion(String json) {
        String pattern = "\"tipo\"\\s*:\\s*\"([^\"]+)\"";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = p.matcher(json);
        if (m.find()) {
            return m.group(1);
        }
        return "DESCONOCIDO";
    }
}