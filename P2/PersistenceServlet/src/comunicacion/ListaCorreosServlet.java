package comunicacion;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.BDUsuario;
import modelo.Usuario;

/**
 * Servlet p/ comunicación
 * 
 * @author maguero
 *
 */
@WebServlet("/ListaCorreosServlet")
public class ListaCorreosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * llamo a post, get siempre hace lo mismo que post
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * Envia y recibe peticiones, actúa como un middleware para establecer
	 * comunicaciones
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String action = request.getParameter("action");

			switch (action) {
			case "aniadirUsuario":
				ObjectOutputStream outputI = new ObjectOutputStream(response.getOutputStream());
				try {
					String nombre = request.getParameter("nombre"), apellido = request.getParameter("apellido"),
							email = request.getParameter("email");

					if (BDUsuario.existeEmail(email)) {
						outputI.writeInt(1);
						outputI.writeObject("Error: Usuario ya existe");
					} else {
						Usuario usuario = new Usuario();
						usuario.setNombre(nombre);
						usuario.setApellido(apellido);
						usuario.setEmail(email);
						BDUsuario.insertar(usuario);
						outputI.writeInt(0);
						outputI.writeObject("OK: Usuario AGREGADO exitosamente");
					}
				} catch (Exception e) {
					outputI.writeInt(-1);
					outputI.writeObject("Excepcion: Al intentar agregar usuario");
				} finally {
					outputI.flush();
					outputI.close();
				}
				break;

			case "actualizarUsuario":
				ObjectOutputStream outputU = new ObjectOutputStream(response.getOutputStream());
				try {
					String nombre = request.getParameter("nombre"), apellido = request.getParameter("apellido"),
							email = request.getParameter("email");

					if (BDUsuario.existeEmail(email)) {
						Usuario usuario = new Usuario();
						usuario.setNombre(nombre);
						usuario.setApellido(apellido);
						usuario.setEmail(email);
						BDUsuario.actualizar(usuario);
						outputU.writeInt(0);
						outputU.writeObject("OK: Usuario ACTUALIZADO exitosamente");
					} else {
						outputU.writeInt(1);
						outputU.writeObject("Error: Usuario no existe");
					}
				} catch (Exception e) {
					outputU.writeInt(-1);
					outputU.writeObject("Excepcion: Al intentar actualizar usuario");
				} finally {
					outputU.flush();
					outputU.close();
				}
				break;

			case "eliminarUsuario":
				ObjectOutputStream outputD = new ObjectOutputStream(response.getOutputStream());
				try {
					String email = request.getParameter("email");

					if (BDUsuario.existeEmail(email)) {
						Usuario usuario = BDUsuario.seleccionarUsuario(email);
						BDUsuario.eliminar(usuario);
						outputD.writeInt(0);
						outputD.writeObject("OK: Usuario ELIMINADO exitosamente");
					} else {
						outputD.writeInt(1);
						outputD.writeObject("Error: Usuario no existe");
					}
				} catch (Exception e) {
					outputD.writeInt(-1);
					outputD.writeObject("Excepxion: Al intentar eliminar usuario");
				} finally {
					outputD.flush();
					outputD.close();
				}
				break;

			case "listarUsuarios":
				List<Usuario> lista = BDUsuario.listarUsuarios();
				ObjectOutputStream outputS = new ObjectOutputStream(response.getOutputStream());
				outputS.writeObject(lista);
				outputS.flush();
				outputS.close();
				break;

			default:
				ObjectOutputStream output = new ObjectOutputStream(response.getOutputStream());
				output.writeInt(-2);
				output.writeObject("Fatal: Accion inexistente para esta pantalla");
				output.flush();
				output.close();
				break;
			}
		} catch (Exception e) {
			response.setContentType("text/html");
			PrintWriter print = response.getWriter();
			print.println("<table style='tr:hover {background-color: #f5f5f5}' >");
			print.println("<caption><h3>" + "Listado de usuarios" + "</h3></caption>");
			print.println("<tr bgcolor='skyblue'><th>Nombre</th><th>Apellido</th><th>Email</th>");
			for (Usuario u : BDUsuario.listarUsuarios()) {
				print.println("<tr title='" + u.toString() + "'>");
				print.println("<td>" + u.getNombre() + "</td>");
				print.println("<td>" + u.getApellido() + "</td>");
				print.println("<td>" + u.getEmail() + "</td></tr>");
				print.println("</tr>");
			}
			print.println("</table>");
		}
	}
}