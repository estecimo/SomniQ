package com.example.calidadsueno.domain;

public class CasoCalidadSueno {

    private String percepcionCalidad; // Buena, Mala
    private String duracionSueno; // Normal (6-9 horas), Corta (< 6 horas), Larga (> 9 horas)
    private String latencia; // Normal (5-15 minutos), Prolongada (> 15 minutos), Patologica (< 5 minutos)
    private String covid; // Si, No

    public CasoCalidadSueno(String percepcionCalidad, String duracionSueno, String latencia, String covid) {
        this.percepcionCalidad = percepcionCalidad;
        this.duracionSueno = duracionSueno;
        this.latencia = latencia;
        this.covid = covid;
    }

    public String getPercepcionCalidad() {
        return percepcionCalidad;
    }

    public String getDuracionSueno() {
        return duracionSueno;
    }

    public String getLatencia() {
        return latencia;
    }

    public String getCovid() {
        return covid;
    }
}
