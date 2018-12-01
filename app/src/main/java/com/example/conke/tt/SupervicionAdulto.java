package com.example.conke.tt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

public class SupervicionAdulto extends AppCompatActivity  {
    String idAM = "";
    Button btn_visualizar_temperatura;
    Button btn_visualizar_pulso;
    Button btn_visualizar_localizacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervicion_adulto);
        Toolbar barra =findViewById(R.id.toolbar_supervicion);
        setSupportActionBar(barra);
        barra.setTitle("Supervición del adulto mayor");
        barra.setTitleTextColor(16777215);
        idAM =getIntent().getStringExtra("idAM");
        btn_visualizar_pulso=findViewById(R.id.btn_visualizar_pulso);
        btn_visualizar_temperatura=findViewById(R.id.btn_visualizar_temperatura);
        btn_visualizar_localizacion = findViewById(R.id.btn_localizacion);
        btn_visualizar_pulso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPulsos();

            }
        });

        btn_visualizar_temperatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTemperatures();

            }
        });






    }

    private void getTemperatures() {
        Intent i = new Intent(SupervicionAdulto.this,registro_adulto_valores.class);
        i.putExtra("operation",1);
        i.putExtra("idAM",idAM);
        startActivity(i);
    }

    private void getPulsos() {
        Intent i = new Intent(SupervicionAdulto.this,registro_adulto_valores.class);
        i.putExtra("operation",2);
        i.putExtra("idAM",idAM);
        startActivity(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notificaciones, menu);
        return true;

    }



}