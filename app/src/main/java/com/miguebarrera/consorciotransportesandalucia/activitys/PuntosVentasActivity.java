package com.miguebarrera.consorciotransportesandalucia.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.miguebarrera.consorciotransportesandalucia.R;

public class PuntosVentasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntos_ventas);
        setTitle(getString(R.string.title_activity_puntos_venta));
    }
}
