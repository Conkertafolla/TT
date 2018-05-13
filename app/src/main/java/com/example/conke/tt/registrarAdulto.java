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

import java.util.Calendar;

public class registrarAdulto extends AppCompatActivity {
    EditText fechaNac;
    private int dia,mes,ano;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fechaNac=findViewById(R.id.fecha_AM);
        fechaNac.setOnClickListener((View.OnClickListener) this);
        setContentView(R.layout.activity_registrar_adulto);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClick(View v){
        if(v==fechaNac){
            final Calendar c =Calendar.getInstance();
            dia=c.get(Calendar.DAY_OF_MONTH);
            mes=c.get(Calendar.MONTH);
            ano=c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    fechaNac.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                }
            },dia,mes,ano);
            datePickerDialog.show();
        }
    }
}
