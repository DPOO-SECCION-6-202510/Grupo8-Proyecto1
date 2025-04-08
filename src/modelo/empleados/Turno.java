package modelo.empleados;

import java.util.Date;

public class Turno {
    private int id;
    private Date horarioInicio;
    private Date horaFin;
    private String tipoTurno;
    private String ubicacionTemporal;
    private PuestoAsignado puestoAsignado;
    private Empleado empleadoAsignado;  // Relación bidireccional

    // Constructor
    public Turno(int id, Date horarioInicio, Date horaFin, String tipoTurno, String ubicacionTemporal, PuestoAsignado puestoAsignado) {
        this.id = id;
        this.horarioInicio = horarioInicio;
        this.horaFin = horaFin;
        this.tipoTurno = tipoTurno;
        this.ubicacionTemporal = ubicacionTemporal;
        this.puestoAsignado = puestoAsignado;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(Date horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public String getTipoTurno() {
        return tipoTurno;
    }

    public void setTipoTurno(String tipoTurno) {
        this.tipoTurno = tipoTurno;
    }

    public String getUbicacionTemporal() {
        return ubicacionTemporal;
    }

    public void setUbicacionTemporal(String ubicacionTemporal) {
        this.ubicacionTemporal = ubicacionTemporal;
    }

    public PuestoAsignado getPuestoAsignado() {
        return puestoAsignado;
    }

    public void setPuestoAsignado(PuestoAsignado puestoAsignado) {
        this.puestoAsignado = puestoAsignado;
    }

    public Empleado getEmpleadoAsignado() {
        return empleadoAsignado;
    }

    public void setEmpleadoAsignado(Empleado empleadoAsignado) {
        this.empleadoAsignado = empleadoAsignado;
    }

    @Override
    public String toString() {
        return "Turno ID: " + id + ", Tipo: " + tipoTurno + ", Inicio: " + horarioInicio +
               ", Fin: " + horaFin + ", Ubicación: " + ubicacionTemporal + ", Puesto: " + puestoAsignado +
               (empleadoAsignado != null ? ", Empleado: " + empleadoAsignado.getNombre() : "");
    }
}

