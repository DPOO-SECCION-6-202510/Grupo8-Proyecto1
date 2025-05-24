package modelo.empleados;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Empleado extends Usuario {
    private String cargo;
    private List<Turno> historialTurnos;
    private boolean disponible;
    private HashMap<String, Turno> turno;
    private List<String> certificaciones;

    // Constructor
    public Empleado(int id, String nombre, String login, String password, TipoUsuario tipo, String cargo) {
        super(id, nombre, login, password, tipo);
        this.cargo = cargo;
        this.historialTurnos = new ArrayList<>();
        this.disponible = true;
        this.turno = new HashMap<>();
        this.certificaciones = new ArrayList<>();
    }

    // Getters y Setters
    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public List<Turno> getHistorialTurnos() {
        return historialTurnos;
    }

    public void setHistorialTurnos(List<Turno> historialTurnos) {
        this.historialTurnos = historialTurnos;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public HashMap<String, Turno> getTurno() {
        return turno;
    }

    public void setTurno(HashMap<String, Turno> turno) {
        this.turno = turno;
    }

    public List<String> getCertificaciones() {
        return certificaciones;
    }

    public void setCertificaciones(List<String> certificaciones) {
        this.certificaciones = certificaciones;
    }

    // Métodos
    @Override
    public void mostrarInformacion() {
        System.out.println("Empleado: " + getNombre());
        System.out.println("ID: " + getId());
        System.out.println("Cargo: " + cargo);
        System.out.println("Disponible: " + (disponible ? "Sí" : "No"));
        System.out.println("Certificaciones: " + certificaciones);
    }

    public void asignarTurno(Turno nuevoTurno) {
        if (disponible) {
            turno.put(nuevoTurno.getHorarioInicio().toString(), nuevoTurno);  // puedes usar otra clave si prefieres
            historialTurnos.add(nuevoTurno);
            nuevoTurno.setEmpleadoAsignado(this);  // Relación bidireccional
            disponible = false;
            System.out.println("Turno asignado correctamente a " + getNombre());
        } else {
            System.out.println("El empleado no está disponible para nuevos turnos.");
        }
    }

    public void registrarAsistencia() {
        System.out.println("Asistencia registrada para: " + getNombre());
    }

    /**
     * Crea un objeto Empleado a partir de una cadena JSON
     * @param jsonString cadena JSON que representa un empleado
     * @return objeto Empleado deserializado
     * @throws IllegalArgumentException si el JSON no es válido o no contiene los campos requeridos
     */
    public static Empleado fromJson(String jsonString) {
        if (jsonString == null || jsonString.trim().isEmpty()) {
            throw new IllegalArgumentException("El JSON no puede ser nulo o vacío");
        }
        
        try {
            // Remover espacios y llaves externas
            String json = jsonString.trim();
            if (!json.startsWith("{") || !json.endsWith("}")) {
                throw new IllegalArgumentException("JSON mal formado");
            }
            
            json = json.substring(1, json.length() - 1); // Remover { }
            
            // Variables para almacenar los valores
            int id = 0;
            String nombre = "";
            String login = "";
            String password = ""; // Ahora se parseará correctamente del JSON
            TipoUsuario tipo = null;
            String cargo = "";
            boolean disponible = true;
            List<String> certificaciones = new ArrayList<>();
            
            // Parsear campos básicos
            String[] campos = splitJsonFields(json);
            
            for (String campo : campos) {
                String[] keyValue = campo.split(":", 2);
                if (keyValue.length != 2) continue;
                
                String key = keyValue[0].trim().replace("\"", "");
                String value = keyValue[1].trim();
                
                switch (key) {
                    case "id":
                        id = Integer.parseInt(value);
                        break;
                    case "nombre":
                        nombre = unescapeJson(value.replace("\"", ""));
                        break;
                    case "login":
                        login = unescapeJson(value.replace("\"", ""));
                        break;
                    case "password":
                        password = unescapeJson(value.replace("\"", ""));
                        break;
                    case "tipo":
                        String tipoStr = value.replace("\"", "");
                        if (!tipoStr.equals("null")) {
                            tipo = TipoUsuario.valueOf(tipoStr);
                        }
                        break;
                    case "cargo":
                        cargo = unescapeJson(value.replace("\"", ""));
                        break;
                    case "disponible":
                        disponible = Boolean.parseBoolean(value);
                        break;
                    case "certificaciones":
                        certificaciones = parseCertificaciones(value);
                        break;
                }
            }
            
            // Crear el objeto Empleado con la password parseada del JSON
            Empleado empleado = new Empleado(id, nombre, login, password, tipo, cargo);
            empleado.setDisponible(disponible);
            empleado.setCertificaciones(certificaciones);
            
            return empleado;
            
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al parsear JSON: " + e.getMessage(), e);
        }
    }
    
    /**
     * Método auxiliar para dividir los campos del JSON
     * @param json contenido JSON sin llaves externas
     * @return array de campos
     */
    private static String[] splitJsonFields(String json) {
        List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;
        boolean inArray = false;
        boolean inObject = false;
        int objectDepth = 0;
        int arrayDepth = 0;
        
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            
            if (c == '"' && (i == 0 || json.charAt(i-1) != '\\')) {
                inQuotes = !inQuotes;
            } else if (!inQuotes) {
                if (c == '[') {
                    inArray = true;
                    arrayDepth++;
                } else if (c == ']') {
                    arrayDepth--;
                    if (arrayDepth == 0) inArray = false;
                } else if (c == '{') {
                    inObject = true;
                    objectDepth++;
                } else if (c == '}') {
                    objectDepth--;
                    if (objectDepth == 0) inObject = false;
                } else if (c == ',' && !inArray && !inObject) {
                    fields.add(currentField.toString().trim());
                    currentField = new StringBuilder();
                    continue;
                }
            }
            
            currentField.append(c);
        }
        
        if (currentField.length() > 0) {
            fields.add(currentField.toString().trim());
        }
        
        return fields.toArray(new String[0]);
    }
    
    /**
     * Parsea el array de certificaciones del JSON
     * @param arrayJson string que representa el array JSON
     * @return lista de certificaciones
     */
    private static List<String> parseCertificaciones(String arrayJson) {
        List<String> certificaciones = new ArrayList<>();
        
        if (arrayJson.equals("[]")) {
            return certificaciones;
        }
        
        // Remover corchetes
        String content = arrayJson.substring(1, arrayJson.length() - 1);
        String[] items = content.split(",");
        
        for (String item : items) {
            String cert = item.trim().replace("\"", "");
            if (!cert.isEmpty()) {
                certificaciones.add(unescapeJson(cert));
            }
        }
        
        return certificaciones;
    }
    
    /**
     * Método auxiliar para des-escapar caracteres especiales del JSON
     * @param str cadena a des-escapar
     * @return cadena des-escapada
     */
    private static String unescapeJson(String str) {
        if (str == null || str.equals("null")) return null;
        return str.replace("\\\"", "\"")
                  .replace("\\\\", "\\")
                  .replace("\\n", "\n")
                  .replace("\\r", "\r")
                  .replace("\\t", "\t");
    }
}