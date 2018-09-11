package com.example.conke.tt;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class adaptadorAE extends RecyclerView.Adapter<adaptadorAE.ViewHolderAE> implements View.OnClickListener {
    @NonNull
    ArrayList<adultoEncargado> adultosEncargados;
    private View.OnClickListener listener;

    public adaptadorAE(@NonNull ArrayList<adultoEncargado> adultosEncargados) {
        this.adultosEncargados = adultosEncargados;
    }

    @Override
    public ViewHolderAE onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_ae,null,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        view.setOnClickListener(this);
        return new ViewHolderAE(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAE holder, int position) {
        holder.labelIdAE.setText(adultosEncargados.get(position).getIdPersona().toString());
        holder.labelNameAE.setText(adultosEncargados.get(position).getNombre().toString());
        holder.labelMailAE.setText(adultosEncargados.get(position).getCorreo().toString());
    }

    @Override
    public int getItemCount() {
        return adultosEncargados.size();
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

    public class ViewHolderAE extends RecyclerView.ViewHolder {
        TextView labelNameAE;
        TextView labelMailAE;
        TextView labelIdAE;

        public ViewHolderAE(View itemView) {
            super(itemView);
            labelNameAE = itemView.findViewById(R.id.label_name_ae);
            labelMailAE = itemView.findViewById(R.id.label_mail_ae);
            labelIdAE = itemView.findViewById(R.id.id_ae);
        }
    }
}
