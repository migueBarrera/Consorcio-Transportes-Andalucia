package com.consorcio.consorciotransportesandalucia.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.consorcio.consorciotransportesandalucia.R;
import com.consorcio.consorciotransportesandalucia.adapters.PuntoVentaInfoWindowAdapter;
import com.consorcio.consorciotransportesandalucia.models.CapsuleParadas;
import com.consorcio.consorciotransportesandalucia.models.CapsulePuntosVenta;
import com.consorcio.consorciotransportesandalucia.models.MarkerInfo;
import com.consorcio.consorciotransportesandalucia.models.Parada;
import com.consorcio.consorciotransportesandalucia.models.PuntosVenta;
import com.consorcio.consorciotransportesandalucia.renders.ParadasCustomClusterRenderer;
import com.consorcio.consorciotransportesandalucia.renders.PuntosVentaCustomClusterRenderer;
import com.consorcio.consorciotransportesandalucia.utils.ClienteApi;
import com.consorcio.consorciotransportesandalucia.utils.Const;
import com.consorcio.consorciotransportesandalucia.utils.HeadersHelpers;
import com.consorcio.consorciotransportesandalucia.utils.SharedPreferencesUtil;
import com.consorcio.consorciotransportesandalucia.utils.Util;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.google.maps.android.clustering.ClusterManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PuntosDeVentasFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private GoogleMap mMap;
    private ClusterManager<PuntosVenta> mClusterManager;
    Dialog progressDialog;

    public PuntosDeVentasFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PuntosDeVentasFragment newInstance(String param1, String param2) {
        PuntosDeVentasFragment fragment = new PuntosDeVentasFragment();
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
        View view = inflater.inflate(R.layout.fragment_puntos_de_ventas, container, false);

        //Add support to map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapFragment.getView().setClickable(true);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadPuntosVenta();
    }

    private void loadPuntosVenta() {
        if (Util.hasInternet(getContext())){
            //Activamos el progress
            progressDialog = ProgressDialog.show(getContext(), "", getResources().getString(R.string.progress_puntos_ventas), true);
            ClienteApi miClienteApi = new ClienteApi();
            int idConsorcio = SharedPreferencesUtil.getInt(getActivity(), Const.SHAREDKEYS.ID_CONSORCIO);
            miClienteApi.getPuntosVenta(HeadersHelpers.getHeaders(), idConsorcio, new Callback<CapsulePuntosVenta>() {
                @Override
                public void onResponse(Call<CapsulePuntosVenta> call, Response<CapsulePuntosVenta> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()){
                        CapsulePuntosVenta capsulePuntosVenta = response.body();
                        setDataToView(capsulePuntosVenta);
                    }
                }

                @Override
                public void onFailure(Call<CapsulePuntosVenta> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        }
    }

    private void setDataToView(CapsulePuntosVenta capsulePuntosVenta) {
        for (PuntosVenta puntosVenta: capsulePuntosVenta.getPuntosVenta()) {
            mClusterManager.addItem(puntosVenta);
        }

        // Position the map.
        mMap.moveCamera(Util.getCamaraUpdate(getActivity()));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
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
        mClusterManager = new ClusterManager<PuntosVenta>(getContext(), mMap);


// Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

        mMap.setInfoWindowAdapter(new PuntoVentaInfoWindowAdapter(getActivity()));
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String markerInfoJSON = marker.getSnippet();
                MarkerInfo markerInfo = new Gson().fromJson(markerInfoJSON,MarkerInfo.class);
                Util.goToMap(getActivity(),markerInfo.getPos());
            }
        });
        //Render Map
        final PuntosVentaCustomClusterRenderer renderer = new PuntosVentaCustomClusterRenderer(getContext(), mMap, mClusterManager);
        mClusterManager.setRenderer(renderer);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
