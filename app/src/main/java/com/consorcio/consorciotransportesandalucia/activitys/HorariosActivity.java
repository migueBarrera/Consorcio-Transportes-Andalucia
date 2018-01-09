package com.consorcio.consorciotransportesandalucia.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.consorcio.consorciotransportesandalucia.R;

public class HorariosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horarios);
        setTitle(getString(R.string.title_activity_horarios));
    }
}
