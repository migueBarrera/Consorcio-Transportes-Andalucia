package com.consorcio.consorciotransportesandalucia.activitys;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.consorcio.consorciotransportesandalucia.R;
import com.consorcio.consorciotransportesandalucia.fragments.LineaHorarioFragment;
import com.consorcio.consorciotransportesandalucia.fragments.LineaInfoFragment;
import com.consorcio.consorciotransportesandalucia.fragments.LineaItinerarioFragment;
import com.consorcio.consorciotransportesandalucia.models.CapsuleLineaDetalle;
import com.consorcio.consorciotransportesandalucia.models.CapsuleLineas;
import com.consorcio.consorciotransportesandalucia.utils.ClienteApi;
import com.consorcio.consorciotransportesandalucia.utils.Const;
import com.consorcio.consorciotransportesandalucia.utils.SharedPreferencesUtil;
import com.consorcio.consorciotransportesandalucia.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LineaDetailActivity extends AppCompatActivity implements LineaHorarioFragment.OnFragmentInteractionListenerLineaHorario {

    @BindView(R.id.navigation_linea)
    BottomNavigationView navigation;
    ClienteApi clienteApi;
    private CapsuleLineaDetalle capsuleLineaDetalle;
    private int idConsorcio, idLinea;
    Dialog progressDialog;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linea_detail);
        ButterKnife.bind(this);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportActionBar().hide();
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
                    //Inicializamos el MapsFragemnts al inicio
                    Fragment lineaInfoFragment= new LineaInfoFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, lineaInfoFragment).commitAllowingStateLoss();
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
                    return true;
                case  R.id.navigation_linea_itinerary:
                    fragment = new LineaItinerarioFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, fragment).commitAllowingStateLoss();
                    disableScroll();
                    nestedScrollView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            return false;
                        }
                    });
                    return true;
                case R.id.navigation_linea_info:
                    fragment = new LineaInfoFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, fragment).commitAllowingStateLoss();

                    //nestedScrollView.stopNestedScroll();
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

    private void disableScroll(){
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        CoordinatorLayout.LayoutParams params =
                (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(AppBarLayout appBarLayout) {
                return false;
            }
        });
        params.setBehavior(behavior);
    }
}
