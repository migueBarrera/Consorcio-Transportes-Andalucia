package com.miguebarrera.consorciotransportesandalucia.models;

import java.util.ArrayList;

/**
 * Created by migueBarreraBluumi on 16/01/2018.
 */

public class CapsuleHorariosLinea {
    private ArrayList<Planificadore> planificadores;

    public ArrayList<Planificadore> getPlanificadores() { return this.planificadores; }

    public void setPlanificadores(ArrayList<Planificadore> planificadores) { this.planificadores = planificadores; }

    private ArrayList<Frecuencia> frecuencias;

    public ArrayList<Frecuencia> getFrecuencias() { return this.frecuencias; }

    public void setFrecuencias(ArrayList<Frecuencia> frecuencias) { this.frecuencias = frecuencias; }

    private String horaCorte;

    public String getHoraCorte() { return this.horaCorte; }

    public void setHoraCorte(String horaCorte) { this.horaCorte = horaCorte; }
}
