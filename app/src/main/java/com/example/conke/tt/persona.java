package com.example.conke.tt;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by conke on 07/08/2018.
 */

public abstract class persona implements Parcelable{
    protected String idPersona;
    protected String nombre;
    protected String apPaterno;
    protected String apMaterno;
    protected String fechaNacimiento;
    protected int sexo;

    public persona (){

    }

    public persona (String idPersona,String nombre,String apPaterno,String apMaterno,String fechaNacimiento,int sexo){
            this.idPersona = idPersona;
            this.nombre=nombre;
            this.apPaterno=apPaterno;
            this.apMaterno=apMaterno;
            this.fechaNacimiento=fechaNacimiento;
            this.sexo=sexo;
    }

    protected persona(Parcel in) {
        idPersona = in.readString();
        nombre = in.readString();
        apPaterno = in.readString();
        apMaterno = in.readString();
        fechaNacimiento = in.readString();
        sexo = in.readInt();
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idPersona);
        parcel.writeString(nombre);
        parcel.writeString(apPaterno);
        parcel.writeString(apMaterno);
        parcel.writeString(fechaNacimiento);
        parcel.writeInt(sexo);
    }


    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }


}
