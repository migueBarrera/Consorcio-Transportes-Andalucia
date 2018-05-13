package com.miguebarrera.consorciotransportesandalucia.renders;

import android.content.Context;


import com.miguebarrera.consorciotransportesandalucia.R;
import com.miguebarrera.consorciotransportesandalucia.models.MarkerInfo;
import com.miguebarrera.consorciotransportesandalucia.models.PuntosVenta;
import com.miguebarrera.consorciotransportesandalucia.models.TipoPuntoVenta;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
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


        /*if (item.tipo == null) {
            item.setTipo(TipoPuntoVenta.Default);
        }*/

        String type = "";

        switch (item.getTipo()) {

                case Comercio:
                    markerDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.bussines);
                    type = TipoPuntoVenta.Comercio.toString();
                    break;
                case Estanco:
                    markerDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.smoke);
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
                case Taquilla:
                    markerDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ticket);
                    type = TipoPuntoVenta.Taquilla.toString();
                    break;
                case Copistería:
                    markerDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.papeleria);
                    type = TipoPuntoVenta.Copistería.toString();
                    break;
                case Kiosko_Librería:
                    markerDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.kiosko);
                    type = TipoPuntoVenta.Kiosko_Librería.toString();
                    break;
                case Tienda:
                    markerDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.bussines);
                    type = TipoPuntoVenta.Tienda.toString();
                    break;
                case Alimentación:
                    markerDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.bar);
                    type = TipoPuntoVenta.Alimentación.toString();
                    break;
                case Información:
                    markerDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.info);
                    type = TipoPuntoVenta.Información.toString();
                    break;
                case Bar:
                    markerDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.bar);
                    type = TipoPuntoVenta.Bar.toString();
                    break;
                case Prensa:
                    markerDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.news);
                    type = TipoPuntoVenta.Prensa.toString();
                    break;
                case Metro:
                    markerDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.metro);
                    type = TipoPuntoVenta.Metro.toString();
                    break;
                case Default:
                    if(item.getDireccion().contains("Metro") || item.getDireccion().contains("METRO") || item.getDireccion().contains("metro"))
                        markerDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.metro);
                    else
                        markerDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.info);
                    type = context.getString(R.string.info_bussines_unknow_puntos_venta);
                    break;
            }

            markerOptions.icon(markerDescriptor).snippet(item.getDireccion());
            markerOptions.title(type);


        MarkerInfo markerInfo = new MarkerInfo();
        markerInfo.setTitle(item.getDireccion());
        markerInfo.setNucleo(item.getNucleo());
        markerInfo.setPos(item.getPosition());
        markerInfo.setTipoPuntoVenta(item.getTipo());
        markerOptions.snippet(new Gson().toJson(markerInfo));

    }
}
