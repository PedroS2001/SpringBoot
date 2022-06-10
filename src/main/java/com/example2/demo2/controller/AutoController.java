package com.example2.demo2.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example2.demo2.model.AutoModel;
import com.example2.demo2.model.PersonaModel;
import com.example2.demo2.repository.AutoRepository;

@RestController
@RequestMapping("/apiAuto")
public class AutoController {

	@Autowired
	AutoRepository autoRepository;
	
	
	/** Agrega un auto a la base de datos
	 * Se le pasa el objeto "persona" con el campo "dni", los otros no hacen falta
	 * 
	 * @param auto
	 * @return
	 */
	@PostMapping("/auto")
	public AutoModel agregarAuto(@RequestBody AutoModel auto)
	{
		return this.autoRepository.save(auto);
	}
	
	/** Trae todos los autos guardados en la base de datos con sus Personas
	 * 
	 * @return
	 */
	@GetMapping("/autos")
	public ResponseEntity<Iterable<AutoModel>> getAutos()
	{
		return new ResponseEntity<Iterable<AutoModel>>(this.autoRepository.findAll(), HttpStatus.OK);
	}
	
	/** Trae de la base de datos un Auto con todos sus datos
	 *  Trae el auto con el id pasado por parametro
	 * @param id
	 * @return
	 */
	@GetMapping("/autos/{id}")
	public ResponseEntity<?> getUnAuto(@PathVariable Long id)
	{
		try {
			return new ResponseEntity<AutoModel>(this.autoRepository.findById(id).get(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("No se encontro un auto con ese ID", HttpStatus.OK);
		}
	}
	
	
}
