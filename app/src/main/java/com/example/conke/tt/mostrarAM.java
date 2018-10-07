package com.example.conke.tt;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class mostrarAM extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
  RecyclerView recyclerAM;
  ArrayList<adultoMayor> listaAM = new ArrayList<adultoMayor>();
  adaptadorAM adaptador;
  LinearLayoutManager layoutManagerAM;
  JsonObjectRequest jsonObjectRequest;
  ProgressDialog progressDialog;
  FloatingActionButton fab;
  String idAM=" ";
  int flag=0;
  int form =0;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    form =getIntent().getIntExtra("form",0);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_mostrar_am);
    Toolbar toolbar = (Toolbar) findViewById(R.id.tool_adultos_mayor);
    setSupportActionBar(toolbar);

    recyclerAM = (RecyclerView) findViewById(R.id.recycler_AM);
    layoutManagerAM = new LinearLayoutManager(this);
    recyclerAM.setLayoutManager(layoutManagerAM);
    adaptador = new adaptadorAM(listaAM);
    recyclerAM.setAdapter(adaptador);
    fab = (FloatingActionButton) findViewById(R.id.fab_add_am);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent i = new Intent(mostrarAM.this,registrarAdulto.class);
        startActivity(i);

      }
    });

    adaptador.setOnclickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        idAM = listaAM.get(recyclerAM.getChildAdapterPosition(view)).getIdPersona().trim();
        consultaAM();
      }
    });

    if(form ==1) {
      CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
      p.setAnchorId(View.NO_ID);
      fab.setLayoutParams(p);
      fab.setVisibility(View.GONE);
    }

    consultaWs();
  }



  private void consultaWs() {
    progressDialog = new ProgressDialog(this);
    progressDialog.setMessage("Cargando...");
    progressDialog.show();
    String url =getResources().getString(R.string.ipconfig)+"AM";
    jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
    VolleySingleton.getInstanciaVolley(this).addToRequestQueue(jsonObjectRequest);
  }


  private void consultaAM() {
    progressDialog = new ProgressDialog(this);
    flag=1;
    progressDialog.setMessage("Cargando...");
    progressDialog.show();
    idAM.trim();
    String url =getResources().getString(R.string.ipconfig)+"AM/"+idAM;
    jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
    VolleySingleton.getInstanciaVolley(this).addToRequestQueue(jsonObjectRequest);


  }

  @Override
  public void onErrorResponse(VolleyError error) {
    Toast.makeText(this,"No se pudo conectar con el servidor",Toast.LENGTH_LONG).show();
    System.out.println();
    Log.d("ERROR",error.toString());
    progressDialog.hide();

  }

  @Override
  public void onResponse(JSONObject response) {
    adultoMayor adultoM = new adultoMayor();
    JSONObject jsonObject = null;

    if (flag ==1){

      try {
        JSONArray json = response.optJSONArray("Datos_AM");
        jsonObject=json.getJSONObject(0);
        adultoM.setIdPersona("IdPersona");
        adultoM.setApMaterno(jsonObject.optString("ApMat"));
        adultoM.setApPaterno(jsonObject.optString("ApPat"));
        adultoM.setFechaNacimiento(jsonObject.optString("FechaNac"));
        adultoM.setNombre(jsonObject.optString("Nombre"));
        adultoM.setSexo(jsonObject.optInt("Sexo"));
        progressDialog.hide();
        flag=0;
        if(form==1){
          goTosupervicion();
        }else{
          update_AM(adultoM);
        }


      } catch (JSONException e) {
        e.printStackTrace();
        Toast.makeText(this,"No se pudo conectar con el servidor"+e.toString(),Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR",e.toString());
        progressDialog.hide();
      }


    }
    else{
      try {
        JSONArray json = response.optJSONArray("AdultosMayores");
        int tamanio = json.length();

        for (int i=0; i<json.length(); i++){
          jsonObject=json.getJSONObject(i);
          adultoM.setNombre(jsonObject.optString("Nombre"));
          adultoM.setIdPersona(jsonObject.optString("idAdMayor"));
          listaAM.add(adultoM);
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

  private void goTosupervicion() {
    Intent intent = new Intent(this,SupervicionAdulto.class);
    intent.putExtra("idAM",idAM);
    startActivity(intent);
  }

  private void update_AM(adultoMayor uAM) {
    Intent intent = new Intent(this,registrarAdulto.class);
    intent.putExtra("adultoMayor",uAM);
    intent.putExtra("update",1);
    startActivity(intent);
  }

  public void DeleteAlert(View v){
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage("Seguro que desea eliminar a este usuario")
            .setTitle("Eliminar Usuario")
            .setCancelable(false)
            .setPositiveButton("Aceptar",
                    new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(),"Eliminando",Toast.LENGTH_LONG).show();
                        dialog.cancel();
                      }
                    })
            .setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getApplicationContext(),"Cancelado",Toast.LENGTH_LONG).show();
                dialog.cancel();
              }
            });
    AlertDialog alert = builder.create();
    alert.show();
  }
}
