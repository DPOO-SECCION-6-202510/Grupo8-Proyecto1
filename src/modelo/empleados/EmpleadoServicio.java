package modelo.empleados;

public class EmpleadoServicio extends Empleado {
    private String tipoServicio;
    private boolean servicioGeneral;
    private boolean manejoCocina;

    // Constructor
    public EmpleadoServicio(int id, String nombre, String login, String password, TipoUsuario tipo, String cargo,
                            String tipoServicio, boolean servicioGeneral, boolean manejoCocina) {
        super(id, nombre, login, password, tipo, cargo);
        this.tipoServicio = tipoServicio;
        this.servicioGeneral = servicioGeneral;
        this.manejoCocina = manejoCocina;
    }

    // Getters y Setters
    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public boolean isServicioGeneral() {
        return servicioGeneral;
    }

    public void setServicioGeneral(boolean servicioGeneral) {
        this.servicioGeneral = servicioGeneral;
    }

    public boolean isManejoCocina() {
        return manejoCocina;
    }

    public void setManejoCocina(boolean manejoCocina) {
        this.manejoCocina = manejoCocina;
    }

    // Métodos propios
    public void vender(String producto) {
        System.out.println(getNombre() + " ha vendido: " + producto);
    }

    public void generarFactura(String producto, double precio) {
        System.out.println("Factura generada:");
        System.out.println("Producto: " + producto + " - Precio: $" + precio);
    }

    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println("Tipo de Servicio: " + tipoServicio);
        System.out.println("Manejo en cocina: " + (manejoCocina ? "Sí" : "No"));
        System.out.println("Servicio general: " + (servicioGeneral ? "Sí" : "No"));
    }
}
