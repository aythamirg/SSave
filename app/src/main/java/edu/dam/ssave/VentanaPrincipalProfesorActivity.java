package edu.dam.ssave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class VentanaPrincipalProfesorActivity extends AppCompatActivity {

    Button btnAnadirNuevaSalida, btnListarNuevaSalida;

    Button btnAnadirNuevaEntrada, btnListarNuevaEntrada;

    Button btnCerrarSesionProfesor;

    String dniProfesor_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_principal_profesor);

        recibirDatosActivity();

        btnAnadirNuevaSalida = (Button) findViewById(R.id.btnAnadirNuevaSalida);
        btnAnadirNuevaSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(VentanaPrincipalProfesorActivity.this, SalidasCentroAlumnadoActivity.class);

                intent.putExtra("datoDniProfesor",dniProfesor_);

                startActivity(intent);


            }
        });


        btnListarNuevaSalida = (Button) findViewById(R.id.btnListarSalidasCentro);
        btnListarNuevaSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(VentanaPrincipalProfesorActivity.this, ListarSalidasCentroActivity.class));

            }
        });


        btnAnadirNuevaEntrada = (Button) findViewById(R.id.btnAnadirNuevaEntrada);
        btnAnadirNuevaEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(VentanaPrincipalProfesorActivity.this, EntradasCentroAlumnadoActivity.class);

                intent.putExtra("datoDniProfesor",dniProfesor_);

                startActivity(intent);

            }
        });


        btnListarNuevaEntrada = (Button) findViewById(R.id.btnListarEntradasCentro);
        btnListarNuevaEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(VentanaPrincipalProfesorActivity.this, ListarEntradasCentroActivity.class));
            }
        });

        btnCerrarSesionProfesor = (Button) findViewById(R.id.btnCerrarSesionProfesor);
        btnCerrarSesionProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(VentanaPrincipalProfesorActivity.this, LoginProfesorActivity.class));
                finish();

            }
        });
    }



    public void recibirDatosActivity(){

        Bundle exras = getIntent().getExtras();

        if (exras == null){

            Toast.makeText(getApplicationContext(), "Error al pasar datos de una activity a otra", Toast.LENGTH_SHORT).show();

        }else {

            String d1 = exras.getString("datoDniProfesor");

            dniProfesor_ = d1;

        }





    }
}
