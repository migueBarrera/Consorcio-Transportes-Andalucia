package com.miguebarrera.consorciotransportesandalucia.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miguebarrera.consorciotransportesandalucia.R;
import com.miguebarrera.consorciotransportesandalucia.adapters.TarifasInterurbanasAdapter;
import com.miguebarrera.consorciotransportesandalucia.adapters.TarifasUrbanasAdapter;
import com.miguebarrera.consorciotransportesandalucia.models.CapsuleTarifasInterurbanas;
import com.miguebarrera.consorciotransportesandalucia.models.CapsuleTarifasUrbanas;
import com.miguebarrera.consorciotransportesandalucia.models.TarifasInterurbana;
import com.miguebarrera.consorciotransportesandalucia.models.TarifasUrbana;
import com.miguebarrera.consorciotransportesandalucia.utils.ClienteApi;
import com.miguebarrera.consorciotransportesandalucia.utils.Const;
import com.miguebarrera.consorciotransportesandalucia.utils.HeadersHelpers;
import com.miguebarrera.consorciotransportesandalucia.utils.LinearLayoutManagerNoScroll;
import com.miguebarrera.consorciotransportesandalucia.utils.MessageUtil;
import com.miguebarrera.consorciotransportesandalucia.utils.SharedPreferencesUtil;
import com.miguebarrera.consorciotransportesandalucia.utils.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TarifaFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    ClienteApi clienteApi;
    int idConsorcio;
    @BindView(R.id.recycler_view_tarifa)
    RecyclerView recyclerViewInterurbanas;
    @BindView(R.id.recycler_view_tarifa_urbana)
    RecyclerView recyclerViewUrbanas;
    @BindView(R.id.fragment_tarifa_title_int)
    TextView titleInterurbana;
    @BindView(R.id.fragment_tarifa_title_urb)
    TextView titleUrbana;
    Dialog progressDialog;

    public TarifaFragment() {
        // Required empty public constructor
    }

    public static TarifaFragment newInstance(String param1, String param2) {
        TarifaFragment fragment = new TarifaFragment();
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

        clienteApi = new ClienteApi();
        idConsorcio = SharedPreferencesUtil.getInt(getActivity(), Const.SHAREDKEYS.ID_CONSORCIO);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tarifa, container, false);
        ButterKnife.bind(this,view);
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
        loadTarifasInterurbanas();
        loadTarifasUrbanas();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void loadTarifasInterurbanas(){
        if (Util.hasInternet(getContext())){
            //Activamos el progress
            progressDialog = ProgressDialog.show(getContext(), "", getResources().getString(R.string.progress_tarifas), true);
            clienteApi.getTarifasInterurbana(HeadersHelpers.getHeaders(), idConsorcio, new Callback<CapsuleTarifasInterurbanas>() {
                @Override
                public void onResponse(Call<CapsuleTarifasInterurbanas> call, Response<CapsuleTarifasInterurbanas> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()){
                        setTarifasInterurbanas(response.body().getTarifasInterurbanas());
                    }else
                        setTarifasUrbanas(null);
                }

                @Override
                public void onFailure(Call<CapsuleTarifasInterurbanas> call, Throwable t) {
                    progressDialog.dismiss();
                    setTarifasInterurbanas(null);
                    MessageUtil.showNoServerWork(getActivity());
                }
            });
        }
    }


    private void setTarifasInterurbanas(ArrayList<TarifasInterurbana> list){
        if (list != null && list.size() != 0){
            titleInterurbana.setVisibility(View.VISIBLE);
            recyclerViewInterurbanas.setVisibility(View.VISIBLE);
            TarifasInterurbanasAdapter adapter = new TarifasInterurbanasAdapter(list.toArray(new TarifasInterurbana[0]));
            recyclerViewInterurbanas.setHasFixedSize(true);
            LinearLayoutManagerNoScroll llm = new LinearLayoutManagerNoScroll(getContext());
            llm.setScrollEnabled(false);
            recyclerViewInterurbanas.setLayoutManager(llm);
            recyclerViewInterurbanas.setRecycledViewPool(new RecyclerView.RecycledViewPool());
            recyclerViewInterurbanas.setItemAnimator(new DefaultItemAnimator());
            recyclerViewInterurbanas.setAdapter(adapter);
        }else {
            titleInterurbana.setVisibility(View.GONE);
            recyclerViewInterurbanas.setVisibility(View.GONE);
        }
    }

    private void setTarifasUrbanas(ArrayList<TarifasUrbana> list){
        if (list != null && list.size() != 0){
            titleUrbana.setVisibility(View.VISIBLE);
            recyclerViewUrbanas.setVisibility(View.VISIBLE);
            TarifasUrbanasAdapter adapter = new TarifasUrbanasAdapter(list.toArray(new TarifasUrbana[0]));
            recyclerViewUrbanas.setHasFixedSize(true);
            LinearLayoutManagerNoScroll llm = new LinearLayoutManagerNoScroll(getContext());//new LinearLayoutManager(getContext());
            llm.setScrollEnabled(false);
            recyclerViewUrbanas.setLayoutManager(llm);
            recyclerViewUrbanas.setRecycledViewPool(new RecyclerView.RecycledViewPool());
            recyclerViewUrbanas.setItemAnimator(new DefaultItemAnimator());
            recyclerViewUrbanas.setAdapter(adapter);
        }else {
            titleUrbana.setVisibility(View.GONE);
            recyclerViewUrbanas.setVisibility(View.GONE);
        }
    }

    private void loadTarifasUrbanas(){
        if (Util.hasInternet(getContext())){
            clienteApi.getTarifasUrbanas(HeadersHelpers.getHeaders(), idConsorcio, new Callback<CapsuleTarifasUrbanas>() {
                @Override
                public void onResponse(Call<CapsuleTarifasUrbanas> call, Response<CapsuleTarifasUrbanas> response) {
                    if (response.isSuccessful()){
                        setTarifasUrbanas(response.body().getTarifasUrbanas());
                    }else
                        setTarifasUrbanas(null);
                }

                @Override
                public void onFailure(Call<CapsuleTarifasUrbanas> call, Throwable t) {
                    setTarifasUrbanas(null);
                }
            });
        }
    }
}
