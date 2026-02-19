package com.example.calidadsueno.interfaces.rest;

import com.example.calidadsueno.application.ClasificarCalidadSuenoUseCase;
import com.example.calidadsueno.domain.CasoCalidadSueno;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calidad-sueno")
@CrossOrigin
public class CalidadSuenoController {

    private final ClasificarCalidadSuenoUseCase useCase;

    public CalidadSuenoController(ClasificarCalidadSuenoUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping("/clasificar")
    public CalidadSuenoResponseDTO clasificar(@RequestBody CalidadSuenoRequestDTO req) throws Exception {

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

        return new CalidadSuenoResponseDTO(useCase.ejecutar(caso));
    }
}
