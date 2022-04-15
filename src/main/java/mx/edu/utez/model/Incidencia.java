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
@Table(name = "incidencias")
public class Incidencia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 45)
	private String nombre;

	@Column(nullable = true, length = 250)
	private String descripcion;

	@Column(nullable = false, length = 45)
	private Date fechaRegistro;

	@Column(columnDefinition = "tinyint not null")
	private boolean habilitado;

	@Column(nullable = false, length = 10)
	private int status;

	@Column(nullable = true)
	private double costo;
	
	@Column(nullable = true,columnDefinition = "tinyint null")
	private boolean pago;

	@ManyToOne
	@JoinColumn(name = "user_comite_id", updatable = false, nullable = false)
	@JsonIgnore
	private UserComite userComite;

	@ManyToOne
	@JoinColumn(name = "categoria_id", updatable = false, nullable = false)
	private Categoria categoria;

	@OneToMany(mappedBy = "incidencia", cascade = CascadeType.ALL)
	private Set<Comentario> comentario;

	@OneToMany(mappedBy = "incidencia", cascade = CascadeType.ALL)
	private Set<Anexo> anexo;

	public Incidencia() {
		this.comentario = Set.of(new Comentario());
		this.anexo = Set.of(new Anexo());
	}

	public Incidencia(Long id) {
		this.comentario = Set.of(new Comentario());
		this.anexo = Set.of(new Anexo());
		this.id = id;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public UserComite getUserComite() {
		return userComite;
	}

	public void setUserComite(UserComite userComite) {
		this.userComite = userComite;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Set<Comentario> getComentario() {
		return comentario;
	}

	public void setComentario(Set<Comentario> comentario) {
		this.comentario = comentario;
	}

	public Set<Anexo> getAnexo() {
		return anexo;
	}

	public void setAnexo(Set<Anexo> anexo) {
		this.anexo = anexo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public double getCosto() {
		return costo;
	}

	public void setPago(boolean pago) {
		this.pago = pago;
	}

	public boolean getPago() {
		return pago;
	}

	public void print() {
		System.out.println(id
				+ " \nn- " + nombre
				+ " \nd- " + descripcion
				+ " \nc- " + categoria.getId()
				+ " \ncom- " + comentario);
	}

}
