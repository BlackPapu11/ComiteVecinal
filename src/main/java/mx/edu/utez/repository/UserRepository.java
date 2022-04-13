package mx.edu.utez.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.edu.utez.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByCorreo(String correo);

	@Modifying
	@Query(value = "update users u set u.contrasena = :contrasena where u.correo = :correo", nativeQuery = true)
	void updateContrasena(@Param("contrasena") String contrasena, @Param("correo") String correo);

}
