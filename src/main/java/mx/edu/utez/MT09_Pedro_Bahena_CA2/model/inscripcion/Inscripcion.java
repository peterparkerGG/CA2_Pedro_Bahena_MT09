package mx.edu.utez.MT09_Pedro_Bahena_CA2.model.inscripcion;

import jakarta.persistence.*;
import mx.edu.utez.MT09_Pedro_Bahena_CA2.model.curso.Curso;

import java.time.LocalDate;

@Entity
@Table(name = "inscripciones")
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreParticipante;

    @Column(nullable = false)
    private Integer edadParticipante;

    @Column(nullable = false)
    private String correo;

    @Column(nullable = false)
    private LocalDate fechaInscripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoInscripcion estado;

    private Double calificacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    public Inscripcion() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombreParticipante() { return nombreParticipante; }
    public void setNombreParticipante(String nombreParticipante) { this.nombreParticipante = nombreParticipante; }
    public Integer getEdadParticipante() { return edadParticipante; }
    public void setEdadParticipante(Integer edadParticipante) { this.edadParticipante = edadParticipante; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public LocalDate getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(LocalDate fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }
    public EstadoInscripcion getEstado() { return estado; }
    public void setEstado(EstadoInscripcion estado) { this.estado = estado; }
    public Double getCalificacion() { return calificacion; }
    public void setCalificacion(Double calificacion) { this.calificacion = calificacion; }
    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }
}
