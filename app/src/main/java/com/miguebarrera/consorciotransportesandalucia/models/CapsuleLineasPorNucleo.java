package com.miguebarrera.consorciotransportesandalucia.models;

import java.util.ArrayList;

/**
 * Created by migueBarreraBluumi on 15/01/2018.
 */

public class CapsuleLineasPorNucleo {
    private ArrayList<Bloque> bloques;

    public ArrayList<Bloque> getBloques() { return this.bloques; }

    public void setBloques(ArrayList<Bloque> bloques) { this.bloques = bloques; }

    private ArrayList<Horario> horario;

    public ArrayList<Horario> getHorario() { return this.horario; }

    public void setHorario(ArrayList<Horario> horario) { this.horario = horario; }

    private ArrayList<Frecuencia> frecuencias;

    public ArrayList<Frecuencia> getFrecuencias() { return this.frecuencias; }

    public void setFrecuencias(ArrayList<Frecuencia> frecuencias) { this.frecuencias = frecuencias; }

    private ArrayList<Nucleo> nucleos;

    public ArrayList<Nucleo> getNucleos() { return this.nucleos; }

    public void setNucleos(ArrayList<Nucleo> nucleos) { this.nucleos = nucleos; }
}
