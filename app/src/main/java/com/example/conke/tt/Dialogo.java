package com.example.conke.tt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;


public class Dialogo extends DialogFragment {

    public interface OnDialogListener{
        void OnPositiveButtonClicked();
        void OnNegativeButtonClicked();
        void OnNeutralButtonClicked();

    }

    private OnDialogListener OnDialogListener;

    public void onAttach(Context context){
        super.onAttach(context);
        OnDialogListener =(OnDialogListener)getActivity();
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        builder.setTitle("Esta Seguro...")
                .setMessage("Al eliminar este usuario no podra recuperar su información")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        OnDialogListener.OnPositiveButtonClicked();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        OnDialogListener.OnNegativeButtonClicked();
                    }
                });


        return builder.create();
    }
}