package modelo.persistencia;

import modelo.empleados.Empleado;
// Importar las subclases de empleados necesarias
// import modelo.empleados.EmpleadoOperativo;
// import modelo.empleados.EmpleadoAdministrativo;
// etc.
import java.util.List;
import java.util.ArrayList;

/**
 * Clase especializada en persistir la lista de empleados en un archivo JSON.
 */
public class PersistenceEmpleados extends JSONPersistenceManager {
    private static final String ARCHIVO_EMPLEADOS = "empleados.json";
    
    public PersistenceEmpleados(String rutaAlmacenamiento) {
        super(rutaAlmacenamiento);
    }
    
    public boolean guardarListaEmpleados(List<Empleado> lista) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"empleados\":[");
        for (int i = 0; i < lista.size(); i++) {
            Empleado empleado = lista.get(i);
            sb.append(empleado.toJson());
            if (i < lista.size()-1) {
                sb.append(",");
            }
        }
        sb.append("]}");
        return guardarArchivo(ARCHIVO_EMPLEADOS, sb.toString());
    }
    
    public List<Empleado> cargarListaEmpleados() {
        String contenido = cargarArchivo(ARCHIVO_EMPLEADOS);
        List<Empleado> lista = new ArrayList<>();
        
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
        // Esta lógica es más robusta para manejar objetos JSON anidados
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
            // Identificar el tipo de empleado
            String tipo = extraerTipoEmpleado(objStr);
            
            // Crear la instancia apropiada según el tipo
            Empleado empleado = null;
            
            // Descomentar y adaptar estos bloques cuando se conozcan los tipos específicos
            /*
            if ("OPERATIVO".equals(tipo)) {
                empleado = EmpleadoOperativo.fromJson(objStr);
            } else if ("ADMINISTRATIVO".equals(tipo)) {
                empleado = EmpleadoAdministrativo.fromJson(objStr);
            } else {
                // Otros tipos de empleados
            }
            */
            
            // IMPORTANTE: Este es un marcador de posición. Debes ajustar esta lógica
            // para manejar los tipos específicos de empleados en tu aplicación.
            System.err.println("Tipo de empleado detectado: " + tipo);
            System.err.println("ADVERTENCIA: La función cargarListaEmpleados() necesita implementación específica para cada tipo.");
            
            if (empleado != null) {
                lista.add(empleado);
            }
        }
        
        return lista;
    }
    
    /**
     * Extrae el tipo de empleado desde un objeto JSON.
     * Basado en el campo "tipo" que debe estar incluido en el JSON de cada empleado.
     * 
     * @param json String representando un objeto JSON de empleado
     * @return El tipo de empleado (ej: "OPERATIVO", "ADMINISTRATIVO", etc.)
     */
    private String extraerTipoEmpleado(String json) {
        String pattern = "\"tipo\"\\s*:\\s*\"([^\"]+)\"";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = p.matcher(json);
        if (m.find()) {
            return m.group(1);
        }
        return "DESCONOCIDO";
    }
}