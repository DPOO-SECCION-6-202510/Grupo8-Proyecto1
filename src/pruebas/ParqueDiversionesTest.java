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

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1, "Ana", "ana123", "clave123", TipoUsuario.CLIENTE, "ninguno", 1.65, 55.0);
        empleado = new Empleado(100, "Pedro", "pedroEmp", "emp123", TipoUsuario.EMPLEADO, "vendedor");
        tiquete = new TiquetesNormales("T001", Tipo.FAMILIAR, new ArrayList<>(), 0);
    }

    // ======= Cliente =======
    @Test
    @DisplayName("Crear cliente con datos válidos")
    public void testCrearClienteConDatosValidos() {
        Cliente clienteTest = new Cliente(1, "Juan", "juan123", "pass", TipoUsuario.CLIENTE, "ninguno", 1.70, 65.0);
        assertEquals("Juan", clienteTest.getNombre());
        assertEquals(1.70, clienteTest.getAltura());
        assertEquals(65.0, clienteTest.getPeso());
    }

    @Test
    @DisplayName("Historial de compras del cliente")
    public void testHistorialComprasCliente() {
        cliente.comprarTiquete("Compra 1");
        cliente.comprarTiquete("Compra 2");
        assertEquals(2, cliente.getHistorialCompras().size());
    }

    @Test
    @DisplayName("Cliente tiene datos correctos después de creación")
    public void testDatosClienteCorrectos() {
        assertEquals(1, cliente.getId());
        assertEquals("Ana", cliente.getNombre());
        assertEquals("ana123", cliente.getLogin());
        assertEquals("clave123", cliente.getPassword());
        assertEquals(TipoUsuario.CLIENTE, cliente.getTipoUsuario());
        assertEquals("ninguno", cliente.getRiesgosSalud());
        assertEquals(1.65, cliente.getAltura());
        assertEquals(55.0, cliente.getPeso());
    }

    // ======= TiquetesNormales =======
    @Test
    @DisplayName("Tiquete se puede marcar como usado")
    public void testTiqueteUsado() {
        TiquetesNormales t = new TiquetesNormales("T001", Tipo.FAMILIAR, new ArrayList<>(), 0);
        t.setUtilizado(true);
        assertTrue(t.isUtilizado());
    }

    @Test
    @DisplayName("Tiquete recién creado no está usado")
    public void testTiqueteNuevoNoUsado() {
        assertFalse(tiquete.isUtilizado());
    }

    @Test
    @DisplayName("Propiedades del tiquete son correctas")
    public void testPropiedadesTiquete() {
        assertEquals("T001", tiquete.getId());
        assertEquals(Tipo.FAMILIAR, tiquete.getTipo());
        assertNotNull(tiquete.getAtracciones());
        assertEquals(0, tiquete.getCantidadUsos());
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

    @Test
    @DisplayName("Acceso denegado a atracción mecánica por peso bajo")
    public void testValidarAccesoMecanicaDenegadoPorPesoBajo() {
        AtraccionMecanica am = new AtraccionMecanica(1, "Roller", 5, "Zona A", "FAMILIAR", 1.40, 2.0, 50, 100, "Alto", "");
        Map<String, Object> p = new HashMap<>();
        p.put("altura", 1.70);
        p.put("peso", 40.0);
        assertFalse(am.validarAcceso(p));
    }

    @Test
    @DisplayName("Acceso denegado a atracción mecánica por peso alto")
    public void testValidarAccesoMecanicaDenegadoPorPesoAlto() {
        AtraccionMecanica am = new AtraccionMecanica(1, "Roller", 5, "Zona A", "FAMILIAR", 1.40, 2.0, 50, 100, "Alto", "");
        Map<String, Object> p = new HashMap<>();
        p.put("altura", 1.70);
        p.put("peso", 120.0);
        assertFalse(am.validarAcceso(p));
    }

    @Test
    @DisplayName("Propiedades de atracción mecánica son correctas")
    public void testPropiedadesAtraccionMecanica() {
        AtraccionMecanica am = new AtraccionMecanica(1, "Roller", 5, "Zona A", "FAMILIAR", 1.40, 2.0, 50, 100, "Alto", "Problemas cardíacos");
        assertEquals(1, am.getId());
        assertEquals("Roller", am.getNombre());
        assertEquals(5, am.getCapacidad());
        assertEquals("Zona A", am.getUbicacion());
        assertEquals("FAMILIAR", am.getNivelExclusividad());
        assertEquals(1.40, am.getAlturaMinima());
        assertEquals(2.0, am.getAlturaMaxima());
        assertEquals(50.0, am.getPesoMinimo());
        assertEquals(100.0, am.getPesoMaximo());
        assertEquals("Alto", am.getNivelRiesgo());
        assertEquals("Problemas cardíacos", am.getContraindicaciones());
    }

    // ======= Empleado =======
    @Test
    @DisplayName("Crear empleado correctamente")
    public void testCrearEmpleado() {
        Empleado emp = new Empleado(100, "Carlos", "carlosEmp", "1234", TipoUsuario.EMPLEADO, "vendedor");
        assertEquals("Carlos", emp.getNombre());
        assertEquals("vendedor", emp.getCargo());
    }

    @Test
    @DisplayName("Empleado tiene datos correctos después de creación")
    public void testDatosEmpleadoCorrectos() {
        assertEquals(100, empleado.getId());
        assertEquals("Pedro", empleado.getNombre());
        assertEquals("pedroEmp", empleado.getLogin());
        assertEquals("emp123", empleado.getPassword());
        assertEquals(TipoUsuario.EMPLEADO, empleado.getTipoUsuario());
        assertEquals("vendedor", empleado.getCargo());
    }

    // ======= Casos Edge =======
    @Test
    @DisplayName("Validar acceso con parámetros nulos")
    public void testValidarAccesoConParametrosNulos() {
        AtraccionMecanica am = new AtraccionMecanica(1, "Roller", 5, "Zona A", "FAMILIAR", 1.40, 2.0, 50, 100, "Alto", "");
        assertFalse(am.validarAcceso(null));
    }

    @Test
    @DisplayName("Validar acceso con parámetros incompletos")
    public void testValidarAccesoConParametrosIncompletos() {
        AtraccionMecanica am = new AtraccionMecanica(1, "Roller", 5, "Zona A", "FAMILIAR", 1.40, 2.0, 50, 100, "Alto", "");
        Map<String, Object> p = new HashMap<>();
        p.put("altura", 1.70);
        // Falta el peso
        assertFalse(am.validarAcceso(p));
    }

    @Test
    @DisplayName("Cliente puede tener múltiples compras")
    public void testClienteMultiplesCompras() {
        cliente.comprarTiquete("Tiquete 1");
        cliente.comprarTiquete("Tiquete 2");
        cliente.comprarTiquete("Tiquete 3");
        
        assertEquals(3, cliente.getHistorialCompras().size());
        assertTrue(cliente.getHistorialCompras().contains("Tiquete 1"));
        assertTrue(cliente.getHistorialCompras().contains("Tiquete 2"));
        assertTrue(cliente.getHistorialCompras().contains("Tiquete 3"));
    }

    @Test
    @DisplayName("Tiquete puede cambiar de no usado a usado")
    public void testCambioEstadoTiquete() {
        assertFalse(tiquete.isUtilizado());
        tiquete.setUtilizado(true);
        assertTrue(tiquete.isUtilizado());
        tiquete.setUtilizado(false);
        assertFalse(tiquete.isUtilizado());
    }
}