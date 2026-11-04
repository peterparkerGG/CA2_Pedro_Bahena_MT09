package mx.edu.utez.MT09_Pedro_Bahena_CA2.controller;

import jakarta.validation.Valid;
import mx.edu.utez.MT09_Pedro_Bahena_CA2.model.curso.Curso;
import mx.edu.utez.MT09_Pedro_Bahena_CA2.model.curso.EstadoCurso;
import mx.edu.utez.MT09_Pedro_Bahena_CA2.model.dto.CalificacionDTO;
import mx.edu.utez.MT09_Pedro_Bahena_CA2.model.dto.CursoDTO;
import mx.edu.utez.MT09_Pedro_Bahena_CA2.model.dto.InscripcionDTO;
import mx.edu.utez.MT09_Pedro_Bahena_CA2.model.inscripcion.EstadoInscripcion;
import mx.edu.utez.MT09_Pedro_Bahena_CA2.model.inscripcion.Inscripcion;
import mx.edu.utez.MT09_Pedro_Bahena_CA2.service.MyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MyController {

    private final MyService service;

    public MyController(MyService service) {
        this.service = service;
    }

    @PostMapping("/cursos")
    public ResponseEntity<Curso> registrarCurso(@Valid @RequestBody CursoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarCurso(dto));
    }

    @GetMapping("/cursos/disponibles")
    public ResponseEntity<List<Curso>> listarCursosDisponibles() {
        return ResponseEntity.ok(service.listarCursosDisponibles());
    }

    @GetMapping("/cursos")
    public ResponseEntity<List<Curso>> listarTodosCursos() {
        return ResponseEntity.ok(service.listarTodosCursos());
    }

    @PutMapping("/cursos/{id}/estado")
    public ResponseEntity<Curso> cambiarEstadoCurso(@PathVariable Long id, @RequestParam EstadoCurso estado) {
        return ResponseEntity.ok(service.cambiarEstadoCurso(id, estado));
    }

    @PostMapping("/inscripciones")
    public ResponseEntity<Inscripcion> inscribir(@Valid @RequestBody InscripcionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.inscribirParticipante(dto));
    }

    @PutMapping("/inscripciones/{id}/cancelar")
    public ResponseEntity<Inscripcion> cancelarInscripcion(@PathVariable Long id) {
        return ResponseEntity.ok(service.cancelarInscripcion(id));
    }

    @GetMapping("/inscripciones/correo")
    public ResponseEntity<List<Inscripcion>> consultarPorCorreo(@RequestParam String correo) {
        return ResponseEntity.ok(service.consultarPorCorreo(correo));
    }

    @GetMapping("/cursos/{cursoId}/inscripciones")
    public ResponseEntity<List<Inscripcion>> consultarPorCurso(@PathVariable Long cursoId) {
        return ResponseEntity.ok(service.consultarPorCurso(cursoId));
    }
    @GetMapping("/inscripciones/{id}")
    public ResponseEntity<Inscripcion> consultarDetalle(@PathVariable Long id) {
        return ResponseEntity.ok(service.consultarDetalleInscripcion(id));
    }

    @GetMapping("/inscripciones/estado")
    public ResponseEntity<List<Inscripcion>> consultarPorEstado(@RequestParam EstadoInscripcion estado) {
        return ResponseEntity.ok(service.consultarPorEstado(estado));
    }

    @PutMapping("/inscripciones/{id}/calificacion")
    public ResponseEntity<Inscripcion> registrarCalificacion(@PathVariable Long id, @Valid @RequestBody CalificacionDTO dto) {
        return ResponseEntity.ok(service.registrarCalificacion(id, dto.getCalificacion()));
    }
}
