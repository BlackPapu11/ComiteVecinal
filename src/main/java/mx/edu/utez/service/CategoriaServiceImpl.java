package mx.edu.utez.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import mx.edu.utez.model.Categoria;
import mx.edu.utez.repository.CategoriaRepository;


@Service
public class CategoriaServiceImpl implements CategoriaService{
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	@Override
	public List<Categoria> listar() {
		return categoriaRepository.findAll(Sort.by("id"));
	}

	@Override
	public boolean guardar(Categoria categoria) {
		try {
			categoriaRepository.save(categoria);
			return true;
		}catch(Exception ex) {
			System.err.println(ex.getMessage());
			return false;
		}
	}

	@Override
	public boolean eliminar(long id) {
		boolean exist = categoriaRepository.existsById(id);
		if(exist) {
			categoriaRepository.deleteById(id);
			return !categoriaRepository.existsById(id);
		}
		
		return false;
	}

	@Override
	public Categoria mostrar(long id) {
		Optional<Categoria> optional=categoriaRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public Page<Categoria> listarPaginacion(Pageable page) {
		return categoriaRepository.findAll(page);
	}
	
}
