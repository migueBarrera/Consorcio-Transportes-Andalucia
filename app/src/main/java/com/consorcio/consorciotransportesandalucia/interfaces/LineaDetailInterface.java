package com.consorcio.consorciotransportesandalucia.interfaces;

import com.consorcio.consorciotransportesandalucia.models.CapsuleLineaDetalle;

/**
 * Created by migueBarreraBluumi on 17/01/2018.
 */

public interface LineaDetailInterface {
    CapsuleLineaDetalle getCapsuleLineaDetail();
    int getConsorcioId();
    int getLineaId();
}
