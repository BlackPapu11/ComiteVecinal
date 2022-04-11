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
@Table(name = "comites")
public class Comite {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 45)
	private String nombreComite;


	@OneToMany(mappedBy = "comite", cascade = CascadeType.ALL)
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

	

}
