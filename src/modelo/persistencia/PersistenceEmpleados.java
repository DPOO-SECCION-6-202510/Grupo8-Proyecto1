package modelo.persistencia;

import modelo.empleados.Empleado;
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
        // Parseo básico similar al de clientes.
        int inicio = contenido.indexOf('[');
        int fin = contenido.lastIndexOf(']');
        if (inicio < 0 || fin < 0) {
            return lista;
        }
        String arrayContent = contenido.substring(inicio+1, fin).trim();
        if (arrayContent.isEmpty()){
            return lista;
        }
        String[] objetos = arrayContent.split("},");
        for (int i = 0; i < objetos.length; i++) {
            String objStr = objetos[i].trim();
            if (!objStr.endsWith("}")) {
                objStr += "}";
            }
            Empleado empleado = Empleado.fromJson(objStr);
            if (empleado != null) {
                lista.add(empleado);
            }
        }
        return lista;
    }
}