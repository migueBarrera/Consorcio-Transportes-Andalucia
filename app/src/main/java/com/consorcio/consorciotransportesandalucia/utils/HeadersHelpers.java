package com.consorcio.consorciotransportesandalucia.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by migueBarreraBluumi on 17/01/2018.
 */

public class HeadersHelpers {

    public static Map<String,String > getHeaders(){
        Map<String, String> map = new HashMap<>();

        map.put(Const.REST.HEADERS.LANG,"ES");


        return map;
    }
}
