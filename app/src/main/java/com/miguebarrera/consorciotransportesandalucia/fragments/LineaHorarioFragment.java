package com.miguebarrera.consorciotransportesandalucia.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miguebarrera.consorciotransportesandalucia.R;
import com.miguebarrera.consorciotransportesandalucia.interfaces.LineaDetailInterface;
import com.miguebarrera.consorciotransportesandalucia.models.CapsuleHorariosLinea;
import com.miguebarrera.consorciotransportesandalucia.models.CapsuleLineaDetalle;
import com.miguebarrera.consorciotransportesandalucia.utils.ClienteApi;
import com.miguebarrera.consorciotransportesandalucia.utils.Util;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.miguebarrera.consorciotransportesandalucia.utils.Util.hasInternet;


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
        View view = inflater.inflate(R.layout.fragment_linea_horario, container, false);

        return view;
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
                        if(capsuleHorariosLinea!=null)
                            setDataToView(capsuleHorariosLinea);
                        else
                            setDataToView(null);
                    }
                }

                @Override
                public void onFailure(Call<CapsuleHorariosLinea> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        }
    }

    private void setDataToView(CapsuleHorariosLinea capsuleHorariosLinea) {
        if (capsuleHorariosLinea != null){

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
