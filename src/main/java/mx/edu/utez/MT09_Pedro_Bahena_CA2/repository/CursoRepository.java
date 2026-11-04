package mx.edu.utez.MT09_Pedro_Bahena_CA2.repository;
import mx.edu.utez.MT09_Pedro_Bahena_CA2.model.curso.Curso;
import mx.edu.utez.MT09_Pedro_Bahena_CA2.model.curso.EstadoCurso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    List<Curso> findByEstado(EstadoCurso estado);
}
