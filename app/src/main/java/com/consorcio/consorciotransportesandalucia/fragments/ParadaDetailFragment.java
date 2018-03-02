package com.consorcio.consorciotransportesandalucia.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.consorcio.consorciotransportesandalucia.R;
import com.consorcio.consorciotransportesandalucia.interfaces.ParadaDetailInterface;
import com.consorcio.consorciotransportesandalucia.models.Parada;
import com.consorcio.consorciotransportesandalucia.utils.ClienteApi;
import com.consorcio.consorciotransportesandalucia.utils.Const;
import com.consorcio.consorciotransportesandalucia.utils.SharedPreferencesUtil;
import com.consorcio.consorciotransportesandalucia.utils.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParadaDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ParadaDetailInterface mListener;
    private int idParada;
    private Parada parada;

    public ParadaDetailFragment() {
        // Required empty public constructor
    }

    public static ParadaDetailFragment newInstance(String param1, String param2) {
        ParadaDetailFragment fragment = new ParadaDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Parada getParada(){
        return parada;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        idParada = mListener.getParadaId();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parada_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }



    private void loadData() {
        if (Util.hasInternet(getContext())){
            ClienteApi clienteApi = new ClienteApi();
            int idConsorcio = SharedPreferencesUtil.getInt(getActivity(), Const.SHAREDKEYS.ID_CONSORCIO);
            clienteApi.getParadaDetail(null, idConsorcio, idParada, new Callback<Parada>() {
                @Override
                public void onResponse(Call<Parada> call, Response<Parada> response) {
                    if (response.isSuccessful()){
                        parada = response.body();
                        setDataToView(parada);
                    }else
                        setDataToView(null);
                }

                @Override
                public void onFailure(Call<Parada> call, Throwable t) {
                    setDataToView(null);
                }
            });
        }
    }

    private void setDataToView(Parada parada) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ParadaDetailInterface) {
            mListener = (ParadaDetailInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ParadaDetailInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
