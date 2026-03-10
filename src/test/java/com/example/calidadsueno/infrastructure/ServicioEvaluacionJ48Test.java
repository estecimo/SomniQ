package com.example.calidadsueno.infrastructure;

import com.example.calidadsueno.domain.CasoCalidadSueno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ServicioEvaluacionJ48Test {

    private final ServicioEvaluacionJ48 servicio = new ServicioEvaluacionJ48();

    @BeforeEach
    void setUp() throws Exception {
        servicio.init();
    }

    @Test
    void testArbolJ48() {
        // 1. Mala -> Mala
        assertEquals("Mala", servicio.evaluar(new CasoCalidadSueno("Mala", "Normal", "Normal", "No")));

        // 2. Buena, Duracion Normal, Latencia Prolongada, Covid No -> Mala
        assertEquals("Mala", servicio.evaluar(new CasoCalidadSueno("Buena", "Normal", "Prolongada", "No")));

        // 3. Buena, Duracion Normal, Latencia Prolongada, Covid Si -> Buena
        assertEquals("Buena", servicio.evaluar(new CasoCalidadSueno("Buena", "Normal", "Prolongada", "Si")));

        // 4. Buena, Duracion Normal, Latencia Normal -> Buena
        assertEquals("Buena", servicio.evaluar(new CasoCalidadSueno("Buena", "Normal", "Normal", "No")));

        // 5. Buena, Duracion Normal, Latencia Patologica -> Buena
        assertEquals("Buena", servicio.evaluar(new CasoCalidadSueno("Buena", "Normal", "Patologica", "No")));

        // 6. Buena, Duracion Corta -> Mala
        assertEquals("Mala", servicio.evaluar(new CasoCalidadSueno("Buena", "Corta", "Normal", "No")));

        // 7. Buena, Duracion Larga -> Buena
        assertEquals("Buena", servicio.evaluar(new CasoCalidadSueno("Buena", "Larga", "Normal", "No")));
    }
}
