package com.consorcio.consorciotransportesandalucia.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.consorcio.consorciotransportesandalucia.R;
import com.consorcio.consorciotransportesandalucia.interfaces.RecyclerOnItemClickListener;
import com.consorcio.consorciotransportesandalucia.models.Parada;
import com.consorcio.consorciotransportesandalucia.utils.Const;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by migueBarreraBluumi on 30/01/2018.
 */

public class ParadaAdapter extends RecyclerView.Adapter<ParadaAdapter.ViewHolder> {

    Context context;
    ArrayList<Parada> paradas;
    RecyclerOnItemClickListener mItemClickListener;

    public void SetOnItemClickListener(final RecyclerOnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public ParadaAdapter(Context context,ArrayList<Parada> paradas){
        this.paradas = paradas;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_adapter_parada_fav, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Parada parada = paradas.get(position);
        holder.textViewTitle.setText(parada.getNombre());
    }

    @Override
    public int getItemCount() {
        return paradas.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder  implements View.OnClickListener{
        @BindView(R.id.row_adapter_parada_title)
        TextView textViewTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getPosition());
            }
        }
    }
}
