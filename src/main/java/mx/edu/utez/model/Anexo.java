package mx.edu.utez.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "anexos")
public class Anexo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 150)
	private String evidencia;
	
	@ManyToOne
    @JoinColumn(name = "incidencia_id", updatable = false, nullable = true)
	private Incidencia incidencia;
	
	@ManyToOne
    @JoinColumn(name = "comentario_id", updatable = false, nullable = true)
	private Comentario comentario;
	
	
	
}
