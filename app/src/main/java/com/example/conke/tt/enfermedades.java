package com.example.conke.tt;



public class enfermedades {
    private int id;
    private String nombre;

    public enfermedades(int id, String nombre){
        this.id=id;
        this.nombre=nombre;
    }

    public void setId(int id){
        this.id=id;
    }
    public void setNombre(String nombre){
        this.nombre=nombre;
    }

    public int getId(){
        return id;
    }
    public String getNombre(String nombre){
        return nombre;
    }

}
