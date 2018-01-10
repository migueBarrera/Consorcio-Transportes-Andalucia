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
    }
}
