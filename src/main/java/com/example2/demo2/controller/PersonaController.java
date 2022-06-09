package com.example2.demo2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example2.demo2.model.PersonaModel;

@RestController
@RequestMapping("/apiPersona")
public class PersonaController {

	List<PersonaModel> lista = new ArrayList<>();

	public PersonaController() {
		//Hardcodeo 3 empleados y los agrego a la lista para probar
		PersonaModel p1 = new PersonaModel("Juan", "Perez", 11111111);
		PersonaModel p2 = new PersonaModel("Marcos", "Juarez", 22222222);
		PersonaModel p3 = new PersonaModel("Nazarena", "Martinez", 33333333);
		
		lista.add(p1);
		lista.add(p2);
		lista.add(p3);
	} 

	@GetMapping("/message")
	public List<String> getMessage()
	{
		List<String> lista = new ArrayList<>();
		String msg = "Llegue al servidor";
		lista.add(msg);
		lista.add("Hola");
		lista.add("Chau");
		System.out.println(msg);
		
		return lista;
	}
	
	/** Devuelve una lista con todas las personas
	 * 
	 * @return
	 */
	@GetMapping("/personas")
	public ResponseEntity<?> getPersonas()
	{
		return new ResponseEntity<List<PersonaModel>>( this.lista, HttpStatus.ACCEPTED);
	}
	
	/**	Devuelve la persona con el dni que se le pasa como parametro
	 * En caso de que no encuentre una persona con ese dni, devuelve un mensaje de erorr
	 * 
	 * @param dni
	 * @return
	 */
	@GetMapping("/personas/{dni}")
	public ResponseEntity<?> getUnaPersona( @PathVariable Integer dni)
	{
		for(PersonaModel p: this.lista)
		{
			if(dni.equals(p.getDni()))
			{
				return new ResponseEntity<PersonaModel>(p,  HttpStatus.OK);
			}
		}
		
		return new ResponseEntity<String>("No se encontro una persona con ese DNI", HttpStatus.NOT_FOUND);
	}
	
	/** Agrega una persona al array
	 * En caso de que falte un campo, le asigna null. y si le sobra lo ignora
	 * La persona en el postman se pasa en el body, tipo raw, formato JSON. y se pasa el JSON.
	 * 
	 * @return
	 */
	@PostMapping("/persona")
	public PersonaModel newPersona(@RequestBody PersonaModel persona)
	{
		this.lista.add(persona); 
		return persona;
	}
	
	/** Edita una persona.
	 * La busca por el dni y le cambia el nombre y apellido
	 * 
	 * @param persona
	 * @return
	 */
	@PutMapping("/persona")
	public PersonaModel editPersona(@RequestBody PersonaModel persona) {
		
		for(PersonaModel p :this.lista) {
			if(persona.getDni().equals(p.getDni())) {
				p.setApellido(persona.getApellido());
				p.setNombre(persona.getNombre());
				return p;
			}		
		}
		return null;	
	}
	
	
	/** Elimina de la lista a la persona con el dni pasado como parametro
	 * 
	 * @param dni
	 * @return
	 */
	@DeleteMapping("/persona/{dni}")
	public boolean deletePersona(@PathVariable Integer dni)
	{
		for(PersonaModel p: this.lista)
		{
			if(dni.equals(p.getDni()))
			{
				this.lista.remove(p);
				return true;
			}
		}
		return false;
	}
	
	
}
