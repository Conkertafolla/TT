package com.example.conke.tt;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MonitoreoFragment.OnFragmentInteractionListener,
        PersonasFragment.OnFragmentInteractionListener,AEFragment.OnFragmentInteractionListener,notification.OnFragmentInteractionListener, Response.ErrorListener, Response.Listener<JSONObject>,View.OnClickListener {
        String idAdmayor="";
        ClipData.Item logout;


        adultoEncargado adulto;
        Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("news");



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Fragment fragment=  new MonitoreoFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_main,fragment).commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        adulto = getIntent().getParcelableExtra("adultoEncargado");





    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        TextView nameAE = findViewById(R.id.nameAE_menu);
        TextView mailAE = findViewById(R.id.mailAE_menu);
        mailAE.setText(adulto.getCorreo().toString());
        nameAE.setText(adulto.getNombre().toString());
        this.menu = menu;


        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clic
        // ks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id==R.id.logout){
            closeSession();

        }


        return super.onOptionsItemSelected(item);
    }

    private void closeSession() {
        finish();
        Intent intent = new Intent(this,login.class);
        startActivity(intent);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override


    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment miFrament =  null;
        boolean fragmentseleccionado = false;


        if (id == R.id.nav_inicio) {
            miFrament = new MonitoreoFragment();
            fragmentseleccionado = true;
        } else if (id == R.id.nav_notification) {
            miFrament = new notification();
            fragmentseleccionado = true;

        } else if (id == R.id.nav_select) {
            Bundle form = new Bundle();
            form.putInt("form",1);
            miFrament = new PersonasFragment();
            miFrament.setArguments(form);
            fragmentseleccionado = true;

        } else if (id == R.id.nav_gestionar) {
            Bundle pass = new Bundle();
            pass.putInt("form",0);
            miFrament = new PersonasFragment();
            miFrament.setArguments(pass);
            fragmentseleccionado = true;
        } else if (id == R.id.nav_enc) {
            miFrament = new AEFragment();
            fragmentseleccionado = true;
        } else if (id == R.id.nav_setting) {
            /*miFrament =  new FormAdulMayFragment();
            fragmentseleccionado = true;*/
        }

        if(fragmentseleccionado==true){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, miFrament).addToBackStack(null).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public String getidAdmayor(){
        return this.idAdmayor;
    }

    public void setidAdmayor(String idAdmayor){
        this.idAdmayor = idAdmayor;
    }

    public void registerToken(String token){
        String url =getResources().getString(R.string.ipconfig)+"tokenRegister";
        HashMap<String, String> postParam= new HashMap<String, String>();
        postParam.put("token",token);
        JSONObject jsonObject = new JSONObject(postParam);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Request.Method.POST,url,jsonObject,this,this);
        VolleySingleton.getInstanciaVolley(this).addToRequestQueue(jsonObjectRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }

    @Override
    public void onClick(View view) {

    }
}
