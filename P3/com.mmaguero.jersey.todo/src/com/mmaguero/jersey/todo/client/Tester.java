package com.mmaguero.jersey.todo.client;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import com.mmaguero.jersey.todo.model.Todo;

public class Tester {
	public static void main(String[] args) {

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget service = client.target(getBaseURI());

		// OTHER todo
		Todo todo = new Todo("3", "Luego aprendemos BPEL");
		Response response = service.path("rest").path("todos").path(todo.getId()).request(MediaType.APPLICATION_XML)
				.put(Entity.entity(todo, MediaType.APPLICATION_XML), Response.class);
		// Retorna 201 == created resource
		System.out.println("Respuesta: " + response.getStatus());

		// Get Todos
		System.out.println("Mostrando como XML Plano");
		System.out.println(service.path("rest").path("todos").request().accept(MediaType.TEXT_XML).get(String.class));

		// Get XML for application
		System.out.println(
				service.path("rest").path("todos").request().accept(MediaType.APPLICATION_XML).get(String.class));

		// Form Todo
		Form form = new Form();
		form.param("id", "4");
		form.param("summary", "Aún nos queda aprender apps con Android Studio");
		response = service.path("rest").path("todos").request()
				.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
		System.out.println("Respuesta Form " + response.getStatus());
		// Get all, and new id 4
		System.out.println("Después de crear recurso cuatro");
		System.out.println(
				service.path("rest").path("todos").request().accept(MediaType.APPLICATION_XML).get(String.class));

		// Borrar recurso 1
		Response checkDelete = service.path("rest").path("todos/1").request().accept(MediaType.APPLICATION_XML).get();
		service.path("rest").path("todos/1").request().delete();

		// Luego borrar 1
		System.out.println("Después de borrar recurso uno " + checkDelete.getStatus());
		System.out.println(
				service.path("rest").path("todos").request().accept(MediaType.APPLICATION_XML).get(String.class));
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/com.mmaguero.jersey.todo").build();
	}
}