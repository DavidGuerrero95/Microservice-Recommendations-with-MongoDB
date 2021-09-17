package com.appcity.app.recomendaciones.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "recomendaciones")
public class Recomendaciones {

	@Id
	private String id;

	@Indexed(unique = true)
	private String nombre;

	private List<String> busquedas;

	private List<String> intereses;

	private List<Double> ubicacion;

	public Recomendaciones() {
	}

	public Recomendaciones(String nombre, List<String> busquedas, List<String> intereses, List<Double> ubicacion) {
		super();
		this.nombre = nombre;
		this.busquedas = busquedas;
		this.intereses = intereses;
		this.ubicacion = ubicacion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<String> getBusquedas() {
		return busquedas;
	}

	public void setBusquedas(List<String> busquedas) {
		this.busquedas = busquedas;
	}

	public List<String> getIntereses() {
		return intereses;
	}

	public void setIntereses(List<String> intereses) {
		this.intereses = intereses;
	}

	public List<Double> getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(List<Double> ubicacion) {
		this.ubicacion = ubicacion;
	}

}
