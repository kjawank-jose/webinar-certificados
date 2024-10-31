package com.kjawank.webinar_certificados.service;

import com.kjawank.webinar_certificados.model.Participante;
import com.kjawank.webinar_certificados.model.RegistroAsistencia;
import com.kjawank.webinar_certificados.repository.ParticipanteRepository;
import com.kjawank.webinar_certificados.repository.RegistroAsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AsistenciaService {

    @Autowired
    private RegistroAsistenciaRepository registroAsistenciaRepository;

    @Autowired
    private ParticipanteRepository participanteRepository; // Inyectar el repositorio

    private static final LocalTime HORA_INICIO_VALIDO = LocalTime.of(9, 0);
    private static final LocalTime HORA_FIN_VALIDO = LocalTime.of(12, 0);
    private static final Duration TIEMPO_TOTAL_WEBINAR = Duration.ofHours(6); // 6 horas en total para ambos días

    public void registrarConexion(Long participanteId, LocalDateTime horaConexion) {
        // Buscar el objeto Participante por su ID
        Participante participante = participanteRepository.findById(participanteId)
                .orElseThrow(() -> new IllegalArgumentException("Participante no encontrado"));

        RegistroAsistencia registro = new RegistroAsistencia(participante, horaConexion, null);
        registroAsistenciaRepository.save(registro);
    }

    public void registrarDesconexion(Long participanteId, LocalDateTime horaDesconexion) {
        // Busca el registro correspondiente y actualiza la hora de desconexión
        registroAsistenciaRepository.findByParticipanteIdAndHoraDesconexionIsNull(participanteId)
                .ifPresent(registro -> {
                    registro.setHoraDesconexion(horaDesconexion);
                    registroAsistenciaRepository.save(registro); // Guarda el registro actualizado
                });
    }

    // Método para listar todos los registros de asistencia
    public List<RegistroAsistencia> listarRegistros() {
        return registroAsistenciaRepository.findAll();
    }

    // Método para obtener registros de un participante específico
    public List<RegistroAsistencia> obtenerRegistrosPorParticipante(Long participanteId) {
        return registroAsistenciaRepository.findByParticipanteId(participanteId);
    }

    public boolean puedeGenerarCertificado(Long participanteId) {
        Duration tiempoTotalPermanencia = registroAsistenciaRepository.findByParticipanteId(participanteId).stream()
                .map(this::calcularTiempoPermanenciaValido)
                .reduce(Duration.ZERO, Duration::plus); // Sumar el tiempo de ambos días

        Duration tiempoMinimoRequerido = TIEMPO_TOTAL_WEBINAR.multipliedBy(80).dividedBy(100); // 80% de 6 horas
        return tiempoTotalPermanencia.compareTo(tiempoMinimoRequerido) >= 0;
    }

    private Duration calcularTiempoPermanenciaValido(RegistroAsistencia registro) {
        LocalDateTime inicioSesion = registro.getHoraConexion();
        LocalDateTime finSesion = registro.getHoraDesconexion();

        // Si no hay registro de desconexión, se asume la hora límite de desconexión
        if (finSesion == null) {
            finSesion = LocalDateTime.of(inicioSesion.toLocalDate(), HORA_FIN_VALIDO);
        }

        // Ajustar inicio y fin de sesión a las horas válidas para el cálculo
        LocalDateTime inicioValido = inicioSesion.toLocalTime().isBefore(HORA_INICIO_VALIDO)
                ? LocalDateTime.of(inicioSesion.toLocalDate(), HORA_INICIO_VALIDO)
                : inicioSesion;

        LocalDateTime finValido = finSesion.toLocalTime().isAfter(HORA_FIN_VALIDO)
                ? LocalDateTime.of(finSesion.toLocalDate(), HORA_FIN_VALIDO)
                : finSesion;

        // Calcular y retornar la duración válida
        return Duration.between(inicioValido, finValido).isNegative()
                ? Duration.ZERO
                : Duration.between(inicioValido, finValido);
    }
}


