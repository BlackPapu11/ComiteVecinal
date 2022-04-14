package mx.edu.utez.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.edu.utez.model.Municipio;
import mx.edu.utez.model.User;

public interface UserService {
	List<User> listar();
	
	boolean guardar(User user);

	boolean eliminar(long id);

	User mostrar(long id);

	Page<User> listarPaginacion(Pageable page);
	
	User buscarPorCorreo(String correo);
	
	boolean cambiarContrasena(String password, String username);
	
	


}
