package com.example.conke.tt;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class registro_adulto_valores extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {
    RecyclerView recyclerRegister;
    String idAM=" ";
    int operationCode=0;
    ArrayList<registro> registros = new ArrayList<>();
    adapterRegistro adaptador;
    LinearLayoutManager layoutManagerR;
    TextView valorHeader;
    JsonObjectRequest jsonObjectRequest;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_adulto_valores);
        recyclerRegister = findViewById(R.id.recycler_register);
        layoutManagerR= new LinearLayoutManager(this);
        recyclerRegister.setLayoutManager(layoutManagerR);
        valorHeader=findViewById(R.id.valor_header);
        adaptador = new adapterRegistro(registros);
        recyclerRegister.setAdapter(adaptador);
        idAM =getIntent().getStringExtra("idAM");
        operationCode =getIntent().getIntExtra("operation",0);
        consultaRegistros();


    }

    private void consultaRegistros() {
        String url="";
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        if(operationCode ==1){
            url =getResources().getString(R.string.ipconfig)+"Temperatura/"+idAM;
            valorHeader.setText("Temperatura");
        }
        if(operationCode ==2) {
            url = getResources().getString(R.string.ipconfig)+"Ritmo/" + idAM;
            valorHeader.setText("Ritmo");
        }
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        VolleySingleton.getInstanciaVolley(this).addToRequestQueue(jsonObjectRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

        JSONObject jsonObject = null;

        try {
            JSONArray json = response.optJSONArray("RegistroRitmo");
            int tamanio = json.length();

            for (int i=0; i<json.length(); i++){
                registro register = new registro();
                jsonObject=json.getJSONObject(i);
                register.setNombre(jsonObject.optString("Nombre"));
                register.setFecha(jsonObject.optString("Fecha"));
                register.setHora(jsonObject.optString("hora"));
                register.setValor(jsonObject.optString("Valor"));
                registros.add(register);
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