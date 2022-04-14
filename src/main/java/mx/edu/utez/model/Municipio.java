package mx.edu.utez.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "municipios")
public class Municipio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 45)
	@NotBlank
	@Pattern(regexp="^[a-zA-Z\\s]+{2,254}")
	private String nombre;
	
	@NotNull
	@Column(columnDefinition = "tinyint not null")
	private boolean habilitado;
	
	@OneToOne(mappedBy = "municipio", cascade = CascadeType.ALL)
	@JsonIgnore
	private User user;
	
	@OneToMany(mappedBy = "municipio", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Colonia> Colonia;
	
	public Municipio() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Colonia> getColonia() {
		return Colonia;
	}

	public void setColonia(Set<Colonia> colonia) {
		Colonia = colonia;
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}
	
	
	
}
