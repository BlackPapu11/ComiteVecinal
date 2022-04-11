package mx.edu.utez.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.model.User;

public interface UserRepository extends JpaRepository<User,Long>{
	User findByCorreo(String correo);
}
