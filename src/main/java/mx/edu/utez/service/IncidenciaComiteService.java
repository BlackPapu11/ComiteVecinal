package mx.edu.utez.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.model.Colonia;
import mx.edu.utez.model.Comentario;
import mx.edu.utez.model.Comite;
import mx.edu.utez.model.Incidencia;
import mx.edu.utez.model.User;
import mx.edu.utez.repository.ColoniaRepository;
import mx.edu.utez.repository.ComentarioRepository;
import mx.edu.utez.repository.ComiteRepository;
import mx.edu.utez.repository.IncidenciaRepository;
import mx.edu.utez.repository.UserRepository;

@Service
@Transactional
public class IncidenciaComiteService {
    @Autowired
    IncidenciaRepository incidenciaRepository;
    @Autowired
    ComiteRepository comiteRepository;
    @Autowired
    ColoniaRepository coloniaRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ComentarioRepository comentarioRepository;

    @Transactional(readOnly = true)
    public Set<Incidencia> findByComite(Long id) {
        return incidenciaRepository.findAllByUserComiteComiteIdAndHabilitadoOrderByStatusAscHabilitadoDesc(id, true);
    }

    @Transactional(readOnly = true)
    public List<Comite> findAllComiteByColonia(Long id) {
        return comiteRepository.findAllByColoniaIdAndStatus(id, true);
    }

    @Transactional(readOnly = true)
    public List<Colonia> findAllColoniaByMunicipio(String correo) {
        long id = userRepository.findByCorreo(correo).getMunicipio().getId();
        return coloniaRepository.findAllByMunicipioIdAndStatus(id, true);
    }

    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public User findUserByCorreo(String correo) {
        return userRepository.findByCorreo(correo);
    }

    @Transactional(readOnly = true)
    public Incidencia findIncidenciaById(Long id) {
        return incidenciaRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public Comentario findCommentById(Long id) {
        return comentarioRepository.findById(id).get();
    }

    @Transactional(rollbackFor = { SQLException.class })
    public Comentario saveComentario(Comentario comentario) {
        return findCommentById(comentarioRepository.saveAndFlush(comentario).getId());
    }

    @Transactional(rollbackFor = { SQLException.class })
    public Incidencia saveIncidenciaAtencion(Incidencia incidencia) {
        return incidenciaRepository.saveAndFlush(incidencia);
    }
}
