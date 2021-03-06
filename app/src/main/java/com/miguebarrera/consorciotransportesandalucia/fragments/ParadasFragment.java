package com.miguebarrera.consorciotransportesandalucia.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miguebarrera.consorciotransportesandalucia.R;
import com.miguebarrera.consorciotransportesandalucia.activitys.ParadaDetailActivity;
import com.miguebarrera.consorciotransportesandalucia.adapters.ParadasInfoWindowAdapter;
import com.miguebarrera.consorciotransportesandalucia.models.CapsuleParadas;
import com.miguebarrera.consorciotransportesandalucia.models.MarkerInfo;
import com.miguebarrera.consorciotransportesandalucia.models.Parada;
import com.miguebarrera.consorciotransportesandalucia.renders.ParadasCustomClusterRenderer;
import com.miguebarrera.consorciotransportesandalucia.utils.ClienteApi;
import com.miguebarrera.consorciotransportesandalucia.utils.Const;
import com.miguebarrera.consorciotransportesandalucia.utils.SharedPreferencesUtil;
import com.miguebarrera.consorciotransportesandalucia.utils.Util;
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


public class ParadasFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private GoogleMap mMap;
    private ClusterManager<Parada> mClusterManager;
    Dialog progressDialog;

    public ParadasFragment() {
        // Required empty public constructor
    }



    public static ParadasFragment newInstance(String param1, String param2) {
        ParadasFragment fragment = new ParadasFragment();
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
        View view = inflater.inflate(R.layout.fragment_paradas, container, false);

        //Add support to map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapFragment.getView().setClickable(true);

        return view;


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
    public void onStart() {
        super.onStart();
        loadParadas();
    }

    private void loadParadas() {
        if (Util.hasInternet(getContext())){
            //Activamos el progress
            progressDialog = ProgressDialog.show(getContext(), "", getResources().getString(R.string.progress_paradas), true);
            ClienteApi miClienteApi = new ClienteApi();
            int idConsorcio = SharedPreferencesUtil.getInt(getActivity(), Const.SHAREDKEYS.ID_CONSORCIO);
            miClienteApi.getParadas(null, idConsorcio, new Callback<CapsuleParadas>() {
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


        // Position the map.
        mMap.moveCamera(Util.getCamaraUpdate(getActivity()));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<Parada>(getContext(), mMap);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraIdleListener(mClusterManager);
        //mMap.setOnMarkerClickListener(mClusterManager);

        mMap.setInfoWindowAdapter(new ParadasInfoWindowAdapter(getActivity()));
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String markerInfoJSON = marker.getSnippet();
                MarkerInfo markerInfo = new Gson().fromJson(markerInfoJSON,MarkerInfo.class);
                SharedPreferencesUtil.setInt(getActivity(),Const.SHAREDKEYS.ID_PARADA,Integer.valueOf(markerInfo.getIdParada()));
                Intent i = new Intent(getContext(), ParadaDetailActivity.class);
                startActivity(i);
            }
        });

        //Render Map
        final ParadasCustomClusterRenderer renderer = new ParadasCustomClusterRenderer(getContext(), mMap, mClusterManager);
        mClusterManager.setRenderer(renderer);
    }

    private void setDataToView(CapsuleParadas capsuleParadas){

        addItemsToMap(capsuleParadas);

        // Position the map.
        mMap.moveCamera(Util.getCamaraUpdate(getActivity(),13));
    }

    private void addItemsToMap(CapsuleParadas capsuleParadas) {
        for (Parada parada: capsuleParadas.getParadas()) {
            mClusterManager.addItem(parada);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
