package com.consorcio.consorciotransportesandalucia.models;

/**
 * Created by migueBarreraBluumi on 11/01/2018.
 */

public class Linea {
    private String idLinea;

    public String getIdLinea() { return this.idLinea; }

    public void setIdLinea(String idLinea) { this.idLinea = idLinea; }

    private String codigo;

    public String getCodigo() { return this.codigo; }

    public void setCodigo(String codigo) { this.codigo = codigo; }

    private String nombre;

    public String getNombre() { return this.nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    private String hayNoticias;

    public String getHayNoticias() { return this.hayNoticias; }

    public void setHayNoticias(String hayNoticias) { this.hayNoticias = hayNoticias; }

    public TipoModoLinea modo;

    public TipoModoLinea getModo() { return this.modo; }

    public void setModo(TipoModoLinea modo) { this.modo = modo; }

    private String idModo;

    public String getIdModo() { return this.idModo; }

    public void setIdModo(String idModo) { this.idModo = idModo; }

    private String operadores;

    public String getOperadores() { return this.operadores; }

    public void setOperadores(String operadores) { this.operadores = operadores; }


}
