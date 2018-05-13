package com.miguebarrera.consorciotransportesandalucia.interfaces;

import com.miguebarrera.consorciotransportesandalucia.models.CapsuleLineaDetalle;

/**
 * Created by migueBarreraBluumi on 17/01/2018.
 */

public interface LineaDetailInterface {
    CapsuleLineaDetalle getCapsuleLineaDetail();
    int getConsorcioId();
    int getLineaId();
}
