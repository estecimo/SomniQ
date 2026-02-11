package com.example.calidadsueno.infrastructure;

import com.example.calidadsueno.domain.CasoCalidadSueno;
import org.springframework.stereotype.Service;

@Service
public class ServicioEvaluacionJ48 {

    public String evaluar(CasoCalidadSueno caso) {
        // Logic derived from J48 pruned tree

        // Root: Como percibe su calidad de sueno?
        if ("Buena".equalsIgnoreCase(caso.getPercepcionCalidad())) {

            // Branch: Duracion total de sueno
            if ("Normal".equalsIgnoreCase(caso.getDuracionSueno())) {

                // Branch: Latencia
                if ("Prolongada".equalsIgnoreCase(caso.getLatencia())) {

                    // Branch: Tuvo COVID-19?
                    if ("No".equalsIgnoreCase(caso.getCovid())) {
                        return "Mala";
                    } else { // Si
                        return "Buena";
                    }

                } else if ("Normal".equalsIgnoreCase(caso.getLatencia())) {
                    return "Buena";
                } else { // Patologica
                    return "Buena";
                }

            } else if ("Corta".equalsIgnoreCase(caso.getDuracionSueno())) {
                return "Mala";
            } else { // Larga
                return "Buena";
            }

        } else { // Mala
            return "Mala";
        }
    }
}
