package com.consorcio.consorciotransportesandalucia.models;

import java.util.ArrayList;

/**
 * Created by migueBarreraBluumi on 16/01/2018.
 */

public class Planificadore {
    private String idPlani;

    public String getIdPlani() { return this.idPlani; }

    public void setIdPlani(String idPlani) { this.idPlani = idPlani; }

    private String fechaInicio;

    public String getFechaInicio() { return this.fechaInicio; }

    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }

    private String fechaFin;

    public String getFechaFin() { return this.fechaFin; }

    public void setFechaFin(String fechaFin) { this.fechaFin = fechaFin; }

    private String muestraFechaFin;

    public String getMuestraFechaFin() { return this.muestraFechaFin; }

    public void setMuestraFechaFin(String muestraFechaFin) { this.muestraFechaFin = muestraFechaFin; }

    private ArrayList<NucleosIda> nucleosIda;

    public ArrayList<NucleosIda> getNucleosIda() { return this.nucleosIda; }

    public void setNucleosIda(ArrayList<NucleosIda> nucleosIda) { this.nucleosIda = nucleosIda; }

    private ArrayList<NucleosVuelta> nucleosVuelta;

    public ArrayList<NucleosVuelta> getNucleosVuelta() { return this.nucleosVuelta; }

    public void setNucleosVuelta(ArrayList<NucleosVuelta> nucleosVuelta) { this.nucleosVuelta = nucleosVuelta; }

    private ArrayList<BloquesIda> bloquesIda;

    public ArrayList<BloquesIda> getBloquesIda() { return this.bloquesIda; }

    public void setBloquesIda(ArrayList<BloquesIda> bloquesIda) { this.bloquesIda = bloquesIda; }

    private ArrayList<HorarioIda> horarioIda;

    public ArrayList<HorarioIda> getHorarioIda() { return this.horarioIda; }

    public void setHorarioIda(ArrayList<HorarioIda> horarioIda) { this.horarioIda = horarioIda; }

    private ArrayList<BloquesVuelta> bloquesVuelta;

    public ArrayList<BloquesVuelta> getBloquesVuelta() { return this.bloquesVuelta; }

    public void setBloquesVuelta(ArrayList<BloquesVuelta> bloquesVuelta) { this.bloquesVuelta = bloquesVuelta; }

    private String especial;

    public String getEspecial() { return this.especial; }

    public void setEspecial(String especial) { this.especial = especial; }

    private ArrayList<HorarioVuelta> horarioVuelta;

    public ArrayList<HorarioVuelta> getHorarioVuelta() { return this.horarioVuelta; }

    public void setHorarioVuelta(ArrayList<HorarioVuelta> horarioVuelta) { this.horarioVuelta = horarioVuelta; }
}
