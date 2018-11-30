package com.example.conke.tt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class login extends AppCompatActivity implements View.OnClickListener,Response.Listener<JSONObject>,Response.ErrorListener{

    TextView login;
    EditText mail ;
    EditText password ;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.loginButton);
        mail = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });

    }

    private void checkLogin() {
        progressDialog = new ProgressDialog(this);
        String url =getResources().getString(R.string.ipconfig)+"Login";
        HashMap<String, String> postParam= new HashMap<String, String>();
        postParam.put("user",mail.getText().toString());
        postParam.put("password",password.getText().toString());
        JSONObject jsonObject = new JSONObject(postParam);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Request.Method.POST,url,jsonObject,this,this);
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
        adultoEncargado adultoE = new adultoEncargado();
        JSONObject jsonObject = null;
        String status;
        try {
            JSONArray json = response.optJSONArray("Login");
            jsonObject=json.getJSONObject(0);
            status=jsonObject.optString("status");
            if(!(status.equals("Fallido"))){
                adultoE.setIdPersona(jsonObject.optString("idPersona"));
                adultoE.setNombre(jsonObject.optString("Nombre"));
                adultoE.setCorreo(jsonObject.optString("Correo"));
                progressDialog.hide();
                Intent intent = new Intent(this,MainActivity.class);
                intent.putExtra("adultoEncargado",adultoE);
                startActivity(intent);
            }else{
                Toast.makeText(this,"Datos incorrectos",Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,"No se pudo conectar con el servidor"+e.toString(),Toast.LENGTH_LONG).show();
            System.out.println();
            Log.d("ERROR",e.toString());
            progressDialog.hide();
        }

    }

    @Override
    public void onClick(View view) {

    }






}
