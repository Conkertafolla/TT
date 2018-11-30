package com.example.conke.tt;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.MediaStore;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class registrarAdulto extends AppCompatActivity implements View.OnClickListener,Response.Listener<JSONObject>,Response.ErrorListener,Dialogo.OnDialogListener{
  private static final String CERO = "0";
  private static final String BARRA = "/";
  private static final String GUION = "-";
  private static final int PICK_IMAGE = 100;
  public final Calendar c = Calendar.getInstance();
  private RadioGroup sexR;
  private RadioButton masculino;
  private RadioButton femenino;
  private ImageButton fotoAM;
  private ArrayList<enfermedad> enfermedades;
  private final int ENFERMEDADES_CODE =1;
  private int Sexo =0;
  TextView idAM;
  Button enfermedadesBtn;
  Button enviar;
  Button eliminar;
  ProgressDialog progressDialog;
  int peticiontype=0;


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
    enviar= findViewById(R.id.Registrar);
    eliminar=findViewById(R.id.eliminarAE);
    idAM = findViewById(R.id.idAM);
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
      enviar.setText("Actualizar");
      fillForm(adulto);

    }
    enviar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(checkValidation()){
          if (flag == 1) {
            peticiontype = 2;
            updateAM();

          } else {
            peticiontype = 1;
              registrarAM();
              sendEnfermedades();
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

    private void sendEnfermedades() {

        HashMap<String, JSONArray> postParam= new HashMap<>();
       if (enfermedades != null){
           progressDialog = new ProgressDialog(this);
           String url =getResources().getString(R.string.ipconfig)+"iEnfermedades";
           JSONArray array=new JSONArray();

           for(int i=0;i<enfermedades.size();i++){
               JSONObject obj=new JSONObject();
               try {
                   obj.put("Id",enfermedades.get(i).getIdEnfermedad());
                   obj.put("Nombre",enfermedades.get(i).getNombre());
               } catch (JSONException e) {
                   e.printStackTrace();
               }
               array.put(obj);
           }

        postParam.put("enfermedades",array);
           JSONObject jsonObject = new JSONObject(postParam);
           JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Request.Method.POST,url,jsonObject,this,this);
           VolleySingleton.getInstanciaVolley(this).addToRequestQueue(jsonObjectRequest);
       }

    }

    private void showFormError() {

    Toast.makeText(this, "Error en los datos insertados", Toast.LENGTH_LONG).show();

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
        etFecha.setText(year+GUION+mesFormateado+GUION+diaFormateado);
      }
    },ano,mes,dia);
    recogerFecha.show();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == ENFERMEDADES_CODE){
      if (resultCode == Activity.RESULT_OK){

          enfermedades = data.getParcelableArrayListExtra("enfermedades");
          //Bundle bundle = data.getExtras();
          //enfermedades = (ArrayList<enfermedad>) bundle.getSerializable("enfermedades");
          enfermedades.size();
          for(int i=0 ; i<enfermedades.size();i++){
              Log.i("id",enfermedades.get(i).getIdEnfermedad());
              Log.i("nombre",enfermedades.get(i).getNombre());
          }
      }

      }
      if(resultCode == RESULT_CANCELED){


      }

    }


  private void fillForm(adultoMayor adulto) {
    nombreAM.setText(adulto.getNombre());
    apellidoPaternoAM.setText(adulto.getApPaterno());
    apellidoMaternoAM.setText(adulto.getApMaterno());
    etFecha.setText(adulto.getFechaNacimiento());
    idAM.setText(adulto.getIdPersona());


    switch(adulto.getSexo()){
      case 0:
        femenino.setChecked(true);
        break;
      case 1:
        masculino.setChecked(true);
        break;
    }


  }

  private void deleteAM() {
    progressDialog = new ProgressDialog(this);
    progressDialog.setMessage("Cargando...");
    progressDialog.show();
    String url = getResources().getString(R.string.ipconfig)+"dAE/"+idAM.getText().toString().trim();
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Request.Method.DELETE,url,null,this,this);

    VolleySingleton.getInstanciaVolley(this).addToRequestQueue(jsonObjectRequest);

  }

  private void registrarAM() {
    progressDialog = new ProgressDialog(this);
    String url =getResources().getString(R.string.ipconfig)+"iAM";
    HashMap<String, String> postParam= new HashMap<String, String>();
    postParam.put("Nombre",nombreAM.getText().toString());
    postParam.put("ApPat",apellidoPaternoAM.getText().toString());
    postParam.put("ApMat",apellidoMaternoAM.getText().toString());
    postParam.put("FechaNac",etFecha.getText().toString());
    postParam.put("Sexo", Integer.toString(Sexo));
    JSONObject jsonObject = new JSONObject(postParam);
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Request.Method.POST,url,jsonObject,this,this);
    VolleySingleton.getInstanciaVolley(this).addToRequestQueue(jsonObjectRequest);

  }

  private void updateAM() {
    progressDialog = new ProgressDialog(this);
    String url =getResources().getString(R.string.ipconfig)+"Ac_AM/"+idAM.getText().toString().trim();

    HashMap<String, String> postParam= new HashMap<String, String>();
    postParam.put("Nombre",nombreAM.getText().toString());
    postParam.put("ApPat",apellidoPaternoAM.getText().toString());
    postParam.put("ApMat",apellidoMaternoAM.getText().toString());
    postParam.put("FechaNac",etFecha.getText().toString());
    postParam.put("Sexo", Integer.toString(Sexo));
    JSONObject jsonObject = new JSONObject(postParam);
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Request.Method.PUT,url,jsonObject,this,this);
    VolleySingleton.getInstanciaVolley(this).addToRequestQueue(jsonObjectRequest);

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

  private boolean checkValidation() {
    boolean ret = true;
    if (!Validation.isOnlyText(nombreAM,true))
      ret = false;
    if (!Validation.isOnlyText(apellidoPaternoAM,true))
      ret = false;
    if (!Validation.isOnlyText(apellidoMaternoAM,true))
      ret = false;
    if (!Validation.isDate(etFecha, true))
      ret = false;
    return ret;



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
        default:
            message="Regitro realizado exitosamente";
      }
      Toast.makeText(this,message,Toast.LENGTH_LONG).show();
      finish();
      progressDialog.hide();
    }

  }

  @Override
  public void OnPositiveButtonClicked() {
    peticiontype=3;
    deleteAM();
  }

  @Override
  public void OnNegativeButtonClicked() {
    Toast.makeText(this,"Operación cancelada",Toast.LENGTH_LONG).show();
  }

  @Override
  public void OnNeutralButtonClicked() {

  }
}