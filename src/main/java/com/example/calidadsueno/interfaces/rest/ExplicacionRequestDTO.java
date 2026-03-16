package com.example.calidadsueno.interfaces.rest;

public class ExplicacionRequestDTO {
    public String percepcion;
    public String frecuenciaMedicacion;
    public String duracionSueno;
    public String somnolenciaDiurna;
    public String adiccionInternet;
    public String nivelAdiccion;
    public String ventaOnline;
    public String comprasOnline;
    public String sexo;
    public String latencia;

    // Atributo adicional para el resultado de la predicción
    public String resultado;

    public ExplicacionRequestDTO() {}

}