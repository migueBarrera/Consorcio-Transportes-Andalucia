package com.consorcio.consorciotransportesandalucia.renders;

import android.content.Context;

import com.consorcio.consorciotransportesandalucia.models.PuntosVenta;
import com.consorcio.consorciotransportesandalucia.models.TipoPuntoVenta;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

/**
 * Created by migueBarreraBluumi on 10/01/2018.
 */

public class PuntosVentaCustomClusterRenderer extends DefaultClusterRenderer<PuntosVenta> {

    public PuntosVentaCustomClusterRenderer(Context context, GoogleMap map, ClusterManager<PuntosVenta> clusterManager) {
        super(context, map, clusterManager);
    }


    @Override
    protected void onBeforeClusterItemRendered(PuntosVenta item, MarkerOptions markerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions);
        BitmapDescriptor markerDescriptor = null;


        if (item.tipo == null)
            item.setTipo(TipoPuntoVenta.Default);

            switch (item.getTipo()) {

                case Comercio:
                    markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
                    break;
                case Estanco:
                    markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
                    break;
                case Papelería:
                    markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
                    break;
                case Kiosco:
                    markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
                    break;
                case Default:
                    markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                    break;
            }

            markerOptions.icon(markerDescriptor);
            markerOptions.title(item.getDireccion());

    }
}
