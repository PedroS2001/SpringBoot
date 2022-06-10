package com.example2.demo2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example2.demo2.model.PersonaModel;

@Repository
public interface PersonaRepository extends CrudRepository<PersonaModel, Integer>  {
	//Puedo hacer mi propia consulta -> findBy+campoExistente y el JPA la interpreta y ejecuta solo
	public PersonaModel findByNombre(String nombre);
	public PersonaModel findByApellido(String apellido);
		
	//Tambien puedo hacer mis consultas a mano
	//:nombre tiene que ser igual al nombre del parametro 
	@Query("select per from PersonaModel per where per.nombre = :nombre")
	public PersonaModel bucarPorNombre(String nombre);
		
	//Al ser nativeQuery ejecuta lo que yo puse en value tal cual lo escribi
	@Query(nativeQuery = true, value="select * from demo_persona per where per.nombre like :nombreInicio%")
	public List<PersonaModel> bucarNombreEmpiezaPor(String nombreInicio);

}
