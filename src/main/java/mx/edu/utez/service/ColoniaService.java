package mx.edu.utez.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.model.Colonia;
import mx.edu.utez.repository.ColoniaRepository;
import mx.edu.utez.repository.UserRepository;

@Service
@Transactional
public class ColoniaService {
    @Autowired
    ColoniaRepository coloniaRepository;
    @Autowired
    UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Colonia> findAll(String correo) {
        long id = userRepository.findByCorreo(correo).getMunicipio().getId();
        return coloniaRepository.findAllByMunicipioId(id);
    }

    @Transactional(rollbackFor = { SQLException.class })
    public boolean saveColonia(Colonia colonia) {
        colonia.setStatus(true);
        return coloniaRepository.saveAndFlush(colonia).getId() > 0;
    }

    @Transactional(rollbackFor = { SQLException.class })
    public boolean updateColonia(Colonia colonia) {
        if (coloniaRepository.existsById(colonia.getId())) {
            coloniaRepository.saveAndFlush(colonia);
            return true;
        } else {
            return false;
        }
    }

    @Transactional(rollbackFor = { SQLException.class })
    public boolean statusColonia(long id) {
        if (coloniaRepository.existsById(id)) {
            Colonia colonia = coloniaRepository.findById(id).get();
            if (colonia.getStatus()) {
                colonia.setStatus(false);
            } else {
                colonia.setStatus(true);
            }
            coloniaRepository.saveAndFlush(colonia);
            return true;
        }
        return false;
    }
}
