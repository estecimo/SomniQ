package com.example.calidadsueno.domain;

public class CasoCalidadSueno {
    private String percepcion; // "Buena" o "Mala" 
    private String frecuenciaMedicacion; // "Ninguna vez en el último mes", "Una o dos veces...", etc.
    private String duracionSueno; // "Corta (< 6 horas)", "Normal (6-9 horas)", "Larga (> 9 horas)"
    private String somnolenciaDiurna; // "Normal", "Excesiva", "Marginal"
    private String adiccionInternet; // "Sí", "No"
    private String ventaOnline; // "Sí", "No"
    private String comprasOnline; // "Sí", "No"
    private String sexo; // "Hombre", "Mujer"
    private String nivelAdiccion; // "Sin adicción", "Moderada", "Leve", "Severa"
    private String latencia; // "Normal (5-15 minutos)", "Prolongada (> 15 minutos)", "Patológica (< 5 minutos)"

    public CasoCalidadSueno(String percepcion, String frecuenciaMedicacion, String duracionSueno, String somnolenciaDiurna,
                       String adiccionInternet, String ventaOnline, String sexo, String nivelAdiccion,
                       String latencia, String comprasOnline) {
        this.percepcion = percepcion;
        this.frecuenciaMedicacion = frecuenciaMedicacion;
        this.duracionSueno = duracionSueno;
        this.somnolenciaDiurna = somnolenciaDiurna;
        this.adiccionInternet = adiccionInternet;
        this.ventaOnline = ventaOnline;
        this.sexo = sexo;
        this.nivelAdiccion = nivelAdiccion;
        this.latencia = latencia;
        this.comprasOnline = comprasOnline;
    }

    public String getPercepcion() { return percepcion; }
    public String getFrecuenciaMedicacion() { return frecuenciaMedicacion; }
    public String getDuracionSueno() { return duracionSueno; }
    public String getSomnolenciaDiurna() { return somnolenciaDiurna; }
    public String getAdiccionInternet() { return adiccionInternet; }
    public String getVentaOnline() { return ventaOnline; }
    public String getSexo() { return sexo; }
    public String getNivelAdiccion() { return nivelAdiccion; }
    public String getLatencia() { return latencia; }
    public String getComprasOnline() { return comprasOnline; }
}
