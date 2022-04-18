package mx.edu.utez.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.edu.utez.model.Role;

public interface RoleService {
	
List<Role> listar();
	
	boolean guardar(Role role);

	boolean eliminar(long id);

	Role mostrar(long id);

	Page<Role> listarPaginacion(Pageable page);
}
