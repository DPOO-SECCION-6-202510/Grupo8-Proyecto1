package modelo.empleados;

import java.util.ArrayList;
import java.util.List;

public class Cafeteria {
    private String nombre;
    private String ubicacion;
    private List<EmpleadoServicio> empleados;
    private List<Producto> platos;

    public Cafeteria(String nombre, String ubicacion) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.empleados = new ArrayList<>();
        this.platos = new ArrayList<>();
    }

    // Métodos para agregar empleados y platos
    public void agregarEmpleado(EmpleadoServicio empleado) {
        empleados.add(empleado);
    }

    public void agregarPlato(Producto plato) {
        platos.add(plato);
    }

    public void cocinar(Producto plato) {
        System.out.println("Cocinando: " + plato);
    }

    public void vender(Producto producto) {
        System.out.println("Vendiendo producto: " + producto);
    }

    public List<EmpleadoServicio> getEmpleados() {
        return empleados;
    }

    public List<Producto> getPlatos() {
        return platos;
    }
}
