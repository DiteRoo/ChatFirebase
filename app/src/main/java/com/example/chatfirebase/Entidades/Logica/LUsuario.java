package com.example.chatfirebase.Entidades.Logica;

import com.example.chatfirebase.Entidades.Firebase.Usuario;
import com.example.chatfirebase.Persistencia.UsuarioDAO;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class LUsuario {

    private String key;
    private Usuario usuario;

    public LUsuario(String key, Usuario usuario) {
        this.key = key;
        this.usuario = usuario;
    }

    /*public long getCreatedTimestampLong(){
        return (long) usuario.getCreatedTimestamp();
    }*/

    public String obtenerFechaDeCreacion(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date(UsuarioDAO.getInstancia().fechaDeCreacionLong());
        return simpleDateFormat.format(date);
    }
    public String obtenerUltimaFechaDeConexion(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date(UsuarioDAO.getInstancia().fechaDeCreacionLong());
        return simpleDateFormat.format(date);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
