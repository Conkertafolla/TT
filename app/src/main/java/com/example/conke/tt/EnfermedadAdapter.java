package com.example.conke.tt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;



public class EnfermedadAdapter extends ArrayAdapter<enfermedad> {
  public EnfermedadAdapter(Context context, ArrayList<enfermedad> enfermedades) {
    super(context, 0, enfermedades);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    // Get the data item for this position
    enfermedad enfermedad = getItem(position);
    // Check if an existing view is being reused, otherwise inflate the view
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.enfermedades, parent, false);
    }
    // Lookup view for data population
    TextView enfermedad_Name = (TextView) convertView.findViewById(R.id.Enfermedad_Name);
    TextView id_Enfermedad = (TextView) convertView.findViewById(R.id.id_enfermedad);
    // Populate the data into the template view using the data object
    enfermedad_Name.setText(enfermedad.getNombre());
    id_Enfermedad.setText(enfermedad.getIdEnfermedad());
    // Return the completed view to render on screen
    return convertView;
  }
}