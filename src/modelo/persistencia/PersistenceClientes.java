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
     * Utiliza el método toJson() de cada Cliente.
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
     * Utiliza el método fromJson() de la clase Cliente.
     *
     * @return Lista de clientes o una lista vacía si no se encuentra el archivo.
     */
    public List<Cliente> cargarListaClientes() {
        String contenido = cargarArchivo(ARCHIVO_CLIENTES);
        List<Cliente> lista = new ArrayList<>();
        if (contenido == null || contenido.isEmpty()) {
            return lista;
        }
        
        // Extraer el array de clientes del JSON
        int inicio = contenido.indexOf('[');
        int fin = contenido.lastIndexOf(']');
        if (inicio < 0 || fin < 0) {
            return lista;
        }
        String arrayContent = contenido.substring(inicio + 1, fin).trim();
        if (arrayContent.isEmpty()) {
            return lista;
        }
        
        // Dividir el contenido del array por objetos JSON individuales
        int level = 0;
        int startPos = 0;
        
        for (int i = 0; i < arrayContent.length(); i++) {
            char c = arrayContent.charAt(i);
            if (c == '{') level++;
            else if (c == '}') level--;
            
            // Cuando encontramos el cierre de un objeto JSON completo
            if (c == '}' && level == 0) {
                // Extraer el objeto JSON completo
                String clienteJson = arrayContent.substring(startPos, i + 1).trim();
                try {
                    // Crear el objeto Cliente usando el método fromJson
                    Cliente cliente = Cliente.fromJson(clienteJson);
                    lista.add(cliente);
                } catch (Exception e) {
                    System.err.println("Error al parsear cliente: " + e.getMessage());
                }
                
                // Buscar el inicio del siguiente objeto
                while (i + 1 < arrayContent.length() && arrayContent.charAt(i + 1) != '{') {
                    i++;
                }
                startPos = i + 1;
            }
        }
        
        return lista;
    }
}