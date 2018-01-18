package com.consorcio.consorciotransportesandalucia.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.consorcio.consorciotransportesandalucia.R;
import com.consorcio.consorciotransportesandalucia.interfaces.LineaDetailInterface;
import com.consorcio.consorciotransportesandalucia.models.CapsuleLineaDetalle;
import com.consorcio.consorciotransportesandalucia.models.CapsuleParadas;
import com.consorcio.consorciotransportesandalucia.models.Parada;
import com.consorcio.consorciotransportesandalucia.utils.ClienteApi;
import com.consorcio.consorciotransportesandalucia.utils.Const;
import com.consorcio.consorciotransportesandalucia.utils.SharedPreferencesUtil;
import com.consorcio.consorciotransportesandalucia.utils.Util;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LineaItinerarioFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LineaDetailInterface mListener;

    private GoogleMap mMap;
    private ClusterManager<Parada> mClusterManager;
    Dialog progressDialog;
    CapsuleParadas capsuleParadas;
    CapsuleLineaDetalle capsuleLineaDetalle;
    @BindView(R.id.linea_detail_radio_button_ida)
    RadioButton radioButtonIda;
    @BindView(R.id.linea_detail_radio_button_vuelta)
    RadioButton radioButtonVuelta;

    private View.OnClickListener listenerRadioButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Is the button now checked?
            boolean checked = ((RadioButton) view).isChecked();

            // Check which radio button was clicked
            switch(view.getId()) {
                case R.id.linea_detail_radio_button_ida:
                    if (checked){
                        cleanMap();
                        drawIda();
                        drawParadas(true);
                    }
                    break;
                case R.id.linea_detail_radio_button_vuelta:
                    if (checked){
                        cleanMap();
                        drawVuelta();
                        drawParadas(false);
                    }
                    break;
            }
        }
    };

    public LineaItinerarioFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static LineaItinerarioFragment newInstance(String param1, String param2) {
        LineaItinerarioFragment fragment = new LineaItinerarioFragment();
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
        View v = inflater.inflate(R.layout.fragment_linea_itinerario, container, false);
        ButterKnife.bind(this,v);

        //Add support to map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapFragment.getView().setClickable(true);

        //Add Listenes to RadioButon
        radioButtonIda.setOnClickListener(listenerRadioButton);
        radioButtonVuelta.setOnClickListener(listenerRadioButton);


        return v;
    }

    private void configureRadioButtons() {
        if (capsuleLineaDetalle.getTieneIda() == 0)
            radioButtonIda.setVisibility(View.GONE);

        if (capsuleLineaDetalle.getTieneVuelta() == 0)
            radioButtonVuelta.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        capsuleLineaDetalle = mListener.getCapsuleLineaDetail();
        configureRadioButtons();
        loadItinerary();

    }

    private void loadItinerary() {
        if (Util.hasInternet(getContext())){
            //Activamos el progress
            progressDialog = ProgressDialog.show(getContext(), "", getResources().getString(R.string.progress_lineas), true);
            ClienteApi clienteApi = new ClienteApi();
            int idConsorcio = SharedPreferencesUtil.getInt(getActivity(), Const.SHAREDKEYS.ID_CONSORCIO);
            int idLinea = SharedPreferencesUtil.getInt(getActivity(), Const.SHAREDKEYS.ID_LINEA);
            clienteApi.getParadasDeLinea(null, idConsorcio, idLinea, new Callback<CapsuleParadas>() {
                @Override
                public void onResponse(Call<CapsuleParadas> call, Response<CapsuleParadas> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()){
                        capsuleParadas = response.body();
                        setDataToView();
                        Util.log(capsuleParadas);
                    }
                }

                @Override
                public void onFailure(Call<CapsuleParadas> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        }
    }

    private void setDataToView() {
        if (capsuleLineaDetalle.getTieneIda() == 1){
            drawIda();
            drawParadas(true);
            radioButtonIda.setChecked(true);
        }else {
            if (capsuleLineaDetalle.getTieneVuelta() == 1){
                drawVuelta();
                drawParadas(false);
                radioButtonVuelta.setChecked(true);
            }
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.378176, -6.001057), 12));

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LineaDetailInterface) {
            mListener = (LineaDetailInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMyLocationEnabled(true);

        //TODO enviar la posicion del usuario
        // Position the map.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.378176, -6.001057), 8));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<Parada>(getContext(), mMap);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
    }

    private void cleanMap(){
        mMap.clear();
    }

    private void drawIda(){
        //IDA
        if (capsuleLineaDetalle.getTieneIda() == 1){
            PolylineOptions polylineOptions = new PolylineOptions()
                    .width(5)
                    .color(Color.RED);

            for (ArrayList<String> route:
                    capsuleLineaDetalle.getPolilineaIda()) {
                String[] routes = route.get(0).split(",");
                polylineOptions.add(new LatLng(Double.valueOf(routes[0]),Double.valueOf(routes[1])));
            }
            mMap.addPolyline(polylineOptions);
        }
    }

    private void drawVuelta(){
        //Vuelta
        if (capsuleLineaDetalle.getTieneVuelta() == 1){
            PolylineOptions polylineOptions = new PolylineOptions()
                    .width(5)
                    .color(Color.BLUE);

            for (ArrayList<String> route:
                    capsuleLineaDetalle.getPolilineaVuelta()) {
                String[] routes = route.get(0).split(",");
                polylineOptions.add(new LatLng(Double.valueOf(routes[0]),Double.valueOf(routes[1])));
            }

            mMap.addPolyline(polylineOptions);
        }
    }

    private void drawParadas(boolean sentido){
        //PARADAS
        MarkerOptions markerOptions = new MarkerOptions();
        LatLng pos;

        for (Parada parada: capsuleParadas.getParadas()) {
            if (sentido){
                if (parada.getSentido().equals("1")){
                    pos = parada.getPosition();
                    markerOptions.position(pos);
                    mMap.addMarker(markerOptions);
                }
            }else {
                if (parada.getSentido().equals("2")){
                    pos = parada.getPosition();
                    markerOptions.position(pos);
                    mMap.addMarker(markerOptions);
                }
            }
        }
    }
}
