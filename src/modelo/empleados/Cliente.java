package modelo.empleados;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {
    
	private List<String> historialCompras;

    // Constructor
    public Cliente(int id, String nombre, String login, String password, TipoUsuario tipo) {
        super(id, nombre, login, password, tipo);
        this.historialCompras = new ArrayList<>();
    }

    // Getters y Setters
    public List<String> getHistorialCompras() {
        return historialCompras;
    }

    public void setHistorialCompras(List<String> historialCompras) {
        this.historialCompras = historialCompras;
    }

    // Métodos
    @Override
    public void mostrarInformacion() {
        System.out.println("Cliente: " + getNombre());
        System.out.println("ID: " + getId());
        System.out.println("Historial de Compras: " + historialCompras);
    }

    public void consultarHistorial() {
        System.out.println("Historial de Compras de " + getNombre() + ":");
        if (historialCompras.isEmpty()) {
            System.out.println("No hay compras registradas.");
        } else {
            for (String compra : historialCompras) {
                System.out.println("- " + compra);
            }
        }
    }

    public void comprarTiquete(String descripcionCompra) {
        historialCompras.add(descripcionCompra);
        System.out.println("Compra realizada: " + descripcionCompra);
    }
}
