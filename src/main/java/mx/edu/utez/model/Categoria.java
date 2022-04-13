package mx.edu.utez.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "categorias")
public class Categoria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 150)
	@NotBlank
	@Pattern(regexp="^[a-zA-Z\\s]+{2,254}")
	private String nombre;
	
	@NotNull
	@Column(columnDefinition = "tinyint not null")
	private boolean habilitado;
	
	@OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
	private Set<Incidencia> incidencia;
	
	public Categoria() {
		
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

	public Set<Incidencia> getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(Set<Incidencia> incidencia) {
		this.incidencia = incidencia;
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}
	
	
	
}
