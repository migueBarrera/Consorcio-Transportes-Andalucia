package com.consorcio.consorciotransportesandalucia.models;

import java.util.ArrayList;

/**
 * Created by migueBarreraBluumi on 23/01/2018.
 */

public class CapsuleTarifasInterurbanas {
    private ArrayList<TarifasInterurbana> tarifasInterurbanas;

    public ArrayList<TarifasInterurbana> getTarifasInterurbanas() {
        if (this.tarifasInterurbanas != null)
            return this.tarifasInterurbanas;
        else
            return null;
    }

    public void setTarifasInterurbanas(ArrayList<TarifasInterurbana> tarifasInterurbanas) { this.tarifasInterurbanas = tarifasInterurbanas; }

}
