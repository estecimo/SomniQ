package com.example.calidadsueno.interfaces.rest;

import com.example.calidadsueno.application.ClasificarCalidadSuenoUseCase;
import com.example.calidadsueno.domain.CasoCalidadSueno;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calidad-sueno")
@CrossOrigin(origins = "*") // Allow requests from any frontend origin (simplifies testing)
public class CalidadSuenoController {

    private final ClasificarCalidadSuenoUseCase useCase;

    public CalidadSuenoController(ClasificarCalidadSuenoUseCase useCase) {
        this.useCase = useCase;
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
}
