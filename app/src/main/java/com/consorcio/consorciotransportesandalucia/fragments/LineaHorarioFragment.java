package com.consorcio.consorciotransportesandalucia.fragments;

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
import com.consorcio.consorciotransportesandalucia.interfaces.LineaDetailInterface;
import com.consorcio.consorciotransportesandalucia.models.CapsuleHorariosLinea;
import com.consorcio.consorciotransportesandalucia.models.CapsuleLineaDetalle;
import com.consorcio.consorciotransportesandalucia.utils.ClienteApi;
import com.consorcio.consorciotransportesandalucia.utils.Util;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.consorcio.consorciotransportesandalucia.utils.Util.hasInternet;


public class LineaHorarioFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int idConsorcio;
    private int idLinea;

    private LineaDetailInterface mListener;
    private CapsuleLineaDetalle capsuleLineaDetalle;
    private CapsuleHorariosLinea capsuleHorariosLinea;
    Dialog progressDialog;

    public LineaHorarioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    // TODO: Rename and change types and number of parameters
    public static LineaHorarioFragment newInstance(int idConsorcio, int idLinea) {
        LineaHorarioFragment fragment = new LineaHorarioFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_linea_horario, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        capsuleLineaDetalle = mListener.getCapsuleLineaDetail();
        idConsorcio = mListener.getConsorcioId();
        idLinea = mListener.getLineaId();
        loadHorarios();
    }

    private void loadHorarios() {
        if (hasInternet(getContext())){
            //Activamos el progress
            progressDialog = ProgressDialog.show(getContext(), "", getResources().getString(R.string.progress_lineas), true);
            ClienteApi clienteApi = new ClienteApi();
            Map<String,String> map = new HashMap<>();
            map.put("linea",String.valueOf(idLinea));
            clienteApi.getHorariosLinea(map, idConsorcio, new Callback<CapsuleHorariosLinea>() {
                @Override
                public void onResponse(Call<CapsuleHorariosLinea> call, Response<CapsuleHorariosLinea> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()){
                        capsuleHorariosLinea = response.body();
                    }
                }

                @Override
                public void onFailure(Call<CapsuleHorariosLinea> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LineaDetailInterface) {
            mListener = (LineaDetailInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListenerLineaHorario");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
