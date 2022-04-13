package mx.edu.utez.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.model.UserComite;

public interface UserComiteRepository extends JpaRepository<UserComite,Long>{
    Optional<UserComite> findByUserCorreo(String email);
}
