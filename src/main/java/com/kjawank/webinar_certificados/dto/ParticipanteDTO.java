package com.kjawank.webinar_certificados.dto;

public class ParticipanteDTO {

    private Long id;
    private String nombre;
    private String dni;
    private String correo;
    private String telefono;
    private Long duracionPermanenciaEnSegundos;

    public ParticipanteDTO(Long id, String nombre, String dni, String correo, String telefono, Long duracionPermanenciaEnSegundos) {
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
        this.correo = correo;
        this.telefono = telefono;
        this.duracionPermanenciaEnSegundos = duracionPermanenciaEnSegundos;
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

    public Long getDuracionPermanenciaEnSegundos() {
        return duracionPermanenciaEnSegundos;
    }

    public void setDuracionPermanenciaEnSegundos(Long duracionPermanenciaEnSegundos) {
        this.duracionPermanenciaEnSegundos = duracionPermanenciaEnSegundos;
    }
}
