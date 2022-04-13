package mx.edu.utez.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.model.User;
import mx.edu.utez.repository.UserRepository;

@Service
@Transactional 
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public boolean guardar(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eliminar(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User buscarPorCorreo(String correo) {
		return userRepository.findByCorreo(correo);
	}
	
	@Override
	   public boolean cambiarContrasena(String contrasena, String correo) {
	       try {
	           userRepository.updateContrasena(contrasena, correo);
	           return true;
	       } catch (Exception exception) {
	           System.out.println(exception.getMessage());
	           exception.printStackTrace();
	           return false;
	       }
	   }

}
