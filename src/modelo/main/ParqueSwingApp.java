package modelo.main;

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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class ParqueSwingApp extends JFrame {
    private List<Cliente> clientes;
    private List<Empleado> empleados;
    private List<Atraccion> atracciones;
    private List<TiquetesNormales> tiquetes;
    // Mapa de asignaciones empleado->atracción
    private Map<Integer,Integer> asignaciones = new HashMap<>();

    private PersistenceClientes persClientes       = new PersistenceClientes("data");
    private PersistenceEmpleados persEmpleados     = new PersistenceEmpleados("data");
    private PersistenceAtracciones persAtracciones = new PersistenceAtracciones("data");
    private PersistenceTiquetes persTiquetes       = new PersistenceTiquetes("data");

    private CardLayout cardLayout;
    private JPanel cards;

    private JTextField loginField;
    private JPasswordField passField;
    private int currentUserId;
    private String currentRole;

    private Random random = new Random();

    public ParqueSwingApp() {
        super("Parque de Diversiones");
        clientes    = persClientes.cargarListaClientes();
        empleados   = new ArrayList<>(persEmpleados.cargarListaEmpleados());
        atracciones = persAtracciones.cargarListaAtracciones();
        tiquetes    = persTiquetes.cargarListaTiquetes();

        initUI();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(550, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initUI() {
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        cards.add(createLoginPanel(),    "login");
        cards.add(createRegisterPanel(), "register");
        cards.add(createClientPanel(),   "client");
        cards.add(createEmployeePanel(), "employee");
        cards.add(createAdminPanel(),    "admin");
        add(cards);
        cardLayout.show(cards, "login");
    }

    private JPanel createLoginPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx=0; gbc.gridy=0; p.add(new JLabel("Usuario:"), gbc);
        gbc.gridx=1; loginField = new JTextField(15); p.add(loginField, gbc);
        gbc.gridx=0; gbc.gridy=1; p.add(new JLabel("Clave:"), gbc);
        gbc.gridx=1; passField = new JPasswordField(15); p.add(passField, gbc);
        gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=2;
        JButton btnLogin = new JButton("Entrar"); btnLogin.addActionListener(this::onLogin);
        p.add(btnLogin, gbc);
        gbc.gridy=3;
        JButton btnReg = new JButton("Registrarse"); btnReg.addActionListener(e->cardLayout.show(cards,"register"));
        p.add(btnReg, gbc);
        return p;
    }

    private JPanel createRegisterPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        String[] roles={"Cliente","Trabajador"};
        gbc.gridx=0; gbc.gridy=0; p.add(new JLabel("Rol:"), gbc);
        JComboBox<String> combo=new JComboBox<>(roles);
        gbc.gridx=1; p.add(combo, gbc);

        gbc.gridx=0; gbc.gridy=1; p.add(new JLabel("Nombre:"), gbc);
        JTextField fnom=new JTextField(); gbc.gridx=1; p.add(fnom, gbc);

        gbc.gridx=0; gbc.gridy=2; p.add(new JLabel("Login:"), gbc);
        JTextField flog=new JTextField(); gbc.gridx=1; p.add(flog, gbc);

        gbc.gridx=0; gbc.gridy=3; p.add(new JLabel("Password:"), gbc);
        JTextField fpass=new JTextField(); gbc.gridx=1; p.add(fpass, gbc);

        gbc.gridx=0; gbc.gridy=4; p.add(new JLabel("Cargo (solo trab):"), gbc);
        JTextField fcargo=new JTextField(); gbc.gridx=1; p.add(fcargo, gbc);

        gbc.gridx=0; gbc.gridy=5; p.add(new JLabel("Riesgos salud (solo cli):"), gbc);
        JTextField fries=new JTextField(); gbc.gridx=1; p.add(fries, gbc);

        gbc.gridx=0; gbc.gridy=6; p.add(new JLabel("Altura (m):"), gbc);
        JTextField falt=new JTextField(); gbc.gridx=1; p.add(falt, gbc);

        gbc.gridx=0; gbc.gridy=7; p.add(new JLabel("Peso (kg):"), gbc);
        JTextField fpeso=new JTextField(); gbc.gridx=1; p.add(fpeso, gbc);

        JButton bcreate=new JButton("Crear Cuenta");
        gbc.gridx=0; gbc.gridy=8; gbc.gridwidth=2;
        p.add(bcreate, gbc);
        JButton bback=new JButton("Volver");
        gbc.gridy=9; p.add(bback, gbc);

        ActionListener tog=e->{ boolean cl=combo.getSelectedItem().equals("Cliente");
            fcargo.setEnabled(!cl);
            fries.setEnabled(cl);
            falt.setEnabled(cl);
            fpeso.setEnabled(cl);
        };
        combo.addActionListener(tog); tog.actionPerformed(null);
        bback.addActionListener(e->cardLayout.show(cards,"login"));

        bcreate.addActionListener(e->{
            try{
                int id = random.nextInt(9000)+1000;
                String nom=fnom.getText(), lg=flog.getText(), pw=fpass.getText();
                if(combo.getSelectedItem().equals("Cliente")){
                    String rs=fries.getText();
                    double alt=Double.parseDouble(falt.getText());
                    double pe=Double.parseDouble(fpeso.getText());
                    Cliente c = new Cliente(id, nom, lg, pw,
                        modelo.empleados.TipoUsuario.CLIENTE, rs, alt, pe);
                    clientes.add(c);
                    persClientes.guardarListaClientes(clientes);
                    JOptionPane.showMessageDialog(this,
                        "Cliente registrado con ID: " + id);
                } else {
                    String cargo = fcargo.getText();
                    Empleado emp = new Empleado(id, nom, lg, pw,
                        modelo.empleados.TipoUsuario.EMPLEADO, cargo);
                    empleados.add(emp);
                    persEmpleados.guardarListaEmpleados(empleados);
                    JOptionPane.showMessageDialog(this,
                        "Trabajador registrado con ID: " + id);
                }
                cardLayout.show(cards,"login");
            } catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Error en registro");
            }
        });
        return p;
    }

    private JPanel createClientPanel(){
        JPanel p=new JPanel(new GridLayout(3,1,5,5));
        p.add(makeButton("Comprar Tiquete", e->onClientComprar()));
        p.add(makeButton("Ver Mis Tiquetes", e->onClientVerTiquetes()));
        p.add(makeButton("Logout", e->cardLayout.show(cards,"login")));
        return p;
    }

    private JPanel createEmployeePanel(){
        JPanel p=new JPanel(new GridLayout(4,1,5,5));
        p.add(makeButton("Agregar Cliente", e->onAgregarCliente()));
        p.add(makeButton("Vender Tiquete", e->onVenderTiquete()));
        p.add(makeButton("Validar Acceso", e->onValidarAcceso()));
        p.add(makeButton("Logout", e->cardLayout.show(cards,"login")));
        return p;
    }

    private JPanel createAdminPanel(){
        JPanel p=new JPanel(new GridLayout(10,1,4,4));
        p.add(makeButton("Ver Clientes", e->showList("Clientes", clientes)));
        p.add(makeButton("Ver Trabajadores", e->showList("Trabajadores", empleados)));
        p.add(makeButton("Ver Atracciones", e->onAdminVerAtracciones()));
        p.add(makeButton("Agregar Cliente", e->onAgregarCliente()));
        p.add(makeButton("Eliminar Cliente", e->onRemoveCliente()));
        p.add(makeButton("Agregar Trabajador", e->onAgregarTrabajador()));
        p.add(makeButton("Eliminar Trabajador", e->onRemoveTrabajador()));
        p.add(makeButton("Agregar Atracción", e->onAddAtraccion()));
        p.add(makeButton("Asignar Empleado a Atracción", e->onAssignEmpleado()));
        p.add(makeButton("Logout", e->cardLayout.show(cards,"login")));
        return p;
    }

    private JButton makeButton(String txt, ActionListener lst){ JButton b=new JButton(txt); b.addActionListener(lst); return b; }

    private <T> void showList(String title, List<T> list){
        StringBuilder sb = new StringBuilder();
        if(list.isEmpty()) sb.append("<No hay elementos>");
        else for(T o : list) {
            if(o instanceof Cliente c) sb.append(String.format("%d - %s\n", c.getId(), c.getNombre()));
            if(o instanceof Empleado emp) sb.append(String.format("%d - %s\n", emp.getId(), emp.getNombre()));
        }
        JTextArea area = new JTextArea(sb.toString()); area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area); scroll.setPreferredSize(new Dimension(350,200));
        JOptionPane.showMessageDialog(this, scroll, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void onLogin(ActionEvent e){
        String u=loginField.getText().trim(), p=new String(passField.getPassword());
        if(u.equals("admin")&&p.equals("admin")){
            currentRole="ADMIN";
            cardLayout.show(cards,"admin");
            return;
        }
        for(Empleado emp:empleados) if(emp.getLogin().equals(u)&&emp.getPassword().equals(p)){
            currentRole="EMPLEADO";
            cardLayout.show(cards,"employee");
            return;
        }
        for(Cliente cli:clientes) if(cli.getLogin().equals(u)&&cli.getPassword().equals(p)){
            currentRole="CLIENTE";
            currentUserId=cli.getId();
            cardLayout.show(cards,"client");
            return;
        }
        JOptionPane.showMessageDialog(this,"Credenciales inválidas.");
    }

    // Cliente actions
    private void onClientComprar(){
        try{
            // Obtener cliente actual
            Cliente cli = clientes.stream()
                .filter(c->c.getId()==currentUserId).findFirst().orElse(null);
            if(cli == null) return;
            
            // Seleccionar tipo de tiquete
            Tipo[] tipos = Tipo.values();
            Tipo tp = (Tipo) JOptionPane.showInputDialog(
                this,
                "Seleccione tipo de tiquete:",
                "Comprar Tiquete",
                JOptionPane.QUESTION_MESSAGE,
                null,
                tipos,
                tipos[0]
            );
            if(tp == null) return;

            // Asignar precio ficticio según nivel
            float precio;
            switch(tp) {
                case FAMILIAR: precio = 50f; break;
                case ORO:      precio = 100f; break;
                case DIAMANTE: precio = 150f; break;
                case BASICO:precio = 200f; break;
               
                default:       precio = 50f; break;
            }

            // Confirmación de compra
            int resp = JOptionPane.showConfirmDialog(
                this,
                String.format("El precio es $%.2f. ¿Desea confirmar la compra?", precio),
                "Confirmar Compra",
                JOptionPane.YES_NO_OPTION
            );
            if(resp != JOptionPane.YES_OPTION) {
                return; // No compra
            }

            // Crear y registrar tiquete
            TiquetesNormales t = new TiquetesNormales(
                "T" + System.currentTimeMillis(),
                tp,
                new ArrayList<>(),
                0
            );
            tiquetes.add(t);
            cli.comprarTiquete(String.format("Tiquete %s tipo %s comprado a $%.2f", t.getId(), tp, precio));

            // Persistencia
            persTiquetes.guardarListaTiquetes(tiquetes);
            persClientes.guardarListaClientes(clientes);

            JOptionPane.showMessageDialog(
                this,
                "Compra realizada correctamente."
            );
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(this,"Error al procesar la compra");
        }
    }

    private void onClientVerTiquetes(){
        Cliente cli=clientes.stream()
            .filter(c->c.getId()==currentUserId).findFirst().orElse(null);
        if(cli == null) return;
        List<String> hist=cli.getHistorialCompras();
        StringBuilder sb=new StringBuilder();
        if(hist.isEmpty()) sb.append("<No tiene tiquetes>");
        else hist.forEach(s->sb.append(s).append("\n"));
        JTextArea area=new JTextArea(sb.toString()); area.setEditable(false);
        JOptionPane.showMessageDialog(this,new JScrollPane(area),
            "Mis Tiquetes", JOptionPane.INFORMATION_MESSAGE);
    }

    // Admin actions for attractions
    private void onAddAtraccion(){
        try{
            int id=random.nextInt(9000)+1000;
            String[] tipos={"Mecánica","Cultural"};
            String sel=(String)JOptionPane.showInputDialog(this,
                "Tipo Atracción:","Agregar Atracción",
                JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);
            if(sel == null) return;
            String nombre=JOptionPane.showInputDialog(this,"Nombre:");
            if(nombre == null) return;
            String capStr=JOptionPane.showInputDialog(this,"Capacidad:");
            if(capStr == null) return;
            int cap=Integer.parseInt(capStr);
            String ubi=JOptionPane.showInputDialog(this,"Ubicación:");
            if(ubi == null) return;
            String niv=JOptionPane.showInputDialog(this,"Nivel Exclusividad:");
            if(niv == null) return;
            
            Atraccion a;
            if(sel.equals("Mecánica")){
                String minAltStr=JOptionPane.showInputDialog(this,"Altura min:");
                if(minAltStr == null) return;
                double minAlt=Double.parseDouble(minAltStr);
                String maxAltStr=JOptionPane.showInputDialog(this,"Altura max:");
                if(maxAltStr == null) return;
                double maxAlt=Double.parseDouble(maxAltStr);
                String minPStr=JOptionPane.showInputDialog(this,"Peso min:");
                if(minPStr == null) return;
                double minP=Double.parseDouble(minPStr);
                String maxPStr=JOptionPane.showInputDialog(this,"Peso max:");
                if(maxPStr == null) return;
                double maxP=Double.parseDouble(maxPStr);
                String riesgo=JOptionPane.showInputDialog(this,"Nivel Riesgo:");
                if(riesgo == null) return;
                String contra=JOptionPane.showInputDialog(this,"Contraindicaciones:");
                if(contra == null) return;
                a=new AtraccionMecanica(id,nombre,cap,ubi,niv,minAlt,maxAlt,minP,maxP,riesgo,contra);
            } else {
                String edStr=JOptionPane.showInputDialog(this,"Edad mínima:");
                if(edStr == null) return;
                int ed=Integer.parseInt(edStr);
                String desc=JOptionPane.showInputDialog(this,"Descripción:");
                if(desc == null) return;
                a=new AtraccionCultural(id,nombre,cap,ubi,niv,ed,desc);
            }
            a.registrar(); 
            atracciones.add(a);
            persAtracciones.guardarListaAtracciones(atracciones);
            JOptionPane.showMessageDialog(this,"Atracción agregada con ID: "+id);
        }catch(Exception ex){ 
            JOptionPane.showMessageDialog(this,"Error al agregar atracción"); 
        }
    }

    private void onAssignEmpleado(){
        try{
            if(atracciones.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay atracciones disponibles");
                return;
            }
            if(empleados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay empleados disponibles");
                return;
            }
            
            String[] opts=atracciones.stream()
                .map(a->a.getId()+" - "+a.getNombre()).toArray(String[]::new);
            String sel=(String)JOptionPane.showInputDialog(this,
                "Seleccione atracción:","Asignar",
                JOptionPane.QUESTION_MESSAGE,null,opts,opts[0]);
            if(sel==null) return;
            int idA=Integer.parseInt(sel.split(" - ")[0]);
            Atraccion atr=atracciones.stream()
                .filter(a->a.getId()==idA).findFirst().orElse(null);
            if(atr == null) return;
                
            opts=empleados.stream()
                .map(e->e.getId()+" - "+e.getNombre()).toArray(String[]::new);
            sel=(String)JOptionPane.showInputDialog(this,
                "Seleccione empleado:","Asignar",
                JOptionPane.QUESTION_MESSAGE,null,opts,opts[0]);
            if(sel==null) return;
            int idE=Integer.parseInt(sel.split(" - ")[0]);
            asignaciones.put(idE, idA);
            JOptionPane.showMessageDialog(this,
                "Empleado asignado correctamente.");
        }catch(Exception ex){ 
            JOptionPane.showMessageDialog(this,"Error en asignación"); 
        }
    }

    // Mostrar atracciones con sus asignaciones
    private void onAdminVerAtracciones(){
        try {
            StringBuilder sb = new StringBuilder();
            Map<Integer, List<String>> map = new HashMap<>();
            for(Map.Entry<Integer,Integer> entry : asignaciones.entrySet()){
                int empId = entry.getKey();
                int atrId = entry.getValue();
                String empName = empleados.stream()
                    .filter(emp -> emp.getId() == empId)
                    .map(Empleado::getNombre)
                    .findFirst().orElse("<Desconocido>");
                map.computeIfAbsent(atrId, k -> new ArrayList<>()).add(empName);
            }
            for(Atraccion a : atracciones){
                sb.append(a.getId())
                  .append(" - ")
                  .append(a.getNombre())
                  .append(": ");
                List<String> emps = map.get(a.getId());
                sb.append(emps == null || emps.isEmpty()
                          ? "Sin asignar"
                          : String.join(", ", emps))
                  .append("\n");
            }
            JTextArea area = new JTextArea(sb.toString());
            area.setEditable(false);
            JScrollPane scroll = new JScrollPane(area);
            scroll.setPreferredSize(new Dimension(400,300));
            JOptionPane.showMessageDialog(
                this,
                scroll,
                "Atracciones y Asignaciones",
                JOptionPane.INFORMATION_MESSAGE
            );
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al mostrar atracciones");
        }
    }

    private void onAgregarCliente() { cardLayout.show(cards,"register"); }
    private void onRemoveCliente() {
        // Implementación pendiente
        JOptionPane.showMessageDialog(this, "Funcionalidad no implementada aún");
    }
    private void onAgregarTrabajador() { cardLayout.show(cards,"register"); }
    private void onRemoveTrabajador() {
        // Implementación pendiente
        JOptionPane.showMessageDialog(this, "Funcionalidad no implementada aún");
    }

    private void onVenderTiquete(){
        // Implementación pendiente
        JOptionPane.showMessageDialog(this, "Funcionalidad no implementada aún");
    }
    private void onValidarAcceso(){
        // Implementación pendiente
        JOptionPane.showMessageDialog(this, "Funcionalidad no implementada aún");
    }

    private void onSalir(){
        persClientes.guardarListaClientes(clientes);
        persEmpleados.guardarListaEmpleados(empleados);
        persAtracciones.guardarListaAtracciones(atracciones);
        persTiquetes.guardarListaTiquetes(tiquetes);
        System.exit(0);
    }

    public static void main(String[] args){ SwingUtilities.invokeLater(ParqueSwingApp::new); }
}