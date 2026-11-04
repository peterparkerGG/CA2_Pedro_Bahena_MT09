package mx.edu.utez.MT09_Pedro_Bahena_CA2.repository;
import mx.edu.utez.MT09_Pedro_Bahena_CA2.model.inscripcion.EstadoInscripcion;
import mx.edu.utez.MT09_Pedro_Bahena_CA2.model.inscripcion.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    List<Inscripcion> findByCorreo(String correo);
    List<Inscripcion> findByCursoId(Long cursoId);
    List<Inscripcion> findByEstado(EstadoInscripcion estado);
}
