package com.example.calidadsueno.interfaces.rest;

import org.springframework.web.bind.annotation.*;

import com.example.calidadsueno.application.ClasificarCalidadSuenoUseCase;
import com.example.calidadsueno.domain.CasoCalidadSueno;
import com.example.calidadsueno.infrastructure.GeminiService;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/calidad-sueno")
@CrossOrigin(origins = "*") // Allow requests from any frontend origin (simplifies testing)
public class CalidadSuenoController {

    private final ClasificarCalidadSuenoUseCase useCase;
    private final GeminiService geminiService;

    public CalidadSuenoController(ClasificarCalidadSuenoUseCase useCase, GeminiService geminiService) {
        this.useCase = useCase;
        this.geminiService = geminiService;
    }

    @PostMapping("/evaluar")
    public CalidadSuenoResponseDTO evaluar(@RequestBody CalidadSuenoRequestDTO req) {

        CasoCalidadSueno caso = new CasoCalidadSueno(
                req.percepcionCalidad,
                req.duracionSueno,
                req.latencia,
                req.covid);

        return new CalidadSuenoResponseDTO(useCase.ejecutar(caso));
    }

    @PostMapping("/explicacion")
    public Map<String, String> explicar(@RequestBody ExplicacionRequestDTO req) {
        String explicacion = geminiService.getExplanation(req);
        Map<String, String> response = new HashMap<>();
        response.put("explicacion", explicacion);
        return response;
    }
}
