package com.example.conke.tt;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
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
 * {@link PersonasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PersonasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonasFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener  {
    RecyclerView recyclerAM;
    ArrayList<adultoMayor> listaAM = new ArrayList<adultoMayor>();
    adaptadorAM adaptador;
    LinearLayoutManager layoutManagerAM;
    JsonObjectRequest jsonObjectRequest;
    ProgressDialog progressDialog;
    FloatingActionButton fab;
    String idAM=" ";
    senderIdAdulto senderIdAdulto;
    int flag=0;
    int form =0;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PersonasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonasFragment newInstance(String param1, String param2) {
        PersonasFragment fragment = new PersonasFragment();
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
        View view=inflater.inflate(R.layout.fragment_personas, container, false);
        final MainActivity myActivity = (MainActivity) getActivity();

        form = this.getArguments().getInt("form",0);
        recyclerAM = (RecyclerView) view.findViewById(R.id.f_recycler_AM);
        layoutManagerAM = new LinearLayoutManager(getContext());
        recyclerAM.setLayoutManager(layoutManagerAM);
        adaptador = new adaptadorAM(listaAM);
        recyclerAM.setAdapter(adaptador);
        fab = (FloatingActionButton) view.findViewById(R.id.f_fab_add_am);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),registrarAdulto.class);
                getActivity().startActivity(i);

            }
        });

        adaptador.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idAM = listaAM.get(recyclerAM.getChildAdapterPosition(view)).getIdPersona().trim();
                if(form ==1){
                     setData(idAM);
                     getActivity().getFragmentManager().popBackStack();
                     getActivity().onBackPressed();


                }else{
                consultaAM();
                }
            }
        });

        if(form ==1) {
            fab.setVisibility(View.GONE);
        }

        consultaWs();
        // Inflate the layout for this fragment
        return view;

    }


    private void consultaWs() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        String url =getResources().getString(R.string.ipconfig)+"AM";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }


    private void consultaAM() {
        progressDialog = new ProgressDialog(getContext());
        flag=1;
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        idAM.trim();
        String url =getResources().getString(R.string.ipconfig)+"AM/"+idAM;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);


    }



    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"No se pudo conectar con el servidor",Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR",error.toString());
        progressDialog.hide();

    }

    @Override
    public void onResponse(JSONObject response) {
        adultoMayor adultoM = new adultoMayor();
        JSONObject jsonObject = null;

        if (flag ==1){

            try {
                JSONArray json = response.optJSONArray("Datos_AM");
                jsonObject=json.getJSONObject(0);
                adultoM = new adultoMayor();
                adultoM.setIdPersona(jsonObject.optString("IdPersona"));
                adultoM.setApMaterno(jsonObject.optString("ApMat"));
                adultoM.setApPaterno(jsonObject.optString("ApPat"));
                adultoM.setFechaNacimiento(jsonObject.optString("FechaNac"));
                adultoM.setNombre(jsonObject.optString("Nombre"));
                adultoM.setSexo(jsonObject.optInt("Sexo"));
                progressDialog.hide();
                flag=0;
                if(form==1){
                   // goTosupervicion();
                }else{
                    update_AM(adultoM);
                }


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(),"No se pudo conectar con el servidor"+e.toString(),Toast.LENGTH_LONG).show();
                System.out.println();
                Log.d("ERROR",e.toString());
                progressDialog.hide();
            }


        }
        else{
            try {
                JSONArray json = response.optJSONArray("AdultosMayores");
                int tamanio = json.length();

                for (int i=0; i<json.length(); i++){
                    adultoM = new adultoMayor();
                    jsonObject=json.getJSONObject(i);
                    adultoM.setNombre(jsonObject.optString("Nombre"));
                    adultoM.setIdPersona(jsonObject.optString("idAdMayor"));
                    listaAM.add(adultoM);
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

    private void goTosupervicion() {
        Intent intent = new Intent(getActivity(),SupervicionAdulto.class);
        intent.putExtra("idAM",idAM);
        getActivity().startActivity(intent);
    }

    private void update_AM(adultoMayor uAM) {
        Intent intent = new Intent(getActivity(),registrarAdulto.class);
        intent.putExtra("adultoMayor",uAM);
        intent.putExtra("update",1);
        getActivity().startActivity(intent);
    }

    public void DeleteAlert(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Seguro que desea eliminar a este usuario")
                .setTitle("Eliminar Usuario")
                .setCancelable(false)
                .setPositiveButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getContext(),"Eliminando",Toast.LENGTH_LONG).show();
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getContext(),"Cancelado",Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
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
    public interface senderIdAdulto{
        public void sendAdultoId(String idAM);

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

    private void setData(String idAM){
        ((MainActivity)getActivity()).setidAdmayor(idAM);
    }


}
