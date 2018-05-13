package com.miguebarrera.consorciotransportesandalucia.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miguebarrera.consorciotransportesandalucia.R;
import com.miguebarrera.consorciotransportesandalucia.activitys.LineaDetailActivity;
import com.miguebarrera.consorciotransportesandalucia.adapters.LineasAdapter;
import com.miguebarrera.consorciotransportesandalucia.interfaces.RecyclerOnItemClickListener;
import com.miguebarrera.consorciotransportesandalucia.models.CapsuleLineas;
import com.miguebarrera.consorciotransportesandalucia.models.Linea;
import com.miguebarrera.consorciotransportesandalucia.utils.ClienteApi;
import com.miguebarrera.consorciotransportesandalucia.utils.Const;
import com.miguebarrera.consorciotransportesandalucia.utils.SharedPreferencesUtil;
import com.miguebarrera.consorciotransportesandalucia.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LineasFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.rv_lineas)
    RecyclerView recyclerView;
    Dialog progressDialog;
    LineasAdapter adapter;

    public LineasFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static LineasFragment newInstance(String param1, String param2) {
        LineasFragment fragment = new LineasFragment();
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
        View view = inflater.inflate(R.layout.fragment_lineas, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadLineas();
    }

    private void loadLineas() {
        if (Util.hasInternet(getContext())){
            //Activamos el progress
            progressDialog = ProgressDialog.show(getContext(), "", getResources().getString(R.string.progress_lineas), true);
            ClienteApi clienteApi = new ClienteApi();
            int idConsorcio = SharedPreferencesUtil.getInt(getActivity(),Const.SHAREDKEYS.ID_CONSORCIO);
            clienteApi.getLineas(null, idConsorcio, new Callback<CapsuleLineas>() {
                @Override
                public void onResponse(Call<CapsuleLineas> call, Response<CapsuleLineas> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()){
                        CapsuleLineas capsuleLineas = response.body();
                        setDataToView(capsuleLineas);
                    }
                }

                @Override
                public void onFailure(Call<CapsuleLineas> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        }
    }

    public void filter(String query){
        adapter.getFilter().filter(query);
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

    private void setDataToView(final CapsuleLineas capsuleLineas){
        adapter = new LineasAdapter(getContext(),capsuleLineas.getLineas());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.SetOnItemClickListener(new RecyclerOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Linea linea = capsuleLineas.getLineas().get(position);
                SharedPreferencesUtil.setInt(getActivity(),Const.SHAREDKEYS.ID_LINEA,Integer.valueOf(linea.getIdLinea()));
                Intent i = new Intent(getContext(), LineaDetailActivity.class);
                startActivity(i);
            }
        });
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
