package mx.edu.utez.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100, unique = true)
	private String correo;

	@Column(nullable = false, length = 200)
	private String contrasena;
	
	@Column(nullable = true, length = 5, unique = true)
	private String numeroEmpleado;
	
	@Column(nullable = true, length = 100, unique = true)
	private String foto;
	
	@Column(nullable = true, length = 100, unique = true)
	private String identificacion;
	
	@Column(columnDefinition = "tinyint not null")
	private boolean habilitado;
	
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
	
	@OneToOne
    @JoinColumn(name = "persona_id", updatable = false, nullable = false, unique = true)
	private Person person;
	
	@OneToOne
    @JoinColumn(name = "municipio_id", updatable = false, nullable = true)
	@JsonIgnore
	private Municipio municipio;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<UserComite> userComite;
	

	// Metodo para agregar roles
		public void agregarRol(Role role) {
		       if (roles == null) {
		           roles = new HashSet<Role>();
		       }
		       roles.add(role);
		} 

		
		
		public User() {
			
		}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getNumeroEmpleado() {
		return numeroEmpleado;
	}

	public void setNumeroEmpleado(String numeroEmpleado) {
		this.numeroEmpleado = numeroEmpleado;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}



	public Set<Role> getRoles() {
		return roles;
	}



	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}



	public Person getPerson() {
		return person;
	}



	public void setPerson(Person person) {
		this.person = person;
	}



	public Municipio getMunicipio() {
		return municipio;
	}



	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}



	public Set<UserComite> getUserComite() {
		return userComite;
	}



	public void setUserComite(Set<UserComite> userComite) {
		this.userComite = userComite;
	}
	
	
	
	
}
