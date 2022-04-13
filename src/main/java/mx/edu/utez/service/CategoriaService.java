package mx.edu.utez.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.edu.utez.model.Categoria;

public interface CategoriaService {
	List<Categoria> listar();

	boolean guardar(Categoria categoria);

	boolean eliminar(long id);

	Categoria mostrar(long id);

	Page<Categoria> listarPaginacion(Pageable page);
}
