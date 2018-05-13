package com.miguebarrera.consorciotransportesandalucia.activitys;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.miguebarrera.consorciotransportesandalucia.R;
import com.miguebarrera.consorciotransportesandalucia.fragments.MiConsorcioFragment;
import com.miguebarrera.consorciotransportesandalucia.models.CapsuleConsorcio;
import com.miguebarrera.consorciotransportesandalucia.models.Consorcio;
import com.miguebarrera.consorciotransportesandalucia.utils.ClienteApi;
import com.miguebarrera.consorciotransportesandalucia.utils.Const;
import com.miguebarrera.consorciotransportesandalucia.utils.FontUtil;
import com.miguebarrera.consorciotransportesandalucia.utils.MessageUtil;
import com.miguebarrera.consorciotransportesandalucia.utils.SharedPreferencesUtil;
import com.miguebarrera.consorciotransportesandalucia.utils.Util;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ChooseConsorcioActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Consorcio consorcio;
    GoogleMap consorcioMap;
    LatLng consorcioPos;
    private ArrayList<Consorcio> listConsorcios;
    Dialog progressDialog;
    Activity parentActivity;
    @BindView(R.id.activity_consorcio_consorcio)
    RelativeLayout relativeLayoutMiConsorcio;
    @BindView(R.id.activity_consorcio_title)
    TextView textViewTitle;
    @BindView(R.id.activity_consorcio_subtitle)
    TextView textViewSubTitle;
    @BindView(R.id.activity_consorcio_fab)
    FloatingActionButton fab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.map_container)
    FrameLayout mapContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_consorcio);
        parentActivity = this;
        ButterKnife.bind(this);

        toolbar.setTitle("Elije tu consorcio");
        setSupportActionBar(toolbar);

        //Add support to map fragment
        MapFragment mapFragment = (MapFragment) this.getFragmentManager().findFragmentById(R.id.consorcio_map);
        mapFragment.getMapAsync(this);
        mapFragment.getView().setClickable(true);

        addListener();
        textViewTitle.setTypeface(FontUtil.getRegularFont(parentActivity));
        textViewSubTitle.setTypeface(FontUtil.getLightFont(parentActivity));
    }

    private void addListener() {
        relativeLayoutMiConsorcio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Con esto nos ahorramos volver a llamar al servicio si ya ha sido llamado anteriormente
                if (listConsorcios != null && listConsorcios.size() != 0)
                    showDialogConsorcios();
                else
                    loadConsorcios();

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferencesUtil.setBoolean(parentActivity, Const.APP.HAS_CONSORCIO,true);
                startActivity(new Intent(parentActivity, MainActivity.class));
                finish();
            }
        });
    }

    private void loadConsorcios(){
        if (Util.hasInternet(this)){
            //Activamos el progress
            progressDialog = ProgressDialog.show(this, "", getResources().getString(R.string.progress_lineas), true);
            ClienteApi clienteApi = new ClienteApi();
            clienteApi.getConsorcios(null, 0, new Callback<CapsuleConsorcio>() {
                @Override
                public void onResponse(Call<CapsuleConsorcio> call, Response<CapsuleConsorcio> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()){
                        listConsorcios = response.body().getConsorcios();
                        showDialogConsorcios();
                    }
                }

                @Override
                public void onFailure(Call<CapsuleConsorcio> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        }
    }

    private void showDialogConsorcios(){
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(parentActivity);
        final LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_list_consorcio, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();

        ListView listConsorcio = (ListView) dialogView.findViewById(R.id.dialog_list_consorcio_list);
        listConsorcio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Consorcio consorcio = (Consorcio) listConsorcios.get(i);
                b.dismiss();

                SharedPreferencesUtil.setInt(parentActivity, Const.SHAREDKEYS.ID_CONSORCIO,Integer.valueOf(consorcio.getIdConsorcio()));
                loadDataConsorcio();
            }
        });

        ArrayAdapter<Consorcio> adapter = new ArrayAdapter<Consorcio>(parentActivity,android.R.layout.simple_list_item_1, listConsorcios);
        listConsorcio.setAdapter(adapter);

        b.setCancelable(true);

        b.show();
    }

    private void loadDataConsorcio() {
        if (Util.hasInternet(this)){
            ClienteApi clienteApi = new ClienteApi();
            int idConsorcio = SharedPreferencesUtil.getInt(this, Const.SHAREDKEYS.ID_CONSORCIO);
            clienteApi.getConsorcioDetail(null, idConsorcio, new Callback<Consorcio>() {
                @Override
                public void onResponse(Call<Consorcio> call, Response<Consorcio> response) {
                    if (response.isSuccessful()){
                        consorcio = response.body();
                        Gson gson = new Gson();
                        SharedPreferencesUtil.setString(parentActivity,Const.SHAREDKEYS.MY_CONSORCIO,gson.toJson(consorcio,Consorcio.class));
                        setDataToView();
                        new GeocodeAsyncTask().execute();
                        //geocodeAsyncTask.execute();
                    }
                }

                @Override
                public void onFailure(Call<Consorcio> call, Throwable t) {

                }
            });
        }
    }

    private void setDataToView() {
        textViewTitle.setText(consorcio.getNombre());
        textViewSubTitle.setText(consorcio.getNombreCorto());

        fab.animate().alpha(1).setDuration(1000);
        mapContainer.animate().alpha(1).setDuration(1000);
        //fab.setAnimation(new AlphaAnimation());


        //todo click next page
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        consorcioMap = googleMap;
        googleMap.clear();
        /*googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                String uri = "http://maps.google.com/maps?daddr=" + consorcioPos.latitude + "," + consorcioPos.longitude;
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });*/

        if (consorcioPos!=null)
            setPosToMap();
    }

    private void setPosToMap(){
        MarkerOptions markerOptions = new MarkerOptions().position(consorcioPos);
        //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_b));
        consorcioMap.addMarker(markerOptions);

        consorcioMap.animateCamera(CameraUpdateFactory.newLatLngZoom(consorcioPos, 13));
    }


    class GeocodeAsyncTask extends AsyncTask<Void, Void, Address> {

        String errorMessage = "";

        @Override
        protected void onPreExecute() {
            //infoText.setVisibility(View.INVISIBLE);
            //rogressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Address doInBackground(Void ... none) {
            Geocoder geocoder = new Geocoder(parentActivity, Locale.getDefault());
            List<Address> addresses = null;


            String name = consorcio.getDireccion();
            Util.log(consorcio);
            try {
                name = name+ " , " + consorcio.getCiudad()+ " , España";
                addresses = geocoder.getFromLocationName(name, 1);
            } catch (IOException e) {
                errorMessage = "Service not available";
                Log.e(TAG, errorMessage, e);
            }

            if(addresses != null && addresses.size() > 0)
                return addresses.get(0);

            return null;
        }

        protected void onPostExecute(Address address) {
            if(address != null) {
                consorcioPos = new LatLng(address.getLatitude(),address.getLongitude());
                // progressBar.setVisibility(View.INVISIBLE);
                //infoText.setVisibility(View.VISIBLE);
                //infoText.setText(errorMessage);
                if (consorcioMap != null)
                    setPosToMap();
            }
        }
    }
}
