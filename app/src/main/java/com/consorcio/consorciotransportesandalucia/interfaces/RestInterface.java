package com.consorcio.consorciotransportesandalucia.interfaces;

import com.consorcio.consorciotransportesandalucia.models.CapsuleConsorcio;
import com.consorcio.consorciotransportesandalucia.models.CapsuleLineas;
import com.consorcio.consorciotransportesandalucia.models.CapsuleLineasPorNucleo;
import com.consorcio.consorciotransportesandalucia.models.CapsuleMunicipio;
import com.consorcio.consorciotransportesandalucia.models.CapsuleNucleo;
import com.consorcio.consorciotransportesandalucia.models.CapsuleParadas;
import com.consorcio.consorciotransportesandalucia.models.CapsulePuntosVenta;
import com.consorcio.consorciotransportesandalucia.models.Consorcio;
import com.consorcio.consorciotransportesandalucia.utils.Const;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

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

    @GET(Const.REST.GET_LINEAS_NUCLEO_ORIGEN_DESTINO)
    Call<CapsuleLineasPorNucleo> getLineasPorNucleos(@QueryMap Map<String, String> options, @Path("idConsorcio") int idConsorcio);

    @GET(Const.REST.GET_PARADAS_DE_LINEAS)
    Call<CapsuleParadas> getParadasDeLinea(@Path("idConsorcio") int idConsorcio,@Path("idLinea") int idLinea);

    @GET(Const.REST.GET_MUNICIPIOS)
    Call<CapsuleMunicipio> getMunicipios(@Path("idConsorcio") int idConsorcio);

    @GET(Const.REST.GET_NUCLEOS)
    Call<CapsuleNucleo> getNucleos(@Path("idConsorcio") int idConsorcio);

}
