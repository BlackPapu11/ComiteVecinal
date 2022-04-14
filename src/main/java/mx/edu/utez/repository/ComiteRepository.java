package mx.edu.utez.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.model.Comite;

public interface ComiteRepository extends JpaRepository<Comite, Long> {
    List<Comite> findAllByColoniaId(Long id);

    List<Comite> findAllByColoniaIdAndStatus(Long id, Boolean status);
}
