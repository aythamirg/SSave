package edu.dam.ssave;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginAdminActivity extends AppCompatActivity {

    private EditText etEmailLoginUsuario, etPasswordLoginUsuario;

    private Button btnLoginUsuario;

    private String email = "";

    private String password = "";


    private FirebaseAuth mAuth;

    private ProgressBar progressBarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        mAuth = FirebaseAuth.getInstance();

        etEmailLoginUsuario = (EditText) findViewById(R.id.etEmailLoginUsuario);

        etPasswordLoginUsuario = (EditText) findViewById(R.id.etPasswordLoginUsuario);

        btnLoginUsuario = (Button) findViewById(R.id.btnLoginUsuario);

        progressBarLogin = (ProgressBar) findViewById(R.id.progressbarLogin);

        btnLoginUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = etEmailLoginUsuario.getText().toString();

                password = etPasswordLoginUsuario.getText().toString();

                if (email.isEmpty() || password.isEmpty()){

                    Toast.makeText(getApplicationContext(),"Todos los campos deben estar rellenados",Toast.LENGTH_SHORT).show();
                }else{

                    loginUser();

                }



            }
        });



    }

    private void loginUser(){

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {



                progressBarLogin.setVisibility(View.VISIBLE);

                if (task.isSuccessful()){

                    startActivity(new Intent(LoginAdminActivity.this, ConfigAdminActivity.class));
                    progressBarLogin.setVisibility(View.INVISIBLE);
                    finish();
                }else {
                    progressBarLogin.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"No se pudo iniciar sesión",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}


