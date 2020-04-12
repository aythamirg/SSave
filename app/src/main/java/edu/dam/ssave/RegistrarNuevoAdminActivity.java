package edu.dam.ssave;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistrarNuevoAdminActivity extends AppCompatActivity {

    private EditText etPassword, etNombre, etEmail;

    private final String correoAdminVerificar = "admin@gmail.com";

    private Button btnRegistrarUsuario;

    private String name = "";

    private String email = "";

    private String password = "";

    FirebaseAuth mAuth;

    DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_nuevo_admin);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        etNombre = (EditText) findViewById(R.id.etNombre);

        etEmail = (EditText) findViewById(R.id.etEmail);

        etPassword = (EditText) findViewById(R.id.etPassword);



        btnRegistrarUsuario = (Button) findViewById(R.id.btnRegistrar);
        btnRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = etNombre.getText().toString();

                email = etEmail.getText().toString();

                password = etPassword.getText().toString();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()){

                    Toast.makeText(getApplicationContext(),"Debe rellenar todos los campos",Toast.LENGTH_SHORT).show();
                }else{

                    if (email.equals(correoAdminVerificar)){

                        Toast.makeText(getApplicationContext(),"Este correo no es válido",Toast.LENGTH_SHORT).show();


                    }else {

                        registerUser();

                    }
                }

            }
        });
    }

    private void registerUser(){

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    Map<String,Object> map = new HashMap<>();

                    map.put("name",name);

                    map.put("email",email);

                    map.put("password",password);

                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {

                            if (task2.isSuccessful()){

                                Toast.makeText(getApplicationContext(),"Usuario Registrado",Toast.LENGTH_SHORT).show();
                            }else {

                                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();


                            }

                        }
                    });


                }else{

                    Toast.makeText(getApplicationContext(),"No se pudo registrar",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
