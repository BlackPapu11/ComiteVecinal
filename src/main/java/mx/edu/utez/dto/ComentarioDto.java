package mx.edu.utez.dto;

import java.sql.Date;
import java.util.Set;

import mx.edu.utez.model.Anexo;

public class ComentarioDto {
    private Long id;
    private String comentario;
    private String personaMensaje;
    private Date fechaRegistro;
    private Long idIncidencia;
    private Set<Anexo> anexo;

    public ComentarioDto() {
    }

    public ComentarioDto(String comentario, String personaMensaje, Date fechaRegistro, Long idIncidencia) {
        this.comentario = comentario;
        this.personaMensaje = personaMensaje;
        this.fechaRegistro = fechaRegistro;
        this.idIncidencia = idIncidencia;
        this.anexo = Set.of(new Anexo());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getPersonaMensaje() {
        return personaMensaje;
    }

    public void setPersonaMensaje(String personaMensaje) {
        this.personaMensaje = personaMensaje;
    }

    public void setIdIncidencia(Long idIncidencia) {
        this.idIncidencia = idIncidencia;
    }

    public Long getIdIncidencia() {
        return idIncidencia;
    }

    public Set<Anexo> getAnexo() {
        return anexo;
    }

    public void setAnexo(Set<Anexo> anexo) {
        this.anexo = anexo;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }
}
