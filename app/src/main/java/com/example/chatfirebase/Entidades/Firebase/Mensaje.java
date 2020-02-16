package com.example.chatfirebase.Entidades.Firebase;

import com.google.firebase.database.ServerValue;

public class Mensaje {

    private String mensaje;
    private String urlFoto;
    private boolean contieneFoto;
    private String keyEmisor;  //Key del usuario que envia el mensaje
    private Object createdTimestamp;

    public Mensaje(){
        createdTimestamp = ServerValue.TIMESTAMP;
    }


    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }


    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public boolean getContieneFoto() {
        return contieneFoto;
    }

    public void setContieneFoto(boolean contieneFoto) {
        this.contieneFoto = contieneFoto;
    }

    public String getKeyEmisor() {
        return keyEmisor;
    }

    public void setKeyEmisor(String keyEmisor) {
        this.keyEmisor = keyEmisor;
    }

    public Object getCreatedTimestamp() {
        return createdTimestamp;
    }
}
