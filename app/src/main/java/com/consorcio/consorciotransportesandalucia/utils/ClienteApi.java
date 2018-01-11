package com.consorcio.consorciotransportesandalucia.utils;

import android.content.Context;

import com.consorcio.consorciotransportesandalucia.interfaces.RestInterface;
import com.consorcio.consorciotransportesandalucia.models.CapsuleConsorcio;
import com.consorcio.consorciotransportesandalucia.models.CapsuleLineas;
import com.consorcio.consorciotransportesandalucia.models.CapsuleParadas;
import com.consorcio.consorciotransportesandalucia.models.CapsulePuntosVenta;
import com.consorcio.consorciotransportesandalucia.models.Consorcio;

import java.util.List;
import java.util.Map;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by migueBarreraBluumi on 10/01/2018.
 */

public class ClienteApi {
    private Retrofit miRetrofit;
    private RestInterface service;

    public ClienteApi(Context contexto) {
        miRetrofit = new Retrofit.Builder().baseUrl(Const.REST.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = miRetrofit.create(RestInterface.class);
    }

    public void getParadas(Map<String, String> mapHeaders,int idConsorcio, Callback<CapsuleParadas> getParadasCallback) {
        service.getParadas(idConsorcio).enqueue(getParadasCallback);
    }

    public void getPuntosVenta(Map<String, String> mapHeaders,int idConsorcio, Callback<CapsulePuntosVenta> getPuntosVentaCallback) {
        service.getPuntosVenta(idConsorcio).enqueue(getPuntosVentaCallback);
    }

    public void getConsorcios(Map<String, String> mapHeaders,int idConsorcio, Callback<CapsuleConsorcio> getConsorciosCallback) {
        service.getConsorcios(idConsorcio).enqueue(getConsorciosCallback);
    }

    public void getConsorcioDetail(Map<String, String> mapHeaders,int idConsorcio, Callback<Consorcio> getConsorcioCallback) {
        service.getConsorcioDetail(idConsorcio).enqueue(getConsorcioCallback);
    }

    public void getLineas(Map<String, String> mapHeaders,int idConsorcio, Callback<CapsuleLineas> getLineasCallback) {
        service.getLineas(idConsorcio).enqueue(getLineasCallback);
    }

    public void getParadasDeLinea(Map<String, String> mapHeaders,int idConsorcio,int idLinea, Callback<CapsuleParadas> getParadasLineaCallback) {
        service.getParadasDeLinea(idConsorcio,idLinea).enqueue(getParadasLineaCallback);
    }
}
