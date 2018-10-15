package com.example.conke.tt;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
 * {@link notification.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link notification#newInstance} factory method to
 * create an instance of this fragment.
 */
public class notification extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject> {

    RecyclerView recyclerRegister;
    String idAM=" ";
    int operationCode=0;
    ArrayList<registro> registros = new ArrayList<>();
    notificationAdapter adaptador;
    LinearLayoutManager layoutManagerR;
    JsonObjectRequest jsonObjectRequest;
    ProgressDialog progressDialog;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public notification() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment notification.
     */
    // TODO: Rename and change types and number of parameters
    public static notification newInstance(String param1, String param2) {
        notification fragment = new notification();
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
        View view=inflater.inflate(R.layout.fragment_notification, container, false);
        recyclerRegister = (RecyclerView) view.findViewById(R.id.notification_recycler);
        layoutManagerR = new LinearLayoutManager(getContext());
        recyclerRegister.setLayoutManager(layoutManagerR);
        adaptador = new notificationAdapter(registros);
        recyclerRegister.setAdapter(adaptador);
        consultaRegistros();


        return view;
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

    private void consultaRegistros() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        String url =getResources().getString(R.string.ipconfig)+"Notificaciones";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

        JSONObject jsonObject = null;

        try {
            JSONArray json = response.optJSONArray("Notificaciones");
            int tamanio = json.length();

            for (int i=0; i<json.length(); i++){
                registro register = new registro();
                jsonObject=json.getJSONObject(i);
                register.setNombre(jsonObject.optString("Nombre"));
                register.setFecha(jsonObject.optString("Fecha"));
                register.setHora(jsonObject.optString("hora"));
                register.setValor(jsonObject.optString("Valor"));
                register.setTipo(jsonObject.optInt("Tipo"));
                registros.add(register);
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
