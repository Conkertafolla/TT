package com.example.conke.tt;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class registro_adulto_encargado extends AppCompatActivity implements View.OnClickListener,Response.Listener<JSONObject>,Response.ErrorListener{
    private static final String CERO = "0";
    private static final String BARRA = "/";
    private static final String GUION = "-";
    public final Calendar c = Calendar.getInstance();
    Button enviar ;
    private Date nacimiento;
    EditText etFecha;
    ImageButton obtenerFecha;
    EditText nombreAE;
    EditText apellidoPaternoAE;
    EditText apellidoMatenroAE;
    EditText correoAE;
    EditText numeroAE;
    TextView IdAE;
    EditText passwordAE;
    LinearLayout eliminar_form ;
    private RadioGroup sexRAE;
    private RadioButton masculinoAE;
    private RadioButton femeninoAE;
    final int dia= c.get(Calendar.DAY_OF_MONTH);
    final int mes= c.get(Calendar.MONTH);
    final int ano= c.get(Calendar.YEAR);
    private int Sexo = 0;
    private int flag = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_adulto_encargado);
        setTitle("Registrar adulto encargado");
        etFecha= findViewById(R.id.fecha_AE);
        obtenerFecha= findViewById(R.id.selFAE);
        obtenerFecha.setOnClickListener((View.OnClickListener) this);
        nombreAE= findViewById(R.id.nombreAE);
        apellidoPaternoAE=findViewById(R.id.apellidoPaternoAE);
        apellidoMatenroAE=findViewById(R.id.apellidoMaternoAE);
        correoAE=findViewById(R.id.correoAE);
        numeroAE=findViewById(R.id.numeroAE);
        passwordAE=findViewById(R.id.passwordAE);
        enviar= findViewById(R.id.RegistrarAE);
        sexRAE= findViewById(R.id.sexo_AM);
        masculinoAE= findViewById(R.id.sexMAE);
        femeninoAE= findViewById(R.id.sexFAE);
        IdAE= findViewById(R.id.id_AE);

        eliminar_form =findViewById(R.id.eliminarAE_form);


        flag =getIntent().getIntExtra("update",0);

        if (flag==1){
            adultoEncargado adulto = getIntent().getParcelableExtra("adultoEncargado");
            enviar.setText("Actualizar");
            eliminar_form.setVisibility(View.VISIBLE);

            try {
                fillForm(adulto);
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==1){
                    updateAE();

                }else{
                registrarAE();
                }
            }
        });

    }

    private void registrarAE() {

        String url =getResources().getString(R.string.ipconfig)+"iAE";
        HashMap<String, String> postParam= new HashMap<String, String>();
        postParam.put("Nombre",nombreAE.getText().toString());
        postParam.put("ApPat",apellidoPaternoAE.getText().toString());
        postParam.put("ApMat",apellidoMatenroAE.getText().toString());
        postParam.put("Contrasenia",passwordAE.getText().toString());
        postParam.put("Correo",correoAE.getText().toString());
        postParam.put("FechaNac",etFecha.getText().toString());
        postParam.put("NumTel",numeroAE.getText().toString());
        postParam.put("Sexo", Integer.toString(Sexo));
        JSONObject jsonObject = new JSONObject(postParam);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Request.Method.POST,url,jsonObject,this,this);
        VolleySingleton.getInstanciaVolley(this).addToRequestQueue(jsonObjectRequest);

    }

    private void updateAE() {

        String url =getResources().getString(R.string.ipconfig)+"Ac_AE/"+IdAE.getText().toString().trim();

        HashMap<String, String> postParam= new HashMap<String, String>();
        postParam.put("Nombre",nombreAE.getText().toString());
        postParam.put("ApPat",apellidoPaternoAE.getText().toString());
        postParam.put("ApMat",apellidoMatenroAE.getText().toString());
        postParam.put("Contrasenia",passwordAE.getText().toString());
        postParam.put("Correo",correoAE.getText().toString());
        postParam.put("FechaNac",etFecha.getText().toString());
        postParam.put("NumTel",numeroAE.getText().toString());
        postParam.put("Sexo", Integer.toString(Sexo));
        JSONObject jsonObject = new JSONObject(postParam);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Request.Method.PUT,url,jsonObject,this,this);
        VolleySingleton.getInstanciaVolley(this).addToRequestQueue(jsonObjectRequest);

    }

    private void fillForm(adultoEncargado adulto) throws ParseException {
        IdAE.setText(adulto.getIdPersona().toString());
        nombreAE.setText(adulto.getNombre());
        apellidoPaternoAE.setText(adulto.getApPaterno());
        apellidoMatenroAE.setText(adulto.getApMaterno());
        correoAE.setText(adulto.getCorreo());
        numeroAE.setText(adulto.getTelefono());
        passwordAE.setText(adulto.getContrasenia());
        etFecha.setText(adulto.getFechaNacimiento());

        switch(adulto.getSexo()){
            case 0:
                femeninoAE.setChecked(true);
                break;
            case 1:
                masculinoAE.setChecked(true);
                break;
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.selFAE:
                obtenerFecha();
                break;
        }
    }

    public void getSexoAE(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.selFAE:
                Sexo =0;
                break;
            case R.id.sexMAE:
                Sexo=1;
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
                etFecha.setText(year+GUION+mesFormateado+GUION+diaFormateado);
            }
        },ano,mes,dia);
        recogerFecha.show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }


}
