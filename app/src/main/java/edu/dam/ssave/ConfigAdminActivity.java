package edu.dam.ssave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfigAdminActivity extends AppCompatActivity {


    private TextView tvDatosPerfil;

    private EditText etChangePassword;

    private Button btnCerrarSesion, btnRegistrarAdmin, btnChangePassword, btnMasOpcionesAdmin;

    private FirebaseAuth mAuth;

    DatabaseReference mDatabase;

    private String nombreUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_admin);

        mAuth = FirebaseAuth.getInstance();


        mDatabase = FirebaseDatabase.getInstance().getReference();


        nombreUsuario = mAuth.getCurrentUser().getEmail();


        tvDatosPerfil = (TextView) findViewById(R.id.tvDatosPerfil);
        tvDatosPerfil.setText(nombreUsuario);


        etChangePassword = (EditText) findViewById(R.id.etChangePassword);

        btnChangePassword = (Button) findViewById(R.id.btnChangePassword);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cambiarPassword = etChangePassword.getText().toString();

                if (cambiarPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debe introducir una contraseña de 6 dígitos mínimo", Toast.LENGTH_SHORT).show();

                } else {

                    mAuth.getCurrentUser().updatePassword(cambiarPassword);

                    Toast.makeText(getApplicationContext(), "Contraseña cambiada con éxito", Toast.LENGTH_SHORT).show();


                }

            }
        });

        btnRegistrarAdmin = (Button) findViewById(R.id.btnCrearAdministradorNuevo);
        btnRegistrarAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ConfigAdminActivity.this, RegistrarNuevoAdminActivity.class));

            }
        });

        btnCerrarSesion = (Button) findViewById(R.id.btnCerrarSesion);

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();

                startActivity(new Intent(ConfigAdminActivity.this, LoginProfesorActivity.class));

                finish();

            }
        });

        btnMasOpcionesAdmin = (Button) findViewById(R.id.btnMasOpcionesAdmin);
        btnMasOpcionesAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    startActivity(new Intent(ConfigAdminActivity.this, VentanaPrincipalAdminActivity.class));






            }
        });


    }
}
