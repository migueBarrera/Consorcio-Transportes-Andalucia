package com.miguebarrera.consorciotransportesandalucia.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miguebarrera.consorciotransportesandalucia.R;
import com.miguebarrera.consorciotransportesandalucia.interfaces.RecyclerOnItemClickListener;
import com.miguebarrera.consorciotransportesandalucia.models.Noticia;
import com.miguebarrera.consorciotransportesandalucia.utils.FontUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by migueBarreraBluumi on 17/01/2018.
 */

public class NoticiasAdapter extends RecyclerView.Adapter<NoticiasAdapter.ViewHolder> {

    public NoticiasAdapter(Context context , Noticia[] noticias){
        this.noticias = noticias;
        this.context = context;
    }

    Noticia[] noticias;
    Context context;
    RecyclerOnItemClickListener mItemClickListener;

    public void SetOnItemClickListener(final RecyclerOnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_adapter_noticias, parent, false);
        ViewHolder viewHolder= new ViewHolder(v);
        viewHolder.title.setTypeface(FontUtil.getRegularFont(v.getContext()));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Noticia noticia = noticias[position];

        holder.title.setText(noticia.getTitulo());
    }

    @Override
    public int getItemCount() {
        return noticias.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.row_noticias_icon)
        ImageView icon;
        @BindView(R.id.row_adapter_noticias_title)
        TextView title;
        /*@BindView(R.id.row_lineas_title_sub)
        TextView subTitle;*/
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
