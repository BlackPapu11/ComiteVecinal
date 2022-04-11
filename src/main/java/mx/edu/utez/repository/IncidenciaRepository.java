package mx.edu.utez.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.model.Incidencia;

public interface IncidenciaRepository extends JpaRepository<Incidencia,Long> {
	
}
