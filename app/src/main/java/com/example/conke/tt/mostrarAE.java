package com.example.conke.tt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class mostrarAE extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
  RecyclerView recyclerAE;
  ArrayList<adultoEncargado> listaAE = new ArrayList<adultoEncargado>();
  adaptadorAE adaptador;
  LinearLayoutManager layoutManagerAE;
  JsonObjectRequest jsonObjectRequest;
  ProgressDialog progressDialog;
  String idAe= "";
  int flag =0;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    recyclerAE = (RecyclerView) findViewById(R.id.recycler_ae);
    setContentView(R.layout.activity_mostrar_ae);
    setTitle(null);
    Toolbar topToolBar = (Toolbar)findViewById(R.id.tool_adultos_encargados);
    topToolBar.setTitle("Adultos Encargados");
    layoutManagerAE = new LinearLayoutManager(this);
    recyclerAE.setLayoutManager(layoutManagerAE);
    adaptador = new adaptadorAE(listaAE);
    adaptador.setOnclickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        idAe = listaAE.get(recyclerAE.getChildAdapterPosition(view)).getIdPersona().trim();
        consultaAE();
      }
    });

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_ae);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent i = new Intent(mostrarAE.this,registro_adulto_encargado.class);
        startActivity(i);

      }
    });


    recyclerAE.setAdapter(adaptador);

    consultaWs();
  }

  private void consultaAE() {
    progressDialog = new ProgressDialog(this);
    flag=1;
    progressDialog.setMessage("Cargando...");
    progressDialog.show();
    idAe.trim();
    String url =getResources().getString(R.string.ipconfig)+"AE/"+idAe;
    jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
    VolleySingleton.getInstanciaVolley(this).addToRequestQueue(jsonObjectRequest);


  }

  private void consultaWs() {
    progressDialog = new ProgressDialog(this);
    progressDialog.setMessage("Cargando...");
    progressDialog.show();
    String url =getResources().getString(R.string.ipconfig)+"AE";
    jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
    VolleySingleton.getInstanciaVolley(this).addToRequestQueue(jsonObjectRequest);

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
    adultoEncargado adulto = new adultoEncargado();
    JSONObject jsonObject = null;
    if (flag ==1){

      try {
        JSONArray json = response.optJSONArray("Datos_AE");
        jsonObject=json.getJSONObject(0);
        adulto.setIdPersona(jsonObject.optString("idPersona"));
        adulto.setApMaterno(jsonObject.optString("ApMat"));
        adulto.setApPaterno(jsonObject.optString("ApPat"));
        adulto.setContrasenia(jsonObject.optString("Contrasenia"));
        adulto.setCorreo(jsonObject.optString("Correo"));
        adulto.setFechaNacimiento(jsonObject.optString("FechaNac"));
        adulto.setNombre(jsonObject.optString("Nombre"));
        adulto.setTelefono(jsonObject.optString("NumTel"));
        adulto.setSexo(jsonObject.optInt("Sexo"));
        progressDialog.hide();
        adaptador.notifyDataSetChanged();
        update_AE(adulto);


      } catch (JSONException e) {
        e.printStackTrace();
        Toast.makeText(this,"No se pudo conectar con el servidor"+e.toString(),Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR",e.toString());
        progressDialog.hide();
      }


    }else{
      try {
        JSONArray json = response.optJSONArray("AdultoEncargados");
        int tamanio = json.length();

        for (int i=0; i<json.length(); i++){
          adulto = new adultoEncargado();
          jsonObject=json.getJSONObject(i);
          adulto.setCorreo(jsonObject.optString("Correo"));
          adulto.setNombre(jsonObject.optString("Nombre"));
          adulto.setIdPersona(jsonObject.optString("idPersona"));
          listaAE.add(adulto);
        }
        progressDialog.hide();
        adaptador.notifyDataSetChanged();



      } catch (JSONException e) {
        e.printStackTrace();
        Toast.makeText(this,"No se pudo conectar con el servidor"+e.toString(),Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR",e.toString());
        progressDialog.hide();
      }
    }
  }

  private void update_AE(adultoEncargado uAE) {
    Intent intent = new Intent(this,registro_adulto_encargado.class);
    intent.putExtra("adultoEncargado",uAE);
    intent.putExtra("update",1);
    startActivity(intent);
  }
}