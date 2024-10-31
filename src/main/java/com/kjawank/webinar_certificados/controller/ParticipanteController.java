package com.kjawank.webinar_certificados.controller;

import com.kjawank.webinar_certificados.dto.ParticipanteDTO;
import com.kjawank.webinar_certificados.model.Participante;
import com.kjawank.webinar_certificados.service.ParticipanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/participantes")
public class ParticipanteController {

    @Autowired
    private ParticipanteService participanteService;

    // Endpoint para listar todos los participantes
    @GetMapping
    public List<ParticipanteDTO> listarParticipantes() {
        return participanteService.listarParticipantes().stream()
                .map(participante -> new ParticipanteDTO(
                        participante.getId(),
                        participante.getNombre(),
                        participante.getDni(),
                        participante.getCorreo(),
                        participante.getTelefono(),
                        participante.getDuracionPermanenciaEnSegundos()
                ))
                .collect(Collectors.toList());
    }

    // Endpoint para registrar un único participante
    @PostMapping
    public ResponseEntity<Participante> registrarParticipante(@RequestBody Participante participante) {
        Participante nuevoParticipante = participanteService.registrarParticipante(participante);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoParticipante);
    }

    // Endpoint para registrar múltiples participantes
    @PostMapping("/crear")
    public ResponseEntity<List<Participante>> crearParticipantes(@RequestBody List<Participante> participantes) {
        List<Participante> participantesGuardados = participanteService.guardarParticipantes(participantes);
        return ResponseEntity.status(HttpStatus.CREATED).body(participantesGuardados);
    }

    // Endpoint para obtener la duración de permanencia de un participante
    @GetMapping("/{id}/permanencia")
    public ResponseEntity<String> obtenerPermanencia(@PathVariable Long id) {
        Optional<Participante> participante = participanteService.buscarPorId(id);
        if (participante.isPresent()) {
            Long duracionEnSegundos = participante.get().getDuracionPermanenciaEnSegundos();
            String mensajePermanencia = (duracionEnSegundos != null)
                    ? "Tiempo de permanencia: " + (duracionEnSegundos / 60) + " minutos"
                    : "La permanencia aún no está registrada";
            return ResponseEntity.ok(mensajePermanencia);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Participante no encontrado");
        }
    }

    // Endpoint para verificar si el participante cumple con el tiempo mínimo de permanencia
    @GetMapping("/{id}/verificarPermanencia")
    public ResponseEntity<String> verificarPermanencia(@PathVariable Long id, @RequestParam Long tiempoMinimo) {
        Optional<Participante> participante = participanteService.buscarPorId(id);
        if (participante.isPresent()) {
            Long duracionEnSegundos = participante.get().getDuracionPermanenciaEnSegundos();
            if (duracionEnSegundos != null && duracionEnSegundos >= tiempoMinimo) {
                return ResponseEntity.ok("El participante cumple con el tiempo mínimo de permanencia.");
            } else {
                return ResponseEntity.ok("El participante no cumple con el tiempo mínimo de permanencia.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Participante no encontrado");
        }
    }

    // Endpoint para listar solo los participantes que cumplen con el tiempo mínimo de permanencia
    @GetMapping("/cumplen-permanencia")
    public List<ParticipanteDTO> listarParticipantesQueCumplenConPermanencia(@RequestParam Long tiempoMinimo) {
        return participanteService.listarParticipantes().stream()
                .filter(participante -> {
                    Long duracionEnSegundos = participante.getDuracionPermanenciaEnSegundos();
                    return duracionEnSegundos != null && duracionEnSegundos >= tiempoMinimo;
                })
                .map(participante -> new ParticipanteDTO(
                        participante.getId(),
                        participante.getNombre(),
                        participante.getDni(),
                        participante.getCorreo(),
                        participante.getTelefono(),
                        participante.getDuracionPermanenciaEnSegundos()
                ))
                .collect(Collectors.toList());
    }
}
