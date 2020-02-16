package com.example.chatfirebase.Entidades.Logica;

import com.example.chatfirebase.Entidades.Firebase.Mensaje;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LMensaje {

    private Mensaje mensaje;
    private String key;
    private LUsuario lUsuario;

    public LMensaje(String key,Mensaje mensaje) {
        this.mensaje = mensaje;
        this.key = key;
    }



    public LUsuario getlUsuario() {
        return lUsuario;
    }

    public void setlUsuario(LUsuario lUsuario) {
        this.lUsuario = lUsuario;
    }


    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }

    public long getCreatedTimestampLong(){
        return (long) mensaje.getCreatedTimestamp();

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String obtenerFechaDeCreacion(){
        Date date = new Date(getCreatedTimestampLong());
        PrettyTime prettyTime = new PrettyTime(new Date(),Locale.getDefault());
        return prettyTime.format(date);
        /*SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date(getCreatedTimestampLong());
        return simpleDateFormat.format(date);*/
    }
}

