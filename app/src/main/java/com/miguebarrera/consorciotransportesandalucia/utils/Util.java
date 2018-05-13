package com.miguebarrera.consorciotransportesandalucia.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;

import com.miguebarrera.consorciotransportesandalucia.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
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

    public static boolean checkPermissions(Activity parentActivity) {
        int permissionState = ActivityCompat.checkSelfPermission(parentActivity,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermissions(final Activity parentActivity) {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(parentActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(Const.TAGS.TAG_PARADAS_FRAGMENT, "Displaying permission rationale to provide additional context.");
            /*MessageUtil.showSnackbar(parentActivity.getWindow().getDecorView().getRootView(), parentActivity, R.string.permission_rationale,
                    R.string.permission_rationale_ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(parentActivity,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    0);
                        }
                    });*/
            // Request permission
            ActivityCompat.requestPermissions(parentActivity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        } else {
            Log.i(Const.TAGS.TAG_PARADAS_FRAGMENT, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(parentActivity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        }
    }

    public static CameraUpdate getCamaraUpdate(Activity parentActivity){

        return Util.getCamaraUpdate(parentActivity,12);
    }
    public static CameraUpdate getCamaraUpdate(Activity parentActivity,int zoom){
        if (!checkPermissions(parentActivity))
            requestPermissions(parentActivity);

        double latitude = 37.378176;
        double longitud = -6.001057;
        try{
            LocationManager locationManager = (LocationManager)
                    parentActivity.getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            Location location = locationManager.getLastKnownLocation(locationManager
                    .getBestProvider(criteria, false));
             latitude = location.getLatitude();
             longitud = location.getLongitude();
        }catch (SecurityException e){
            Util.log(e.getMessage());
        }


        return CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitud), zoom);
    }
}
