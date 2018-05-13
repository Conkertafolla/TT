package com.example.conke.tt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menu extends AppCompatActivity {
Button gestionarAdultos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Menu principal");
        setContentView(R.layout.activity_menu);
        gestionarAdultos = findViewById(R.id.gestionarAdultos);
        gestionarAdultos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(menu.this, registrarAdulto.class);
                startActivity(i);
            }
        });

    }

}
