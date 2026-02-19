package com.example.calidadsueno.interfaces.rest;

import com.example.calidadsueno.application.ClasificarCalidadSuenoUseCase;
import com.example.calidadsueno.domain.CasoCalidadSueno;
import com.example.calidadsueno.infrastructure.weka.GeminiService;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/calidad-sueno")
@CrossOrigin(origins = "*") 
public class CalidadSuenoController {

    private final ClasificarCalidadSuenoUseCase useCase;
    private final GeminiService geminiService;

    public CalidadSuenoController(ClasificarCalidadSuenoUseCase useCase, GeminiService geminiService) {
        this.useCase = useCase;
        this.geminiService = geminiService;
    }

    // 1. Endpoint para Clasificar con Weka
    @PostMapping("/clasificar")
    public CalidadSuenoResponseDTO clasificar(@RequestBody CalidadSuenoRequestDTO req) throws Exception {
        // Asegúrate de que el orden de los parámetros en el constructor de CasoCalidadSueno 
        // coincida con el orden en tu clase Domain
        CasoCalidadSueno caso = new CasoCalidadSueno(
                req.percepcion,
                req.frecuenciaMedicacion,
                req.duracionSueno,
                req.somnolenciaDiurna,
                req.adiccionInternet,
                req.ventaOnline,
                req.sexo,
                req.nivelAdiccion,
                req.latencia,
                req.comprasOnline
        );

        String resultado = useCase.ejecutar(caso);
        return new CalidadSuenoResponseDTO(resultado);
    }

    // 2. Endpoint para Explicación con Gemini
    @PostMapping("/explicar") // Cambiado a /explicar para coincidir con el app.js
    public Map<String, String> explicar(@RequestBody ExplicacionRequestDTO req) {
        // Le pasamos el objeto DTO completo (que contiene los 10 campos + el resultado)
        // al servicio de Gemini
        String explicacion = geminiService.getExplanation(req, req.resultado);
        
        Map<String, String> response = new HashMap<>();
        response.put("explicacion", explicacion);
        return response;
    }
}