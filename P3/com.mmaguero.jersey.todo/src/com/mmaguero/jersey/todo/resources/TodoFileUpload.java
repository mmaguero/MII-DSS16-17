package com.mmaguero.jersey.todo.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.*;

import com.mmaguero.jersey.todo.dao.TodoDao;
import com.mmaguero.jersey.todo.model.Todo;

@Path("/files")
public class TodoFileUpload {

	private static final String SERVER_UPLOAD_LOCATION_FOLDER = "C:\\Users\\adm\\Desktop\\";

	/**
	 * Upload a File
	 */

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(FormDataMultiPart form) {

		FormDataBodyPart idPart = form.getField("id");
		String cadena = idPart.getValue();
		String output = "\r\n ID: " + cadena;

		FormDataBodyPart desPart = form.getField("description");
		output += "\r\n Description: " + desPart.getValue().toString();

		FormDataBodyPart sumPart = form.getField("summary");
		output += "\r\n Summary: " + sumPart.getValue().toString();

		FormDataBodyPart filePart = form.getField("file");
		ContentDisposition headerOfFilePart = filePart.getContentDisposition();
		InputStream fileInputStream = filePart.getValueAs(InputStream.class);
		String[] name = headerOfFilePart.getFileName().replace(" ", "").split(":");
		String filePath = SERVER_UPLOAD_LOCATION_FOLDER + name[name.length - 1];
		saveFile(fileInputStream, filePath);
		output += "\r\n File: " + filePath;

		Todo todo = new Todo(idPart.getValue().toString(), sumPart.getValue().toString());
		todo.setDescription(desPart.getValue().isEmpty() ? null : desPart.getValue().toString());
		todo.setFile(fileInputStream != null ? null : fileInputStream);
		todo.setFileName(filePath);
		TodoDao.instance.getModel().put(idPart.getValue().toString(), todo);

		return Response.status(200).entity("***Response***\r\n" + output).build();
	}

	// save uploaded file to a defined location on the server
	private void saveFile(InputStream uploadedInputStream, String serverLocation) {

		try {
			OutputStream out = null;
			int read = 0;
			byte[] bytes = new byte[1024];
			out = new FileOutputStream(new File(serverLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}