package com.example.calidadsueno.application;

import com.example.calidadsueno.domain.CasoCalidadSueno;
import com.example.calidadsueno.infrastructure.ServicioEvaluacionJ48;
import org.springframework.stereotype.Service;

@Service
public class ClasificarCalidadSuenoUseCase {

    private final ServicioEvaluacionJ48 servicio;

    public ClasificarCalidadSuenoUseCase(ServicioEvaluacionJ48 servicio) {
        this.servicio = servicio;
    }

    public String ejecutar(CasoCalidadSueno caso) {
        return servicio.evaluar(caso);
    }
}
