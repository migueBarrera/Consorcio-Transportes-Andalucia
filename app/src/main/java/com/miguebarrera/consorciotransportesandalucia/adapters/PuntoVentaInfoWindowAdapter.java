package com.miguebarrera.consorciotransportesandalucia.adapters;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.miguebarrera.consorciotransportesandalucia.R;
import com.miguebarrera.consorciotransportesandalucia.models.MarkerInfo;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;

/**
 * Created by migueBarreraBluumi on 29/01/2018.
 */

public class PuntoVentaInfoWindowAdapter implements GoogleMap.InfoWindowAdapter  {


    private final View myContentsView;
    private Activity parentActivity;

    public PuntoVentaInfoWindowAdapter(Activity parentActivity){
        myContentsView = parentActivity.getLayoutInflater().inflate(R.layout.custom_info_puntosventa_map, null);
        this.parentActivity = parentActivity;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        String markerInfoJSON = marker.getSnippet();
        MarkerInfo markerInfo = new Gson().fromJson(markerInfoJSON,MarkerInfo.class);

        if (markerInfo == null)
            return null;

        TextView textViewTitle = myContentsView.findViewById(R.id.custom_info_puntoventa_title);
        textViewTitle.setText(markerInfo.getTipoPuntoVenta().toString().replace("_"," "));

        TextView textViewSubTitle = myContentsView.findViewById(R.id.custom_info_puntoventa_subtitle);
        textViewSubTitle.setText(markerInfo.getTitle());

        return myContentsView;
    }
}
