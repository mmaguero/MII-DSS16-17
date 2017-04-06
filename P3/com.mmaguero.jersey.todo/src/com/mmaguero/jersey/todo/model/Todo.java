package com.mmaguero.jersey.todo.model;

import java.io.InputStream;

//import java.io.InputStream;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Todo {
	private String id;
	private String summary;
	private String description;
	private InputStream file;
	private String fileName;

	public Todo() {

	}

	public InputStream getFile() {
		return file;
	}

	public void setFile(InputStream file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Todo(String id, String summary) {
		this.id = id;
		this.summary = summary;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Recurso -> ID: " + id + ", descripción: " + description + ", resumen: " + summary;
	}

}