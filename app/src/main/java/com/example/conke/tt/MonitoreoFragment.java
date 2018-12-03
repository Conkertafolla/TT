package com.example.conke.tt;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MonitoreoFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int flag=0;
    String idAM = "";
    Button btn_visualizar_temperatura;
    Button btn_visualizar_pulso;
    Button btn_visualizar_localizacion;
    TextView last_heart;
    TextView laste_temperature;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MonitoreoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MonitoreoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonitoreoFragment newInstance(String param1, String param2) {
        MonitoreoFragment fragment = new MonitoreoFragment();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    /*checar esta parte*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_monitoreo, container, false);
        MainActivity myActivity = (MainActivity) getActivity();


        View view  = inflater.inflate(R.layout.fragment_monitoreo, container, false);
        btn_visualizar_pulso= (Button) view.findViewById(R.id.btn_visualizar_pulso);
        btn_visualizar_temperatura= (Button) view.findViewById(R.id.btn_visualizar_temperatura);
        btn_visualizar_localizacion = (Button) view.findViewById(R.id.btn_localizacion);
        last_heart = view.findViewById(R.id.ritmo_label);
        laste_temperature = view.findViewById(R.id.temperatura_corporal_label);
        btn_visualizar_pulso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPulsos();

            }
        });
        btn_visualizar_temperatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {getTemperatures();

            }
        });
        btn_visualizar_localizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {getLocalizacion();

            }
        });
           getLastRegister();



        return view;

    }

    private void getLastRegister() {
        String url ="";
        if(idAM.equals("")){
             url=getResources().getString(R.string.ipconfig)+"lastRegister";
             flag=0;
        }else{
            url =getResources().getString(R.string.ipconfig)+"lastRegisterId/"+idAM;
            flag=1;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void getLocalizacion() {
        Intent i = new Intent(getActivity(),localizacion.class);
        startActivity(i);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"No se pudo conectar con el servidor",Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR",error.toString());

    }

    @Override
    public void onResponse(JSONObject response) {
        String idPersona=" ";
        String temperatura= "";
        String ritmo="";
        try {
            JSONArray json = response.optJSONArray("datos");
            JSONObject jsonObject=json.getJSONObject(0);
            idPersona=(jsonObject.optString("idAdMayor"));
            temperatura=(jsonObject.optString("temperatura"));
            ritmo=(jsonObject.optString("ritmo"));
            setValues(idPersona,temperatura,ritmo);


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),"No se pudo conectar con el servidor"+e.toString(),Toast.LENGTH_LONG).show();
            System.out.println();
            Log.d("ERROR",e.toString());

        }

    }

    private void setValues(String idPersona, String temperatura, String ritmo) {
          setData(idPersona);
          last_heart.setText("Ultimo registro ritmo cardíaco: "+ritmo);
          laste_temperature.setText("Ultimo registro ritmo temperatura: "+temperatura);

    }

    private void setData(String idAM){
        ((MainActivity)getActivity()).setidAdmayor(idAM);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void getTemperatures() {
        Intent i = new Intent(getActivity(),registro_adulto_valores.class);
        i.putExtra("operation",1);
        i.putExtra("idAM",idAM);
        startActivity(i);
    }

    private void getPulsos() {
        Intent i = new Intent(getActivity(),registro_adulto_valores.class);
        i.putExtra("operation",2);
        i.putExtra("idAM",idAM);
        startActivity(i);

    }



    public void onResume(){
        super.onResume();
            this.idAM = ((MainActivity)getActivity()).getidAdmayor();
            getLastRegister();
    }

}
