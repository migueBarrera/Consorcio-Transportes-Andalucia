package com.consorcio.consorciotransportesandalucia.adapters;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.consorcio.consorciotransportesandalucia.R;
import com.consorcio.consorciotransportesandalucia.models.MarkerInfo;
import com.consorcio.consorciotransportesandalucia.utils.MessageUtil;
import com.consorcio.consorciotransportesandalucia.utils.Util;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;

/**
 * Created by migueBarreraBluumi on 24/01/2018.
 */

public class ParadasInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View myContentsView;
    private Activity parentActivity;

    public ParadasInfoWindowAdapter(Activity parentActivity){
        myContentsView = parentActivity.getLayoutInflater().inflate(R.layout.custom_info_paradas_map, null);
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

        /*TextView textViewGoToMap = myContentsView.findViewById(R.id.custom_info_paradas_go_maps);
        textViewGoToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageUtil.showSnackbar(parentActivity.getParent().getWindow().getDecorView().getRootView(),parentActivity,R.string.title_activity_main);
            }
        });*/

        TextView textViewTitle = myContentsView.findViewById(R.id.custom_info_paradas_title);
        textViewTitle.setText(markerInfo.getTitle());

        return myContentsView;
    }
}
