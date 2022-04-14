package mx.edu.utez.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.model.Comite;
import mx.edu.utez.repository.ComiteRepository;

@Service
@Transactional
public class ComiteService {
    @Autowired
    ComiteRepository comiteRepository;

    @Transactional(readOnly = true)
    public List<Comite> findAll(Long id) {
        return comiteRepository.findAllByColoniaId(id);
    }

    @Transactional(rollbackFor = { SQLException.class })
    public boolean saveComite(Comite comite) {
        comite.setStatus(true);
        return comiteRepository.saveAndFlush(comite).getId() > 0;
    }

    @Transactional(rollbackFor = { SQLException.class })
    public boolean updateComite(Comite comite) {
        if (comiteRepository.existsById(comite.getId())) {
            comiteRepository.saveAndFlush(comite);
            return true;
        } else {
            return false;
        }
    }

    @Transactional(rollbackFor = { SQLException.class })
    public boolean statusComite(long id) {
        if (comiteRepository.existsById(id)) {
            Comite comite = comiteRepository.findById(id).get();
            if (comite.getStatus()) {
                comite.setStatus(false);
            } else {
                comite.setStatus(true);
            }
            comiteRepository.saveAndFlush(comite);
            return true;
        }
        return false;
    }
}
