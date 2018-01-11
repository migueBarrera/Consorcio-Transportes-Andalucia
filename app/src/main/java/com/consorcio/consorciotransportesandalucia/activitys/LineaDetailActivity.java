package com.consorcio.consorciotransportesandalucia.activitys;

import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.consorcio.consorciotransportesandalucia.R;
import com.consorcio.consorciotransportesandalucia.fragments.LineaHorarioFragment;
import com.consorcio.consorciotransportesandalucia.fragments.LineaInfoFragment;
import com.consorcio.consorciotransportesandalucia.fragments.LineaItinerarioFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LineaDetailActivity extends AppCompatActivity {

    @BindView(R.id.navigation_linea)
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linea_detail);
        ButterKnife.bind(this);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()){
                case R.id.navigation_linea_horario:
                    fragment = new LineaHorarioFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, fragment).commitAllowingStateLoss();
                    return true;
                case  R.id.navigation_linea_itinerary:
                    fragment = new LineaItinerarioFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, fragment).commitAllowingStateLoss();
                    return true;
                case R.id.navigation_linea_info:
                    fragment = new LineaInfoFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, fragment).commitAllowingStateLoss();
                    return true;
            }
            return false;
        }
    };
}
