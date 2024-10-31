package com.kjawank.webinar_certificados.controller;


import com.kjawank.webinar_certificados.model.RegistroAsistencia;
import com.kjawank.webinar_certificados.service.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/asistencia")
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    @PostMapping("/entrada/{id}")
    public String registrarEntrada(@PathVariable Long id) {
        // Lógica para registrar entrada
        return "Entrada registrada";
    }

    @PostMapping("/salida/{id}")
    public String registrarSalida(@PathVariable Long id) {
        // Lógica para registrar salida
        return "Salida registrada";
    }

    @GetMapping("/certificado/{id}")
    public String generarCertificado(@PathVariable Long id) {
        // Lógica para validar y generar el certificado
        return "Certificado generado si cumple con permanencia";
    }

    @GetMapping("/registros")
    public List<RegistroAsistencia> listarRegistros() {
        return asistenciaService.listarRegistros();
    }

    @GetMapping("/registros/{participanteId}")
    public List<RegistroAsistencia> obtenerRegistrosPorParticipante(@PathVariable Long participanteId) {
        return asistenciaService.obtenerRegistrosPorParticipante(participanteId);
    }
}
