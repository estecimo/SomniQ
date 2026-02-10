package com.example.credito.domain;

public class CreditoCaso {

    private int edad;
    private double ingresos;
    private int antiguedadLaboral;
    private boolean tieneDeudas;
    private String historial;

    public CreditoCaso(int edad, double ingresos, int antiguedadLaboral,
                       boolean tieneDeudas, String historial) {
        this.edad = edad;
        this.ingresos = ingresos;
        this.antiguedadLaboral = antiguedadLaboral;
        this.tieneDeudas = tieneDeudas;
        this.historial = historial;
    }

    public int getEdad() { return edad; }
    public double getIngresos() { return ingresos; }
    public int getAntiguedadLaboral() { return antiguedadLaboral; }
    public boolean isTieneDeudas() { return tieneDeudas; }
    public String getHistorial() { return historial; }
}
