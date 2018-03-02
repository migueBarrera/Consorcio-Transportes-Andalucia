package com.consorcio.consorciotransportesandalucia.utils;

import android.content.Context;

import com.consorcio.consorciotransportesandalucia.interfaces.RestInterface;
import com.consorcio.consorciotransportesandalucia.models.CapsuleConsorcio;
import com.consorcio.consorciotransportesandalucia.models.CapsuleHorariosLinea;
import com.consorcio.consorciotransportesandalucia.models.CapsuleLineaDetalle;
import com.consorcio.consorciotransportesandalucia.models.CapsuleLineas;
import com.consorcio.consorciotransportesandalucia.models.CapsuleLineasPorNucleo;
import com.consorcio.consorciotransportesandalucia.models.CapsuleMunicipio;
import com.consorcio.consorciotransportesandalucia.models.CapsuleNoticias;
import com.consorcio.consorciotransportesandalucia.models.CapsuleNucleo;
import com.consorcio.consorciotransportesandalucia.models.CapsuleParadas;
import com.consorcio.consorciotransportesandalucia.models.CapsulePuntosVenta;
import com.consorcio.consorciotransportesandalucia.models.CapsuleTarifasInterurbanas;
import com.consorcio.consorciotransportesandalucia.models.CapsuleTarifasUrbanas;
import com.consorcio.consorciotransportesandalucia.models.Consorcio;
import com.consorcio.consorciotransportesandalucia.models.Parada;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by migueBarreraBluumi on 10/01/2018.
 */

public class ClienteApi {
    private Retrofit miRetrofit;
    private RestInterface service;

    public ClienteApi() {
        final OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        miRetrofit = new Retrofit.Builder().baseUrl(Const.REST.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        service = miRetrofit.create(RestInterface.class);
    }

    public void getParadas(Map<String, String> mapHeaders,int idConsorcio, Callback<CapsuleParadas> getParadasCallback) {
        service.getParadas(idConsorcio).enqueue(getParadasCallback);
    }

    public void getParadaDetail(Map<String, String> mapHeaders,int idConsorcio,int idParada, Callback<Parada> getParadasCallback) {
        service.getParadaDetail(idConsorcio,idParada).enqueue(getParadasCallback);
    }

    public void getPuntosVenta(Map<String, String> mapHeaders,int idConsorcio, Callback<CapsulePuntosVenta> getPuntosVentaCallback) {
        service.getPuntosVenta(idConsorcio,mapHeaders).enqueue(getPuntosVentaCallback);
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


    public void getLineasPorNucleos(Map<String, String> mapHeaders,int idConsorcio,int idNucleo, Callback<CapsuleLineas> getLineasCallback) {
        service.getLineasPorNucleos(idConsorcio,idNucleo,mapHeaders).enqueue(getLineasCallback);
    }

    public void getParadasDeLinea(Map<String, String> mapHeaders,int idConsorcio,int idLinea, Callback<CapsuleParadas> getParadasLineaCallback) {
        service.getParadasDeLinea(idConsorcio,idLinea).enqueue(getParadasLineaCallback);
    }

    public void getMunicipios(Map<String, String> mapHeaders,int idConsorcio,Callback<CapsuleMunicipio> getMunicipiosCallback){
        service.getMunicipios(idConsorcio).enqueue(getMunicipiosCallback);
    }

    public void getNucleos(Map<String, String> mapHeaders,int idConsorcio,Callback<CapsuleNucleo> getNucleosCallback){
        service.getNucleos(idConsorcio).enqueue(getNucleosCallback);
    }

    public void getAtencionUsuario(Map<String,String> mapHeader, int idConsorcio, Callback<ResponseBody> responseBodyCall){
        service.getAtencionUsuario(idConsorcio).enqueue(responseBodyCall);
    }

    public void getLineaDetalle(Map<String,String> mapHeader, int idConsorcio,int idLinea, Callback<CapsuleLineaDetalle> responseBodyCall){
        service.getLineaDetalle(idConsorcio,idLinea).enqueue(responseBodyCall);
    }

    public void getHorariosLinea(Map<String,String> map , int idConsorcio, Callback<CapsuleHorariosLinea> capsuleHorariosLineaCallback){
        service.getHorariosLinea(idConsorcio,map).enqueue(capsuleHorariosLineaCallback);
    }

    public void getNoticias(Map<String,String> map , int idConsorcio, int idLinea, Callback<CapsuleNoticias> capsuleNoticiasCallback){
        service.getNoticias(idConsorcio,idLinea,map).enqueue(capsuleNoticiasCallback);
    }

    public void getTarifasInterurbana(Map<String,String> map , int idConsorcio, Callback<CapsuleTarifasInterurbanas> capsuleTarifasCallBAck){
        service.getTarifasInterurbana(idConsorcio,map).enqueue(capsuleTarifasCallBAck);
    }

    public void getTarifasUrbanas(Map<String,String> map , int idConsorcio, Callback<CapsuleTarifasUrbanas> capsuleTarifasCallBAck){
        service.getTarifasUrbana(idConsorcio,map).enqueue(capsuleTarifasCallBAck);
    }

}
