package com.example.conke.tt;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class registrarAdulto extends AppCompatActivity implements View.OnClickListener,Response.Listener<JSONObject>,Response.ErrorListener{
    private static final String CERO = "0";
    private static final String BARRA = "/";
    private static final int PICK_IMAGE = 100;
    public final Calendar c = Calendar.getInstance();
    private RadioGroup sexR;
    private RadioButton masculino;
    private RadioButton femenino;
    private ImageButton fotoAM;
    private ArrayList<enfermedad> enfermedades;
    private final int ENFERMEDADES_CODE =1;
    Button enfermedadesBtn;
    Button enviar;


    final int dia= c.get(Calendar.DAY_OF_MONTH);
    final int mes= c.get(Calendar.MONTH);
    final int ano= c.get(Calendar.YEAR);
    int flag=0;

    EditText etFecha;
    ImageButton obtenerFecha;
    EditText nombreAM;
    EditText apellidoPaternoAM;
    EditText apellidoMaternoAM;
    LinearLayout eliminar_formAM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_adulto);
        setTitle("Registrar adulto mayor");
        etFecha= findViewById(R.id.fecha_AM);
        obtenerFecha= findViewById(R.id.selFec);
        obtenerFecha.setOnClickListener(this);
        sexR= findViewById(R.id.sexo_AM);
        masculino= findViewById(R.id.sexM);
        femenino= findViewById(R.id.sexF);
        fotoAM= findViewById(R.id.foto_AM);
        fotoAM.setOnClickListener(this);
        enfermedadesBtn=findViewById(R.id.enfermedades_btn);
        nombreAM = findViewById(R.id.nombreAM);
        apellidoPaternoAM= findViewById(R.id.apellidoPaternoAM);
        apellidoMaternoAM = findViewById(R.id.apellidoMaternoAM);
        eliminar_formAM = (LinearLayout) findViewById(R.id.eliminarAM_form);
        masculino.setChecked(true);

        enfermedadesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(registrarAdulto.this,Enfermedades_formulario.class);
                startActivityForResult(i,ENFERMEDADES_CODE);
            }
        });

        flag =getIntent().getIntExtra("update",0);

        if (flag==1){
            adultoMayor adulto = getIntent().getParcelableExtra("adultoMayor");
            eliminar_formAM.setVisibility(View.VISIBLE);
            fillForm(adulto);

        }

    }


    public void onClick(View v) {
        switch (v.getId()){
            case R.id.selFec:
                obtenerFecha();
                break;
            case R.id.foto_AM:
                Intent galeria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(galeria,PICK_IMAGE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ENFERMEDADES_CODE){
            if (resultCode == Activity.RESULT_OK){
                enfermedades = data.getParcelableArrayListExtra("enfermedades");
                Log.i("Size enfermedades", String.valueOf(enfermedades.size()));

            }
            if(resultCode == RESULT_CANCELED){


            }

        }


    }

    private void fillForm(adultoMayor adulto) {
        nombreAM.setText(adulto.getNombre());
        apellidoPaternoAM.setText(adulto.getApPaterno());
        apellidoMaternoAM.setText(adulto.getApMaterno());
        etFecha.setText(adulto.getFechaNacimiento());

        switch(adulto.getSexo()){
            case 0:
                femenino.setChecked(true);
                break;
            case 1:
                masculino.setChecked(true);
                break;
        }


    }

    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.isOnlyText(nombreAM,true))
            ret = false;
        if (!Validation.isOnlyText(apellidoPaternoAM,true))
            ret = false;
        if (!Validation.isOnlyText(apellidoMaternoAM,true))
            ret = false;
        return ret;



    }


    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }
}
