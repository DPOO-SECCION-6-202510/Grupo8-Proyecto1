package modelo.empleados;


import java.util.ArrayList;
import java.util.List;

import modelo.atraccion.Atraccion;

public class EmpleadoAtraccion extends Empleado {
    private String capacidadRiesgo;
    private List<Atraccion> capacidadAtraccionesAltas;

    // Constructor
    public EmpleadoAtraccion(int id, String nombre, String login, String password, TipoUsuario tipo, String cargo,
                             String capacidadRiesgo) {
        super(id, nombre, login, password, tipo, cargo);
        this.capacidadRiesgo = capacidadRiesgo;
        this.capacidadAtraccionesAltas = new ArrayList<>();
    }

    // Getters y Setters
    public String getCapacidadRiesgo() {
        return capacidadRiesgo;
    }

    public void setCapacidadRiesgo(String capacidadRiesgo) {
        this.capacidadRiesgo = capacidadRiesgo;
    }

    public List<Atraccion> getCapacidadAtraccionesAltas() {
        return capacidadAtraccionesAltas;
    }

    public void setCapacidadAtraccionesAltas(List<Atraccion> capacidadAtraccionesAltas) {
        this.capacidadAtraccionesAltas = capacidadAtraccionesAltas;
    }

    // Métodos propios
    public void verificarTiquete(String codigoTiquete) {
        System.out.println("Verificando tiquete: " + codigoTiquete + " para acceso a atracción.");
    }

    public void controlarAtraccion(String nombreAtraccion) {
        System.out.println(getNombre() + " está controlando la atracción: " + nombreAtraccion);
    }

    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println("Capacidad de riesgo: " + capacidadRiesgo);
        System.out.println("Atracciones autorizadas:");
        for (Atraccion atr : capacidadAtraccionesAltas) {
            System.out.println("- " + atr.getNombre());
        }
    }
}
