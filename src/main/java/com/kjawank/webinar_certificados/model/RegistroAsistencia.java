package com.kjawank.webinar_certificados.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class RegistroAsistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //private Long participanteId; Esta línea puede ser eliminada o mantenida, dependiendo ...
    private LocalDateTime horaConexion;
    private LocalDateTime horaDesconexion;

    @ManyToOne // Se Agrega esta anotación
    @JoinColumn(name = "participante_id") // Define el nombre de la columna en la tabla
    private Participante participante; // Agrega la referencia a Participante

    // Constructor

    public RegistroAsistencia(Participante participante, LocalDateTime horaConexion, LocalDateTime horaDesconexion) {
        this.participante = participante;
        this.horaConexion = horaConexion;
        this.horaDesconexion = horaDesconexion;
    }


    // Getters y setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getHoraConexion() {
        return horaConexion;
    }

    public void setHoraConexion(LocalDateTime horaConexion) {
        this.horaConexion = horaConexion;
    }

    public LocalDateTime getHoraDesconexion() {
        return horaDesconexion;
    }

    public void setHoraDesconexion(LocalDateTime horaDesconexion) {
        this.horaDesconexion = horaDesconexion;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }
}
