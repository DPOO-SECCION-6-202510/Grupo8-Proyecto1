package modelo.persistencia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Clase base para operaciones básicas de persistencia en formato JSON.
 * Se encarga de escribir y leer cadenas desde archivos ubicados en un directorio.
 */
public class JSONPersistenceManager {

    private String rutaAlmacenamiento;

    /**
     * Constructor.
     * @param rutaAlmacenamiento La ruta del directorio donde se guardarán los archivos JSON.
     */
    public JSONPersistenceManager(String rutaAlmacenamiento) {
        this.rutaAlmacenamiento = rutaAlmacenamiento;
    }
    
    /**
     * Guarda una cadena en un archivo.
     * @param nombreArchivo Nombre del archivo (ej.: "clientes.json").
     * @param contenido Cadena JSON a guardar.
     * @return true si se guardó correctamente, false en caso de error.
     */
    public boolean guardarArchivo(String nombreArchivo, String contenido) {
        try {
            Path path = Paths.get(rutaAlmacenamiento, nombreArchivo);
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            Files.write(path, contenido.getBytes());
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar archivo " + nombreArchivo + ": " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Lee el contenido de un archivo.
     * @param nombreArchivo Nombre del archivo (ej.: "clientes.json").
     * @return La cadena con el contenido o null si ocurre algún error.
     */
    public String cargarArchivo(String nombreArchivo) {
        try {
            Path path = Paths.get(rutaAlmacenamiento, nombreArchivo);
            if (!Files.exists(path)) {
                return null;
            }
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            System.err.println("Error al cargar archivo " + nombreArchivo + ": " + e.getMessage());
            return null;
        }
    }
}