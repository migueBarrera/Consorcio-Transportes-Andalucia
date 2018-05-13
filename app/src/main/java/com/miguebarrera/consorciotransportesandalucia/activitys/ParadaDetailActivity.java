package com.miguebarrera.consorciotransportesandalucia.activitys;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.miguebarrera.consorciotransportesandalucia.R;
import com.miguebarrera.consorciotransportesandalucia.fragments.ParadaDetailFragment;
import com.miguebarrera.consorciotransportesandalucia.interfaces.ParadaDetailInterface;
import com.miguebarrera.consorciotransportesandalucia.utils.Const;
import com.miguebarrera.consorciotransportesandalucia.utils.MessageUtil;
import com.miguebarrera.consorciotransportesandalucia.utils.SharedPreferencesDatabase;
import com.miguebarrera.consorciotransportesandalucia.utils.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParadaDetailActivity extends AppCompatActivity implements ParadaDetailInterface {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    int idParada;
    boolean isFavourite;
    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;
    ParadaDetailFragment fragment;
    Activity parentActivity;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parada_detail);
        ButterKnife.bind(this);
        this.parentActivity = this;

        idParada = SharedPreferencesUtil.getInt(this, Const.SHAREDKEYS.ID_PARADA);
        isFavourite = SharedPreferencesDatabase.isFavouriteParada(this,idParada);

        addListener();
        configureToolbar();

        //Inicializamos el MapsFragemnts al inicio
        fragment = new ParadaDetailFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment).commitAllowingStateLoss();
    }

    private void addListener() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int infoId = R.string.parada_add_favourite;
                if (isFavourite){
                    SharedPreferencesDatabase.removeParadaFav(parentActivity,idParada);
                    infoId = R.string.parada_remove_favourite;
                }else {
                    SharedPreferencesDatabase.addParadaToFav(parentActivity,fragment.getParada());
                }

                isFavourite = !isFavourite;
                MessageUtil.showSnackbar(getWindow().getDecorView().getRootView(),parentActivity,infoId);
                updateFloatingButton();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateFloatingButton();
    }

    private void updateFloatingButton() {
        int resIdImage = R.drawable.ic_star_outline;

        if (isFavourite)
            resIdImage = R.drawable.ic_star_fill;

        floatingActionButton.setImageResource(resIdImage);
    }

    private void configureToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void setTitleToolbar(String toolbarTitle){
        collapsingToolbarLayout.setTitle(toolbarTitle);
    }

    @Override
    public int getParadaId() {
        return idParada;
    }
}
