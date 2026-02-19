package com.example.calidadsueno.application;

import com.example.calidadsueno.domain.CasoCalidadSueno;
import com.example.calidadsueno.infrastructure.weka.WekaModeloCalidadSueno;
import org.springframework.stereotype.Service;

@Service
public class ClasificarCalidadSuenoUseCase {
    private final WekaModeloCalidadSueno modelo;

    public ClasificarCalidadSuenoUseCase(WekaModeloCalidadSueno modelo) {
        this.modelo = modelo;
    }

    public String ejecutar(CasoCalidadSueno caso) throws Exception {
        return modelo.clasificar(caso);
    }
}
