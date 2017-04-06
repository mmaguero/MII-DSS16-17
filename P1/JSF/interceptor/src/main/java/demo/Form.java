package demo;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "form", eager = true)
@SessionScoped
public class Form {

	private String estadoColor;
	private String estadoTexto;
	private String aceleraTexto;
	private String titulo;

	protected Boolean apagado;

	public Form() {
		System.out.println("Form ha comenzado!");
		this.estadoColor = "green";
		this.estadoTexto = "Encender";
		this.aceleraTexto = "Acelerar";
		this.titulo = "Apagado";
		this.apagado = true;
	}

	public String getEstadoColor() {
		return estadoColor;
	}

	public void setEstadoColor(String estadoColor) {
		this.estadoColor = estadoColor;
	}

	public String getEstadoTexto() {
		return estadoTexto;
	}

	public void setEstadoTexto(String estadoTexto) {
		this.estadoTexto = estadoTexto;
	}

	public String getAceleraTexto() {
		return aceleraTexto;
	}

	public void setAceleraTexto(String aceleraTexto) {
		this.aceleraTexto = aceleraTexto;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void acelerar() {
		if (!apagado) {
			this.aceleraTexto = "Acelerando";
			this.estadoTexto = "Apagar";
			this.estadoColor = "red";
			this.titulo = "Encendido";
		}
	}

	public void cambiarElementos() {
		cambiarEstado();
		if (!apagado) {
			this.estadoTexto = "Apagar";
			this.estadoColor = "red";
			this.aceleraTexto = "Acelerar";
			this.titulo = "Encendido";
		} else {
			this.estadoTexto = "Encender";
			this.estadoColor = "green";
			this.aceleraTexto = "Acelerar";
			this.titulo = "Apagado";
		}

	}

	public void cambiarEstado() {
		this.apagado = !this.apagado;
	}
}
