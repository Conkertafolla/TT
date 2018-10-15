package com.example.conke.tt;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;


public  class adultoEncargado extends persona implements Parcelable{
  private String correo;
  private String telefono;
  private String contrasenia;


  public adultoEncargado(){
    super();
  }

  public adultoEncargado (String idPersona,String nombre,String apPaterno,String apMaterno,String fechaNacimiento,int sexo, String correo, String telefono, String contrasenia){
    super(idPersona,nombre,apPaterno,apMaterno,fechaNacimiento,sexo);
    this.correo=correo;
    this.telefono=telefono;
    this.contrasenia=contrasenia;

  }
  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getContrasenia() {
    return contrasenia;
  }

  public void setContrasenia(String contrasenia) {
    this.contrasenia = contrasenia;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Parcelable.Creator<adultoEncargado> CREATOR = new Parcelable.Creator<adultoEncargado>() {
    public adultoEncargado createFromParcel(Parcel in) {
      return new adultoEncargado(in);
    }

    public adultoEncargado[] newArray(int size) {
      return new adultoEncargado[size];
    }
  };

  private adultoEncargado (Parcel in) {
    super(in);
    correo = in.readString();
    telefono = in.readString();
    contrasenia = in.readString();

  }

  public void writeToParcel (Parcel out,int flags){
    super.writeToParcel(out, flags);
    out.writeString(correo);
    out.writeString(telefono);
    out.writeString(contrasenia);
  }

}