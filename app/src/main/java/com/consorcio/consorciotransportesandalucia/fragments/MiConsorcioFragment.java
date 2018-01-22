package com.consorcio.consorciotransportesandalucia.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.consorcio.consorciotransportesandalucia.R;
import com.consorcio.consorciotransportesandalucia.models.CapsuleConsorcio;
import com.consorcio.consorciotransportesandalucia.models.Consorcio;
import com.consorcio.consorciotransportesandalucia.utils.ClienteApi;
import com.consorcio.consorciotransportesandalucia.utils.Const;
import com.consorcio.consorciotransportesandalucia.utils.MessageUtil;
import com.consorcio.consorciotransportesandalucia.utils.SharedPreferencesUtil;
import com.consorcio.consorciotransportesandalucia.utils.Util;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class MiConsorcioFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Consorcio consorcio;
    GoogleMap consorcioMap;
    LatLng consorcioPos;
    @BindView(R.id.fragment_mi_consorcio_title)
    TextView textViewTitle;
    @BindView(R.id.fragment_mi_consorcio_subtitle)
    TextView textViewSubTitle;
    @BindView(R.id.fragment_mi_consorcio_consorcio)
    RelativeLayout relativeLayoutMiConsorcio;
    private ArrayList<Consorcio> listConsorcios;
    Dialog progressDialog;
    @BindView(R.id.fragment_mi_consorcio_email)
    ImageView imageViewEmail;
    @BindView(R.id.fragment_mi_consorcio_web)
    ImageView imageViewWeb;
    @BindView(R.id.fragment_mi_consorcio_phone)
    ImageView imageViewPhone;

    public MiConsorcioFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MiConsorcioFragment newInstance(String param1, String param2) {
        MiConsorcioFragment fragment = new MiConsorcioFragment();
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
        View v = inflater.inflate(R.layout.fragment_mi_consorcio, container, false);
        ButterKnife.bind(this,v);

        //Add support to map fragment
        MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.consorcio_map);
        mapFragment.getMapAsync(this);
        mapFragment.getView().setClickable(true);

        addListener();

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(getActivity().getFragmentManager().findFragmentById(R.id.consorcio_map));
        fragmentTransaction.commit();
    }

    private void addListener() {
        relativeLayoutMiConsorcio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Con esto nos ahorramos volver a llamar al servicio si ya ha sido llamado anteriormente
                if (listConsorcios != null && listConsorcios.size() != 0)
                    showDialogConsorcios();
                else
                    loadConsorcios();

            }
        });
    }
    private void loadConsorcios(){
        if (Util.hasInternet(getContext())){
            //Activamos el progress
            progressDialog = ProgressDialog.show(getContext(), "", getResources().getString(R.string.progress_lineas), true);
            ClienteApi clienteApi = new ClienteApi();
            clienteApi.getConsorcios(null, 0, new Callback<CapsuleConsorcio>() {
                @Override
                public void onResponse(Call<CapsuleConsorcio> call, Response<CapsuleConsorcio> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()){
                        listConsorcios = response.body().getConsorcios();
                        showDialogConsorcios();
                    }
                }

                @Override
                public void onFailure(Call<CapsuleConsorcio> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        }
    }

    private void showDialogConsorcios(){
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        final LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_list_consorcio, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();

        ListView listConsorcio = (ListView) dialogView.findViewById(R.id.dialog_list_consorcio_list);
        listConsorcio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Consorcio consorcio = (Consorcio) listConsorcios.get(i);
                b.dismiss();

                SharedPreferencesUtil.setInt(getActivity(),Const.SHAREDKEYS.ID_CONSORCIO,Integer.valueOf(consorcio.getIdConsorcio()));
                loadDataConsorcio();
            }
        });

        ArrayAdapter<Consorcio> adapter = new ArrayAdapter<Consorcio>(getContext(),android.R.layout.simple_list_item_1, listConsorcios);
        listConsorcio.setAdapter(adapter);

        b.setCancelable(true);

        b.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        loadDataConsorcio();
    }

    private void loadDataConsorcio() {
        if (Util.hasInternet(getContext())){
            ClienteApi clienteApi = new ClienteApi();
            int idConsorcio = SharedPreferencesUtil.getInt(getActivity(), Const.SHAREDKEYS.ID_CONSORCIO);
            clienteApi.getConsorcioDetail(null, idConsorcio, new Callback<Consorcio>() {
                @Override
                public void onResponse(Call<Consorcio> call, Response<Consorcio> response) {
                    if (response.isSuccessful()){
                        consorcio = response.body();
                        Gson gson = new Gson();
                        SharedPreferencesUtil.setString(getActivity(),Const.SHAREDKEYS.MY_CONSORCIO,gson.toJson(consorcio,Consorcio.class));
                        setDataToView();
                        new GeocodeAsyncTask().execute();
                        //geocodeAsyncTask.execute();
                    }
                }

                @Override
                public void onFailure(Call<Consorcio> call, Throwable t) {

                }
            });
        }
    }

    private void setDataToView() {
        textViewTitle.setText(consorcio.getNombre());
        textViewSubTitle.setText(consorcio.getNombreCorto());

        imageViewWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!consorcio.getWeb().isEmpty())
                    Util.goToWeb(getContext(),consorcio.getWeb());
                else
                    MessageUtil.showSnackbar(getActivity().getWindow().getDecorView().getRootView(),getContext(),R.string.mi_consorcio_no_web);
            }
        });

        imageViewEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!consorcio.getEmail().isEmpty())
                    Util.sendEmail(getContext(),consorcio.getEmail());
                else
                    MessageUtil.showSnackbar(getActivity().getWindow().getDecorView().getRootView(),getContext(),R.string.mi_consorcio_no_email);
            }
        });

        imageViewPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!consorcio.getTlf1().isEmpty())
                    Util.callToNumber(getContext(),consorcio.getTlf1());
                else
                    MessageUtil.showSnackbar(getActivity().getWindow().getDecorView().getRootView(),getContext(),R.string.mi_consorcio_no_phone);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        consorcioMap = googleMap;
        googleMap.clear();
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                        String uri = "http://maps.google.com/maps?daddr=" + consorcioPos.latitude + "," + consorcioPos.longitude;
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        mapIntent.setPackage("com.google.android.apps.maps");
                        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                            startActivity(mapIntent);
                        }
            }
        });

        if (consorcioPos!=null)
            setPosToMap();
    }

    private void setPosToMap(){
        MarkerOptions markerOptions = new MarkerOptions().position(consorcioPos);
        //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_b));
        consorcioMap.addMarker(markerOptions);

        consorcioMap.animateCamera(CameraUpdateFactory.newLatLngZoom(consorcioPos, 13));
    }

    class GeocodeAsyncTask extends AsyncTask<Void, Void, Address> {

        String errorMessage = "";

        @Override
        protected void onPreExecute() {
            //infoText.setVisibility(View.INVISIBLE);
            //rogressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Address doInBackground(Void ... none) {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = null;


                String name = consorcio.getDireccion();
                Util.log(consorcio);
                try {
                    name = name+ " , " + consorcio.getCiudad()+ " , España";
                    addresses = geocoder.getFromLocationName(name, 1);
                } catch (IOException e) {
                    errorMessage = "Service not available";
                    Log.e(TAG, errorMessage, e);
                }

            if(addresses != null && addresses.size() > 0)
                return addresses.get(0);

            return null;
        }

        protected void onPostExecute(Address address) {
            if(address != null) {
                consorcioPos = new LatLng(address.getLatitude(),address.getLongitude());
               // progressBar.setVisibility(View.INVISIBLE);
                //infoText.setVisibility(View.VISIBLE);
                //infoText.setText(errorMessage);
                if (consorcioMap != null)
                    setPosToMap();
            }
        }
    }


}
