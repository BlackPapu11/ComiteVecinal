package mx.edu.utez.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import mx.edu.utez.model.Municipio;
import mx.edu.utez.repository.MunicipioRepository;

@Service
public class MunicipioServiceImpl implements MunicipioService{

	@Autowired
	private MunicipioRepository municipioRepository;
	
	@Override
	public List<Municipio> listar() {
		return municipioRepository.findAll(Sort.by("id"));
	}

	@Override
	public boolean guardar(Municipio municipio) {
		try {
			municipioRepository.save(municipio);
			return true;
		}catch(Exception ex) {
			System.err.println(ex.getMessage());
			return false;
		}
	}

	@Override
	public boolean eliminar(long id) {
		boolean exist = municipioRepository.existsById(id);
		if(exist) {
			municipioRepository.deleteById(id);
			return !municipioRepository.existsById(id);
		}
		
		return false;
	}

	@Override
	public Municipio mostrar(long id) {
		Optional<Municipio> optional=municipioRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public Page<Municipio> listarPaginacion(Pageable page) {
		return municipioRepository.findAll(page);
	}

}
