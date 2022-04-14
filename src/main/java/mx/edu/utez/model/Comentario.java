package mx.edu.utez.model;

import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "comentarios")
public class Comentario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 45)
	private String comentario;

	@Column(nullable = false, length = 10)
	private int personaMensaje;

	@Column(nullable = false, length = 45)
	private Date fechaRegistro;

	@ManyToOne
	@JoinColumn(name = "incidencia_id", updatable = false, nullable = false)
	@JsonIgnore
	private Incidencia incidencia;

	@OneToMany(mappedBy = "comentario", cascade = CascadeType.ALL)
	private Set<Anexo> anexo;

	public Comentario() {

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

	public int getPersonaMensaje() {
		return personaMensaje;
	}

	public void setPersonaMensaje(int personaMensaje) {
		this.personaMensaje = personaMensaje;
	}

	public Incidencia getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(Incidencia incidencia) {
		this.incidencia = incidencia;
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
