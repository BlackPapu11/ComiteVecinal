package mx.edu.utez.service;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.model.Municipio;
import mx.edu.utez.model.User;
import mx.edu.utez.repository.UserRepository;

@Service
@Transactional 
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public boolean guardar(User user) {
		try {
			userRepository.save(user);
			return true;
		}catch(Exception ex) {
			System.err.println(ex.getMessage());
			return false;
		}
	}

	@Override
	public boolean eliminar(long id) {
		boolean exist = userRepository.existsById(id);
		if(exist) {
			userRepository.deleteById(id);
			return !userRepository.existsById(id);
		}
		
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

	@Override
	public List<User> listar() {
		return userRepository.findAll(Sort.by("id"));
	}

	@Override
	public User mostrar(long id) {
		Optional<User> optional=userRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public Page<User> listarPaginacion(Pageable page) {
		return userRepository.findAll(page);
	}

	
}
