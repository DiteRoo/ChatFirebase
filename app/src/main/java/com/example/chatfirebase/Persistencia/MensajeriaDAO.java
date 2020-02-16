package com.example.chatfirebase.Persistencia;

import com.example.chatfirebase.Entidades.Firebase.Mensaje;
import com.example.chatfirebase.Utilidades.Constantes;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MensajeriaDAO {

    private static MensajeriaDAO mensajeriaDAO;

    private FirebaseDatabase database;
    private DatabaseReference referenceMensajeria;

    public static MensajeriaDAO getInstancia(){
        if(mensajeriaDAO==null) mensajeriaDAO = new MensajeriaDAO();
        return mensajeriaDAO;
    }

    private MensajeriaDAO(){
        database = FirebaseDatabase.getInstance();
        referenceMensajeria = database.getReference(Constantes.NODO_USUARIOS);
        //storage = FirebaseStorage.getInstance();
        //referenceUsuarios = database.getReference(Constantes.NODO_USUARIOS);
        //referenceFotoDePerfil = storage.getReference("Fotos/FotoPerfil/"+getKeyUsuario());
    }

    public void nuevoMensaje(String keyEmisor, String keyReceptor, Mensaje mensaje){
        DatabaseReference referenceEmisor = referenceMensajeria.child(keyEmisor).child(keyReceptor);
        DatabaseReference referenceReceptor = referenceMensajeria.child(keyReceptor).child(keyEmisor);
        referenceEmisor.push().setValue(mensaje);
        referenceReceptor.push().setValue(mensaje);
    }

}
