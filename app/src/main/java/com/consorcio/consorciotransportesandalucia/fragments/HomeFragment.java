package com.consorcio.consorciotransportesandalucia.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.consorcio.consorciotransportesandalucia.R;
import com.consorcio.consorciotransportesandalucia.activitys.LineaDetailActivity;
import com.consorcio.consorciotransportesandalucia.activitys.ParadaDetailActivity;
import com.consorcio.consorciotransportesandalucia.adapters.LineasAdapter;
import com.consorcio.consorciotransportesandalucia.adapters.LineasFavAdapter;
import com.consorcio.consorciotransportesandalucia.adapters.ParadaAdapter;
import com.consorcio.consorciotransportesandalucia.interfaces.RecyclerOnItemClickListener;
import com.consorcio.consorciotransportesandalucia.models.CapsuleLineaDetalle;
import com.consorcio.consorciotransportesandalucia.models.Linea;
import com.consorcio.consorciotransportesandalucia.models.Parada;
import com.consorcio.consorciotransportesandalucia.utils.Const;
import com.consorcio.consorciotransportesandalucia.utils.SharedPreferencesDatabase;
import com.consorcio.consorciotransportesandalucia.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.consorcio.consorciotransportesandalucia.utils.SharedPreferencesDatabase.getLineFav;


public class HomeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    @BindView(R.id.fragment_home_lines_text_no_content)
    TextView textViewLinesNoContent;
    @BindView(R.id.fragment_home_paradas_text_no_content)
    TextView textViewParadasNoContent;
    @BindView(R.id.fragment_home_lines_recycler)
    RecyclerView recyclerViewLines;
    @BindView(R.id.fragment_home_paradas_recycler)
    RecyclerView recyclerViewParadas;
    @BindView(R.id.fragment_home_lines_progressBar)
    ProgressBar progressBarLines;
    @BindView(R.id.fragment_home_paradas_progressBar)
    ProgressBar progressBarParadas;

    ArrayList<CapsuleLineaDetalle> listLineasFav;
    ArrayList<Parada> listParadasFav;

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        cleanUI();
        loadFavLines();
        loadFavParadas();
    }

    private void cleanUI() {
        //LINES
        progressBarLines.setActivated(true);
        progressBarLines.setVisibility(View.VISIBLE);
        textViewLinesNoContent.setVisibility(View.GONE);
        recyclerViewLines.setVisibility(View.GONE);

        //PARADAS
        progressBarParadas.setActivated(true);
        progressBarParadas.setVisibility(View.VISIBLE);
        textViewParadasNoContent.setVisibility(View.GONE);
        recyclerViewParadas.setVisibility(View.GONE);
    }

    private void loadFavParadas() {
        listParadasFav = SharedPreferencesDatabase.getParadasFav(getActivity());
        progressBarParadas.setActivated(false);
        progressBarParadas.setVisibility(View.GONE);
        if (listParadasFav == null || listParadasFav.isEmpty()){
            textViewParadasNoContent.setVisibility(View.VISIBLE);
            recyclerViewParadas.setVisibility(View.GONE);
        }else {
            textViewParadasNoContent.setVisibility(View.GONE);
            recyclerViewParadas.setVisibility(View.VISIBLE);

            ParadaAdapter adapter = new ParadaAdapter(getContext(),listParadasFav);
            recyclerViewParadas.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            recyclerViewParadas.setLayoutManager(llm);
            recyclerViewParadas.setRecycledViewPool(new RecyclerView.RecycledViewPool());
            recyclerViewParadas.setItemAnimator(new DefaultItemAnimator());
            recyclerViewParadas.setAdapter(adapter);
            adapter.SetOnItemClickListener(new RecyclerOnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    SharedPreferencesUtil.setInt(getActivity(),Const.SHAREDKEYS.ID_PARADA,Integer.valueOf(listParadasFav.get(position).getIdParada()));
                    Intent i = new Intent(getContext(), ParadaDetailActivity.class);
                    startActivity(i);
                }
            });

        }
    }

    private void loadFavLines() {
        listLineasFav = SharedPreferencesDatabase.getLineFav(getActivity());
        progressBarLines.setActivated(false);
        progressBarLines.setVisibility(View.GONE);
        if (listLineasFav == null || listLineasFav.isEmpty()){
            textViewLinesNoContent.setVisibility(View.VISIBLE);
            recyclerViewLines.setVisibility(View.GONE);
        }else {
            textViewLinesNoContent.setVisibility(View.GONE);
            recyclerViewLines.setVisibility(View.VISIBLE);

            LineasFavAdapter adapter = new LineasFavAdapter(getContext(),listLineasFav);
            recyclerViewLines.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            recyclerViewLines.setLayoutManager(llm);
            recyclerViewLines.setRecycledViewPool(new RecyclerView.RecycledViewPool());
            recyclerViewLines.setItemAnimator(new DefaultItemAnimator());
            recyclerViewLines.setAdapter(adapter);
            adapter.SetOnItemClickListener(new RecyclerOnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    CapsuleLineaDetalle linea = listLineasFav.get(position);
                    SharedPreferencesUtil.setInt(getActivity(), Const.SHAREDKEYS.ID_LINEA,Integer.valueOf(linea.getIdLinea()));
                    Intent i = new Intent(getContext(), LineaDetailActivity.class);
                    startActivity(i);
                }
            });
        }
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
