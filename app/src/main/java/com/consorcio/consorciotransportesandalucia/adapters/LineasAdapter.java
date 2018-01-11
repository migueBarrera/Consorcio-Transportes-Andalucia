package com.consorcio.consorciotransportesandalucia.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.consorcio.consorciotransportesandalucia.R;
import com.consorcio.consorciotransportesandalucia.interfaces.RecyclerOnItemClickListener;
import com.consorcio.consorciotransportesandalucia.models.Linea;
import com.consorcio.consorciotransportesandalucia.models.TipoModoLinea;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by migueBarreraBluumi on 11/01/2018.
 */

public class LineasAdapter extends RecyclerView.Adapter<LineasAdapter.ViewHolder>{

    Linea[] lineas;
    Context context;
    RecyclerOnItemClickListener mItemClickListener;

    public LineasAdapter(Context context,Linea[] lineas){
        this.lineas = lineas;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_adapter_lineas, parent, false);
        ViewHolder viewHolder= new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Linea linea = lineas[position];

        holder.title.setText(linea.getCodigo());
        holder.subTitle.setText(linea.getNombre());
        Drawable drawable = null;

        if (linea.modo == null){
            linea.setModo(TipoModoLinea.Default);
        }

        switch (linea.getModo()){

            case Bus:
                drawable = context.getDrawable(R.mipmap.bus);
                break;
            case Tren:
                drawable = context.getDrawable(R.mipmap.train);
                break;
            case Default:
                drawable = context.getDrawable(R.mipmap.route);
                break;
        }

        holder.icon.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return lineas.length;
    }

    public void SetOnItemClickListener(final RecyclerOnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.row_lineas_icon)
        ImageView icon;
        @BindView(R.id.row_lineas_title)
        TextView title;
        @BindView(R.id.row_lineas_title_sub)
        TextView subTitle;
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
