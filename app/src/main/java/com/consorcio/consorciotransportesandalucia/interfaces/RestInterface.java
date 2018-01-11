package com.consorcio.consorciotransportesandalucia.interfaces;

import com.consorcio.consorciotransportesandalucia.models.CapsuleConsorcio;
import com.consorcio.consorciotransportesandalucia.models.CapsuleLineas;
import com.consorcio.consorciotransportesandalucia.models.CapsuleParadas;
import com.consorcio.consorciotransportesandalucia.models.CapsulePuntosVenta;
import com.consorcio.consorciotransportesandalucia.models.Consorcio;
import com.consorcio.consorciotransportesandalucia.utils.Const;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by migueBarreraBluumi on 10/01/2018.
 */

public interface RestInterface {

    @GET(Const.REST.GET_PARADAS)
    Call<CapsuleParadas> getParadas(@Path("idConsorcio") int idConsorcio);

    @GET(Const.REST.GET_PUNTOS_DE_VENTAS)
    Call<CapsulePuntosVenta> getPuntosVenta(@Path("idConsorcio") int idConsorcio);

    @GET(Const.REST.GET_CONSORCIOS)
    Call<CapsuleConsorcio> getConsorcios(@Path("idConsorcio") int idConsorcio);

    @GET(Const.REST.GET_CONSORCIO_DETAIL)
    Call<Consorcio> getConsorcioDetail(@Path("idConsorcio") int idConsorcio);

    @GET(Const.REST.GET_LINEAS)
    Call<CapsuleLineas> getLineas(@Path("idConsorcio") int idConsorcio);

    @GET(Const.REST.GET_PARADAS_DE_LINEAS)
    Call<CapsuleParadas> getParadasDeLinea(@Path("idConsorcio") int idConsorcio,@Path("idLinea") int idLinea);

}
