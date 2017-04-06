package jpa.eclipselink.main;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import jpa.eclipselink.modelo.Familia;
import jpa.eclipselink.modelo.Persona;
import java.util.List;

public class JpaTest {
	private static final String PERSISTENCE_UNIT_NAME = "relaciones_persistentes";
	private EntityManagerFactory factoria;
	  @Before
	  public void setUp() throws Exception {
	    factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	    EntityManager em = factoria.createEntityManager();
	    // Comenzar una nueva transaccion local de tal forma que pueda persistir como una nueva entidad
	    em.getTransaction().begin();
		// Leer las entradas que ya hay en la base de datos 
	    Query q = em.createQuery("select m from Persona m");
	    // Las personas no deben tener ningun atributo asignado todavia
	    
	    // Comprobar si necesitamos crear entradas en la base
	    boolean crearseNuevasEntradas = (q.getResultList().size() == 0);
	    // Vamos a ello... 
	   if (crearseNuevasEntradas){
	      assertTrue(q.getResultList().size() == 0);
	      Familia familia = new Familia();
	      familia.setDescripcion("Familia Martinez");
	      em.persist(familia);
	      for (int i = 0; i < 20; i++) {
	        Persona persona = new Persona();
	        persona.setNombre("Pepe_" + i);
	        persona.setApellidos("Martinez_" + i);
	        em.persist(persona);
	       // familia.getMiembros().add(persona);
	       
	        familia.setMiembros(persona);
	        // ahora hacemos que persista la relacion familia-persona
	      
	        em.persist(persona);
	        em.persist(familia);
	      }    
	   }
	    // Ahora hay que hacer "commit" de la transaccion, lo que causa que la 
	   //entidad se salve en la base de datos.
	   em.getTransaction().commit();
	    //Ahora hay que cerrar el EntityManager o perderemos nuestras entradas.
	   em.close();
    }//setUp()
	  @Test
	  public void comprobarPersonas() {
	  //ahora vamos a comprobar la base de datos para ver si las entradas que 
      //hemos creado estan alli.
	  //Para eso, nos crearemos un gestor de entidades "fresco".
		  EntityManager em = factoria.createEntityManager();
	  //Realizaremos una consulta simple que consistira en seleccionar a todas las personas
		  Query q = em.createQuery("select m from Persona m");
	  //Si todo ha ido bien, la lista de personas ha de contener a 20 miembros:
	  //utilizar "assertTrue()", "getResultList()" y "size()" para comprobarlo.
		  assertTrue(q.getResultList().size() == 20);
	  //Acordarse de cerrar el gestor de entidades
		  em.close();
      }
	  @Test
	  public void comprobarFamilia() {
	  //Para esto, nos crearemos un gestor de entidades "fresco"
		  EntityManager em = factoria.createEntityManager();
	  //Recorrer cada una de las entidades y mostrar cada uno de sus campos asi como la fecha de creacion
		  Query q = em.createQuery("select f from Familia f");
		  List<Persona> listaPersona = ((Familia)q.getSingleResult()).getMiembros(); 
		  for(Persona persona: listaPersona){
			  System.out.println(persona.getNombre()+" "+persona.getApellidos());
		  }
	  //Deberiamos tener una familia con 20 personas
      //Utilizar "assertTrue()", "getResultList()", "size()" y "getSingleResult()" para determinarlo
		  assertTrue(q.getResultList().size() == 1);
//		  System.out.println("tamaño familia "+((Familia)q.getSingleResult()).getMiembros().size());
	      assertTrue(((Familia) q.getSingleResult()).getMiembros().size() == 20);
	  //Acordarse de cerrar el gestor de entidades  
		  em.close();
	}
	  
	  @Test(expected = javax.persistence.NoResultException.class)
	  public void eliminarPersona() {
	    //Para esto, nos crearemos un gestor de entidades "fresco"  
		  EntityManager em = factoria.createEntityManager();
	    //Comenzar una nueva transaccion local de tal manera que podamos hacer persitente una nueva 
	    //entidad. Ahora me creare la consulta necesaria eliminar la persona de nombre y apellidos 
	    //que indicare despues. 
		  em.getTransaction().begin();   
	Query q = em.createQuery("SELECT p FROM Persona p WHERE p.nombre=:nombre AND p.apellidos=:apellidos");
	   
	   //Ahora asigno los parametros
	    q.setParameter("nombre", "Pepe_1");
	    q.setParameter("apellidos", "Martinez_!");
	   
	//Ahora utilizo el metodo: "getSingleResult()" para obtener a la persona que me interesa y 
	//los metodos: "remove(persona)" y "commit()" para eliminarla de la entidad y confirmar la 
	//eliminacion, respectivamente.    
	    Persona usuario = (Persona) q.getSingleResult();
	    em.remove(usuario);
	    em.getTransaction().commit();
	    Persona persona = (Persona) q.getSingleResult();
	    assertNull(persona);
	//Acordarse de cerrar el gestor de entidades
	    em.close();
	  }  
}