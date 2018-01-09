package com.consorcio.consorciotransportesandalucia.utils;

import com.consorcio.consorciotransportesandalucia.models.MenuItem;

/**
 * Created by migueBarreraBluumi on 09/01/2018.
 */

public class MainMenuUtil {

    public static MenuItem[] getMenuItems(){
        return new MenuItem[]{new MenuItem("Paradas"),new MenuItem("Horarios"),new MenuItem("Puntos de ventas")};
    }
}
