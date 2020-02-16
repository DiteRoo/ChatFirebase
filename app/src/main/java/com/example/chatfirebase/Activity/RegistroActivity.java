package com.example.chatfirebase.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chatfirebase.Entidades.Firebase.Usuario;
import com.example.chatfirebase.Persistencia.UsuarioDAO;
import com.example.chatfirebase.R;
import com.example.chatfirebase.Utilidades.Constantes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kbeanie.multipicker.api.CacheLocation;
import com.kbeanie.multipicker.api.CameraImagePicker;
import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenImage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RegistroActivity extends AppCompatActivity {

    private EditText Nombre;
    private EditText TextEmail;
    private EditText TextPassword;
    private EditText TextPasswordRepetida;
    private Button btnRegistrar;
    private ProgressDialog progressDialog;

    private CircleImageView fotoPerfil;
    private EditText txtFechaDeNacimiento;
    private RadioButton rdHombre;
    private RadioButton rdMujer;

    private ImagePicker imagePicker;
    private CameraImagePicker cameraPicker;

    //Declaramos un objeto firebaseAuth
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private long fechaDeNacimiento;
    String fechaNacimento;

    private String pickerPath;
    private Uri fotoPerfilUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        //Referenciamos los views
        fotoPerfil = findViewById(R.id.fotoPerfil);
        Nombre = (EditText) findViewById(R.id.idRegistroNombre);
        TextEmail = (EditText) findViewById(R.id.idRegistroCorreo);
        TextPassword = (EditText) findViewById(R.id.idRegistroContraseña);
        TextPasswordRepetida = findViewById(R.id.idRegistroContraseñaRepetida);
        btnRegistrar = (Button) findViewById(R.id.idRegistroRegistrar);

        txtFechaDeNacimiento = findViewById(R.id.txtFechaDeNacimiento);
        rdHombre = findViewById(R.id.rdHombre);
        rdMujer = findViewById(R.id.rdMujer);

        imagePicker = new ImagePicker(this);
        cameraPicker = new CameraImagePicker(this);

        cameraPicker.setCacheLocation(CacheLocation.EXTERNAL_STORAGE_APP_DIR);

        imagePicker.setImagePickerCallback(new ImagePickerCallback() {
            @Override
            public void onImagesChosen(List<ChosenImage> list) {
                if(!list.isEmpty()){
                    String path = list.get(0).getOriginalPath();
                    fotoPerfilUri = Uri.fromFile(new File(path));
                    fotoPerfil.setImageURI(fotoPerfilUri);
                }
                else{
                    Toast.makeText(RegistroActivity.this, "Error url vacia", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String s) {
                Toast.makeText(RegistroActivity.this, "Error: "+s, Toast.LENGTH_SHORT).show();
            }
        });

        cameraPicker.setImagePickerCallback(new ImagePickerCallback() {
            @Override
            public void onImagesChosen(List<ChosenImage> list) {
                String path = list.get(0).getOriginalPath();
                fotoPerfilUri = Uri.fromFile(new File(path));
                fotoPerfil.setImageURI(fotoPerfilUri);
            }

            @Override
            public void onError(String s) {
                Toast.makeText(RegistroActivity.this, "Error: "+s, Toast.LENGTH_SHORT).show();
            }
        });


        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(RegistroActivity.this);
                dialog.setTitle("Foto de perfil");

                String[] items = {"Galeria","Camara"};

                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                Toast.makeText(RegistroActivity.this, "Seleccione una imagen", Toast.LENGTH_SHORT).show();
                                imagePicker.pickImage(); //Inicializar el callback
                                break;
                            case 1:
                                pickerPath = cameraPicker.pickImage();
                                break;
                        }
                    }
                });

                AlertDialog dialogConstruido = dialog.create();
                dialogConstruido.show();

            }
        });



        progressDialog = new ProgressDialog(this);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });

        txtFechaDeNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegistroActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int mes, int dia) {
                        Calendar calendarResultado = Calendar.getInstance();
                        calendarResultado.set(Calendar.YEAR,year);
                        calendarResultado.set(Calendar.MONTH,mes);
                        calendarResultado.set(Calendar.DAY_OF_MONTH,dia);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        Date date = calendarResultado.getTime();
                        String fechaDeNacimientoTexto = simpleDateFormat.format(date);
                        fechaDeNacimiento = date.getTime();
                        txtFechaDeNacimiento.setText(fechaDeNacimientoTexto);
                    }
                },calendar.get(Calendar.YEAR)-18,calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Picker.PICK_IMAGE_DEVICE && resultCode == RESULT_OK){
            imagePicker.submit(data);
        }else if(requestCode == Picker.PICK_IMAGE_CAMERA && resultCode == RESULT_OK){
            cameraPicker.reinitialize(pickerPath);
            cameraPicker.submit(data);
        }
    }



    private void registrarUsuario(){

        //Obtenemos el email y la contraseña desde las cajas de texto
        final String Name = Nombre.getText().toString().trim();
        final String email = TextEmail.getText().toString().trim();
        final String password  = TextPassword.getText().toString().trim();
        final String passwordRepetida  = TextPasswordRepetida.getText().toString().trim();

        final String fechaDeNacer = txtFechaDeNacimiento.getText().toString().trim();


        if(TextUtils.isEmpty(Name)){
            Toast.makeText(this,"Ingrese su nombre",Toast.LENGTH_LONG).show();
            return;
        }
        //Verificamos que las cajas de texto no esten vacías
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Ingrese su email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Ingrese su clave",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(passwordRepetida)){
            Toast.makeText(this,"Falta ingresar la verifcacion de contraseña",Toast.LENGTH_LONG).show();
            return;
        }
        if(!password.equals(passwordRepetida)){
            Toast.makeText(this,"Las contraseñas no coinciden",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(fechaDeNacer)){
            Toast.makeText(this,"Ingrese su fecha de nacimiento",Toast.LENGTH_LONG).show();
            return;
        }


        //progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.show();
        //creating a new buser
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            final String genero;

                            if(rdHombre.isChecked()){
                                genero = "Hombre";
                            }else{
                                genero = "Mujer";
                            }

                            if(fotoPerfilUri!=null) {

                                UsuarioDAO.getInstancia().subirFotoUri(fotoPerfilUri, new UsuarioDAO.IDevolverUrlFoto() {
                                    @Override
                                    public void devolerUrlString(String url) {
                                        Toast.makeText(RegistroActivity.this, "Se registro correctamente.", Toast.LENGTH_SHORT).show();
                                        Usuario usuario = new Usuario();
                                        usuario.setCorreo(email);
                                        usuario.setNombre(Name);
                                        usuario.setFechaDeNacimiento(fechaDeNacimiento);
                                        usuario.setGenero(genero);
                                        usuario.setFotoPerfilUrl(url);
                                        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                        DatabaseReference reference = database.getReference("Usuarios/" + currentUser.getUid());
                                        reference.setValue(usuario);
                                        finish();
                                    }
                                });

                            }else{
                                Toast.makeText(RegistroActivity.this, "Se registro correctamente.", Toast.LENGTH_SHORT).show();
                                Usuario usuario = new Usuario();
                                usuario.setCorreo(email);
                                usuario.setNombre(Name);
                                usuario.setFechaDeNacimiento(fechaDeNacimiento);
                                usuario.setGenero(genero);
                                usuario.setFotoPerfilUrl(Constantes.URL_FOTO_POR_DEFECTO_HOMBRE);
                                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                DatabaseReference reference = database.getReference("Usuarios/"+currentUser.getUid());
                                reference.setValue(usuario);
                                finish();
                            }

                        }else{

                            Toast.makeText(RegistroActivity.this,"No se pudo registrar el usuario ",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

}
