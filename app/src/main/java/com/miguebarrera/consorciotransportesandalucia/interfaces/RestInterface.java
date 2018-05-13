package com.miguebarrera.consorciotransportesandalucia.interfaces;

import com.miguebarrera.consorciotransportesandalucia.models.CapsuleConsorcio;
import com.miguebarrera.consorciotransportesandalucia.models.CapsuleHorariosLinea;
import com.miguebarrera.consorciotransportesandalucia.models.CapsuleLineaDetalle;
import com.miguebarrera.consorciotransportesandalucia.models.CapsuleLineas;
import com.miguebarrera.consorciotransportesandalucia.models.CapsuleLineasPorNucleo;
import com.miguebarrera.consorciotransportesandalucia.models.CapsuleMunicipio;
import com.miguebarrera.consorciotransportesandalucia.models.CapsuleNoticias;
import com.miguebarrera.consorciotransportesandalucia.models.CapsuleNucleo;
import com.miguebarrera.consorciotransportesandalucia.models.CapsuleParadas;
import com.miguebarrera.consorciotransportesandalucia.models.CapsulePuntosVenta;
import com.miguebarrera.consorciotransportesandalucia.models.CapsuleTarifasInterurbanas;
import com.miguebarrera.consorciotransportesandalucia.models.CapsuleTarifasUrbanas;
import com.miguebarrera.consorciotransportesandalucia.models.Consorcio;
import com.miguebarrera.consorciotransportesandalucia.models.Parada;
import com.miguebarrera.consorciotransportesandalucia.utils.Const;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by migueBarreraBluumi on 10/01/2018.
 */

public interface RestInterface {

    @GET(Const.REST.GET_PARADAS)
    Call<CapsuleParadas> getParadas(@Path("idConsorcio") int idConsorcio);

    @GET(Const.REST.GET_PARADAS_DETAIL)
    Call<Parada> getParadaDetail(@Path("idConsorcio") int idConsorcio,@Path("idParada") int idParada);

    @GET(Const.REST.GET_PUNTOS_DE_VENTAS)
    Call<CapsulePuntosVenta> getPuntosVenta(@Path("idConsorcio") int idConsorcio,@QueryMap Map<String, String> options);

    @GET(Const.REST.GET_CONSORCIOS)
    Call<CapsuleConsorcio> getConsorcios(@Path("idConsorcio") int idConsorcio);

    @GET(Const.REST.GET_CONSORCIO_DETAIL)
    Call<Consorcio> getConsorcioDetail(@Path("idConsorcio") int idConsorcio);

    @GET(Const.REST.GET_LINEAS)
    Call<CapsuleLineas> getLineas(@Path("idConsorcio") int idConsorcio);

    @GET(Const.REST.GET_LINEAS_NUCLEO)
    Call<CapsuleLineas> getLineasPorNucleos(@Path("idConsorcio") int idConsorcio,@Path("idNucleo") int idNucleo,@QueryMap Map<String, String> options);

    @GET(Const.REST.GET_PARADAS_DE_LINEAS)
    Call<CapsuleParadas> getParadasDeLinea(@Path("idConsorcio") int idConsorcio,@Path("idLinea") int idLinea);

    @GET(Const.REST.GET_MUNICIPIOS)
    Call<CapsuleMunicipio> getMunicipios(@Path("idConsorcio") int idConsorcio);

    @GET(Const.REST.GET_NUCLEOS)
    Call<CapsuleNucleo> getNucleos(@Path("idConsorcio") int idConsorcio);

    @GET(Const.REST.GET_ATENCION_USUARIO)
    Call<ResponseBody> getAtencionUsuario(@Path("idConsorcio") int idConsorcio);

    @GET(Const.REST.GET_LINEA_DETALLE)
    Call<CapsuleLineaDetalle> getLineaDetalle(@Path("idConsorcio") int idConsorcio,@Path("idLinea") int idLinea);

    @GET(Const.REST.GET_HORARIOS_LINEA)
    Call<CapsuleHorariosLinea> getHorariosLinea(@Path("idConsorcio") int idConsorcio, @QueryMap Map<String, String> options);

    @GET(Const.REST.GET_NOTICIAS)
    Call<CapsuleNoticias> getNoticias(@Path("idConsorcio") int idConsorcio,@Path("idLinea") int idLinea, @QueryMap Map<String, String> options);

    @GET(Const.REST.GET_TARIFAS_INTERURBANAS)
    Call<CapsuleTarifasInterurbanas> getTarifasInterurbana(@Path("idConsorcio") int idConsorcio,@QueryMap Map<String, String> options);

    @GET(Const.REST.GET_TARIFAS_URBANAS)
    Call<CapsuleTarifasUrbanas> getTarifasUrbana(@Path("idConsorcio") int idConsorcio, @QueryMap Map<String, String> options);
}
