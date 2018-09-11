package com.example.conke.tt;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class adaptadorAM extends RecyclerView.Adapter<adaptadorAM.ViewHolderAM> implements View.OnClickListener{
    ArrayList<adultoMayor> adultosMayores;
    private View.OnClickListener listener;


    public adaptadorAM(@NonNull ArrayList<adultoMayor> adultosMayores) {
        this.adultosMayores = adultosMayores;
    }

    @NonNull
    @Override
    public ViewHolderAM onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_am,null,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        view.setOnClickListener(this);
        return new ViewHolderAM(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAM holder, int position) {
        holder.labelIdAM.setText(adultosMayores.get(position).getIdPersona().toString());
        holder.labelNameAM.setText(adultosMayores.get(position).getNombre().toString());

    }

    @Override
    public int getItemCount() {
        return adultosMayores.size();
    }

    public void  setOnclickListener(View.OnClickListener listener){
        this.listener =listener;

    }

    @Override
    public void onClick(View view) {
        if (listener != null){
            listener.onClick(view);
        }

    }


    public class ViewHolderAM extends RecyclerView.ViewHolder {
        TextView labelNameAM;
        TextView labelIdAM;
        public ViewHolderAM(View itemView) {
            super(itemView);
            labelNameAM = itemView.findViewById(R.id.label_name_am);
            labelIdAM = itemView.findViewById(R.id.id_am);
        }
    }
}
