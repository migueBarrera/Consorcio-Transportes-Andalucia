package com.consorcio.consorciotransportesandalucia.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.consorcio.consorciotransportesandalucia.activitys.ParadaDetailActivity;
import com.consorcio.consorciotransportesandalucia.models.CapsuleLineaDetalle;
import com.consorcio.consorciotransportesandalucia.models.Parada;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by migueBarreraBluumi on 30/01/2018.
 */

public class SharedPreferencesDatabase {

    public static void addLineToFav(Activity activity,CapsuleLineaDetalle linea){
        ArrayList<CapsuleLineaDetalle> lineas = getLineFav(activity);
        lineas.add(linea);

        setLineFav(activity,lineas);
    }

    public static ArrayList<CapsuleLineaDetalle> getLineFav(Activity activity){
        int idConsorcio = SharedPreferencesUtil.getInt(activity,Const.SHAREDKEYS.ID_CONSORCIO);
        ArrayList<CapsuleLineaDetalle> lineaList = new ArrayList<>();
        String listString = SharedPreferencesUtil.getString(activity,Const.SHAREDDATABASEKEYS.MY_FAVOURITES_LINES+idConsorcio);
        if (!listString.isEmpty()){
            Gson gson = new Gson();
            Collection<CapsuleLineaDetalle> collection = gson.fromJson(listString,new TypeToken<List<CapsuleLineaDetalle>>(){}.getType());
            lineaList = new ArrayList<>(collection);
        }

        return lineaList;
    }

    private static void setLineFav(Activity activity,ArrayList<CapsuleLineaDetalle> list){
        int idConsorcio = SharedPreferencesUtil.getInt(activity,Const.SHAREDKEYS.ID_CONSORCIO);
        Gson gson = new Gson();
        String listString = gson.toJson(list);
        SharedPreferencesUtil.setString(activity,Const.SHAREDDATABASEKEYS.MY_FAVOURITES_LINES+idConsorcio,listString);
    }

    public static boolean isFavouriteLine(Activity activity, int id){
        boolean isFavourite = false;
        ArrayList<CapsuleLineaDetalle> lineas = getLineFav(activity);
        for (int i = 0;i<lineas.size() && !isFavourite;i++){
            int idLinea = Integer.valueOf(lineas.get(i).getIdLinea());
            if (idLinea == id)
                isFavourite = true;
        }

        return isFavourite;
    }

    public static void removeLineFav(Activity activity,int id){
        ArrayList<CapsuleLineaDetalle> lineas = getLineFav(activity);
        ArrayList<CapsuleLineaDetalle> newList = new ArrayList<>();
        int size = lineas.size();
        for (int i = 0;i<size;i++){
            int idLinea = Integer.valueOf(lineas.get(i).getIdLinea());
            if (idLinea != id)
                newList.add(lineas.get(i));
        }
        setLineFav(activity,newList);
    }

    public static void addParadaToFav(Activity activity,Parada paradas){
        ArrayList<Parada> lineas = getParadasFav(activity);
        lineas.add(paradas);

        setParadasFav(activity,lineas);
    }

    public static ArrayList<Parada> getParadasFav(Activity activity){
        int idConsorcio = SharedPreferencesUtil.getInt(activity,Const.SHAREDKEYS.ID_CONSORCIO);
        ArrayList<Parada> lineaList = new ArrayList<>();
        String listString = SharedPreferencesUtil.getString(activity,Const.SHAREDDATABASEKEYS.MY_FAVOURITES_PARADAS+idConsorcio);
        if (!listString.isEmpty()){
            Gson gson = new Gson();
            Collection<Parada> collection = gson.fromJson(listString,new TypeToken<List<Parada>>(){}.getType());
            lineaList = new ArrayList<>(collection);
        }

        return lineaList;
    }

    private static void setParadasFav(Activity activity,ArrayList<Parada> list){
        int idConsorcio = SharedPreferencesUtil.getInt(activity,Const.SHAREDKEYS.ID_CONSORCIO);
        Gson gson = new Gson();
        String listString = gson.toJson(list);
        SharedPreferencesUtil.setString(activity,Const.SHAREDDATABASEKEYS.MY_FAVOURITES_PARADAS+idConsorcio,listString);
    }

    public static boolean isFavouriteParada(Activity parentActivity, int idParada) {
        boolean isFavourite = false;
        ArrayList<Parada> paradas = getParadasFav(parentActivity);
        for (int i = 0;i<paradas.size() && !isFavourite;i++){
            int id = Integer.valueOf(paradas.get(i).getIdParada());
            if (id == idParada)
                isFavourite = true;
        }

        return isFavourite;
    }

    public static void removeParadaFav(Activity parent, int idParada) {
        ArrayList<Parada> paradas = getParadasFav(parent);
        ArrayList<Parada> newList = new ArrayList<>();
        int size = paradas.size();
        for (int i = 0;i<size;i++){
            int id = Integer.valueOf(paradas.get(i).getIdParada());
            if (id != idParada)
                newList.add(paradas.get(i));
        }
        setParadasFav(parent,newList);
    }
}
