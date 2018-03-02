package com.consorcio.consorciotransportesandalucia.models;

import com.google.gson.annotations.SerializedName;

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

    public String modo;

    public TipoModoLinea getModo() {
        TipoModoLinea var = null;

        if (modo.equals("AUTOBÚS INTERURBANO"))
            var = TipoModoLinea.AUTOBÚS_INTERURBANO;
        else if (modo.equals("Bus"))
            var = TipoModoLinea.Bus;
        else if (modo.equals("Tren"))
            var = TipoModoLinea.Tren;
        else if (modo.equals("Autobús") || modo.equals("AUTOBUS"))
            var = TipoModoLinea.Autobús;
        else if (modo.equals("BARCO"))
            var = TipoModoLinea.BARCO;
        else if (modo.equals("CERCANÍAS"))
            var = TipoModoLinea.CERCANÍAS;
        else if (modo.equals("MEDIA_DISTANCIA"))
            var = TipoModoLinea.MEDIA_DISTANCIA;
        else
            var = TipoModoLinea.Default;

        return var;
    }

    public void setModoString(String modoString) {
        this.modo = modoString;
    }

    private String idModo;

    public String getIdModo() { return this.idModo; }

    public void setIdModo(String idModo) { this.idModo = idModo; }

    private String operadores;

    public String getOperadores() { return this.operadores; }

    public void setOperadores(String operadores) { this.operadores = operadores; }


}
