package edu.dam.ssave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VentanaPrincipalAdminActivity extends AppCompatActivity {

    Button btnAnadirNuevoAlumno, btnListarNuevoAlumno;

    Button btnAnadirNuevoTutor, btnListarNuevoTutor;

    Button btnAnadirNuevoProfesor,btnListarNuevoProfesor;

    Button btnAnadirNuevoAutorizado, btnListarNuevoAutorizado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_principal_admin);

        btnAnadirNuevoAlumno = (Button) findViewById(R.id.btnAnadirNuevoAlumno);
        btnAnadirNuevoAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(VentanaPrincipalAdminActivity.this, AnadirNuevosAlumnosActivity.class));

            }
        });

        btnAnadirNuevoTutor = findViewById(R.id.btnAnadirNuevoTutor);
        btnAnadirNuevoTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(VentanaPrincipalAdminActivity.this, AnadirNuevosTutoresActivity.class));

            }
        });

        btnListarNuevoAlumno = (Button) findViewById(R.id.btnListarNuevoAlumno);
        btnListarNuevoAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(VentanaPrincipalAdminActivity.this, ListarAlumnosActivity.class));

            }
        });

        btnListarNuevoTutor = (Button) findViewById(R.id.btnListarNuevosTutores);
        btnListarNuevoTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(VentanaPrincipalAdminActivity.this,ListarTutoresActivity.class));

            }
        });

        btnAnadirNuevoProfesor = (Button) findViewById(R.id.btnAnadirNuevoProfesor);
        btnAnadirNuevoProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(VentanaPrincipalAdminActivity.this,AnadirNuevosProfesoresActivity.class));

            }
        });

        btnListarNuevoProfesor = (Button) findViewById(R.id.btnListarNuevoProfesor);
        btnListarNuevoProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(VentanaPrincipalAdminActivity.this,ListarProfesoresActivity.class));

            }
        });

        btnAnadirNuevoAutorizado = (Button) findViewById(R.id.btnAnadirNuevoAutorizado);
        btnAnadirNuevoAutorizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(VentanaPrincipalAdminActivity.this,EmparejarAlumnosTutoresActivity.class));
            }
        });

        btnListarNuevoAutorizado = findViewById(R.id.btnListarNuevoAutorizado);
        btnListarNuevoAutorizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(VentanaPrincipalAdminActivity.this, ListarAutorizadosActivity.class));
            }
        });
    }
}
