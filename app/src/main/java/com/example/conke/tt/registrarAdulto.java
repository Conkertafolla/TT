package com.example.conke.tt;

import android.app.DatePickerDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Calendar;

public class registrarAdulto extends AppCompatActivity implements View.OnClickListener{
    private static final String CERO = "0";
    private static final String BARRA = "/";
    public final Calendar c = Calendar.getInstance();

    final int dia= c.get(Calendar.DAY_OF_MONTH);
    final int mes= c.get(Calendar.MONTH);
    final int ano= c.get(Calendar.YEAR);

    EditText etFecha;
    ImageButton obtenerFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_adulto);
        etFecha=(EditText) findViewById(R.id.fecha_AM);
        obtenerFecha=(ImageButton) findViewById(R.id.selFec);
        obtenerFecha.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.selFec:
                obtenerFecha();
                break;
        }
    }

    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final int mesActual = month+1;
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                etFecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
            }
        },ano,mes,dia);
        recogerFecha.show();
    }




}
