package mx.edu.utez.service;

import mx.edu.utez.model.Anexo;
import mx.edu.utez.model.Categoria;
import mx.edu.utez.model.Comentario;
import mx.edu.utez.model.Incidencia;
import mx.edu.utez.model.UserComite;
import mx.edu.utez.repository.AnexoRepository;
import mx.edu.utez.repository.CategoriaRepository;
import mx.edu.utez.repository.ComentarioRepository;
import mx.edu.utez.repository.IncidenciaRepository;
import mx.edu.utez.repository.UserComiteRepository;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class IncidenciasService {
    @Autowired
    IncidenciaRepository incidenciaRepository;
    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    AnexoRepository anexoRepository;
    @Autowired
    ComentarioRepository comentarioRepository;
    @Autowired
    UserComiteRepository userComiteRepository;

    @Transactional(readOnly = true)
    public List<Incidencia> findAll(String email) {
        return incidenciaRepository.findAllByUserComiteUserCorreoOrderByStatusAscHabilitadoDesc(email);
    }

    @Transactional(readOnly = true)
    public List<Categoria> findAllCategories() {
        return categoriaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<UserComite> findUserComite(String email) {
        return userComiteRepository.findByUserCorreo(email);
    }

    @Transactional(readOnly = true)
    public Optional<Incidencia> findById(long id) {
        return incidenciaRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findCommentsAndAnexosByIncidencia(long id) {
        Map<String, Object> data = new HashMap<>();
        data.put("comments", comentarioRepository.findAllByIncidenciaId(id));
        data.put("anexos", anexoRepository.findAllByIncidenciaId(id));
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @Transactional(rollbackFor = { SQLException.class })
    public ResponseEntity<Object> removeAnexoById(long id) {
        Map<String, Object> data = new HashMap<>();
        Optional<Anexo> anexo = anexoRepository.findById(id);
        try {
            Path anexosIncidencia = Paths.get("C:\\projects\\" + anexo.get().getEvidencia());
            Files.deleteIfExists(anexosIncidencia);
            anexoRepository.deleteById(id);
            data.put("error", false);
        } catch (IOException e) {
            data.put("error", true);
            e.printStackTrace();
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @Transactional(rollbackFor = { SQLException.class })
    public Incidencia updateIncidence(Incidencia incidencia, MultipartFile[] files, String path) {
        Incidencia saveIncidencia = incidenciaRepository.saveAndFlush(incidencia);
        Set<Anexo> anexos = new HashSet<>();
        if (files.length > 0) {
            for (MultipartFile file : files) {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename()).replace(" ", "_");
                Anexo anexo = new Anexo();
                anexo.setEvidencia(fileName);
                anexo.setIncidencia(saveIncidencia);
                anexos.add(anexo);
                try {
                    Path anexosIncidencia = Paths.get(path + fileName);
                    file.transferTo(anexosIncidencia);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            anexoRepository.saveAllAndFlush(anexos);
        }
        return saveIncidencia;
    }

    @Transactional(rollbackFor = { SQLException.class })
    public ResponseEntity<Object> atendida(Long id) {
        Incidencia incidencia = incidenciaRepository.findById(id).get();
        incidencia.setStatus(4);
        incidencia.setPago(true);
        Map<String, Object> data = new HashMap<>();
        data.put("error", false);
        incidenciaRepository.saveAndFlush(incidencia);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @Transactional(rollbackFor = { SQLException.class })
    public Incidencia saveIncidence(Incidencia incidencia, MultipartFile[] files, String path) {
        Set<Comentario> comentarios = incidencia.getComentario();
        incidencia.setComentario(null);
        incidencia.setAnexo(null);
        Incidencia saveIncidencia = incidenciaRepository.saveAndFlush(incidencia);
        if (saveIncidencia.getId() > 0) {
            for (Comentario comentario : comentarios) {
                comentario.setIncidencia(saveIncidencia);
                comentario.setFechaRegistro(incidencia.getFechaRegistro());
                comentario.setPersonaMensaje(Integer.parseInt(incidencia.getUserComite().getUser().getId() + ""));
            }
            comentarioRepository.saveAllAndFlush(comentarios);
            Set<Anexo> anexos = new HashSet<>();
            for (MultipartFile file : files) {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename()).replace(" ", "_");
                Anexo anexo = new Anexo();
                anexo.setEvidencia(fileName);
                anexo.setIncidencia(saveIncidencia);
                anexos.add(anexo);
                try {
                    Path anexosIncidencia = Paths.get(path + fileName);
                    file.transferTo(anexosIncidencia);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            anexoRepository.saveAllAndFlush(anexos);
        }
        return saveIncidencia;
    }

    @Transactional(rollbackFor = { SQLException.class })
    public boolean statusIncidencia(long id) {
        Incidencia incidencia = incidenciaRepository.findById(id).get();
        if (incidencia.isHabilitado()) {
            incidencia.setHabilitado(false);
        } else {
            incidencia.setHabilitado(true);
        }
        incidenciaRepository.saveAndFlush(incidencia);
        return true;
    }
}
