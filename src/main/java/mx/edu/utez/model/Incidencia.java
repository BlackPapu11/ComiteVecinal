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
	
	@ManyToOne
    @JoinColumn(name = "user_comite_id", updatable = false, nullable = false)
	private UserComite userComite;
	
	@ManyToOne
    @JoinColumn(name = "categoria_id", updatable = false, nullable = false)
	private Categoria categoria;
	
	@OneToMany(mappedBy = "incidencia", cascade = CascadeType.ALL)
	private Set<Comentario> comentario;
	
	@OneToMany(mappedBy = "incidencia", cascade = CascadeType.ALL)
	private Set<Anexo> anexo;
	
}	

