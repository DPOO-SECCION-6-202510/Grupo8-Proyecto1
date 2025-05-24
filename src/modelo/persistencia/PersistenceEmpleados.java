package modelo.persistencia;

import modelo.empleados.Empleado;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Date;
import java.text.SimpleDateFormat;

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
            Empleado e = lista.get(i);
            sb.append(e.toJson());  // Asume que Empleado tiene toJson()
            if (i < lista.size()-1) sb.append(",");
        }
        sb.append("]}");
        return guardarArchivo(ARCHIVO_EMPLEADOS, sb.toString());
    }

    public List<Empleado> cargarListaEmpleados() {
        String contenido = cargarArchivo(ARCHIVO_EMPLEADOS);
        List<Empleado> lista = new ArrayList<>();
        if (contenido == null || contenido.isEmpty()) return lista;

        int inicio = contenido.indexOf('[');
        int fin    = contenido.lastIndexOf(']');
        if (inicio < 0 || fin < 0) return lista;

        String arrayContent = contenido.substring(inicio+1, fin).trim();
        if (arrayContent.isEmpty()) return lista;

        // Partir cada objeto JSON
        List<String> objs = new ArrayList<>();
        int depth=0, start=0;
        for (int i=0; i<arrayContent.length(); i++) {
            char c=arrayContent.charAt(i);
            if (c=='{') depth++;
            if (c=='}') {
                depth--;
                if (depth==0) {
                    objs.add(arrayContent.substring(start, i+1));
                    start = i+1;
                    // saltar comas o espacios
                    while (start<arrayContent.length() &&
                          (arrayContent.charAt(start)==',' ||
                           Character.isWhitespace(arrayContent.charAt(start)))) {
                        start++;
                    }
                    i = start-1;
                }
            }
        }

        // Deserializar cada uno
        for (String jsonObj : objs) {
            // Empleado.fromJson debe existir
            try {
                Empleado e = Empleado.fromJson(jsonObj);
                lista.add(e);
            } catch (Exception ex) {
                System.err.println("No se pudo parsear empleado: " + ex.getMessage());
            }
        }
        return lista;
    }
}
