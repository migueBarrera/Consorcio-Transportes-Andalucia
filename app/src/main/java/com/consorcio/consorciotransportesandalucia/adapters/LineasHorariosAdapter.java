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
import com.consorcio.consorciotransportesandalucia.models.Horario;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by migueBarreraBluumi on 15/01/2018.
 */

public class LineasHorariosAdapter extends RecyclerView.Adapter<LineasHorariosAdapter.ViewHolder>{

    Horario[] horarios;
    Context context;
    RecyclerOnItemClickListener mItemClickListener;

    public LineasHorariosAdapter(Context context,Horario[] horarios){
        this.horarios = horarios;
        this.context = context;
    }


    @Override
    public LineasHorariosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_adapter_lineas, parent, false);
        LineasHorariosAdapter.ViewHolder viewHolder= new LineasHorariosAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LineasHorariosAdapter.ViewHolder holder, int position) {
        Horario horario = horarios[position];


        /*holder.title.setText(linea.getCodigo());
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

        holder.icon.setImageDrawable(drawable);*/
    }

    @Override
    public int getItemCount() {
        return horarios.length;
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
