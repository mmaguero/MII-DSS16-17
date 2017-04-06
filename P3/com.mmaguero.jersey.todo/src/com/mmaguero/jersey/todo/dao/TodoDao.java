package com.mmaguero.jersey.todo.dao;

import java.util.HashMap;
import java.util.Map;

import com.mmaguero.jersey.todo.model.Todo;

public enum TodoDao {
	instance;

	private Map<String, Todo> contentProvider = new HashMap<>();

	private TodoDao() {

		Todo todo = new Todo("1", "Learn REST");
		todo.setDescription(
				"Ingresar u:alumno, p:DSS2014Inn - Leer http://lsi.ugr.es/dsbcs/Documentos/Practica/practica3.html");
		contentProvider.put("1", todo);
		todo = new Todo("2", "Aprender algo sobre DSBCS");
		todo.setDescription("Ingresar u:alumno, p:DSS2014Inn - Leer todo el material de http://lsi.ugr.es/dsbcs");
		contentProvider.put("2", todo);

	}

	public Map<String, Todo> getModel() {
		return contentProvider;
	}

}