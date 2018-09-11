package com.example.conke.tt;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by conke on 07/08/2018.
 */

public class adultoMayor extends persona implements Parcelable{

    private Bitmap foto;
    private List<enfermedad> enfermedades = new ArrayList<enfermedad>();

    public adultoMayor (){


    }

    public adultoMayor (String idPersona,String nombre,String apPaterno,String apMaterno,String fechaNacimiento,int sexo, Bitmap foto, List<enfermedad> enfermedades){
            super(idPersona,nombre,apPaterno,apMaterno,fechaNacimiento,sexo);
            this.foto = foto;
            this.enfermedades = enfermedades;

    }


    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public List<enfermedad> getEnfermedades() {
        return enfermedades;
    }

    public void setEnfermedades(List<enfermedad> enfermedades) {
        this.enfermedades = enfermedades;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected adultoMayor(Parcel in) {
        super(in);
        foto = in.readParcelable(Bitmap.class.getClassLoader());
        enfermedades = in.createTypedArrayList(enfermedad.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(foto, flags);
        dest.writeTypedList(enfermedades);
    }

    public static final Creator<adultoMayor> CREATOR = new Creator<adultoMayor>() {
        @Override
        public adultoMayor createFromParcel(Parcel in) {
            return new adultoMayor(in);
        }

        @Override
        public adultoMayor[] newArray(int size) {
            return new adultoMayor[size];
        }
    };

}
