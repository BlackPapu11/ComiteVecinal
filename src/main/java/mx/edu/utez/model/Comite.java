package mx.edu.utez.model;

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
@Table(name = "comites")
public class Comite {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 45)
	private String nombreComite;

	@Column(columnDefinition = "tinyint not null")
	private boolean status;

	@OneToMany(mappedBy = "comite", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<UserComite> userComite;

	@ManyToOne
	@JoinColumn(name = "colonia_id", updatable = false, nullable = false)
	private Colonia colonia;

	public Comite() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreComite() {
		return nombreComite;
	}

	public void setNombreComite(String nombreComite) {
		this.nombreComite = nombreComite;
	}

	public void setColonia(Colonia colonia) {
		this.colonia = colonia;
	}

	public Colonia getColonia() {
		return colonia;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean getStatus() {
		return status;
	}
}
