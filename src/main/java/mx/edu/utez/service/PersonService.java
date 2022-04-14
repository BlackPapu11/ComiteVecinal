package mx.edu.utez.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.edu.utez.model.Person;


public interface PersonService {
	List<Person> listar();

	boolean guardar(Person person);

	boolean eliminar(long id);

	Person mostrar(long id);

	Page<Person> listarPaginacion(Pageable page);
}
