package com.miguebarrera.consorciotransportesandalucia.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.miguebarrera.consorciotransportesandalucia.R;
import com.miguebarrera.consorciotransportesandalucia.utils.ClienteApi;
import com.miguebarrera.consorciotransportesandalucia.utils.Const;
import com.miguebarrera.consorciotransportesandalucia.utils.SharedPreferencesUtil;
import com.miguebarrera.consorciotransportesandalucia.utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.miguebarrera.consorciotransportesandalucia.utils.Util.hasInternet;


public class CentroAtencionFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    
    @BindView(R.id.fragment_centro_atencion_webview)
    WebView webView;

    public CentroAtencionFragment() {
        // Required empty public constructor
    }



    public static CentroAtencionFragment newInstance(String param1, String param2) {
        CentroAtencionFragment fragment = new CentroAtencionFragment();
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
        View view = inflater.inflate(R.layout.fragment_centro_atencion, container, false);

        ButterKnife.bind(this, view);
        loadContent();
        return view;
    }


    private void loadContent() {
        if (hasInternet(getContext())){
            ClienteApi clienteApi = new ClienteApi();
            int idConsorcio = SharedPreferencesUtil.getInt(getActivity(), Const.SHAREDKEYS.ID_CONSORCIO);
            clienteApi.getAtencionUsuario(null, idConsorcio, new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            String value = jsonObject.getString("txtAtencionUsr");
                            webView.loadData(value,"text/html; charset=utf-8", "UTF-8");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
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
}
