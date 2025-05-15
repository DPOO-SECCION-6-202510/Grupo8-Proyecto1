package pruebas;

import modelo.empleados.Cliente;
import modelo.empleados.TipoUsuario;
import modelo.empleados.Empleado;
import modelo.tiquetes.TiquetesNormales;
import modelo.tiquetes.Tipo;
import modelo.atraccion.Atraccion;
import modelo.atraccion.AtraccionMecanica;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class ParqueDiversionesTest {

    private Cliente cliente;
    private Empleado empleado;
    private TiquetesNormales tiquete;

void setUp() {
        cliente = new Cliente(1, "Ana", "ana123", "clave123", TipoUsuario.CLIENTE, "ninguno", 1.65, 55.0);
        empleado = new Empleado(100, "Pedro", "pedroEmp", "emp123", TipoUsuario.EMPLEADO, "vendedor");
    }

    // ======= Cliente =======
    @Test
    @DisplayName("Crear cliente con datos válidos")
    public void testCrearClienteConDatosValidos() {
        Cliente cliente = new Cliente(1, "Juan", "juan123", "pass", TipoUsuario.CLIENTE, "ninguno", 1.70, 65.0);
        assertEquals("Juan", cliente.getNombre());
        assertEquals(1.70, cliente.getAltura());
        assertEquals(65.0, cliente.getPeso());
    }

    @Test
    @DisplayName("Historial de compras del cliente")
    public void testHistorialComprasCliente() {
        cliente.comprarTiquete("Compra 1");
        cliente.comprarTiquete("Compra 2");
        assertEquals(2, cliente.getHistorialCompras().size());
    }

    // ======= TiquetesNormales =======
    @Test
    @DisplayName("Tiquete se puede marcar como usado")
    public void testTiqueteUsado() {
        TiquetesNormales t = new TiquetesNormales("T001", Tipo.FAMILIAR, new ArrayList<>(), 0);
        t.setUtilizado(true);
        assertTrue(t.isUtilizado());
    }

    // ======= AtraccionMecanica =======
    @Test
    @DisplayName("Acceso permitido a atracción mecánica")
    public void testValidarAccesoMecanicaPermitido() {
        AtraccionMecanica am = new AtraccionMecanica(1, "Roller", 5, "Zona A", "FAMILIAR", 1.40, 2.0, 50, 100, "Alto", "");
        Map<String, Object> p = new HashMap<>();
        p.put("altura", 1.70);
        p.put("peso", 70.0);
        assertTrue(am.validarAcceso(p));
    }

    @Test
    @DisplayName("Acceso denegado a atracción mecánica por altura")
    public void testValidarAccesoMecanicaDenegadoPorAltura() {
        AtraccionMecanica am = new AtraccionMecanica(1, "Roller", 5, "Zona A", "FAMILIAR", 1.40, 2.0, 50, 100, "Alto", "");
        Map<String, Object> p = new HashMap<>();
        p.put("altura", 1.20);
        p.put("peso", 70.0);
        assertFalse(am.validarAcceso(p));
    }

    // ======= Empleado =======
    @Test
    @DisplayName("Crear empleado correctamente")
    public void testCrearEmpleado() {
        Empleado emp = new Empleado(100, "Carlos", "carlosEmp", "1234", TipoUsuario.EMPLEADO, "vendedor");
        assertEquals("Carlos", emp.getNombre());
        assertEquals("vendedor", emp.getCargo());
    }
}