package com.example.conke.tt;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;



public class MonitoreoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String idAM = "";
    Button btn_visualizar_temperatura;
    Button btn_visualizar_pulso;

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
    }

}
