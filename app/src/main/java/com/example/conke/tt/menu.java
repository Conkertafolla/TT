package com.example.conke.tt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class menu extends AppCompatActivity {
Button gestionarAdultos;
Button getGestionarEncargado;
Button getSupervisar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_menu);
        gestionarAdultos = findViewById(R.id.gestionarAdultos);
        getGestionarEncargado = findViewById(R.id.gestionarUsuarios);
        getSupervisar = findViewById(R.id.supervision);
        Toolbar barra =findViewById(R.id.tool_menu);
        setSupportActionBar(barra);
        barra.setTitle("Menu principal");
        barra.setTitleTextColor(16777215);



        gestionarAdultos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(menu.this, mostrarAM.class);
                startActivity(i);
            }
        });
        getGestionarEncargado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(menu.this,mostrarAE.class);
                startActivity(i);
            }
        });

        getSupervisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(menu.this ,mostrarAM.class);
                i.putExtra("form",1);
                startActivity(i);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notificaciones, menu);
        return true;

    }


}
