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

@Entity
@Table(name = "colonias")
public class Colonia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 45)
	private String nombre;
	
	@Column(nullable = false, length = 5)
	private String codigoPostal;
	
	@OneToMany(mappedBy = "colonia", cascade = CascadeType.ALL)
	private Set<Comite> comite;
	
	@ManyToOne
    @JoinColumn(name = "municipio_id", updatable = false, nullable = false)
	private Municipio municipio;

}
