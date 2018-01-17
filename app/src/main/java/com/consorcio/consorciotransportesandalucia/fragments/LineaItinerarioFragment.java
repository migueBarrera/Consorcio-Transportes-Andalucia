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

import com.consorcio.consorciotransportesandalucia.R;
import com.consorcio.consorciotransportesandalucia.interfaces.LineaDetailInterface;
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

        //Add support to map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapFragment.getView().setClickable(true);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
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
                        CapsuleParadas capsuleParadas = response.body();
                        setDataToView(capsuleParadas);
                    }
                }

                @Override
                public void onFailure(Call<CapsuleParadas> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        }
    }

    private void setDataToView(CapsuleParadas capsuleParadas) {
        MarkerOptions markerOptions = new MarkerOptions();
        PolylineOptions polylineOptions = new PolylineOptions()
                .width(5)
                .color(Color.RED);
        LatLng pos;

        for (Parada parada: capsuleParadas.getParadas()) {
            pos = parada.getPosition();
            polylineOptions.add(pos);
            markerOptions.position(pos);
            mMap.addMarker(markerOptions);
        }

        mMap.addPolyline(polylineOptions);
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
}
