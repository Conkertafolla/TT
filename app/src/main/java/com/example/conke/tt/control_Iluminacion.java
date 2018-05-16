package com.example.conke.tt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class control_Iluminacion extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private SwitchCompat dormitorio;
    private SwitchCompat dormitorio2;
    private SwitchCompat sala;
    private SwitchCompat comedor;
    private SwitchCompat estudio;
    private SwitchCompat banio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control__iluminacion);
        setTitle("Control de iluminación");
        dormitorio=(SwitchCompat) findViewById(R.id.dormitorio1);
        dormitorio2=(SwitchCompat) findViewById(R.id.dormitorio2);
        sala=(SwitchCompat) findViewById(R.id.sala);
        comedor=(SwitchCompat) findViewById(R.id.comedor);
        estudio=(SwitchCompat) findViewById(R.id.estudio);
        banio=(SwitchCompat) findViewById(R.id.banio);

       dormitorio.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
       dormitorio2.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
       sala.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        estudio.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        banio.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        comedor.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);


    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String mensaje="Luz de ";
        switch (buttonView.getId()) {
            case R.id.dormitorio1:
                mensaje+=dormitorio.getText().toString();
                if(dormitorio.isChecked()){
                    mensaje+=" encendida";
                }
                else{
                    mensaje+=" apagada";
                }

                muestramensaje(mensaje);

                break;
            case R.id.dormitorio2:
                mensaje+=dormitorio2.getText().toString();
                if(dormitorio2.isChecked()){
                    mensaje+=" encendida";
                }
                else{
                    mensaje+=" apagada";
                }

                muestramensaje(mensaje);

                break;

            case R.id.sala:
                mensaje+=sala.getText().toString();
                if(sala.isChecked()){
                    mensaje+=" encendida";
                }
                else{
                    mensaje+=" apagada";
                }

                muestramensaje(mensaje);

                break;

            case R.id.comedor:
                mensaje+=comedor.getText().toString();
                if(comedor.isChecked()){
                    mensaje+=" encendida";
                }
                else{
                    mensaje+=" apagada";
                }

                muestramensaje(mensaje);

                break;

            case R.id.estudio:
                mensaje+=estudio.getText().toString();
                if(estudio.isChecked()){
                    mensaje+=" encendida";
                }
                else{
                    mensaje+=" apagada";
                }

                muestramensaje(mensaje);

                break;

            case R.id.banio:
                mensaje+=banio.getText().toString();
                if(banio.isChecked()){
                    mensaje+=" encendida";
                }
                else{
                    mensaje+=" apagada";
                }

                muestramensaje(mensaje);

                break;
        }

    }




    private void muestramensaje(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();

    }


}
