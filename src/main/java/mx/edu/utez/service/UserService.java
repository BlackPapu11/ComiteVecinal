package mx.edu.utez.service;

import mx.edu.utez.model.User;

public interface UserService {
	
	boolean guardar(User user);

	boolean eliminar(long id);

	User buscarPorCorreo(String correo);

}
