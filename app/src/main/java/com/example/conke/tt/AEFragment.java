package com.example.conke.tt;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AEFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AEFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AEFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {

    RecyclerView recyclerAE;
    ArrayList<adultoEncargado> listaAE = new ArrayList<adultoEncargado>();
    adaptadorAE adaptador;
    LinearLayoutManager layoutManagerAE;
    JsonObjectRequest jsonObjectRequest;
    ProgressDialog progressDialog;
    String idAe= "";
    int flag =0;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AEFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AEFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AEFragment newInstance(String param1, String param2) {
        AEFragment fragment = new AEFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ae, container, false);
        recyclerAE = (RecyclerView) view.findViewById(R.id.f_recycler_ae);
        layoutManagerAE = new LinearLayoutManager(this.getContext());
        recyclerAE.setLayoutManager(layoutManagerAE);
        adaptador = new adaptadorAE(listaAE);
        adaptador.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idAe = listaAE.get(recyclerAE.getChildAdapterPosition(view)).getIdPersona().trim();
                consultaAE();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.f_fab_add_ae);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),registro_adulto_encargado.class);
                getActivity().startActivity(i);

            }
        });


        recyclerAE.setAdapter(adaptador);

        consultaWs();
        // Inflate the layout for this fragment
        return view;
    }
    private void consultaAE() {
        progressDialog = new ProgressDialog(getContext());
        flag=1;
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        idAe.trim();
        String url =getResources().getString(R.string.ipconfig)+"AE/"+idAe;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);


    }

    private void consultaWs() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        String url =getResources().getString(R.string.ipconfig)+"AE";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);

    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"No se pudo conectar con el servidor"+error.toString(),Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR",error.toString());
        progressDialog.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
        adultoEncargado adulto = new adultoEncargado();
        JSONObject jsonObject = null;
        if (flag ==1){

            try {
                JSONArray json = response.optJSONArray("Datos_AE");
                jsonObject=json.getJSONObject(0);
                adulto.setIdPersona(jsonObject.optString("idPersona"));
                adulto.setApMaterno(jsonObject.optString("ApMat"));
                adulto.setApPaterno(jsonObject.optString("ApPat"));
                adulto.setContrasenia(jsonObject.optString("Contrasenia"));
                adulto.setCorreo(jsonObject.optString("Correo"));
                adulto.setFechaNacimiento(jsonObject.optString("FechaNac"));
                adulto.setNombre(jsonObject.optString("Nombre"));
                adulto.setTelefono(jsonObject.optString("NumTel"));
                adulto.setSexo(jsonObject.optInt("Sexo"));
                progressDialog.hide();
                adaptador.notifyDataSetChanged();
                update_AE(adulto);


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(),"No se pudo conectar con el servidor"+e.toString(),Toast.LENGTH_LONG).show();
                System.out.println();
                Log.d("ERROR",e.toString());
                progressDialog.hide();
            }


        }else{
            try {
                JSONArray json = response.optJSONArray("AdultoEncargados");
                int tamanio = json.length();

                for (int i=0; i<json.length(); i++){
                    adulto = new adultoEncargado();
                    jsonObject=json.getJSONObject(i);
                    adulto.setCorreo(jsonObject.optString("Correo"));
                    adulto.setNombre(jsonObject.optString("Nombre"));
                    adulto.setIdPersona(jsonObject.optString("idPersona"));
                    listaAE.add(adulto);
                }
                progressDialog.hide();
                adaptador.notifyDataSetChanged();



            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(),"No se pudo conectar con el servidor"+e.toString(),Toast.LENGTH_LONG).show();
                System.out.println();
                Log.d("ERROR",e.toString());
                progressDialog.hide();
            }
        }
    }

    private void update_AE(adultoEncargado uAE) {
        Intent intent = new Intent(getActivity(),registro_adulto_encargado.class);
        intent.putExtra("adultoEncargado",uAE);
        intent.putExtra("update",1);
        getActivity().startActivity(intent);
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
