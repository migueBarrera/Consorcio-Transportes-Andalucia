package com.consorcio.consorciotransportesandalucia.activitys;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.consorcio.consorciotransportesandalucia.R;
import com.consorcio.consorciotransportesandalucia.fragments.ParadaDetailFragment;
import com.consorcio.consorciotransportesandalucia.interfaces.ParadaDetailInterface;
import com.consorcio.consorciotransportesandalucia.utils.Const;
import com.consorcio.consorciotransportesandalucia.utils.MessageUtil;
import com.consorcio.consorciotransportesandalucia.utils.SharedPreferencesDatabase;
import com.consorcio.consorciotransportesandalucia.utils.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParadaDetailActivity extends AppCompatActivity implements ParadaDetailInterface {

    int idParada;
    boolean isFavourite;
    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;
    ParadaDetailFragment fragment;
    Activity parentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parada_detail);
        ButterKnife.bind(this);
        this.parentActivity = this;

        idParada = SharedPreferencesUtil.getInt(this, Const.SHAREDKEYS.ID_PARADA);
        isFavourite = SharedPreferencesDatabase.isFavouriteParada(this,idParada);

        addListener();

        //Inicializamos el MapsFragemnts al inicio
        fragment = new ParadaDetailFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment).commitAllowingStateLoss();
    }

    private void addListener() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int infoId = R.string.linea_add_favourite;
                if (isFavourite){
                    SharedPreferencesDatabase.removeParadaFav(parentActivity,idParada);
                    infoId = R.string.linea_remove_favourite;
                }else {
                    SharedPreferencesDatabase.addParadaToFav(parentActivity,fragment.getParada());
                }

                isFavourite = !isFavourite;
                MessageUtil.showSnackbar(getWindow().getDecorView().getRootView(),parentActivity,infoId);
                configureFloatingButton();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        configureFloatingButton();
    }

    private void configureFloatingButton() {
        int resIdImage = R.drawable.ic_star_outline;

        if (isFavourite)
            resIdImage = R.drawable.ic_star_fill;

        floatingActionButton.setImageResource(resIdImage);
    }

    @Override
    public int getParadaId() {
        return idParada;
    }
}
