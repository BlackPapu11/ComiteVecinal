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

@Entity
@Table(name = "municipios")
public class Municipio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 45)
	private String nombre;
	
	@OneToOne(mappedBy = "municipio", cascade = CascadeType.ALL)
	private User user;
	
	@OneToMany(mappedBy = "municipio", cascade = CascadeType.ALL)
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
	
	
}
