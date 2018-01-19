package com.consorcio.consorciotransportesandalucia.utils;

/**
 * Created by migueBarreraBluumi on 10/01/2018.
 */

public class Const {

    public class KEYS{
        public static final String PREFERENCE_FILE_KEY = "453fg565543643r5tgrgg6554tygt";
    }

    public class SHAREDKEYS{
        public static final String ID_CONSORCIO = "Id_consorcio";
        public static final String ID_LINEA = "Id_linea";
        public static final String MY_CONSORCIO = "My_consorcio";
    }

    public class TAGS{
        public static final String TAG_PARADAS_FRAGMENT = "PARADAS FRAGMENT";
    }

    public class REST{
        public static final String BASE_URL = "http://api.ctan.es/v1/Consorcios/{idConsorcio}/";
        public static final String GET_CONSORCIOS = BASE_URL + "consorcios";
        public static final String GET_CONSORCIO_DETAIL = BASE_URL + "consorcios/consorcio";
        public static final String GET_PARADAS =  BASE_URL +"paradas";
        public static final String GET_PUNTOS_DE_VENTAS = BASE_URL +"puntos_venta";
        public static final String GET_LINEAS = BASE_URL + "lineas";
        public static final String GET_PARADAS_DE_LINEAS = BASE_URL + "lineas/{idLinea}/paradas";
        public static final String GET_MUNICIPIOS = BASE_URL + "municipios/";
        public static final String GET_NUCLEOS = BASE_URL + "nucleos";
        public static final String GET_LINEAS_NUCLEO_ORIGEN_DESTINO = BASE_URL + "horarios_origen_destino";
        public static final String GET_ATENCION_USUARIO = BASE_URL + "att_usuario";
        public static final String GET_LINEA_DETALLE = BASE_URL + "lineas/{idLinea}";
        public static final String GET_HORARIOS_LINEA = BASE_URL + "horarios_lineas";
        public static final String GET_NOTICIAS = BASE_URL + "lineas/{idLinea}/noticias";


        public class HEADERS{
            public static final String LANG = "lang";
        }
    }

    public class APP{
        public static final String PACKAGE_NAME = "com.consorcio.consorciotransportesandalucia";
        public static final String RECEIVER = PACKAGE_NAME + ".utils.RECIVER";
        public static final String LOCATION_NAME_DATA_EXTRA  = PACKAGE_NAME + ".LOCATION_NAME_DATA_EXTRA ";
    }
}
