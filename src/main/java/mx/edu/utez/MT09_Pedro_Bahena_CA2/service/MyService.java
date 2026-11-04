package mx.edu.utez.MT09_Pedro_Bahena_CA2.service;

import mx.edu.utez.MT09_Pedro_Bahena_CA2.exception.BadRequestException;
import mx.edu.utez.MT09_Pedro_Bahena_CA2.exception.ResourceNotFoundException;
import mx.edu.utez.MT09_Pedro_Bahena_CA2.model.curso.Curso;
import mx.edu.utez.MT09_Pedro_Bahena_CA2.model.curso.EstadoCurso;
import mx.edu.utez.MT09_Pedro_Bahena_CA2.model.dto.CursoDTO;
import mx.edu.utez.MT09_Pedro_Bahena_CA2.model.dto.InscripcionDTO;
import mx.edu.utez.MT09_Pedro_Bahena_CA2.model.inscripcion.EstadoInscripcion;
import mx.edu.utez.MT09_Pedro_Bahena_CA2.model.inscripcion.Inscripcion;
import mx.edu.utez.MT09_Pedro_Bahena_CA2.repository.CursoRepository;
import mx.edu.utez.MT09_Pedro_Bahena_CA2.repository.InscripcionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MyService {

    private final CursoRepository cursoRepo;
    private final InscripcionRepository inscripcionRepo;

    public MyService(CursoRepository cursoRepo, InscripcionRepository inscripcionRepo) {
        this.cursoRepo = cursoRepo;
        this.inscripcionRepo = inscripcionRepo;
    }

    @Transactional
    public Curso registrarCurso(CursoDTO dto) {
        Curso curso = new Curso();
        curso.setNombre(dto.getNombre());
        curso.setDescripcion(dto.getDescripcion());
        curso.setFechaInicio(dto.getFechaInicio());
        curso.setFechaFin(dto.getFechaFin());
        curso.setEstado(EstadoCurso.DISPONIBLE);
        return cursoRepo.save(curso);
    }

    public List<Curso> listarCursosDisponibles() {
        return cursoRepo.findByEstado(EstadoCurso.DISPONIBLE);
    }

    public List<Curso> listarTodosCursos() {
        return cursoRepo.findAll();
    }

    @Transactional
    public Curso cambiarEstadoCurso(Long cursoId, EstadoCurso nuevoEstado) {
        Curso curso = cursoRepo.findById(cursoId)
                .orElseThrow(() -> new ResourceNotFoundException("El curso con ID " + cursoId + " no existe."));

        if (curso.getEstado() == EstadoCurso.FINALIZADO) {
            throw new BadRequestException("No se puede cambiar el estado de un curso FINALIZADO.");
        }

        if (curso.getEstado() == EstadoCurso.DISPONIBLE && nuevoEstado == EstadoCurso.EN_CURSO) {
            curso.setEstado(EstadoCurso.EN_CURSO);
        } else if (curso.getEstado() == EstadoCurso.EN_CURSO && nuevoEstado == EstadoCurso.FINALIZADO) {
            curso.setEstado(EstadoCurso.FINALIZADO);
        } else {
            throw new BadRequestException("Transición no permitida de " + curso.getEstado() + " a " + nuevoEstado);
        }

        return cursoRepo.save(curso);
    }

    @Transactional
    public Inscripcion inscribirParticipante(InscripcionDTO dto) {
        Curso curso = cursoRepo.findById(dto.getCursoId())
                .orElseThrow(() -> new ResourceNotFoundException("El curso especificado no existe."));

        if (curso.getEstado() != EstadoCurso.DISPONIBLE) {
            throw new BadRequestException("El curso debe estar en estado DISPONIBLE para poder inscribirse.");
        }

        Inscripcion ins = new Inscripcion();
        ins.setNombreParticipante(dto.getNombreParticipante());
        ins.setEdadParticipante(dto.getEdadParticipante());
        ins.setCorreo(dto.getCorreo());
        ins.setFechaInscripcion(LocalDate.now());
        ins.setEstado(EstadoInscripcion.ACTIVA);
        ins.setCalificacion(null);
        ins.setCurso(curso);

        return inscripcionRepo.save(ins);
    }

    @Transactional
    public Inscripcion cancelarInscripcion(Long inscripcionId) {
        Inscripcion ins = inscripcionRepo.findById(inscripcionId)
                .orElseThrow(() -> new ResourceNotFoundException("La inscripción no existe."));

        if (ins.getEstado() != EstadoInscripcion.ACTIVA) {
            throw new BadRequestException("Únicamente se pueden cancelar inscripciones en estado ACTIVA.");
        }

        if (LocalDate.now().isAfter(ins.getCurso().getFechaInicio()) || LocalDate.now().isEqual(ins.getCurso().getFechaInicio())) {
            throw new BadRequestException("No se puede cancelar la inscripción si el curso ya ha comenzado.");
        }

        ins.setEstado(EstadoInscripcion.CANCELADA);
        return inscripcionRepo.save(ins);
    }


    public List<Inscripcion> consultarPorCorreo(String correo) {
        return inscripcionRepo.findByCorreo(correo);
    }

    public List<Inscripcion> consultarPorCurso(Long cursoId) {
        if (!cursoRepo.existsById(cursoId)) {
            throw new ResourceNotFoundException("El curso con ID " + cursoId + " no existe.");
        }
        return inscripcionRepo.findByCursoId(cursoId);
    }

    public Inscripcion consultarDetalleInscripcion(Long id) {
        return inscripcionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada."));
    }

    public List<Inscripcion> consultarPorEstado(EstadoInscripcion estado) {
        return inscripcionRepo.findByEstado(estado);
    }

    @Transactional
    public Inscripcion registrarCalificacion(Long inscripcionId, Double calificacion) {
        Inscripcion ins = inscripcionRepo.findById(inscripcionId)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada."));

        if (ins.getCurso().getEstado() != EstadoCurso.FINALIZADO) {
            throw new BadRequestException("El curso asociado debe estar en estado FINALIZADO para calificar.");
        }

        if (ins.getEstado() != EstadoInscripcion.ACTIVA) {
            throw new BadRequestException("Solo se pueden registrar calificaciones en inscripciones en estado ACTIVA.");
        }

        ins.setCalificacion(calificacion);
        ins.setEstado(EstadoInscripcion.COMPLETADA);
        return inscripcionRepo.save(ins);
    }
}