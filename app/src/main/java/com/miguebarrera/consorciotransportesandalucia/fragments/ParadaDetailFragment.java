package com.miguebarrera.consorciotransportesandalucia.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miguebarrera.consorciotransportesandalucia.R;
import com.miguebarrera.consorciotransportesandalucia.interfaces.ParadaDetailInterface;
import com.miguebarrera.consorciotransportesandalucia.models.Parada;
import com.miguebarrera.consorciotransportesandalucia.utils.ClienteApi;
import com.miguebarrera.consorciotransportesandalucia.utils.Const;
import com.miguebarrera.consorciotransportesandalucia.utils.SharedPreferencesUtil;
import com.miguebarrera.consorciotransportesandalucia.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    @BindView(R.id.parada_detail_view_correspondecia_content)
    TextView viewCorrespondeciaContent;
    @BindView(R.id.parada_detail_municipio)
    TextView viewMunicipio;
    @BindView(R.id.parada_detail_nucleo)
    TextView viewNucleo;
    @BindView(R.id.parada_detail_ir_a_mapa)
    TextView viewIrAParada;

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
        View view = inflater.inflate(R.layout.fragment_parada_detail, container, false);
        ButterKnife.bind(this,view);
        return view;
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
        if (parada != null){
            setCorrespondencias();
            setMunicipioYNucleo();
            mListener.setTitleToolbar(parada.getNombre());
        }else {
            viewCorrespondeciaContent.setText("No se encuentran correspondecias de esta parada");
            viewNucleo.setText("desconocido");
            viewMunicipio.setText("desconocido");
            viewIrAParada.setVisibility(View.GONE);
        }
    }

    private void setMunicipioYNucleo() {
        viewMunicipio.setText(" " + parada.getMunicipio());
        viewNucleo.setText(" " + parada.getNucleo());
    }

    private void setCorrespondencias() {
        String correspondecias = parada.getCorrespondecias();
        //Preparamos correspondecias
        if (correspondecias.contains("Correspondencia con:"))
            correspondecias = correspondecias.replace("Correspondencia con:","").replace(","," , ");

        viewCorrespondeciaContent.setText(correspondecias);
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

    @OnClick(R.id.parada_detail_ir_a_mapa)
    public void clikcIrAMapa(){
        Util.goToMap(getActivity(),parada.getPosition());
    }
}
