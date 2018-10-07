package com.example.conke.tt;

import android.os.Parcel;
import android.os.Parcelable;


public class enfermedad implements Parcelable {
  private String idEnfermedad;
  private String nombre;


  public enfermedad (){

  }

  public enfermedad(String idEnfermedad, String nombre) {
    this.idEnfermedad = idEnfermedad;
    this.nombre = nombre;
  }


  protected enfermedad(Parcel in) {
    idEnfermedad = new String();
    nombre = new String();

  }

  public static final Creator<enfermedad> CREATOR = new Creator<enfermedad>() {
    @Override
    public enfermedad createFromParcel(Parcel in) {
      return new enfermedad(in);
    }

    @Override
    public enfermedad[] newArray(int size) {
      return new enfermedad[size];
    }
  };

  public String getIdEnfermedad() {
    return idEnfermedad;
  }

  public void setIdEnfermedad(String idEnfermedad) {
    this.idEnfermedad = idEnfermedad;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(idEnfermedad);
    parcel.writeString(nombre);
  }

  public void readParcel(Parcel in) {
    idEnfermedad=in.readString();
    nombre=in.readString();
  }
}