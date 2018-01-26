package com.consorcio.consorciotransportesandalucia.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.consorcio.consorciotransportesandalucia.R;
import com.consorcio.consorciotransportesandalucia.interfaces.RecyclerOnItemClickListener;
import com.consorcio.consorciotransportesandalucia.models.Linea;
import com.consorcio.consorciotransportesandalucia.models.TipoModoLinea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.consorcio.consorciotransportesandalucia.models.TipoModoLinea.AUTOBÚS_INTERURBANO;
import static com.consorcio.consorciotransportesandalucia.models.TipoModoLinea.Autobús;
import static com.consorcio.consorciotransportesandalucia.models.TipoModoLinea.BARCO;
import static com.consorcio.consorciotransportesandalucia.models.TipoModoLinea.Bus;
import static com.consorcio.consorciotransportesandalucia.models.TipoModoLinea.CERCANÍAS;
import static com.consorcio.consorciotransportesandalucia.models.TipoModoLinea.Default;
import static com.consorcio.consorciotransportesandalucia.models.TipoModoLinea.Tren;

/**
 * Created by migueBarreraBluumi on 11/01/2018.
 */

public class LineasAdapter extends RecyclerView.Adapter<LineasAdapter.ViewHolder> implements Filterable {

    List<Linea> lineas;
    List<Linea> lineasFiltered;
    Context context;
    RecyclerOnItemClickListener mItemClickListener;

    public LineasAdapter(Context context,List<Linea> lineas){
        this.lineas = lineas;
        this.lineasFiltered = lineas;
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
        Linea linea = lineasFiltered.get(position);

        holder.title.setText(linea.getCodigo());
        holder.subTitle.setText(linea.getNombre());
        Drawable drawable = null;


        switch (linea.getModo()){

            case AUTOBÚS_INTERURBANO:
            case Autobús:
            case Bus:
                drawable = context.getDrawable(R.mipmap.bus);
                break;
            case MEDIA_DISTANCIA:
            case CERCANÍAS:
            case Tren:
                drawable = context.getDrawable(R.mipmap.train);
                break;
            case BARCO:
                drawable = context.getResources().getDrawable(R.drawable.boat);
                break;
            case Default:
                drawable = context.getDrawable(R.mipmap.route);
                break;
        }

        holder.icon.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return lineasFiltered.size();
    }

    public void SetOnItemClickListener(final RecyclerOnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().toLowerCase();
                if (charString.isEmpty()) {
                    lineasFiltered = new ArrayList<>(lineas);
                } else {
                    List<Linea> filteredList = new ArrayList<>();
                    for (Linea row : lineas) {

                        if (row.getNombre().toLowerCase().contains(charSequence))
                            filteredList.add(row);

                    }

                    lineasFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = lineasFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                lineasFiltered = (ArrayList<Linea>) filterResults.values;

                //neas.clear();
                //lineas.addAll(lineasFiltered);

                notifyDataSetChanged();
            }
        };
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
