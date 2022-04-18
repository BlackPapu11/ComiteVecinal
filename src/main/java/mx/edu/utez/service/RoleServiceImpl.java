package mx.edu.utez.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import mx.edu.utez.model.Role;
import mx.edu.utez.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public List<Role> listar() {
		return roleRepository.findAll(Sort.by("id"));
	}

	@Override
	public boolean guardar(Role role) {
		try {
			roleRepository.save(role);
			return true;
		}catch(Exception ex) {
			System.err.println(ex.getMessage());
			return false;
		}
	}

	@Override
	public boolean eliminar(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Role mostrar(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Role> listarPaginacion(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
