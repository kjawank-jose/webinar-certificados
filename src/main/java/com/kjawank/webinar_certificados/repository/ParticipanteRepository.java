package com.kjawank.webinar_certificados.repository;

import com.kjawank.webinar_certificados.model.Participante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
    // Se puede agregar métodos personalizados si es necesario
}
