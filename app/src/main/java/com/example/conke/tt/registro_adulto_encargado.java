package com.example.conke.tt;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class registro_adulto_encargado extends AppCompatActivity implements View.OnClickListener,Response.Listener<JSONObject>,Response.ErrorListener,Dialogo.OnDialogListener {
    private static final String CERO = "0";
    private static final String BARRA = "/";
    private static final String GUION = "-";
    public final Calendar c = Calendar.getInstance();
    Button enviar ;
    Button eliminar;
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
    ProgressDialog progressDialog;
    private int peticiontype=0;


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
        eliminar =findViewById(R.id.eliminarAE) ;
        masculinoAE.setChecked(true);
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
                if(checkValidation()){
                    if (flag == 1) {
                        peticiontype = 2;
                        updateAE();

                    } else {
                        peticiontype = 1;
                        registrarAE();
                    }
                }else{

                    showFormError();
                }

            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogo = new Dialogo();
                dialogo.show(getFragmentManager(),"undialogo");


            }
        });

    }

    private void showFormError() {

         Toast.makeText(this, "Error en los datos insertados", Toast.LENGTH_LONG).show();

    }

    private void deleteAE() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        String url = getResources().getString(R.string.ipconfig)+"dAE/"+IdAE.getText().toString().trim();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Request.Method.DELETE,url,null,this,this);
        VolleySingleton.getInstanciaVolley(this).addToRequestQueue(jsonObjectRequest);

    }

    private void registrarAE() {
        progressDialog = new ProgressDialog(this);
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
        progressDialog = new ProgressDialog(this);
        String url =getResources().getString(R.string.ipconfig)+"Ac_AE/"+IdAE.getText().toString().trim();

        HashMap<String, String> postParam= new HashMap<String, String>();
        postParam.put("Nombre",nombreAE.getText().toString());
        postParam.put("ApPat",apellidoPaternoAE.getText().toString());
        postParam.put("ApMat",apellidoMatenroAE.getText().toString());
        postParam.put("Contrasenia",passwordAE.getText().toString());
        postParam.put("Correo",correoAE.getText().toString());
        postParam.put("FechaNac",etFecha.getText().toString());
        postParam.put("Telefono",numeroAE.getText().toString());
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
        Toast.makeText(this,"No se pudo conectar con el servidor"+error.toString(),Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR",error.toString());
        progressDialog.hide();

    }

    @Override
    public void onResponse(JSONObject response) {

        JSONObject jsonObject = null;
        int respuesta=0;
        String message="";
        respuesta=response.optInt("datos");
        Log.i("Valor de consulta",String.valueOf(respuesta));
        if(respuesta ==1){
            switch (peticiontype){
                case 1:
                    message="Regitro realizado exitosamente";
                    break;
               case 2:
                   message="Regitro actualizado exitosamente";
                   break;
               case 3:
                    message="Regitro eliminado exitosamente";
                    break;
            }
            Toast.makeText(this,message,Toast.LENGTH_LONG).show();
            finish();
            progressDialog.hide();
        }


    }


    @Override
    public void OnPositiveButtonClicked() {
        peticiontype=3;
        deleteAE();
    }

    @Override
    public void OnNegativeButtonClicked() {
        Toast.makeText(this,"Operación cancelada",Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnNeutralButtonClicked() {

    }

    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.isOnlyText(nombreAE,true))
            ret = false;
        if (!Validation.isOnlyText(apellidoPaternoAE,true))
            ret = false;
        if (!Validation.isOnlyText(apellidoMatenroAE,true))
            ret = false;
        if (!Validation.isEmailAddress(correoAE, true))
            ret = false;
        if (!Validation.isPhoneNumber(numeroAE, true))
            ret = false;
        if (!Validation.hasText(passwordAE))
            ret = false;
        if (!Validation.isDate(etFecha, true))
            ret = false;

        return ret;
    }
}
