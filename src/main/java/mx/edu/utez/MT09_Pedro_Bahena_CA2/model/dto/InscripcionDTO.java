package mx.edu.utez.MT09_Pedro_Bahena_CA2.model.dto;

import jakarta.validation.constraints.*;

public class InscripcionDTO {

    @NotNull(message = "El ID del curso es obligatorio")
    private Long cursoId;

    @NotBlank(message = "El nombre del participante no puede estar vacio")
    private String nombreParticipante;

    @NotNull(message = "La edad es obligatoria")
    @Positive(message = "La edad debe ser un número positivo")
    private Integer edadParticipante;

    @NotBlank(message = "El correo no puede estar vacio")
    @Email(message = "Debe proporcionar un correo electronico valido")
    private String correo;

    public Long getCursoId() { return cursoId; }
    public void setCursoId(Long cursoId) { this.cursoId = cursoId; }
    public String getNombreParticipante() { return nombreParticipante; }
    public void setNombreParticipante(String nombreParticipante) { this.nombreParticipante = nombreParticipante; }
    public Integer getEdadParticipante() { return edadParticipante; }
    public void setEdadParticipante(Integer edadParticipante) { this.edadParticipante = edadParticipante; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
}
