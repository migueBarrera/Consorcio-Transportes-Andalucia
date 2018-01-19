package com.consorcio.consorciotransportesandalucia.models;

import java.util.ArrayList;

/**
 * Created by migueBarreraBluumi on 16/01/2018.
 */

public class CapsuleLineaDetalle {
    private String idLinea;

    public String getIdLinea() { return this.idLinea; }

    public void setIdLinea(String idLinea) { this.idLinea = idLinea; }

    private String codigo;

    public String getCodigo() { return this.codigo; }

    public void setCodigo(String codigo) { this.codigo = codigo; }

    private String nombre;

    public String getNombre() { return this.nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public TipoModoLinea modo;

    public TipoModoLinea getModo() { return this.modo; }

    public void setModo(TipoModoLinea modo) { this.modo = modo; }

    private String operadores;

    public String getOperadores() { return this.operadores; }

    public void setOperadores(String operadores) { this.operadores = operadores; }

    private int hayNoticias;

    public int getHayNoticias() { return this.hayNoticias; }

    public void setHayNoticias(int hayNoticias) { this.hayNoticias = hayNoticias; }

    private String termometroIda;

    public String getTermometroIda() { return this.termometroIda; }

    public void setTermometroIda(String termometroIda) { this.termometroIda = termometroIda; }

    private String termometroVuelta;

    public String getTermometroVuelta() { return this.termometroVuelta; }

    public void setTermometroVuelta(String termometroVuelta) { this.termometroVuelta = termometroVuelta; }

    private ArrayList<ArrayList<String>> polilinea;

    public ArrayList<ArrayList<String>> getPolilinea() { return this.polilinea; }

    public void setPolilinea(ArrayList<ArrayList<String>> polilinea) { this.polilinea = polilinea; }

    private ArrayList<ArrayList<String>> polilineaIda;

    public ArrayList<ArrayList<String>> getPolilineaIda() { return this.polilineaIda; }

    public void setPolilineaIda(ArrayList<ArrayList<String>> polilineaIda) { this.polilineaIda = polilineaIda; }

    private ArrayList<ArrayList<String>> polilineaVuelta;

    public ArrayList<ArrayList<String>> getPolilineaVuelta() { return this.polilineaVuelta; }

    public void setPolilineaVuelta(ArrayList<ArrayList<String>> polilineaVuelta) { this.polilineaVuelta = polilineaVuelta; }

    private String grosor;

    public String getGrosor() { return this.grosor; }

    public void setGrosor(String grosor) { this.grosor = grosor; }

    private String color;

    public String getColor() { return this.color; }

    public void setColor(String color) { this.color = color; }

    public int tieneIda;

    public int getTieneIda() { return this.tieneIda; }

    public void setTieneIda(int tieneIda) { this.tieneIda = tieneIda; }

    public int tieneVuelta;

    public int getTieneVuelta() { return this.tieneVuelta; }

    public void setTieneVuelta(int tieneVuelta) { this.tieneVuelta = tieneVuelta; }

    private String pmr;

    public String getPmr() { return this.pmr; }

    public void setPmr(String pmr) { this.pmr = pmr; }

    private String concesion;

    public String getConcesion() { return this.concesion; }

    public void setConcesion(String concesion) { this.concesion = concesion; }
}
