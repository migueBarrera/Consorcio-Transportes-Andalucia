package com.consorcio.consorciotransportesandalucia.renders;

import android.content.Context;


import com.consorcio.consorciotransportesandalucia.R;
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

    private Context context;

    public PuntosVentaCustomClusterRenderer(Context context, GoogleMap map, ClusterManager<PuntosVenta> clusterManager) {
        super(context, map, clusterManager);
        this.context = context;
    }


    @Override
    protected void onBeforeClusterItemRendered(PuntosVenta item, MarkerOptions markerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions);
        BitmapDescriptor markerDescriptor = null;


        if (item.tipo == null) {
            item.setTipo(TipoPuntoVenta.Default);
        }

        String type = "";

        switch (item.getTipo()) {

                case Comercio:
                    markerDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.bussines);
                    type = TipoPuntoVenta.Comercio.toString();
                    break;
                case Estanco:
                    markerDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.bussines);
                    type = TipoPuntoVenta.Estanco.toString();
                    break;
                case Papelería:
                    markerDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.papeleria);
                    type = TipoPuntoVenta.Papelería.toString();
                    break;
                case Kiosco:
                    markerDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.kiosko);
                    type = TipoPuntoVenta.Kiosco.toString();
                    break;
                case Default:
                    markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                    type = context.getString(R.string.info_bussines_unknow_puntos_venta);
                    break;
            }

            markerOptions.icon(markerDescriptor).snippet(item.getDireccion());
            markerOptions.title(type);

    }
}
