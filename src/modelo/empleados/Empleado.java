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
}
