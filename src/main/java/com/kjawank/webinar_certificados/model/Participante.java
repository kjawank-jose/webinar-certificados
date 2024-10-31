package com.kjawank.webinar_certificados.model;

import jakarta.persistence.*;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "participantes")
public class Participante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(unique = true)
    private String dni;

    @Column(nullable = false)
    private String correo;

    @Column(nullable = false)
    private String telefono;

    @Column(name = "tiempo_entrada", nullable = false)
    private LocalDateTime tiempoEntrada;

    @Column(name = "tiempo_salida")
    private LocalDateTime tiempoSalida;

    // Relación uno a muchos con RegistroAsistencia
    @OneToMany(mappedBy = "participante", cascade = CascadeType.ALL)
    private List<RegistroAsistencia> registrosAsistencia;

    // Método para calcular la duración de permanencia
    public Long getDuracionPermanenciaEnSegundos() {
        if (tiempoEntrada != null && tiempoSalida != null) {
            return Duration.between(tiempoEntrada, tiempoSalida).getSeconds();
        }
        return null;
    }

    // Getters y setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDateTime getTiempoEntrada() {
        return tiempoEntrada;
    }

    public void setTiempoEntrada(LocalDateTime tiempoEntrada) {
        this.tiempoEntrada = tiempoEntrada;
    }

    public LocalDateTime getTiempoSalida() {
        return tiempoSalida;
    }

    public void setTiempoSalida(LocalDateTime tiempoSalida) {
        this.tiempoSalida = tiempoSalida;
    }

    public List<RegistroAsistencia> getRegistrosAsistencia() {
        return registrosAsistencia;
    }

    public void setRegistrosAsistencia(List<RegistroAsistencia> registrosAsistencia) {
        this.registrosAsistencia = registrosAsistencia;
    }


}
