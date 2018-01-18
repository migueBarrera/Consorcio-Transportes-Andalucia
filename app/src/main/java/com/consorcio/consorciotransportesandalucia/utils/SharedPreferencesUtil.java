package com.consorcio.consorciotransportesandalucia.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

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

    public static String getString(Activity parentActivity,String key){
        SharedPreferences sharedPreferences = getSharedPreferences(parentActivity);
        return sharedPreferences.getString(key,"");
    }

    public static void setInt(Activity parentActivity,String key,int value){
        SharedPreferences sharedPreferences = getSharedPreferences(parentActivity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(Activity parentActivity,String key){
        SharedPreferences sharedPreferences = getSharedPreferences(parentActivity);
        return sharedPreferences.getInt(key,2);
    }
}
