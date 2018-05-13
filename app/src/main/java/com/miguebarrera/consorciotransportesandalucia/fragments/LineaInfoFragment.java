package com.miguebarrera.consorciotransportesandalucia.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.miguebarrera.consorciotransportesandalucia.R;
import com.miguebarrera.consorciotransportesandalucia.adapters.NoticiasAdapter;
import com.miguebarrera.consorciotransportesandalucia.interfaces.LineaDetailInterface;
import com.miguebarrera.consorciotransportesandalucia.interfaces.RecyclerOnItemClickListener;
import com.miguebarrera.consorciotransportesandalucia.models.CapsuleLineaDetalle;
import com.miguebarrera.consorciotransportesandalucia.models.CapsuleNoticias;
import com.miguebarrera.consorciotransportesandalucia.models.Noticia;
import com.miguebarrera.consorciotransportesandalucia.models.TipoModoLinea;
import com.miguebarrera.consorciotransportesandalucia.utils.ClienteApi;
import com.miguebarrera.consorciotransportesandalucia.utils.Const;
import com.miguebarrera.consorciotransportesandalucia.utils.FontUtil;
import com.miguebarrera.consorciotransportesandalucia.utils.HeadersHelpers;
import com.miguebarrera.consorciotransportesandalucia.utils.LinearLayoutManagerNoScroll;
import com.miguebarrera.consorciotransportesandalucia.utils.SharedPreferencesUtil;
import com.miguebarrera.consorciotransportesandalucia.utils.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
    @BindView(R.id.linea_detail_view_recorrido_title)
    TextView viewRecorridoTitle;
    @BindView(R.id.linea_detail_view_operadora_title)
    TextView viewOperadoraTitle;
    @BindView(R.id.linea_detail_view_warning_title)
    TextView viewWarningTitle;
    @BindView(R.id.linea_detail_view_noticias_title)
    TextView viewNoticiasTitle;
    @BindView(R.id.linea_detail_termometro_vuelta)
    TextView textViewTermometroVuelta;
    @BindView(R.id.linea_detail_icon)
    ImageView lineaIcon;
    private CapsuleLineaDetalle capsuleLineaDetalle;


    public LineaInfoFragment() {
        // Required empty public constructor
    }


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

        setFonts();

        return view;
    }

    private void setFonts() {
        //Recorrido
        viewRecorridoContent.setTypeface(FontUtil.getRegularFont(getActivity()));
        viewRecorridoTitle.setTypeface(FontUtil.getBoldFont(getActivity()));
        //Operadora
        viewOperadoraContent.setTypeface(FontUtil.getRegularFont(getActivity()));
        viewOperadoraTitle.setTypeface(FontUtil.getBoldFont(getActivity()));
        //Warning
        viewWarnignContent.setTypeface(FontUtil.getRegularFont(getActivity()));
        viewWarningTitle.setTypeface(FontUtil.getBoldFont(getActivity()));
        //Noticias
        textViewNoDataNoticias.setTypeface(FontUtil.getRegularFont(getActivity()));
        viewNoticiasTitle.setTypeface(FontUtil.getBoldFont(getActivity()));
        //Termometros
        textViewTermometroIda.setTypeface(FontUtil.getBoldFont(getActivity()));
        textViewTermometroVuelta.setTypeface(FontUtil.getBoldFont(getActivity()));
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

        if (capsuleLineaDetalle.modo == null){
            capsuleLineaDetalle.setModo(TipoModoLinea.AUTOBÚS_INTERURBANO);
        }

        switch (capsuleLineaDetalle.getModo()){

            case AUTOBÚS_INTERURBANO:
            case Autobús:
            case Bus:
                drawable = this.getResources().getDrawable(R.mipmap.bus);
                break;
            case MEDIA_DISTANCIA:
            case CERCANÍAS:
            case Tren:
                drawable = this.getResources().getDrawable(R.mipmap.train);
                break;
            case BARCO:
                drawable = this.getResources().getDrawable(R.drawable.boat);
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

    private void setNoticiasToView(final ArrayList<Noticia> noticias) {
        progressBarNoticias.setActivated(false);
        progressBarNoticias.setVisibility(View.GONE);
        if (noticias != null){
            listNoticias.setVisibility(View.VISIBLE);
            NoticiasAdapter adapter = new NoticiasAdapter(getContext(),noticias.toArray(new Noticia[0]));
            listNoticias.setHasFixedSize(true);
            LinearLayoutManagerNoScroll llm = new LinearLayoutManagerNoScroll(getContext());
            llm.setScrollEnabled(false);
            listNoticias.setLayoutManager(llm);
            listNoticias.setRecycledViewPool(new RecyclerView.RecycledViewPool());
            listNoticias.setItemAnimator(new DefaultItemAnimator());
            listNoticias.setAdapter(adapter);
            adapter.SetOnItemClickListener(new RecyclerOnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Noticia noticia = noticias.get(position);
                    showDialogNews(noticia);
                }
            });

        }else {
            textViewNoDataNoticias.setVisibility(View.VISIBLE);
        }

    }

    private void setRecorrido() {
        //String[] recorrido = capsuleLineaDetalle.getNombre().split(" ");
        //int length = recorrido[0].length();
        //viewRecorridoContent.setText(capsuleLineaDetalle.getNombre().substring(length));
        viewRecorridoContent.setText(capsuleLineaDetalle.getNombre());
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
        Util.log(url);

        b.setCancelable(true);

        b.show();
    }

    private void showDialogNews(Noticia noticia){
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        final LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_news, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();
        //DATE
        TextView date = (TextView) dialogView.findViewById(R.id.dialog_news_date);
        date.setText(noticia.getFechaInicio());
        date.setTypeface(FontUtil.getBoldFont(getActivity()));

        //TITLE
        TextView title = (TextView) dialogView.findViewById(R.id.dialog_news_title);
        title.setText(noticia.getTitulo());
        title.setTypeface(FontUtil.getRegularFont(getActivity()));

        //SUBTITLE
        TextView subtitle = (TextView) dialogView.findViewById(R.id.dialog_news_subtitle);
        subtitle.setText(noticia.getSubTitulo());
        subtitle.setTypeface(FontUtil.getRegularFont(getActivity()));

        //RESUMEN
        TextView resumen = (TextView) dialogView.findViewById(R.id.dialog_news_resumen);
        //Hay ocaciones que el resumen es el subtitulo . aveces algo mas . Si lo contiene lo ocultamos para no duplicar la info
        if (noticia.getResumen().contains(noticia.getSubTitulo()))
            subtitle.setVisibility(View.GONE);
        resumen.setText(noticia.getResumen());

        //LINES TODO Por algun motivo siempre viene a null el objeto pero desde postman si sale ese parametro
        TextView lines = (TextView) dialogView.findViewById(R.id.dialog_news_lines);
        LinearLayout linearLayoutLines = dialogView.findViewById(R.id.dialog_news_lines_layout);
        if (noticia.getLineas() != null){
            linearLayoutLines.setVisibility(View.VISIBLE);
            lines.setText(noticia.getLineas());
        }

        b.setCancelable(true);

        b.show();
    }

    @OnClick(R.id.linea_detail_termometro_ida)
    public void clikcTermometroIda(){
        showDialogTermometro(capsuleLineaDetalle.getTermometroIda());
    }

    @OnClick(R.id.linea_detail_termometro_vuelta)
    public void clikcTermometroVuelta(){
        showDialogTermometro(capsuleLineaDetalle.getTermometroVuelta());
    }

}
