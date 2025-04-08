package modelo.empleados;

import java.util.ArrayList;
import java.util.List;

public class Taquilla {
    private int tiquetesVendidos;
    private int tiquetesDisponibles;
    private List<EmpleadoServicio> empleados;

    public Taquilla(int tiquetesDisponibles) {
        this.tiquetesDisponibles = tiquetesDisponibles;
        this.tiquetesVendidos = 0;
        this.empleados = new ArrayList<>();
    }

    public void agregarEmpleado(EmpleadoServicio empleado) {
        empleados.add(empleado);
    }

    public void venderTiquete() {
        if (tiquetesDisponibles > 0) {
            tiquetesVendidos++;
            tiquetesDisponibles--;
            System.out.println("Tiquete vendido. Tiquetes restantes: " + tiquetesDisponibles);
        } else {
            System.out.println("No hay tiquetes disponibles.");
        }
    }

    public int getTiquetesVendidos() {
        return tiquetesVendidos;
    }

    public int getTiquetesDisponibles() {
        return tiquetesDisponibles;
    }

    public List<EmpleadoServicio> getEmpleados() {
        return empleados;
    }
}
