package com.consorcio.consorciotransportesandalucia.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

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

    public static void log(String TAG,String content){
        Log.e("---------DATA---------",content);
    }

    public static void log(String content){
        Log.e("---------DATA---------",content);
    }

    public static void log(Object content){
        Gson gson = new Gson();
        Log.e("---------DATA---------",gson.toJson(content));
    }

    public static void callToNumber(Context context,String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        context.startActivity(intent);
    }

    public static void goToWeb(Context context,String url){
        Intent intent= new Intent(Intent.ACTION_VIEW,Uri.parse(url));
        context.startActivity(intent);
    }

    public static void sendEmail(Context context,String mailto){
        String mail = "mailto:"+mailto;

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(mail));

        try {
            context.startActivity(emailIntent);
        } catch (ActivityNotFoundException e) {
            //TODO: Handle case where no email app is available
        }
    }

    public static void goToMap(Activity parentActivity, LatLng pos) {
        String uri = "http://maps.google.com/maps?daddr=" + pos.latitude + "," + pos.longitude;
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(parentActivity.getPackageManager()) != null) {
            parentActivity.startActivity(mapIntent);
        }
    }
}
