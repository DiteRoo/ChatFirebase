package com.example.chatfirebase.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatfirebase.Entidades.Usuario;
import com.example.chatfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroActivity extends AppCompatActivity {

    private EditText Nombre;
    private EditText TextEmail;
    private EditText TextPassword;
    private Button btnRegistrar;
    private ProgressDialog progressDialog;

    //Declaramos un objeto firebaseAuth
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        //Referenciamos los views
        Nombre = (EditText) findViewById(R.id.Name_id);
        TextEmail = (EditText) findViewById(R.id.emailRegister);
        TextPassword = (EditText) findViewById(R.id.passwordRegister);

        btnRegistrar = (Button) findViewById(R.id.buttonRegister);

        progressDialog = new ProgressDialog(this);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });


    }

    private void registrarUsuario(){

        //Obtenemos el email y la contraseña desde las cajas de texto
        String Name = Nombre.getText().toString().trim();
        String email = TextEmail.getText().toString().trim();
        String password  = TextPassword.getText().toString().trim();


        if(TextUtils.isEmpty(Name)){
            Toast.makeText(this,"Se debe ingresar un Nombre",Toast.LENGTH_LONG).show();
            return;
        }
        //Verificamos que las cajas de texto no esten vacías
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se debe ingresar un email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Falta ingresar la contraseña",Toast.LENGTH_LONG).show();
            return;
        }


        //progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.show();
        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            Toast.makeText(RegistroActivity.this, "Se registro correctamente.", Toast.LENGTH_SHORT).show();
                            Usuario usuario = new Usuario();
                            usuario.setCorreo(TextEmail.getText().toString().trim());
                            usuario.setNombre(Nombre.getText().toString().trim());
                            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                            DatabaseReference reference = database.getReference("Usuarios/"+currentUser.getUid());
                            reference.setValue(usuario);
                            finish();

                            Toast.makeText(RegistroActivity.this,"Se ha registrado el usuario con el email: "+ TextEmail.getText(),Toast.LENGTH_LONG).show();
                        }else{

                            Toast.makeText(RegistroActivity.this,"No se pudo registrar el usuario ",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

}
