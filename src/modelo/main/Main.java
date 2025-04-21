package modelo.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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

    // Datos en memoria
    private static List<Cliente> clientes       = new ArrayList<>();
    private static List<Empleado> empleados     = new ArrayList<>();
    private static List<Atraccion> atracciones  = new ArrayList<>();
    private static List<TiquetesNormales> tiquetes = new ArrayList<>();

    // Persistencia
    private static PersistenceClientes       persClientes    = new PersistenceClientes("data");
    private static PersistenceEmpleados      persEmpleados   = new PersistenceEmpleados("data");
    private static PersistenceAtracciones    persAtracciones = new PersistenceAtracciones("data");
    private static PersistenceTiquetes       persTiquetes    = new PersistenceTiquetes("data");

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n===== MENU DEL SISTEMA DE PARQUE =====");
            System.out.println("1. Agregar Cliente");
            System.out.println("2. Agregar Trabajador");
            System.out.println("3. Agregar Atracción");
            System.out.println("4. Asignar Trabajador a Atracción");
            System.out.println("5. Vender Tiquete");
            System.out.println("6. Guardar Datos");
            System.out.println("7. Cargar Datos");
            System.out.println("8. Mostrar Información");
            System.out.println("9. Validar Acceso a Atracción");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1: agregarCliente();                 break;
                case 2: agregarTrabajador();              break;
                case 3: agregarAtraccion();               break;
                case 4: asignarTrabajadorAAtraccion();    break;
                case 5: venderTiquete();                  break;
                case 6: guardarDatos();                   break;
                case 7: cargarDatos();                    break;
                case 8: mostrarInformacion();             break;
                case 9: validarAcceso();                  break;
                case 0: System.out.println("Saliendo...");break;
                default: System.out.println("Opción no válida."); 
            }
        } while (opcion != 0);
        scanner.close();
    }

    private static void agregarCliente() {
        System.out.print("Ingrese ID del cliente: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Ingrese nombre del cliente: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese login del cliente: ");
        String login = scanner.nextLine();
        System.out.print("Ingrese contraseña: ");
        String password = scanner.nextLine();
        System.out.print("Ingrese riesgos de salud (separados por comas): ");
        String riesgos = scanner.nextLine();
        System.out.print("Ingrese altura del cliente en metros: ");
        double altura = Double.parseDouble(scanner.nextLine());
        System.out.print("Ingrese peso del cliente en kg: ");
        double peso = Double.parseDouble(scanner.nextLine());

        // Nuevo constructor de Cliente que incluye riesgos, altura y peso
        Cliente c = new Cliente(id, nombre, login, password,
                                modelo.empleados.TipoUsuario.CLIENTE,
                                riesgos, altura, peso);
        clientes.add(c);
        System.out.println("Cliente agregado correctamente.");
    }

    private static void agregarTrabajador() {
        System.out.print("Ingrese ID del trabajador: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Ingrese nombre del trabajador: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese login del trabajador: ");
        String login = scanner.nextLine();
        System.out.print("Ingrese contraseña: ");
        String password = scanner.nextLine();
        System.out.print("Ingrese cargo: ");
        String cargo = scanner.nextLine();

        Empleado e = new Empleado(id, nombre, login, password,
                                  modelo.empleados.TipoUsuario.EMPLEADO,
                                  cargo);
        empleados.add(e);
        System.out.println("Trabajador agregado correctamente.");
    }

    private static void agregarAtraccion() {
        System.out.println("Seleccione tipo de atracción: 1=Mecánica, 2=Cultural");
        int tipo = Integer.parseInt(scanner.nextLine());
        System.out.print("Ingrese ID de la atracción: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Ingrese nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese capacidad: ");
        int capacidad = Integer.parseInt(scanner.nextLine());
        System.out.print("Ingrese ubicación: ");
        String ubicacion = scanner.nextLine();
        System.out.print("Nivel de exclusividad (Familiar, Oro, Diamante): ");
        String nivel = scanner.nextLine();

        if (tipo == 1) {
            System.out.print("Altura mínima (m): ");
            double minAlt = Double.parseDouble(scanner.nextLine());
            System.out.print("Altura máxima (m): ");
            double maxAlt = Double.parseDouble(scanner.nextLine());
            System.out.print("Peso mínimo (kg): ");
            double minPeso = Double.parseDouble(scanner.nextLine());
            System.out.print("Peso máximo (kg): ");
            double maxPeso = Double.parseDouble(scanner.nextLine());
            System.out.print("Nivel de riesgo (Medio/Alto): ");
            String riesgo = scanner.nextLine();
            System.out.print("Contraindicaciones (coma sep.): ");
            String contra = scanner.nextLine();

            AtraccionMecanica am = new AtraccionMecanica(
                id, nombre, capacidad, ubicacion, nivel,
                minAlt, maxAlt, minPeso, maxPeso, riesgo, contra
            );
            am.registrar();
            atracciones.add(am);
            System.out.println("Atracción mecánica agregada.");
        } else {
            System.out.print("Edad mínima: ");
            int edad = Integer.parseInt(scanner.nextLine());
            System.out.print("Descripción temática: ");
            String desc = scanner.nextLine();

            AtraccionCultural ac = new AtraccionCultural(
                id, nombre, capacidad, ubicacion, nivel, edad, desc
            );
            ac.registrar();
            atracciones.add(ac);
            System.out.println("Atracción cultural agregada.");
        }
    }

    private static void asignarTrabajadorAAtraccion() {
        System.out.print("ID atracción: ");
        int idA = Integer.parseInt(scanner.nextLine());
        Atraccion atr = atracciones.stream()
            .filter(a -> a.getId() == idA)
            .findFirst().orElse(null);
        if (atr == null) {
            System.out.println("Atracción no encontrada.");
            return;
        }
        System.out.print("ID trabajador: ");
        int idT = Integer.parseInt(scanner.nextLine());
        Empleado emp = empleados.stream()
            .filter(e -> e.getId() == idT)
            .findFirst().orElse(null);
        if (emp == null) {
            System.out.println("Trabajador no encontrado.");
            return;
        }
        System.out.println("Asignado " + emp.getNombre() + " a " + atr.getNombre());
    }

    private static void venderTiquete() {
        System.out.print("ID vendedor (empleado): ");
        int idV = Integer.parseInt(scanner.nextLine());
        Empleado ven = empleados.stream()
            .filter(e -> e.getId() == idV)
            .findFirst().orElse(null);
        if (ven == null) {
            System.out.println("Empleado no encontrado.");
            return;
        }
        System.out.print("ID cliente: ");
        int idC = Integer.parseInt(scanner.nextLine());
        Cliente cli = clientes.stream()
            .filter(c -> c.getId() == idC)
            .findFirst().orElse(null);
        if (cli == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        System.out.print("Precio del tiquete: ");
        float precio = Float.parseFloat(scanner.nextLine());
        System.out.print("Monto pagado: ");
        float pago = Float.parseFloat(scanner.nextLine());

        if (pago >= precio) {
            TiquetesNormales t = new TiquetesNormales(
                "T" + System.currentTimeMillis(),
                Tipo.FAMILIAR, new ArrayList<>(), 0
            );
            tiquetes.add(t);
            cli.comprarTiquete("Tiquete " + t.getId() +
                " tipo " + t.getTipo() + " comprado a $" + precio);
            System.out.println("Venta completada. Cambio: $" + (pago - precio));
        } else {
            System.out.println("Pago insuficiente. Venta cancelada.");
        }
    }

    private static void validarAcceso() {
        System.out.print("ID tiquete: ");
        String idT = scanner.nextLine();
        TiquetesNormales tik = tiquetes.stream()
            .filter(t -> t.getId().equals(idT))
            .findFirst().orElse(null);
        if (tik == null) {
            System.out.println("Tiquete no válido.");
            return;
        }
        if (tik.isUtilizado()) {
            System.out.println("Tiquete ya usado.");
            return;
        }
        System.out.print("ID atracción: ");
        int idA = Integer.parseInt(scanner.nextLine());
        Atraccion atr = atracciones.stream()
            .filter(a -> a.getId() == idA)
            .findFirst().orElse(null);
        if (atr == null) {
            System.out.println("Atracción no encontrada.");
            return;
        }

        Map<String,Object> params = new HashMap<>();
        if (atr instanceof AtraccionMecanica) {
            System.out.print("Altura (m): ");
            params.put("altura", Double.parseDouble(scanner.nextLine()));
            System.out.print("Peso (kg): ");
            params.put("peso", Double.parseDouble(scanner.nextLine()));
        } else {
            System.out.print("Edad visitante: ");
            params.put("edad", Integer.parseInt(scanner.nextLine()));
        }

        boolean ok = atr.validarAcceso(params);
        if (ok) {
            tik.setUtilizado(true);
            System.out.println("Acceso permitido. ¡Disfruta!");
        } else {
            System.out.println("Acceso denegado por restricciones.");
        }
    }

    private static void guardarDatos() {
        persClientes.guardarListaClientes(clientes);
        persEmpleados.guardarListaEmpleados(empleados);
        persAtracciones.guardarListaAtracciones(atracciones);
        persTiquetes.guardarListaTiquetes(tiquetes);
        System.out.println("Datos guardados.");
    }

    private static void cargarDatos() {
        clientes    = persClientes.cargarListaClientes();
        empleados   = persEmpleados.cargarListaEmpleados();
        atracciones = persAtracciones.cargarListaAtracciones();
        tiquetes    = persTiquetes.cargarListaTiquetes();
        System.out.println("Datos recargados.");
    }

    private static void mostrarInformacion() {
        System.out.println("\n--- Clientes ---");
        clientes.forEach(Cliente::mostrarInformacion);
        System.out.println("\n--- Trabajadores ---");
        empleados.forEach(Empleado::mostrarInformacion);
        System.out.println("\n--- Atracciones ---");
        atracciones.forEach(a ->
            System.out.printf("ID:%d, %s, Cap:%d, Ubic:%s, Nivel:%s, Estado:%s%n",
                a.getId(), a.getNombre(), a.getCapacidad(),
                a.getUbicacion(), a.getNivelExclusividad(), a.obtenerEstado())
        );
        System.out.println("\n--- Tiquetes Vendidos ---");
        tiquetes.forEach(t ->
            System.out.printf("ID:%s, Tipo:%s, Usado:%b%n",
                t.getId(), t.getTipo(), t.isUtilizado())
        );
    }
}