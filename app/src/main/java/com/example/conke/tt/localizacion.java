package com.example.conke.tt;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class localizacion extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{
    SwitchCompat Dormitorio;
    SwitchCompat Sala;
    Handler mHandler;
    HashMap<String, Object> habitaciones = new HashMap <String, Object> ();
    public class habitacion{


        String Descripcion;
        String idHabitacion;
        int estado;

        public habitacion (String Descripcion, int Estado){
            this.Descripcion=Descripcion;
            this.estado = Estado;
        }

        public habitacion (){

        }

        public String getDescripcion() {
            return Descripcion;
        }

        public void setDescripcion(String descripcion) {
            Descripcion = descripcion;
        }

        public int getEstado() {
            return estado;
        }

        public void setEstado(int estado) {
            this.estado = estado;
        }

        public String getIdHabitacion() {
            return idHabitacion;
        }

        public void setIdHabitacion(String idHabitacion) {
            this.idHabitacion = idHabitacion;
        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacion);
        Dormitorio= findViewById(R.id.dormitorio);
        Sala= findViewById(R.id.sala);
        this.mHandler = new Handler();
        this.mHandler.postDelayed(m_Runnable,3000);

        Dormitorio.setClickable(false);
        Sala.setClickable(false);
        getLocalizacion();
    }

    private void getLocalizacion() {
        String url =getResources().getString(R.string.ipconfig)+"localizacion";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getInstanciaVolley(this).addToRequestQueue(jsonObjectRequest);
    }


    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            Toast.makeText(localizacion.this,"Actualizando",Toast.LENGTH_SHORT).show();
            getLocalizacion();
            mHandler.postDelayed(this,3000);
        }

    };
    
    @Override
    protected void onPause(){
        super.onPause();
        mHandler.removeCallbacks(m_Runnable);
    }

    @Override
    public void onBackPressed() {
        mHandler.removeCallbacksAndMessages(null);

        super.onBackPressed();
    }


    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        JSONObject jsonObject = null;
        habitaciones.clear();

        try {
            JSONArray json = response.optJSONArray("datos");


            for (int i=0; i<json.length(); i++){
                habitacion hab = new habitacion();
                jsonObject=json.getJSONObject(i);
                hab.setDescripcion(jsonObject.optString("Descripcion"));
                hab.setEstado(jsonObject.optInt("Estado"));
                hab.setIdHabitacion(jsonObject.optString("idHabitacion"));
                habitaciones.put(hab.Descripcion,hab);

            }

            setLocalizacion();




        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,"No se pudo conectar con el servidor"+e.toString(),Toast.LENGTH_LONG).show();
            System.out.println();
            Log.d("ERROR",e.toString());

        }

    }

    private void setLocalizacion() {
        habitacion sala = new habitacion();
        habitacion habitacion = new habitacion();
        habitacion= (localizacion.habitacion) habitaciones.get("Habitacion");
        sala= (localizacion.habitacion) habitaciones.get("Sala");

        if(habitacion.getEstado()==0){
            Dormitorio.setChecked(false);

        }else{
            Dormitorio.setChecked(true);
        }

        if(sala.getEstado()==0){
            Sala.setChecked(false);

        }else{
            Sala.setChecked(true);
        }
    }
}
