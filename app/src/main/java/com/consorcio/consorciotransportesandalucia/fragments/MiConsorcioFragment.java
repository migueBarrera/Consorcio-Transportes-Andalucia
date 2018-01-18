package com.consorcio.consorciotransportesandalucia.fragments;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.consorcio.consorciotransportesandalucia.R;
import com.consorcio.consorciotransportesandalucia.models.Consorcio;
import com.consorcio.consorciotransportesandalucia.utils.ClienteApi;
import com.consorcio.consorciotransportesandalucia.utils.Const;
import com.consorcio.consorciotransportesandalucia.utils.SharedPreferencesUtil;
import com.consorcio.consorciotransportesandalucia.utils.Util;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class MiConsorcioFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Consorcio consorcio;
    GoogleMap consorcioMap;
    LatLng consorcioPos;
    @BindView(R.id.fragment_mi_consorcio_consorcio)
    RelativeLayout relativeLayoutMiConsorcio;

    public MiConsorcioFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MiConsorcioFragment newInstance(String param1, String param2) {
        MiConsorcioFragment fragment = new MiConsorcioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mi_consorcio, container, false);
        ButterKnife.bind(this,v);

        //Add support to map fragment
        MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.consorcio_map);
        mapFragment.getMapAsync(this);
        mapFragment.getView().setClickable(true);

        addListener();

        return v;
    }

    private void addListener() {
        relativeLayoutMiConsorcio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Mostrar un listado con los consorcios disponibles y al selecionar uno cargar la nueva info

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        loadDataConsorcio();
    }

    private void loadDataConsorcio() {
        if (Util.hasInternet(getContext())){
            ClienteApi clienteApi = new ClienteApi();
            int idConsorcio = SharedPreferencesUtil.getInt(getActivity(), Const.SHAREDKEYS.ID_CONSORCIO);
            clienteApi.getConsorcioDetail(null, idConsorcio, new Callback<Consorcio>() {
                @Override
                public void onResponse(Call<Consorcio> call, Response<Consorcio> response) {
                    if (response.isSuccessful()){
                        consorcio = response.body();
                        setDataToView(consorcio);
                        new GeocodeAsyncTask().execute();
                    }
                }

                @Override
                public void onFailure(Call<Consorcio> call, Throwable t) {

                }
            });
        }
    }

    private void setDataToView(Consorcio consorcio) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        consorcioMap = googleMap;
        googleMap.clear();
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                        String uri = "http://maps.google.com/maps?daddr=" + consorcioPos.latitude + "," + consorcioPos.longitude;
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        mapIntent.setPackage("com.google.android.apps.maps");
                        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                            startActivity(mapIntent);
                        }
            }
        });

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
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = null;


                String name = consorcio.getDireccion();
                try {
                    String newName = name.split(",")[0];
                    Util.log(newName);
                    addresses = geocoder.getFromLocationName(newName, 1);
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
