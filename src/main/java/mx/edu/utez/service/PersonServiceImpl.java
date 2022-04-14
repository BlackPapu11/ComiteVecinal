package mx.edu.utez.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import mx.edu.utez.model.Municipio;
import mx.edu.utez.model.Person;
import mx.edu.utez.repository.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	@Override
	public List<Person> listar() {
		return personRepository.findAll(Sort.by("id"));
	}

	@Override
	public boolean guardar(Person person) {
		try {
			personRepository.save(person);
			return true;
		}catch(Exception ex) {
			System.err.println(ex.getMessage());
			return false;
		}
	}

	@Override
	public boolean eliminar(long id) {
		boolean exist = personRepository.existsById(id);
		if(exist) {
			personRepository.deleteById(id);
			return !personRepository.existsById(id);
		}
		
		return false;
	}

	@Override
	public Person mostrar(long id) {
		Optional<Person> optional=personRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public Page<Person> listarPaginacion(Pageable page) {
		return personRepository.findAll(page);
	}

}
