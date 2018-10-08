package com.example.conke.tt;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class notificationAdapter extends RecyclerView.Adapter<notificationAdapter.ViewHoldernotification>{
    ArrayList<registro> registros = new ArrayList<registro>();

    public notificationAdapter(@NonNull ArrayList<registro> registros) {
        this.registros = registros;
    }


    @NonNull
    @Override
    public ViewHoldernotification onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list,null,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new ViewHoldernotification(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoldernotification holder, int position) {


        holder.fecha.setText(registros.get(position).getFecha().toString());
        holder.nombre.setText(registros.get(position).getNombre().toString());
        holder.valor.setText(registros.get(position).getValor().toString());
        holder.hora.setText(registros.get(position).getHora().toString());
        holder.tipo.setText(String.valueOf(registros.get(position).getTipo()).toString());
        if (registros.get(position).getTipo()==1){

            holder.imagen.setImageResource(R.drawable.baseline_error_black_18dp_2);
        }else {

            holder.imagen.setImageResource(R.drawable.baseline_favorite_black_18dp_2);

        }




    }

    @Override
    public int getItemCount() {
        return registros.size();
    }



    public class ViewHoldernotification extends RecyclerView.ViewHolder {
        TextView nombre;
        TextView fecha;
        TextView valor;
        TextView tipo;
        TextView hora;
        ImageView imagen;

        public ViewHoldernotification(View itemView) {
            super(itemView);
            nombre =itemView.findViewById(R.id.label_name_not);
            fecha = itemView.findViewById(R.id.date_notification);
            valor = itemView.findViewById(R.id.value_not);
            hora = itemView.findViewById(R.id.hour_notification);
            tipo = itemView.findViewById(R.id.type_notification);
            imagen = itemView.findViewById(R.id.notification_image);
        }
    }
}
