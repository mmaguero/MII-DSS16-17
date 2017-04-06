package modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Clase p/ persistencia
 * 
 * @author maguero
 *
 */
@Entity
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;
	private String nombre;
	private String apellido;
	private String email;

	/**
	 * Contructor vacío p/ instancias
	 */
	public Usuario() {
	}

	/**
	 * Constructor que recibe objeto p/ instancias
	 * 
	 * @param usuarioAt
	 */
	public Usuario(Usuario usuarioAt) {
		this.id = usuarioAt.getId();
		this.email = usuarioAt.getEmail();
		this.apellido = usuarioAt.getApellido();
		this.nombre = usuarioAt.getNombre();
	}

	/**
	 * Getters para acceder a atributos de la clase
	 * 
	 * @return atributos
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setters para asignar valores a atributos de la clase
	 * 
	 * @param atributos
	 */
	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Sobre-escribimos para devolver detalle de atributos
	 */
	@Override
	public String toString() {
		return nombre + " " + apellido + " - " + email;
	}

}
