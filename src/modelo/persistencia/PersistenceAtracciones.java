package modelo.persistencia;

import modelo.atraccion.Atraccion;
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
        // Un parseo básico similar al de clientes; en un prototipo beta se simplifica.
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
            // Aquí se debe identificar el tipo de atracción para llamar al método de deserialización adecuado.
            // Por simplicidad se asume que todas son del tipo AtraccionCultural.
            Atraccion atraccion = modelo.atraccion.AtraccionCultural.fromJson(objStr);
            if (atraccion != null) {
                lista.add(atraccion);
            }
        }
        return lista;
    }
}