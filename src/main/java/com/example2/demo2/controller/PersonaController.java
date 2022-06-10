package com.example2.demo2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example2.demo2.model.AutoModel;
import com.example2.demo2.model.PersonaModel;
import com.example2.demo2.repository.PersonaRepository;

@RestController
@RequestMapping("/apiPersona")
public class PersonaController {

	List<PersonaModel> lista = new ArrayList<>();
	
	@Autowired
	private PersonaRepository personaRepository; 

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
	
	/** Devuelve una lista con todas las personas en la base de datos
	 * 
	 * @return
	 */
	@GetMapping("/personas")
	public ResponseEntity<?> getPersonas() 
	{
		return new ResponseEntity<Iterable<PersonaModel>>(this.personaRepository.findAll(), HttpStatus.OK);
	}
	
	/** Busca a una persona en la base de datos por nombre
	 * 
	 * @param nombre
	 * @return
	 */
	@GetMapping("/personas/{nombre}")
	public PersonaModel getUnaPersonaNombre( @PathVariable String nombre)
	{ 
		return this.personaRepository.findByNombre(nombre);
	}
	
	/** Tambien para demostrar que puedo hacer mis propias consultar personalizadas
	 * 
	 * @param nombre
	 * @return
	 */
	@GetMapping("/personasp/{nombre}")
	public PersonaModel buscarPorNombre( @PathVariable String nombre)
	{
		return this.personaRepository.bucarPorNombre(nombre);
	}
	
	/** Agrega una persona en la base de datos
	 * En caso de que falte un campo, le asigna null. y si le sobra lo ignora
	 * La persona en el postman se pasa en el body, tipo raw, formato JSON. y se pasa el JSON.
	 * 
	 * @return
	 */
	@PostMapping("/persona")
	public ResponseEntity<?> newPersona(@RequestBody PersonaModel persona)
	{
		Iterable<PersonaModel> iterable = this.personaRepository.findAll();
		for(PersonaModel p: iterable)
		{
			if(persona.getDni() == p.getDni())
			{
				return new ResponseEntity<String>("Ya existe una persona con ese DNI", HttpStatus.BAD_REQUEST);
			}
			
		}
		return new ResponseEntity<PersonaModel>(this.personaRepository.save(persona), HttpStatus.OK);
	}
	
	/** Edita una persona de la base de datos
	 * La busca por el dni, le cambia el nombre y apellido y la vuelve a guardar en la base de datos
	 * 
	 * @param persona
	 * @return
	 */
	@PutMapping("/persona")
	public ResponseEntity<?> editPersona(@RequestBody PersonaModel persona) 
	{
		try {
			PersonaModel newPersona = this.personaRepository.findById(persona.getDni()).get();
			newPersona.setNombre(persona.getNombre());
			newPersona.setApellido(persona.getApellido());
			
			return new ResponseEntity<PersonaModel>(this.personaRepository.save(newPersona), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("No se encontro a la persona", HttpStatus.NOT_FOUND);
		}
	}
	
	/** Busca a la persona con el dni pasado y la borra de la base de datos
	 * Si no encuentra a la persona con ese dni lanza una excepcion que es capturada para avisar que no se encontro la persona
	 * 
	 * @param dni
	 * @return
	 */
	@DeleteMapping("/persona/{dni}")
	public ResponseEntity<?> deletePersona(@PathVariable Integer dni)
	{
		try {
			PersonaModel persona = this.personaRepository.findById(dni).get();
			this.personaRepository.delete(persona);
			return new ResponseEntity<String>("Se elimino a la persona", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("No se encontro a la persona", HttpStatus.NOT_FOUND);
		}
	}
	
	/** Busca en la bbdd todas las personas cuyo nombre empiece con el string pasado por parametro
	 * Devuelve una lista con todas las personas que coincidan
	 * Si no coincide ninguna devuelve una lista vacia
	 * @param nombre
	 * @return
	 */
	@GetMapping("/personabuscar/{nombre}")
	public List<PersonaModel> buscarEmpiezaPor(@PathVariable String nombre)
	{
		return this.personaRepository.bucarNombreEmpiezaPor(nombre);
	}
	
	
}
