package com.kjawank.webinar_certificados.service;

import com.kjawank.webinar_certificados.model.Participante;
import com.kjawank.webinar_certificados.repository.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParticipanteService {

    @Autowired
    private ParticipanteRepository participanteRepository;

    // Duración total del webinar en segundos y porcentaje requerido de permanencia (80%)
    private static final long DURACION_TOTAL_WEBINAR_SEGUNDOS = 3600; // Ejemplo: 1 hora (3600 segundos)
    private static final double PORCENTAJE_REQUERIDO = 0.8; // 80% de permanencia

    public Participante registrarParticipante(Participante participante) {
        // Si el tiempo de salida está definido, calcula la permanencia
        //if (participante.getTiempoEntrada() != null && participante.getTiempoSalida() != null) {
        //    long duracionEnSegundos = Duration.between(participante.getTiempoEntrada(), participante.getTiempoSalida()).getSeconds();
        //    participante.setDuracionPermanenciaEnSegundos(duracionEnSegundos);
        //}
        return participanteRepository.save(participante);
    }

    // Método para listar todos los participantes
    public List<Participante> listarParticipantes() {
        return participanteRepository.findAll();
    }

    public Optional<Participante> buscarPorId(Long id) {
        return participanteRepository.findById(id);
    }

    public List<Participante> guardarParticipantes(List<Participante> participantes) {
        for (Participante participante : participantes) {
            // Calcula la permanencia en el momento de necesitarla
            Long duracionEnSegundos = participante.getDuracionPermanenciaEnSegundos();
            System.out.println("Duración de permanencia: " + duracionEnSegundos + " segundos");
        }
        return participanteRepository.saveAll(participantes);
    }

    // Método para listar participantes que cumplen con el umbral de permanencia
    public List<Participante> listarParticipantesQueCumplenConPermanencia() {
        return participanteRepository.findAll().stream()
                .filter(participante -> {
                    Long permanencia = participante.getDuracionPermanenciaEnSegundos();
                    return permanencia != null && permanencia >= DURACION_TOTAL_WEBINAR_SEGUNDOS * PORCENTAJE_REQUERIDO;
                })
                .collect(Collectors.toList());
    }

}
