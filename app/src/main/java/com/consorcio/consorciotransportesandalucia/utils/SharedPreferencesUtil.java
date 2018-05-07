package com.consorcio.consorciotransportesandalucia.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by migueBarreraBluumi on 10/01/2018.
 */

public class SharedPreferencesUtil {
    public static SharedPreferences getSharedPreferences(Activity parentActivity){
        return parentActivity.getSharedPreferences(Const.KEYS.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
    }

    public static void setString(Activity parentActivity,String key,String value){
        SharedPreferences sharedPreferences = getSharedPreferences(parentActivity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void setObject(Activity parentActivity,String key,Object object){
        Gson gson = new Gson();
        String json = gson.toJson(object,object.getClass());
        SharedPreferences sharedPreferences = getSharedPreferences(parentActivity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, json);
        editor.apply();
    }

    public static Object getObject(Activity parentActivity,String key,Type type){
        SharedPreferences sharedPreferences = getSharedPreferences(parentActivity);
        String json = sharedPreferences.getString(key,"");
        Object object = null;

        if (!json.equals("")){
            Gson gson = new Gson();
            object = gson.fromJson(json,type);
        }

        return object;
    }

    public static String getString(Activity parentActivity,String key){
        SharedPreferences sharedPreferences = getSharedPreferences(parentActivity);
        return sharedPreferences.getString(key,"");
    }

    public static Boolean getBoolean(Activity parentActivity,String key,boolean defaultValue){
        SharedPreferences sharedPreferences = getSharedPreferences(parentActivity);
        return sharedPreferences.getBoolean(key,defaultValue);
    }

    public static void setBoolean(Activity parentActivity,String key,Boolean value){
        SharedPreferences sharedPreferences = getSharedPreferences(parentActivity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void setInt(Activity parentActivity,String key,int value){
        SharedPreferences sharedPreferences = getSharedPreferences(parentActivity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(Activity parentActivity,String key){
        SharedPreferences sharedPreferences = getSharedPreferences(parentActivity);
        return sharedPreferences.getInt(key,1);
    }
}
