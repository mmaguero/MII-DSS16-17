package modelo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import java.util.List;

/**
 * Gestiona las acciones de persistencia
 * 
 * @author maguero
 *
 */
public class BDUsuario {
	private static final String PERSISTENCE_UNIT_NAME = "usuario";
	private static EntityManagerFactory factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

	/**
	 * Comprueba la existencia de un email asociado a algun usuario.
	 * 
	 * @param email
	 * @return boolean
	 */
	public static boolean existeEmail(String email) {
		EntityManager em = factoria.createEntityManager();
		Query query = em.createQuery("Select u from Usuario u WHERE u.email LIKE :email").setParameter("email", email);
		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		} finally {
			em.close();
		}
	}

	/**
	 * Inserta un usuario en la BD, ID unico es email
	 * 
	 * @param usuario
	 */
	public static void insertar(Usuario usuario) {
		if (!existeEmail(usuario.getEmail())) {
			EntityManager em = factoria.createEntityManager();
			em.getTransaction().begin();
			em.persist(usuario);
			em.getTransaction().commit();
			em.close();
		}
	}

	/**
	 * Actualiza nombre y apellido de un usuario en la BD de acuerdo al email.
	 * 
	 * @param usuario
	 */
	public static void actualizar(Usuario datosNuevos) {
		if (existeEmail(datosNuevos.getEmail())) {
			EntityManager em = factoria.createEntityManager();
			Query query = em.createQuery("Select u from Usuario u WHERE u.email LIKE :email").setParameter("email",
					datosNuevos.getEmail());
			Usuario datosAnteriores = (Usuario) query.getSingleResult();
			em.getTransaction().begin();
			datosAnteriores.setNombre(datosNuevos.getNombre());
			datosAnteriores.setApellido(datosNuevos.getApellido());
			em.getTransaction().commit();
			em.close();
		}
	}

	/**
	 * Elimina un usuario de la BD mediante el email.
	 * 
	 * @param usuario
	 */
	public static void eliminar(Usuario usuario) {
		if (existeEmail(usuario.getEmail())) {
			EntityManager em = factoria.createEntityManager();
			em.getTransaction().begin();
			Query query = em.createQuery("Select u from Usuario u WHERE u.email LIKE :email").setParameter("email",
					usuario.getEmail());
			Usuario objetivo = (Usuario) query.getSingleResult();
			em.remove(objetivo);
			em.getTransaction().commit();
			em.close();
		}
	}

	/**
	 * Recupera un usuario de la BD de acuerdo al email.
	 * 
	 * @param email
	 * @return usuario or NULL.
	 */
	public static Usuario seleccionarUsuario(String email) {
		if (existeEmail(email)) {
			EntityManager em = factoria.createEntityManager();
			Query query = em.createQuery("Select u from Usuario u WHERE u.email LIKE :emailUsuario")
					.setParameter("emailUsuario", email);
			Usuario usuario = (Usuario) query.getSingleResult();
			em.close();
			return usuario;
		}
		return null;
	}

	/**
	 * Lista los usuarios registrados.
	 * 
	 * @return lista de usuarios
	 */
	@SuppressWarnings("unchecked")
	public static List<Usuario> listarUsuarios() {
		EntityManager em = factoria.createEntityManager();
		Query query = em.createQuery("Select u from Usuario u");
		List<Usuario> usuarios = query.getResultList();
		em.close();
		return usuarios;
	}

}