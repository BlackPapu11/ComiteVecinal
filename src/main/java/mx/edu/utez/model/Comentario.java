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
@Table(name = "comentarios")
public class Comentario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 45)
	private String comentario;

	
	@ManyToOne
    @JoinColumn(name = "incidencia_id", updatable = false, nullable = false)
	private Incidencia incidencia;
	
	@OneToMany(mappedBy = "comentario", cascade = CascadeType.ALL)
	private Set<Anexo> anexo;
}
