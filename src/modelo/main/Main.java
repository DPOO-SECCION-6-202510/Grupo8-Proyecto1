package modelo.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Date;
import modelo.empleados.Cliente;
import modelo.empleados.Empleado;
import modelo.atraccion.Atraccion;
import modelo.atraccion.AtraccionMecanica;
import modelo.atraccion.AtraccionCultural;
import modelo.tiquetes.TiquetesNormales;
import modelo.tiquetes.Tipo;
import modelo.persistencia.PersistenceClientes;
import modelo.persistencia.PersistenceEmpleados;
import modelo.persistencia.PersistenceAtracciones;
import modelo.persistencia.PersistenceTiquetes;

public class Main {
    
    // Listas de datos en memoria
    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Empleado> empleados = new ArrayList<>();
    private static List<Atraccion> atracciones = new ArrayList<>();
    private static List<TiquetesNormales> tiquetes = new ArrayList<>();
    
    // Instancias de las clases de persistencia (directorio "data" como ejemplo)
    private static PersistenceClientes persClientes = new PersistenceClientes("data");
    private static PersistenceEmpleados persEmpleados = new PersistenceEmpleados("data");
    private static PersistenceAtracciones persAtracciones = new PersistenceAtracciones("data");
    private static PersistenceTiquetes persTiquetes = new PersistenceTiquetes("data");
    
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n===== MENU DEL SISTEMA DE PARQUE DE DIVERSIONES =====");
            System.out.println("1. Agregar Cliente");
            System.out.println("2. Agregar Trabajador");
            System.out.println("3. Agregar Atracción");
            System.out.println("4. Asignar Trabajador a Atracción");
            System.out.println("5. Vender Tiquete (Trabajador de taquilla vende a un Cliente)");
            System.out.println("6. Guardar Datos (Persistencia a JSON)");
            System.out.println("7. Cargar Datos desde JSON");
            System.out.println("8. Mostrar Información del Sistema");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            
            switch (opcion) {
                case 1:
                    agregarCliente();
                    break;
                case 2:
                    agregarTrabajador();
                    break;
                case 3:
                    agregarAtraccion();
                    break;
                case 4:
                    asignarTrabajadorAAtraccion();
                    break;
                case 5:
                    venderTiquete();
                    break;
                case 6:
                    guardarDatos();
                    break;
                case 7:
                    cargarDatos();
                    break;
                case 8:
                    mostrarInformacion();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
        scanner.close();
    }
    
    // Opciones del menú:
    
    private static void agregarCliente() {
        System.out.print("Ingrese ID del cliente: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese nombre del cliente: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese login del cliente: ");
        String login = scanner.nextLine();
        System.out.print("Ingrese contraseña: ");
        String password = scanner.nextLine();
        
        // Asumimos que para cliente el tipo es CLIENTE (definido en TipoUsuario dentro de Usuario)
        Cliente cliente = new Cliente(id, nombre, login, password, modelo.empleados.TipoUsuario.CLIENTE);
        clientes.add(cliente);
        System.out.println("Cliente agregado correctamente.");
    }
    
    private static void agregarTrabajador() {
        System.out.print("Ingrese ID del trabajador: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese nombre del trabajador: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese login del trabajador: ");
        String login = scanner.nextLine();
        System.out.print("Ingrese contraseña: ");
        String password = scanner.nextLine();
        System.out.print("Ingrese cargo: ");
        String cargo = scanner.nextLine();
        
        // Asumimos el tipo EMPLEADO para trabajadores
        Empleado trabajador = new Empleado(id, nombre, login, password, modelo.empleados.TipoUsuario.EMPLEADO, cargo);
        empleados.add(trabajador);
        System.out.println("Trabajador agregado correctamente.");
    }
    
    private static void agregarAtraccion() {
        System.out.println("Seleccione el tipo de atracción: 1 para Mecánica, 2 para Cultural");
        int tipo = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese ID de la atracción: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese nombre de la atracción: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese capacidad: ");
        int capacidad = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese ubicación: ");
        String ubicacion = scanner.nextLine();
        System.out.print("Ingrese nivel de exclusividad (Familiar, Oro, Diamante): ");
        String nivelExclusividad = scanner.nextLine();
        
        if (tipo == 1) { // Atracción mecánica
            System.out.print("Ingrese límite mínimo de altura (en metros): ");
            double minAltura = scanner.nextDouble();
            System.out.print("Ingrese límite máximo de altura (en metros): ");
            double maxAltura = scanner.nextDouble();
            System.out.print("Ingrese límite mínimo de peso (en kg): ");
            double minPeso = scanner.nextDouble();
            System.out.print("Ingrese límite máximo de peso (en kg): ");
            double maxPeso = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Ingrese nivel de riesgo (Medio/Alto): ");
            String nivelRiesgo = scanner.nextLine();
            System.out.print("Ingrese contraindicaciones (separadas por comas): ");
            String contraindicaciones = scanner.nextLine();
            
            AtraccionMecanica atraccion = new AtraccionMecanica(id, nombre, capacidad, ubicacion, nivelExclusividad,
                    minAltura, maxAltura, minPeso, maxPeso, nivelRiesgo, contraindicaciones);
            atraccion.registrar();
            atracciones.add(atraccion);
            System.out.println("Atracción mecánica agregada.");
        } else if (tipo == 2) { // Atracción cultural
            System.out.print("Ingrese restricción de edad: ");
            int restriccionEdad = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Ingrese descripción temática: ");
            String descripcionTematica = scanner.nextLine();
            
            AtraccionCultural atraccion = new AtraccionCultural(id, nombre, capacidad, ubicacion, nivelExclusividad,
                    restriccionEdad, descripcionTematica);
            atraccion.registrar();
            atracciones.add(atraccion);
            System.out.println("Atracción cultural agregada.");
        } else {
            System.out.println("Opción no válida.");
        }
    }
    
    private static void asignarTrabajadorAAtraccion() {
        System.out.print("Ingrese el ID de la atracción para asignar un trabajador: ");
        int idAtraccion = scanner.nextInt();
        scanner.nextLine();
        Atraccion atracSeleccionada = null;
        for (Atraccion a : atracciones) {
            if (a.getId() == idAtraccion) {
                atracSeleccionada = a;
                break;
            }
        }
        if (atracSeleccionada == null) {
            System.out.println("Atracción no encontrada.");
            return;
        }
        System.out.print("Ingrese el ID del trabajador a asignar: ");
        int idTrabajador = scanner.nextInt();
        scanner.nextLine();
        Empleado trabajadorAsignar = null;
        for (Empleado t : empleados) {
            if (t.getId() == idTrabajador) {
                trabajadorAsignar = t;
                break;
            }
        }
        if (trabajadorAsignar == null) {
            System.out.println("Trabajador no encontrado.");
            return;
        }
        // Se puede simular la asignación con un método en Empleado (por ejemplo, asignarTurno) o simplemente mostrar el mensaje
        System.out.println("El trabajador " + trabajadorAsignar.getNombre() + " ha sido asignado a la atracción " + atracSeleccionada.getNombre());
        // Podrías almacenar esta asignación en un mapa (por ejemplo, Map<Integer, Empleado> asignacionTrabajadores) para referencia futura.
    }
    
    private static void venderTiquete() {
        // Se simula que un trabajador de taquilla vende un tiquete a un cliente.
        System.out.print("Ingrese el ID del trabajador de taquilla que realiza la venta: ");
        int idTrabajador = scanner.nextInt();
        scanner.nextLine();
        Empleado trabajadorVenta = null;
        for (Empleado t : empleados) {
            if (t.getId() == idTrabajador) {
                trabajadorVenta = t;
                break;
            }
        }
        if (trabajadorVenta == null) {
            System.out.println("Trabajador no encontrado.");
            return;
        }
        System.out.print("Ingrese el ID del cliente que comprará el tiquete: ");
        int idCliente = scanner.nextInt();
        scanner.nextLine();
        Cliente clienteCompra = null;
        for (Cliente c : clientes) {
            if (c.getId() == idCliente) {
                clienteCompra = c;
                break;
            }
        }
        if (clienteCompra == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        // Crear un tiquete básico: para simplificar, no asignamos atracciones en este ejemplo.
        TiquetesNormales tiquete = new TiquetesNormales("T" + System.currentTimeMillis(), Tipo.FAMILIAR, new ArrayList<>(), 0);
        System.out.print("Ingrese el precio del tiquete: ");
        float precio = scanner.nextFloat();
        scanner.nextLine();
        System.out.print("Ingrese el monto pagado por el cliente: ");
        float pago = scanner.nextFloat();
        scanner.nextLine();
        
        if (pago >= precio) {
            System.out.println("Pago aceptado. Venta completada por " + trabajadorVenta.getNombre());
            // El cliente compra el tiquete (se añade una descripción en su historial de compras)
            clienteCompra.comprarTiquete("Tiquete " + tiquete.getId() + " de tipo " + tiquete.getTipo() + " comprado a $" + precio);
            tiquetes.add(tiquete);
        } else {
            System.out.println("Monto insuficiente. Venta cancelada.");
        }
    }
    
    // Guardar datos en archivos JSON utilizando la persistencia
    private static void guardarDatos() {
        boolean okClientes = persClientes.guardarListaClientes(clientes);
        boolean okEmpleados = persEmpleados.guardarListaEmpleados(empleados);
        boolean okAtracciones = persAtracciones.guardarListaAtracciones(atracciones);
        boolean okTiquetes = persTiquetes.guardarListaTiquetes(tiquetes);
        
        if (okClientes && okEmpleados && okAtracciones && okTiquetes) {
            System.out.println("Todos los datos se han guardado exitosamente en JSON.");
        } else {
            System.out.println("Error al guardar algunos datos.");
        }
    }
    
    // Cargar datos desde archivos JSON
    private static void cargarDatos() {
        clientes = persClientes.cargarListaClientes();
        empleados = persEmpleados.cargarListaEmpleados();
        atracciones = persAtracciones.cargarListaAtracciones();
        tiquetes = persTiquetes.cargarListaTiquetes();
        System.out.println("Datos cargados desde archivos JSON.");
    }
    
    // Mostrar información general del sistema
    private static void mostrarInformacion() {
        System.out.println("\n--- Clientes ---");
        for (Cliente c : clientes) {
            c.mostrarInformacion();
        }
        System.out.println("\n--- Trabajadores ---");
        for (Empleado t : empleados) {
            t.mostrarInformacion();
        }
        System.out.println("\n--- Atracciones ---");
        for (Atraccion a : atracciones) {
            System.out.println("ID: " + a.getId() + ", Nombre: " + a.getNombre() +
                    ", Capacidad: " + a.getCapacidad() + ", Ubicación: " + a.getUbicacion() +
                    ", Exclusividad: " + a.getNivelExclusividad() + ", Estado: " + a.obtenerEstado());
        }
        System.out.println("\n--- Tiquetes Vendidos ---");
        for (TiquetesNormales t : tiquetes) {
            System.out.println("ID: " + t.getId() + ", Tipo: " + t.getTipo() + ", Utilizado: " + t.isUtilizado());
        }
    }
}