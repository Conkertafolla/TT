package com.example.conke.tt;


import android.os.Parcel;
import android.os.Parcelable;

public class registro implements Parcelable {
    protected String nombre;
    protected String valor;
    protected String fecha;
    protected String hora;

    public registro(String nombre, String valor, String fecha, String hora) {
        this.nombre = nombre;
        this.valor = valor;
        this.fecha = fecha;
        this.hora = hora;
    }

    public registro(){

    }


    protected registro(Parcel in) {
        nombre = in.readString();
        valor = in.readString();
        fecha = in.readString();
        hora = in.readString();
    }

    public static final Creator<registro> CREATOR = new Creator<registro>() {
        @Override
        public registro createFromParcel(Parcel in) {
            return new registro(in);
        }

        @Override
        public registro[] newArray(int size) {
            return new registro[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nombre);
        parcel.writeString(valor);
        parcel.writeString(fecha);
        parcel.writeString(hora);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
