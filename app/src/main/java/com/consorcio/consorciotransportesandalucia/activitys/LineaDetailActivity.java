package com.consorcio.consorciotransportesandalucia.activitys;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.consorcio.consorciotransportesandalucia.R;
import com.consorcio.consorciotransportesandalucia.controls.MyNestedScrollView;
import com.consorcio.consorciotransportesandalucia.fragments.LineaHorarioFragment;
import com.consorcio.consorciotransportesandalucia.fragments.LineaInfoFragment;
import com.consorcio.consorciotransportesandalucia.fragments.LineaItinerarioFragment;
import com.consorcio.consorciotransportesandalucia.interfaces.LineaDetailInterface;
import com.consorcio.consorciotransportesandalucia.models.CapsuleLineaDetalle;
import com.consorcio.consorciotransportesandalucia.models.CapsuleLineas;
import com.consorcio.consorciotransportesandalucia.models.Consorcio;
import com.consorcio.consorciotransportesandalucia.utils.ClienteApi;
import com.consorcio.consorciotransportesandalucia.utils.Const;
import com.consorcio.consorciotransportesandalucia.utils.MessageUtil;
import com.consorcio.consorciotransportesandalucia.utils.SharedPreferencesUtil;
import com.consorcio.consorciotransportesandalucia.utils.Util;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linea_detail);
        this.parentActivity = this;
        ButterKnife.bind(this);

        configureToolbar();
        addListener();
    }

    private void addListener() {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO añadir a favoritos
                MessageUtil.showSnackbar(parentActivity.getWindow().getDecorView().getRootView(),parentActivity,R.string.no_work);
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
    }

    private void loadData() {
        if (Util.hasInternet(getApplicationContext())){
            clienteApi = new ClienteApi();
            idConsorcio = SharedPreferencesUtil.getInt(this, Const.SHAREDKEYS.ID_CONSORCIO);
            idLinea = SharedPreferencesUtil.getInt(this, Const.SHAREDKEYS.ID_LINEA);
            loadHorarios(idConsorcio,idLinea);
            loadDetail(idConsorcio,idLinea);
        }
    }

    private void loadHorarios(int idConsorcio, int idLinea) {
        //
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
