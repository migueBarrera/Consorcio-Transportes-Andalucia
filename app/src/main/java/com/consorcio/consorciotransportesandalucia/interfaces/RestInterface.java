package com.consorcio.consorciotransportesandalucia.interfaces;

import com.consorcio.consorciotransportesandalucia.models.CapsuleConsorcio;
import com.consorcio.consorciotransportesandalucia.models.CapsuleHorariosLinea;
import com.consorcio.consorciotransportesandalucia.models.CapsuleLineaDetalle;
import com.consorcio.consorciotransportesandalucia.models.CapsuleLineas;
import com.consorcio.consorciotransportesandalucia.models.CapsuleLineasPorNucleo;
import com.consorcio.consorciotransportesandalucia.models.CapsuleMunicipio;
import com.consorcio.consorciotransportesandalucia.models.CapsuleNucleo;
import com.consorcio.consorciotransportesandalucia.models.CapsuleParadas;
import com.consorcio.consorciotransportesandalucia.models.CapsulePuntosVenta;
import com.consorcio.consorciotransportesandalucia.models.Consorcio;
import com.consorcio.consorciotransportesandalucia.utils.Const;

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

    @GET(Const.REST.GET_PUNTOS_DE_VENTAS)
    Call<CapsulePuntosVenta> getPuntosVenta(@Path("idConsorcio") int idConsorcio);

    @GET(Const.REST.GET_CONSORCIOS)
    Call<CapsuleConsorcio> getConsorcios(@Path("idConsorcio") int idConsorcio);

    @GET(Const.REST.GET_CONSORCIO_DETAIL)
    Call<Consorcio> getConsorcioDetail(@Path("idConsorcio") int idConsorcio);

    @GET(Const.REST.GET_LINEAS)
    Call<CapsuleLineas> getLineas(@Path("idConsorcio") int idConsorcio);

    @GET(Const.REST.GET_LINEAS_NUCLEO_ORIGEN_DESTINO)
    Call<ResponseBody> getLineasPorNucleos(@Path("idConsorcio") int idConsorcio,@QueryMap Map<String, String> options);

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

}
