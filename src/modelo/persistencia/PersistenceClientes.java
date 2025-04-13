package modelo.persistencia;

import modelo.empleados.Cliente;
import java.util.List;
import java.util.ArrayList;

/**
 * Clase especializada en persistir la lista de clientes en un archivo JSON.
 */
public class PersistenceClientes extends JSONPersistenceManager {

    private static final String ARCHIVO_CLIENTES = "clientes.json";
    
    public PersistenceClientes(String rutaAlmacenamiento) {
        super(rutaAlmacenamiento);
    }
    
    /**
     * Guarda la lista de clientes en el archivo JSON.
     * Se asume que cada Cliente implementa el método toJson().
     *
     * @param lista Lista de clientes a guardar.
     * @return true si se guardó correctamente, false en caso contrario.
     */
    public boolean guardarListaClientes(List<Cliente> lista) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"clientes\":[");
        for (int i = 0; i < lista.size(); i++) {
            Cliente cliente = lista.get(i);
            sb.append(cliente.toJson());
            if (i < lista.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]}");
        return guardarArchivo(ARCHIVO_CLIENTES, sb.toString());
    }
    
    /**
     * Carga la lista de clientes desde el archivo JSON.
     * Se asume que el contenido tiene el formato:
     * {"clientes":[ { ... }, { ... } ]}
     *
     * @return Lista de clientes o una lista vacía si no se encuentra el archivo.
     */
    public List<Cliente> cargarListaClientes() {
        String contenido = cargarArchivo(ARCHIVO_CLIENTES);
        List<Cliente> lista = new ArrayList<>();
        if (contenido == null || contenido.isEmpty()) {
            return lista;
        }
        // Aquí se asume un parseo sencillo: se separan los objetos mediante delimitadores simples.
        // Este parseo es muy básico y para un modelo beta.
        int inicio = contenido.indexOf('[');
        int fin = contenido.lastIndexOf(']');
        if (inicio < 0 || fin < 0) {
            return lista;
        }
        String arrayContent = contenido.substring(inicio + 1, fin).trim();
        if (arrayContent.isEmpty()) {
            return lista;
       C
    }
}