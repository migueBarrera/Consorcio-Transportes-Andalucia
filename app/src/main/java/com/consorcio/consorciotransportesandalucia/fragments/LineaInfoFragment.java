package com.consorcio.consorciotransportesandalucia.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.consorcio.consorciotransportesandalucia.R;
import com.consorcio.consorciotransportesandalucia.adapters.NoticiasAdapter;
import com.consorcio.consorciotransportesandalucia.interfaces.LineaDetailInterface;
import com.consorcio.consorciotransportesandalucia.interfaces.RecyclerOnItemClickListener;
import com.consorcio.consorciotransportesandalucia.models.CapsuleLineaDetalle;
import com.consorcio.consorciotransportesandalucia.models.CapsuleNoticias;
import com.consorcio.consorciotransportesandalucia.models.Noticia;
import com.consorcio.consorciotransportesandalucia.utils.ClienteApi;
import com.consorcio.consorciotransportesandalucia.utils.Const;
import com.consorcio.consorciotransportesandalucia.utils.HeadersHelpers;
import com.consorcio.consorciotransportesandalucia.utils.SharedPreferencesUtil;
import com.consorcio.consorciotransportesandalucia.utils.Util;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LineaInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LineaDetailInterface mListener;
    @BindView(R.id.linea_detail_view_warning_content)
    TextView viewWarnignContent;
    @BindView(R.id.linea_detail_view_operadores_content)
    TextView viewOperadoraContent;
    @BindView(R.id.linea_detail_view_recorrido_content)
    TextView viewRecorridoContent;
    @BindView(R.id.linea_detail_listado_noticias)
    RecyclerView listNoticias;
    @BindView(R.id.linea_detail_view_noticias_content)
    TextView textViewNoDataNoticias;
    @BindView(R.id.linea_detail_view_noticias_progressBar)
    ProgressBar progressBarNoticias;
    @BindView(R.id.linea_detail_termometro_ida)
    TextView textViewTermometroIda;
    @BindView(R.id.linea_detail_termometro_vuelta)
    TextView textViewTermometroVuelta;
    @BindView(R.id.linea_detail_icon)
    ImageView lineaIcon;
    private CapsuleLineaDetalle capsuleLineaDetalle;


    public LineaInfoFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static LineaInfoFragment newInstance(String param1, String param2) {
        LineaInfoFragment fragment = new LineaInfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_linea_info, container, false);
        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        capsuleLineaDetalle = mListener.getCapsuleLineaDetail();

        Util.log(capsuleLineaDetalle);
        configureView();
    }

    private void configureView() {
        setIcon();
        setTermometros();
        setRecorrido();
        setPmr();
        setOperadora();
        loadNoticias();
    }

    private void setIcon() {
        Drawable drawable = null;
        switch (capsuleLineaDetalle.getModo()){

            case Autobús:
            case Bus:
                drawable = this.getResources().getDrawable(R.mipmap.bus);
                break;
            case Tren:
                drawable = this.getResources().getDrawable(R.mipmap.train);
                break;
            case Default:
                drawable = this.getResources().getDrawable(R.mipmap.bus);
                break;
        }

        lineaIcon.setImageDrawable(drawable);
    }

    private void setTermometros() {
        //IDA
        if (capsuleLineaDetalle.getTieneIda() == 1){
            textViewTermometroIda.setVisibility(View.VISIBLE);
        }

        //VUELTA
        if (capsuleLineaDetalle.getTieneVuelta() == 1){
            textViewTermometroVuelta.setVisibility(View.VISIBLE);
        }

    }

    private void loadNoticias() {
        if (Util.hasInternet(getContext())){
            ClienteApi clienteApi = new ClienteApi();
            Map<String,String> map = HeadersHelpers.getHeaders();
            clienteApi.getNoticias(map, mListener.getConsorcioId(), mListener.getLineaId(), new Callback<CapsuleNoticias>() {
                @Override
                public void onResponse(Call<CapsuleNoticias> call, Response<CapsuleNoticias> response) {
                    if (response.isSuccessful()){
                        Util.log(response.body());
                        ArrayList<Noticia> noticias = response.body().getNoticias();
                        if (noticias.size() != 0)
                            setNoticiasToView(noticias);
                        else
                            setNoticiasToView(null);
                    }else {
                        setNoticiasToView(null);
                    }
                }

                @Override
                public void onFailure(Call<CapsuleNoticias> call, Throwable t) {

                }
            });
        }
    }

    private void setNoticiasToView(ArrayList<Noticia> noticias) {
        progressBarNoticias.setActivated(false);
        progressBarNoticias.setVisibility(View.GONE);
        if (noticias != null){
            listNoticias.setVisibility(View.VISIBLE);
            NoticiasAdapter adapter = new NoticiasAdapter(getContext(),noticias.toArray(new Noticia[0]));
            listNoticias.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            listNoticias.setLayoutManager(llm);
            listNoticias.setRecycledViewPool(new RecyclerView.RecycledViewPool());
            listNoticias.setItemAnimator(new DefaultItemAnimator());
            listNoticias.setAdapter(adapter);
            adapter.SetOnItemClickListener(new RecyclerOnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
               /* Linea linea = capsuleLineas.getLineas().get(position);
                SharedPreferencesUtil.setInt(getActivity(), Const.SHAREDKEYS.ID_LINEA,Integer.valueOf(linea.getIdLinea()));
                Intent i = new Intent(getContext(), LineaDetailActivity.class);
                startActivity(i);*/
                }
            });

        }else {
            textViewNoDataNoticias.setVisibility(View.VISIBLE);
        }

    }

    private void setRecorrido() {
        String[] recorrido = capsuleLineaDetalle.getNombre().split(" ");
        int length = recorrido[0].length();
        viewRecorridoContent.setText(capsuleLineaDetalle.getNombre().substring(length));
    }

    private void setOperadora() {
        viewOperadoraContent.setText(capsuleLineaDetalle.getOperadores());
    }

    private void setPmr() {
        if (capsuleLineaDetalle.getPmr() != null && !capsuleLineaDetalle.getPmr().isEmpty())
            viewWarnignContent.setText(capsuleLineaDetalle.getPmr());

            //TODO SINO OCULTAR VISTA
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LineaDetailInterface) {
            mListener = (LineaDetailInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement LineaDetailInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void showDialogTermometro(String url){
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        final LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_termometro, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();

        ImageView imageView = (ImageView) dialogView.findViewById(R.id.dialog_termometro_image);
        Picasso.with(getContext())
                .load(url)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(imageView);

        b.setCancelable(true);

        b.show();
    }

    @OnClick(R.id.)
    public void clikcTermometroIda(){
        showDialogTermometro()
    }

}
