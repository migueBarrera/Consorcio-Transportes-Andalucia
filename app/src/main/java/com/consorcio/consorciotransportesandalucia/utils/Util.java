package com.consorcio.consorciotransportesandalucia.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by migueBarreraBluumi on 10/01/2018.
 */

public class Util {
    /**
     * Este metodo comprueba si el dispositivo tiene acceso a internet
     * @param context
     * @return true si el dispositivo tiene internet , false si no lo tiene
     */
    public static boolean hasInternet(Context context){
        boolean hasInternet = false;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            hasInternet = true;
        }

        return hasInternet;
    }
}
