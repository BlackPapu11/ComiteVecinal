package mx.edu.utez.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.model.Incidencia;

public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {
    boolean existsById(long id);
    Optional<Incidencia> findById(long id);
    List<Incidencia> findAllByUserComiteUserCorreoOrderByStatusAscHabilitadoDesc(String email);
    Set<Incidencia> findAllByUserComiteComiteIdAndHabilitadoOrderByStatusAscHabilitadoDesc(Long id, Boolean habilitado);
}
