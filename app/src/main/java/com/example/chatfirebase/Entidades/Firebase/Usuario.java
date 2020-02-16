package com.example.chatfirebase.Entidades.Firebase;

import com.google.firebase.database.ServerValue;

public class Usuario {

    private String fotoPerfilUrl;
    private String nombre;
    private String correo;
    private long fechaDeNacimiento;
    private String genero;
    //private Object createdTimesTamp;

    public Usuario() {
        //createdTimesTamp = ServerValue.TIMESTAMP;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public long getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(long fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    /*public Object getCreatedTimestamp(){
        return createdTimesTamp;
    }*/
}
