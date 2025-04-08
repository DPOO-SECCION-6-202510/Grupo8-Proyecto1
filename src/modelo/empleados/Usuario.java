package modelo.empleados;


	
public abstract class Usuario {
	
	    private int id;
	    private String nombre;
	    private String login;
	    private String password;
	    private TipoUsuario tipo;

	    // Constructor
	    public Usuario(int id, String nombre, String login, String password, TipoUsuario tipo) {
	        this.id = id;
	        this.nombre = nombre;
	        this.login = login;
	        this.password = password;
	        this.tipo = tipo;
	    }

	    // Getters y Setters
	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public String getNombre() {
	        return nombre;
	    }

	    public void setNombre(String nombre) {
	        this.nombre = nombre;
	    }

	    public String getLogin() {
	        return login;
	    }

	    public void setLogin(String login) {
	        this.login = login;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public TipoUsuario getTipo() {
	        return tipo;
	    }

	    public void setTipo(TipoUsuario tipo) {
	        this.tipo = tipo;
	    }

	    // Método para autenticar
	    public boolean autenticar(String login, String password) {
	        return this.login.equals(login) && this.password.equals(password);
	    }

	    // Método para cambiar la contraseña
	    public void cambiarPassword(String nuevaPassword) {
	        this.password = nuevaPassword;
	    }

	    // Método abstracto para mostrar información
	    public abstract void mostrarInformacion();
	}

}
