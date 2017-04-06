package jpa.eclipselink.modelo;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Persona {
	 @Id
	  @GeneratedValue(strategy = GenerationType.TABLE)
	  private String id;
	  private String nombre;
	  private String apellidos;

	  private Familia familia;

	  private String campoVacio = "";

	  private List<Empleo> listaEmpleos = new ArrayList<Empleo>();

	  public String getId() {
	    return id;
	  }

	  public void setId(String Id) {
	    this.id = Id;
	  }

	  public String getNombre() {
	    return nombre;
	  }

	  public void setNombre(String nombre) {
	    this.nombre = nombre;
	  }

	  // Leave the standard column name of the table
	  public String getApellidos() {
	    return apellidos;
	  }

	  public void setApellidos(String apellidos) {
	    this.apellidos = apellidos;
	  }

	  @ManyToOne
	  public Familia getFamilia() {
	    return familia;
	  }

	  public void setFamilia(Familia familia) {
	    this.familia = familia;
	  }

	  @Transient
	  public String getCampoVacio() {
	    return campoVacio;
	  }

	  public void setCampoVacio(String campoVacio) {
	    this.campoVacio = campoVacio;
	  }

	  @OneToMany
	  public List<Empleo> listaEmpleos() {
	    return this.listaEmpleos;
	  }

	  public void setListaEmpleos(List<Empleo> nombre) {
	    this.listaEmpleos = nombre;
	  }

}
