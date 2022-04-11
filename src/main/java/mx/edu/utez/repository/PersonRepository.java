package mx.edu.utez.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.model.Person;

public interface PersonRepository extends JpaRepository<Person,Long> {
	
}
