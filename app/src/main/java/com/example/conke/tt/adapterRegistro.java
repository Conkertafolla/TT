package com.example.conke.tt;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class adapterRegistro extends RecyclerView.Adapter<adapterRegistro.ViewHolderRegister> {

    ArrayList<registro> registros = new ArrayList<registro>();

    public adapterRegistro(ArrayList<registro> registros) {
        this.registros = registros;
    }

    @NonNull
    @Override
    public ViewHolderRegister onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_temperatura,null,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new ViewHolderRegister(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRegister holder, int position) {
        holder.fecha.setText(registros.get(position).getFecha().toString());
        holder.nombre.setText(registros.get(position).getNombre().toString());
        holder.valor.setText(registros.get(position).getValor().toString());
        holder.hora.setText(registros.get(position).getHora().toString());

    }

    @Override
    public int getItemCount() {
        return registros.size();
    }

    public class ViewHolderRegister extends RecyclerView.ViewHolder {
        TextView nombre;
        TextView fecha;
        TextView hora;
        TextView valor;
        public ViewHolderRegister(View itemView) {
            super(itemView);
            nombre =itemView.findViewById(R.id.name_register);
            fecha = itemView.findViewById(R.id.fecha_register);
            hora = itemView.findViewById(R.id.hora_register);
            valor = itemView.findViewById(R.id.valor_register);
        }
    }
}