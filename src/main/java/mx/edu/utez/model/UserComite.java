package mx.edu.utez.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user_comite")
public class UserComite {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
	private User user;
	
	@ManyToOne
    @JoinColumn(name = "comite_id", updatable = false, nullable = false)
	private Comite comite;
	
	@OneToMany(mappedBy = "userComite", cascade = CascadeType.ALL)
	private Set<Incidencia> incidencia;
	
	public UserComite() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Comite getComite() {
		return comite;
	}

	public void setComite(Comite comite) {
		this.comite = comite;
	}

	public Set<Incidencia> getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(Set<Incidencia> incidencia) {
		this.incidencia = incidencia;
	}
	
	
}
