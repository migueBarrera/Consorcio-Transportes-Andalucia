package com.consorcio.consorciotransportesandalucia.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.consorcio.consorciotransportesandalucia.R;
import com.consorcio.consorciotransportesandalucia.activitys.LineaDetailActivity;
import com.consorcio.consorciotransportesandalucia.adapters.LineasAdapter;
import com.consorcio.consorciotransportesandalucia.adapters.LineasHorariosAdapter;
import com.consorcio.consorciotransportesandalucia.interfaces.RecyclerOnItemClickListener;
import com.consorcio.consorciotransportesandalucia.models.CapsuleLineasPorNucleo;
import com.consorcio.consorciotransportesandalucia.models.CapsuleMunicipio;
import com.consorcio.consorciotransportesandalucia.models.CapsuleNucleo;
import com.consorcio.consorciotransportesandalucia.models.Horario;
import com.consorcio.consorciotransportesandalucia.models.Municipio;
import com.consorcio.consorciotransportesandalucia.models.Nucleo;
import com.consorcio.consorciotransportesandalucia.utils.ClienteApi;
import com.consorcio.consorciotransportesandalucia.utils.Const;
import com.consorcio.consorciotransportesandalucia.utils.SharedPreferencesUtil;
import com.consorcio.consorciotransportesandalucia.utils.Util;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LineasOrigenDestinoFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.spinner_mun_origen)
    Spinner spinnerMunOrigen;
    @BindView(R.id.spinner_nuc_origen)
    Spinner spinnerNucOrigen;
    @BindView(R.id.spinner_mun_destino)
    Spinner spinnerMunDestino;
    @BindView(R.id.spinner_nuc_destino)
    Spinner spinnerNucDestino;
    ArrayList<Nucleo> listNucleos;
    ArrayList<Municipio> listMunicipios;
    @BindView(R.id.fragment_lineas_origen_destino_recycler_view)
    RecyclerView recyclerView;
    Dialog progressDialog;

    public LineasOrigenDestinoFragment() {
        // Required empty public constructor
    }

    public static LineasOrigenDestinoFragment newInstance(String param1, String param2) {
        LineasOrigenDestinoFragment fragment = new LineasOrigenDestinoFragment();
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
        View view = inflater.inflate(R.layout.fragment_lineas_origen_destino, container, false);
        ButterKnife.bind(this, view);

        loadData();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void loadData(){
        if (Util.hasInternet(getContext())){
            ClienteApi clienteApi = new ClienteApi();
            int idConsorcio = SharedPreferencesUtil.getInt(getActivity(), Const.SHAREDKEYS.ID_CONSORCIO);

            loadMunicipios(clienteApi,idConsorcio);
            loadNucleos(clienteApi,idConsorcio);
        }
    }

    private void loadMunicipios(ClienteApi clienteApi,int idConsorcio){
        clienteApi.getMunicipios(null, idConsorcio, new Callback<CapsuleMunicipio>() {
            @Override
            public void onResponse(Call<CapsuleMunicipio> call, Response<CapsuleMunicipio> response) {
                if (response.isSuccessful()){
                    listMunicipios = response.body().getMunicipios();
                    setMunicipiosToView(listMunicipios);
                }
            }

            @Override
            public void onFailure(Call<CapsuleMunicipio> call, Throwable t) {

            }
        });
    }

    private void  loadNucleos(ClienteApi clienteApi,int idConsorcio){
        clienteApi.getNucleos(null, idConsorcio, new Callback<CapsuleNucleo>() {
            @Override
            public void onResponse(Call<CapsuleNucleo> call, Response<CapsuleNucleo> response) {
                if (response.isSuccessful()){
                    listNucleos = response.body().getNucleos();
                    setNucleosToView(listNucleos);
                }
            }

            @Override
            public void onFailure(Call<CapsuleNucleo> call, Throwable t) {

            }
        });
    }

    private void setMunicipiosToView(ArrayList<Municipio> municipios){
        SpinnerAdapter adapter = new ArrayAdapter<Municipio>(getContext(),android.R.layout.simple_spinner_dropdown_item,municipios);
        spinnerMunDestino.setAdapter(adapter);
        spinnerMunDestino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Municipio municipio = (Municipio) adapterView.getSelectedItem();
                filterNucleos(false,Integer.valueOf(municipio.getIdMunicipio()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerMunOrigen.setAdapter(adapter);
        spinnerMunOrigen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Municipio municipio = (Municipio) adapterView.getSelectedItem();
                filterNucleos(true,Integer.valueOf(municipio.getIdMunicipio()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerMunOrigen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Municipio municipio = (Municipio) adapterView.getSelectedItem();
                filterNucleos(true,Integer.valueOf(municipio.getIdMunicipio()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setNucleosToView(ArrayList<Nucleo> listadoNucleos){
        final SpinnerAdapter adapter = new ArrayAdapter<Nucleo>(getContext(),android.R.layout.simple_spinner_dropdown_item,listadoNucleos);
        spinnerNucOrigen.setAdapter(adapter);
        spinnerNucDestino.setAdapter(adapter);
        spinnerNucDestino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Nucleo nucleoDestino = (Nucleo) adapterView.getSelectedItem();
                Nucleo nucleoOrigen = (Nucleo) spinnerNucOrigen.getSelectedItem();
                loadLineas(Integer.valueOf(nucleoOrigen.getIdNucleo()),Integer.valueOf(nucleoDestino.getIdNucleo()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void filterNucleos(boolean origin ,int idMunicipio){
        ArrayList<Nucleo> list = new ArrayList<>();
        if (listNucleos != null){
            for (Nucleo n:
                    listNucleos) {
                if (n.idMunicipio != null)
                    if (Integer.valueOf(n.getIdMunicipio()) == idMunicipio)
                        list.add(n);

            }

            SpinnerAdapter adapter = new ArrayAdapter<Nucleo>(getContext(),android.R.layout.simple_spinner_dropdown_item,list);

            if (origin)
                spinnerNucOrigen.setAdapter(adapter);
            else
                spinnerNucDestino.setAdapter(adapter);
        }
    }

    private void loadLineas(int idNucleoOrigen,int idNucleoDestino){
        if (Util.hasInternet(getContext())){
            //Activamos el progress
            progressDialog = ProgressDialog.show(getContext(), "", getResources().getString(R.string.progress_lineas), true);
            ClienteApi clienteApi = new ClienteApi();
            int idConsorcio = SharedPreferencesUtil.getInt(getActivity(), Const.SHAREDKEYS.ID_CONSORCIO);

            Map<String, String> params = new HashMap<String, String>();
            params.put("idNucleoOrigen", String.valueOf(idNucleoOrigen));
            params.put("idNucleoDestino", String.valueOf(idNucleoDestino));

            clienteApi.getLineasPorNucleos(params, idConsorcio, new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()){
                        ArrayList<Horario> misHorarios = new ArrayList<>();
                        ResponseBody responseBody = response.body();
                        Gson g = new Gson();
                        try {
                            String d = responseBody.string();
                            CapsuleLineasPorNucleo capsuleLineasPorNucleo = g.fromJson(d,CapsuleLineasPorNucleo.class);
                            ArrayList<Horario> horarios = capsuleLineasPorNucleo.getHorario();
                            //Quitamos las lineas duplicadas
                            for (Horario h : horarios) {
                                int i = hasItem(misHorarios,h);

                                if (i>-1){
                                    i--;
                                    misHorarios.get(i).setInfoExtra(misHorarios.get(i).getInfoExtra() + " , " + h.getDias());
                                }else {
                                    h.setInfoExtra(h.getDias());
                                    misHorarios.add(h);
                                }
                            }

                            //Quitamos los dias duplicados de cada linea
                            for (Horario h: misHorarios) {
                                ArrayList<String> listInfo = new ArrayList<String>(Arrays.asList(h.getInfoExtra().replace(" ","").split(",")));
                                HashSet<String> hashSet = new HashSet<String>(listInfo);
                                listInfo.clear();
                                listInfo.addAll(hashSet);
                                h.setInfoExtra(listInfo.toString());
                            }


                            setDataToRecyclerView(misHorarios);
                        } catch (IOException e) {

                            e.printStackTrace();
                            clearRecycler();
                        }
                    }else{
                        clearRecycler();

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressDialog.dismiss();
                    clearRecycler();
                }
            });

        }
    }

    private void setDataToRecyclerView(final ArrayList<Horario> misHorarios) {
        LineasHorariosAdapter adapter = new LineasHorariosAdapter(getContext(),misHorarios.toArray(new Horario[0]));
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.SetOnItemClickListener(new RecyclerOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Horario horario = misHorarios.get(position);
                SharedPreferencesUtil.setInt(getActivity(),Const.SHAREDKEYS.ID_LINEA,Integer.valueOf(horario.getIdlinea()));
                Intent i = new Intent(getContext(), LineaDetailActivity.class);
                startActivity(i);
            }
        });
    }

    private int hasItem(ArrayList<Horario> misHorarios, Horario h) {
        boolean has = false;
        int i;

        for(i = 0;i < misHorarios.size() && !has; i++){
            Horario horario = misHorarios.get(i);
            if (h.getIdlinea().equals(horario.getIdlinea()))
                has = true;
        }

        if (!has)
            i = -1;
        
        return i;
    }

    private void clearRecycler(){
        recyclerView.removeAllViewsInLayout();

    }
}
