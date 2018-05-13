package com.miguebarrera.consorciotransportesandalucia.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miguebarrera.consorciotransportesandalucia.R;
import com.miguebarrera.consorciotransportesandalucia.utils.FontUtil;
import com.miguebarrera.consorciotransportesandalucia.utils.MessageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SaldoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.fragment_saldo_image)
    ImageView imageViewLogoNFC;
    @BindView(R.id.fragment_saldo_title)
    TextView nextTitle;
    @BindView(R.id.fragment_saldo_sub_title)
    TextView nextSubTitle;

    public SaldoFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SaldoFragment newInstance(String param1, String param2) {
        SaldoFragment fragment = new SaldoFragment();
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
        View view =inflater.inflate(R.layout.fragment_saldo, container, false);
        ButterKnife.bind(this,view);

        addListener();

        setFonts();

        return view;
    }

    private void setFonts() {
        nextTitle.setTypeface(FontUtil.getRegularFont(getActivity()));
        nextSubTitle.setTypeface(FontUtil.getLightFont(getActivity()));
    }

    private void addListener() {
        imageViewLogoNFC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageUtil.showSnackbar(getActivity().getWindow().getDecorView().getRootView(),getActivity(),R.string.no_work);
            }
        });
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
