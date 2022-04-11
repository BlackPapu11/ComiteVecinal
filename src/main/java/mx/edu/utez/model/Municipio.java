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
}
