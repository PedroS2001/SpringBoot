package com.example2.demo2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="demo_persona")
public class PersonaModel {
	
	@Column(name="nombre")
	private String nombre;
	@Column(name="apellido")
	private String apellido;
	@Id
	@Column(name="dni")
	private Integer dni;

	
	public PersonaModel(){
	}
	
	public PersonaModel(String nombre, String apellido, Integer dni) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Integer getDni() {
		return dni;
	} 

	public void setDni(Integer dni) {
		this.dni = dni;
	} 



	
	
	
}
