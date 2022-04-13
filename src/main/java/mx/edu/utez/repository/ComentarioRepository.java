package mx.edu.utez.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario,Long>{
    Set<Comentario> findAllByIncidenciaId(long id);
}

