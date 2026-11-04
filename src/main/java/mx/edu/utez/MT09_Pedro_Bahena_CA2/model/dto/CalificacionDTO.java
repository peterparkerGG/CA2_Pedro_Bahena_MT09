package mx.edu.utez.MT09_Pedro_Bahena_CA2.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CalificacionDTO {

    @NotNull(message = "La calificación es obligatoria")
    @Min(value = 0, message = "La calificacion mínima es 0")
    @Max(value = 10, message = "La calificacion máxima es 10")
    private Double calificacion;

    public Double getCalificacion() { return calificacion; }
    public void setCalificacion(Double calificacion) { this.calificacion = calificacion; }
}
