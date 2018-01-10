package com.consorcio.consorciotransportesandalucia.renders;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;

import com.consorcio.consorciotransportesandalucia.R;
import com.consorcio.consorciotransportesandalucia.models.Parada;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

/**
 * Created by migueBarreraBluumi on 10/01/2018.
 */

public class ParadasCustomClusterRenderer extends DefaultClusterRenderer<Parada> {

    private final Context mContext;
    //private final IconGenerator mClusterIconGenerator;

    public ParadasCustomClusterRenderer(Context context, GoogleMap map, ClusterManager<Parada> clusterManager) {
        super(context, map, clusterManager);

        this.mContext = context;
        //mClusterIconGenerator = new IconGenerator(mContext.getApplicationContext());
    }

    @Override protected void onBeforeClusterItemRendered(Parada item,
                                                         MarkerOptions markerOptions) {
        //final BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);

        //markerOptions.icon(markerDescriptor);
        //markerOptions.title("papa");
    }


}
