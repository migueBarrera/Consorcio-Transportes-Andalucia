package com.miguebarrera.consorciotransportesandalucia.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miguebarrera.consorciotransportesandalucia.R;
import com.miguebarrera.consorciotransportesandalucia.models.TarifasUrbana;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by migueBarreraBluumi on 24/01/2018.
 */

public class TarifasUrbanasAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    TarifasUrbana[] tarifasUrbanas;

    public TarifasUrbanasAdapter(TarifasUrbana[] tarifasUrbanas){
        this.tarifasUrbanas = tarifasUrbanas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Se utilizan indistintamente el layout de tarifas interurbanas para las urbanas simplemente cambiando el texto de salto por municipio
        if (viewType == TYPE_HEADER){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tarifas_interurbanas_header, parent, false);
            return new ViewHolderHeader(v);
        }else if (viewType == TYPE_ITEM){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_adapter_tarifasinterurbanas, parent, false);
            return new ViewHolder(v);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderHeader){
            ViewHolderHeader viewHolder = (ViewHolderHeader) holder;
            viewHolder.saltos.setText("Municipio");
        }else if (holder instanceof ViewHolder){
            ViewHolder viewHolder= (ViewHolder) holder;
            TarifasUrbana item = tarifasUrbanas[position-1];

            viewHolder.municipio.setText(item.getNombre());
            viewHolder.secillo.setText(item.getTu());
            viewHolder.tarjeta.setText(item.getImporteUsuario());
        }
    }

    private boolean isPositionHeader(int position)
    {
        return position == 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return tarifasUrbanas.length+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.row_tarifas_int_saltos)
        TextView municipio;
        @BindView(R.id.row_tarifas_int_bs)
        TextView secillo;
        @BindView(R.id.row_tarifas_int_tarj)
        TextView tarjeta;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class ViewHolderHeader extends RecyclerView.ViewHolder {

        @BindView(R.id.row_tarifas_int_saltos_header)
        TextView saltos;
        public ViewHolderHeader(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
