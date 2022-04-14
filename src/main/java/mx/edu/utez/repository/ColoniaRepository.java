package mx.edu.utez.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.model.Colonia;

public interface ColoniaRepository extends JpaRepository<Colonia, Long> {
    List<Colonia> findAllByMunicipioId(long id);

    List<Colonia> findAllByMunicipioIdAndStatus(long id, Boolean status);
}
