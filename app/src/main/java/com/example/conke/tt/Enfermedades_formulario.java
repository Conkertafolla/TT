package com.example.conke.tt;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class Enfermedades_formulario extends AppCompatActivity {
    private ArrayList<enfermedad> enfermedades;
    private ListView lv1;
    private EditText et1;
    private int idEnfermedad;
    private EnfermedadAdapter adapter;
    private Button add_enfermedad;
    private Button save_enfermedad;
    private Button cancel_enferemedad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enfermedades_formulario);
        enfermedades = new ArrayList<enfermedad>();
        et1=(EditText)findViewById(R.id.editEnfermedadNombre);
        add_enfermedad = findViewById(R.id.agregar_enfermedad);
        save_enfermedad = findViewById(R.id.btn_guardar_enfermedad);
        cancel_enferemedad = findViewById(R.id.btn_cancelar_enfermedad);
        populateUsersList();

        save_enfermedad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putParcelableArrayListExtra("enfermedades",enfermedades);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
        cancel_enferemedad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED,returnIntent);
                finish();
            }
        });

    }

    private void populateUsersList() {

        // Create the adapter to convert the array to views
        adapter = new EnfermedadAdapter(this, enfermedades);
        // Attach the adapter to a ListView
        ListView lv1 = (ListView) findViewById(R.id.listViewEnfermedad);
        lv1.setAdapter(adapter);
    }

    public void agregar(View v){

        enfermedad auxiliar= new enfermedad(String.valueOf(enfermedades.size()+1),String.valueOf(et1.getText()));
        enfermedades.add(auxiliar);
        adapter.notifyDataSetChanged();
        et1.setText("");



    }




}