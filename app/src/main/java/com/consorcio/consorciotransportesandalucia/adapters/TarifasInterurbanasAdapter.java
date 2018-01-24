package com.consorcio.consorciotransportesandalucia.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.consorcio.consorciotransportesandalucia.R;
import com.consorcio.consorciotransportesandalucia.models.TarifasInterurbana;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by migueBarreraBluumi on 24/01/2018.
 */

public class TarifasInterurbanasAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private TarifasInterurbana[] tarifasInterurbanas;

    public TarifasInterurbanasAdapter(TarifasInterurbana[] tarifasInterurbanas){
        this.tarifasInterurbanas = tarifasInterurbanas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        }else if (holder instanceof ViewHolder){
            ViewHolder holderHeader = (ViewHolder) holder;
            TarifasInterurbana item = tarifasInterurbanas[position-1];

            holderHeader.saltos.setText(item.getSaltos());
            holderHeader.secillo.setText(String.format("%.2f", Double.valueOf(item.getBs())));
            holderHeader.tarjeta.setText(String.format("%.2f", Double.valueOf(item.getTarjeta())));
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
            return tarifasInterurbanas.length+1;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.row_tarifas_int_saltos)
            TextView saltos;
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

        public ViewHolderHeader(View itemView) {
            super(itemView);
        }
    }
}
