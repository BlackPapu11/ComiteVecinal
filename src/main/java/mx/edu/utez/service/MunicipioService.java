package mx.edu.utez.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.edu.utez.model.Municipio;

public interface MunicipioService {

	List<Municipio> listar();

	boolean guardar(Municipio municipio);

	boolean eliminar(long id);

	Municipio mostrar(long id);

	Page<Municipio> listarPaginacion(Pageable page);
}
