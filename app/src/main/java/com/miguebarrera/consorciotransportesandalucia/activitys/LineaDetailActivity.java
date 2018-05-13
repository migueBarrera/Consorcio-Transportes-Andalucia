package com.miguebarrera.consorciotransportesandalucia.activitys;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.miguebarrera.consorciotransportesandalucia.R;
import com.miguebarrera.consorciotransportesandalucia.controls.MyNestedScrollView;
import com.miguebarrera.consorciotransportesandalucia.fragments.LineaHorarioFragment;
import com.miguebarrera.consorciotransportesandalucia.fragments.LineaInfoFragment;
import com.miguebarrera.consorciotransportesandalucia.fragments.LineaItinerarioFragment;
import com.miguebarrera.consorciotransportesandalucia.interfaces.LineaDetailInterface;
import com.miguebarrera.consorciotransportesandalucia.models.CapsuleLineaDetalle;
import com.miguebarrera.consorciotransportesandalucia.models.Consorcio;
import com.miguebarrera.consorciotransportesandalucia.utils.ClienteApi;
import com.miguebarrera.consorciotransportesandalucia.utils.Const;
import com.miguebarrera.consorciotransportesandalucia.utils.MessageUtil;
import com.miguebarrera.consorciotransportesandalucia.utils.SharedPreferencesDatabase;
import com.miguebarrera.consorciotransportesandalucia.utils.SharedPreferencesUtil;
import com.miguebarrera.consorciotransportesandalucia.utils.Util;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LineaDetailActivity extends AppCompatActivity implements LineaDetailInterface {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @BindView(R.id.navigation_linea)
    BottomNavigationView navigation;
    ClienteApi clienteApi;
    private CapsuleLineaDetalle capsuleLineaDetalle;
    private int idConsorcio, idLinea;
    Dialog progressDialog;
    @BindView(R.id.nestedScrollView)
    MyNestedScrollView nestedScrollView;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;
    Activity parentActivity;
    Boolean isFavourite = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linea_detail);
        this.parentActivity = this;
        ButterKnife.bind(this);
        idConsorcio = SharedPreferencesUtil.getInt(this, Const.SHAREDKEYS.ID_CONSORCIO);
        idLinea = SharedPreferencesUtil.getInt(this, Const.SHAREDKEYS.ID_LINEA);
        isFavourite = SharedPreferencesDatabase.isFavouriteLine(this,idLinea);
        configureToolbar();
        addListener();
    }

    private void addListener() {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int infoId = R.string.linea_add_favourite;
                if (isFavourite){
                    SharedPreferencesDatabase.removeLineFav(parentActivity,idLinea);
                    infoId = R.string.linea_remove_favourite;
                }else {
                    SharedPreferencesDatabase.addLineToFav(parentActivity,capsuleLineaDetalle);
                }

                isFavourite = !isFavourite;
                MessageUtil.showSnackbar(getWindow().getDecorView().getRootView(),parentActivity,infoId);
                configureFloatingButton();
            }
        });
    }

    private void configureToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    private void setTitleToolbar(String toolbarTitle){
        collapsingToolbarLayout.setTitle(toolbarTitle);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
        configureFloatingButton();
    }

    private void configureFloatingButton() {
        int resIdImage = R.drawable.ic_star_outline;

        if (isFavourite)
            resIdImage = R.drawable.ic_star_fill;

        floatingActionButton.setImageResource(resIdImage);
    }

    private void loadData() {
        if (Util.hasInternet(getApplicationContext())){
            clienteApi = new ClienteApi();
            loadDetail(idConsorcio,idLinea);
        }
    }

    private void loadDetail(int idConsorcio, int idLinea) {
        //Activamos el progress
        progressDialog = ProgressDialog.show(this, "", getResources().getString(R.string.progress_lineas), true);
        clienteApi.getLineaDetalle(null, idConsorcio, idLinea, new Callback<CapsuleLineaDetalle>() {
            @Override
            public void onResponse(Call<CapsuleLineaDetalle> call, Response<CapsuleLineaDetalle> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    capsuleLineaDetalle = response.body();
                    Util.log(capsuleLineaDetalle);

                    Gson gson = new Gson();
                    Consorcio consorcio = gson.fromJson(SharedPreferencesUtil.getString(parentActivity,Const.SHAREDKEYS.MY_CONSORCIO), Consorcio.class);
                    String title = "Linea: ";
                    if (consorcio.getCiudad().equals("Sevilla")){
                        String[] nameLinea = capsuleLineaDetalle.getNombre().split(" ");
                        title = title + nameLinea[0];
                    }else {
                        title = title + capsuleLineaDetalle.getCodigo();
                    }

                    setTitleToolbar(title);

                    //Inicializamos el MapsFragemnts al inicio
                    Fragment lineaInfoFragment= new LineaInfoFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, lineaInfoFragment).commitAllowingStateLoss();
                    nestedScrollView.setScrollable(true);
                }
            }

            @Override
            public void onFailure(Call<CapsuleLineaDetalle> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
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
                    nestedScrollView.setScrollable(true);
                    return true;
                case  R.id.navigation_linea_itinerary:
                    fragment = new LineaItinerarioFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, fragment).commitAllowingStateLoss();
                    nestedScrollView.setScrollable(false);
                    return true;
                case R.id.navigation_linea_info:
                    fragment = new LineaInfoFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, fragment).commitAllowingStateLoss();

                    nestedScrollView.setScrollable(true);
                    return true;
            }
            return false;
        }
    };

    @Override
    public CapsuleLineaDetalle getCapsuleLineaDetail() {
        return capsuleLineaDetalle;
    }

    @Override
    public int getConsorcioId() {
        return idConsorcio;
    }

    @Override
    public int getLineaId() {
        return idLinea;
    }
}
