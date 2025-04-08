package modelo.empleados;

import java.util.ArrayList;
import java.util.List;

public class Tienda {
    private String nombre;
    private String ubicacion;
    private List<EmpleadoServicio> empleados;
    private List<Producto> productos;

    public Tienda(String nombre, String ubicacion) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.empleados = new ArrayList<>();
        this.productos = new ArrayList<>();
    }

    public void agregarEmpleado(EmpleadoServicio empleado) {
        empleados.add(empleado);
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void vender(Producto producto) {
        System.out.println("Vendiendo producto: " + producto);
    }

    public List<EmpleadoServicio> getEmpleados() {
        return empleados;
    }

    public List<Producto> getProductos() {
        return productos;
    }
}
