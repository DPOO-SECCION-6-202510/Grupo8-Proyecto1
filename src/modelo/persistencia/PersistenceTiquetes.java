package modelo.persistencia;

import modelo.tiquete.TiquetesNormales;
import java.util.List;
import java.util.ArrayList;

/**
 * Clase especializada en persistir la lista de tiquetes en un archivo JSON.
 */
public class PersistenceTiquetes extends JSONPersistenceManager {

    private static final String ARCHIVO_TIQUETES = "tiquetes.json";
    
    public PersistenceTiquetes(String rutaAlmacenamiento) {
        super(rutaAlmacenamiento);
    }
    
    public boolean guardarListaTiquetes(List<TiquetesNormales> lista) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"tiquetes\":[");
        for (int i = 0; i < lista.size(); i++) {
            TiquetesNormales t = lista.get(i);
            sb.append(t.toJson()); // Se asume que TiquetesNormales tiene un método toJson()
            if (i < lista.size()-1) {
                sb.append(",");
            }
        }
        sb.append("]}");
        return guardarArchivo(ARCHIVO_TIQUETES, sb.toString());
    }
    
    public List<TiquetesNormales> cargarListaTiquetes() {
        String contenido = cargarArchivo(ARCHIVO_TIQUETES);
        List<TiquetesNormales> lista = new ArrayList<>();
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
        String[] objetos = arrayContent.split("},");
        for (int i = 0; i < objetos.length; i++) {
            String objStr = objetos[i].trim();
            if (!objStr.endsWith("}")) {
                objStr += "}";
            }
            TiquetesNormales tiquete = modelo.tiquete.TiquetesNormales.fromJson(objStr);
            if (tiquete != null) {
                lista.add(tiquete);
            }
        }
        return lista;
    }
}