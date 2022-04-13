package mx.edu.utez.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.model.Anexo;

public interface AnexoRepository extends JpaRepository<Anexo, Long> {
    Set<Anexo> findAllByIncidenciaId(long id);
}
