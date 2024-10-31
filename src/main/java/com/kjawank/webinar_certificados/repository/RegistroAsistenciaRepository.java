package com.kjawank.webinar_certificados.repository;

import com.kjawank.webinar_certificados.model.RegistroAsistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistroAsistenciaRepository extends JpaRepository<RegistroAsistencia, Long> {

    // Método para encontrar un registro por participanteId y que no tenga hora de desconexión
    Optional<RegistroAsistencia> findByParticipanteIdAndHoraDesconexionIsNull(Long participanteId);

    // Para obtener todos los registros por participanteId
    List<RegistroAsistencia> findByParticipanteId(Long participanteId);
}
