package mx.edu.utez.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.model.User;
import mx.edu.utez.repository.UserRepository;

@Service
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

}
