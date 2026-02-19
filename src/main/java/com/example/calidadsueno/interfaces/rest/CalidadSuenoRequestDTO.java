package com.example.calidadsueno.interfaces.rest;

public class CalidadSuenoRequestDTO {
    public String percepcion; // "Buena" o "Mala" 
    public String frecuenciaMedicacion; // "Ninguna vez en el último mes", "Una o dos veces...", etc.
    public String duracionSueno; // "Corta (< 6 horas)", "Normal (6-9 horas)", "Larga (> 9 horas)"
    public String somnolenciaDiurna; // "Normal", "Excesiva", "Marginal"
    public String adiccionInternet; // "Sí", "No"
    public String ventaOnline; // "Sí", "No"
    public String comprasOnline; // "Sí", "No"
    public String sexo; // "Hombre", "Mujer"
    public String nivelAdiccion; // "Sin adicción", "Moderada", "Leve", "Severa"
    public String latencia; // "Normal (5-15 minutos)", "Prolongada (> 15 minutos)", "Patológica (< 5 minutos)"

    public CalidadSuenoRequestDTO() {}
}
