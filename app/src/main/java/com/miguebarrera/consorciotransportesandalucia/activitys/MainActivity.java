package com.miguebarrera.consorciotransportesandalucia.activitys;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.miguebarrera.consorciotransportesandalucia.R;
import com.miguebarrera.consorciotransportesandalucia.fragments.CentroAtencionFragment;
import com.miguebarrera.consorciotransportesandalucia.fragments.HomeFragment;
import com.miguebarrera.consorciotransportesandalucia.fragments.LineasFragment;
import com.miguebarrera.consorciotransportesandalucia.fragments.LineasOrigenDestinoFragment;
import com.miguebarrera.consorciotransportesandalucia.fragments.MiConsorcioFragment;
import com.miguebarrera.consorciotransportesandalucia.fragments.ParadasFragment;
import com.miguebarrera.consorciotransportesandalucia.fragments.PuntosDeVentasFragment;
import com.miguebarrera.consorciotransportesandalucia.fragments.SaldoFragment;
import com.miguebarrera.consorciotransportesandalucia.fragments.TarifaFragment;
import com.miguebarrera.consorciotransportesandalucia.models.Consorcio;
import com.miguebarrera.consorciotransportesandalucia.utils.Const;
import com.miguebarrera.consorciotransportesandalucia.utils.MessageUtil;
import com.miguebarrera.consorciotransportesandalucia.utils.SharedPreferencesUtil;
import com.miguebarrera.consorciotransportesandalucia.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;

    private SearchView searchView;


    Activity parentActivity;
    String lastFragment = "";
    Fragment fragment = null;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        parentActivity = this;


        //Cargamos el Welcome si es el 1 inicio o no tiene consorcio
        if(SharedPreferencesUtil.getBoolean(this,Const.APP.IS_FIRST_TIME_LAUNCH,true) || !SharedPreferencesUtil.getBoolean(this,Const.APP.HAS_CONSORCIO,false)){
            startActivity(new Intent(parentActivity, WelcomeActivity.class));
            finish();
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        if (!Util.checkPermissions(parentActivity))
            Util.requestPermissions(parentActivity);

       init();
    }

    private void init(){
         Fragment fragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment).commit();
        //Set title to toolbar
        setTitleToToolbar(getString(R.string.title_home));
        navigationView.setCheckedItem(R.id.nav_home);
        supportInvalidateOptionsMenu();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();

        if (lastFragment.equals(LineasFragment.class.getName()) || lastFragment.equals(LineasOrigenDestinoFragment.class.getName())){
            configureSearchView(menu);
        }else{
            menu.findItem(R.id.action_search).setVisible(false);
            searchView.setOnQueryTextListener(null);
        }


        return true;
    }

    private void configureSearchView(Menu menu) {
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (fragment instanceof  LineasFragment)
                    ((LineasFragment)fragment).filter(query);
                else
                    if (fragment instanceof LineasOrigenDestinoFragment)
                        ((LineasOrigenDestinoFragment)fragment).filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (fragment instanceof  LineasFragment)
                    ((LineasFragment)fragment).filter(query);
                else
                if (fragment instanceof LineasOrigenDestinoFragment)
                    ((LineasOrigenDestinoFragment)fragment).filter(query);
                return false;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        String title  = "";
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_paradas) {
            fragment = new ParadasFragment();
            title = getString(R.string.title_activity_paradas);
        } else if (id == R.id.nav_horarios_origen){
            fragment = new LineasOrigenDestinoFragment();
            title = getString(R.string.title_activity_lineas_origen_destino);
        }else if (id == R.id.nav_horarios_lineas) {
            fragment = new LineasFragment();
            title = getString(R.string.title_activity_lineas);
        } else if (id == R.id.nav_puntos_venta) {
            fragment = new PuntosDeVentasFragment();
            Consorcio consorcio = (Consorcio) SharedPreferencesUtil.getObject(parentActivity,Const.SHAREDKEYS.MY_CONSORCIO, Consorcio.class);
            title = getString(R.string.title_activity_puntos_venta) + " "+  consorcio.getProvincia();
        } else if (id == R.id.nav_saldo) {
            fragment = new SaldoFragment();
            title = getString(R.string.title_activity_saldo);
        } else if (id == R.id.nav_consorcio_detail) {
            fragment = new MiConsorcioFragment();
            title = getString(R.string.title_activity_consorcio_detail);
        } else if (id == R.id.nav_centro_atencion) {
            fragment = new CentroAtencionFragment();
            title = getString(R.string.title_centro_atencion);
        }else if (id == R.id.nav_home){
            fragment = new HomeFragment();
            title = getString(R.string.title_home);
        }else if (id == R.id.nav_tarifas){
            fragment = new TarifaFragment();
            title = getString(R.string.title_tarifas);
        }

        if (fragment != null){
            //Solo iniciamos las transaccion si es diferente al ultimo fragment
            if (!lastFragment.equals(fragment.getClass().getName())){
                lastFragment = fragment.getClass().getName();
                //Begin transaction
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment).commit();

                //Set title to toolbar
                setTitleToToolbar(title);

                supportInvalidateOptionsMenu();
            }
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    private void setTitleToToolbar(String title){
        getSupportActionBar().setTitle(title);
    }

}
