package edu.dam.ssave;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AnadirNuevosProfesoresActivity extends AppCompatActivity {

    EditText etNombreRegistrarProfesor,  etApellidosRegistrarProfesores, etPasswordRegistrarProfesor,  etDNIRegistrarProfesor;

    Button btnRegistrarNuevoProfesor;

    ConexDB conexDB = new ConexDB();

    Connection connection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_nuevos_profesores);

        connection = conexDB.conexionBD();

        if (connection == null) {

            Toast.makeText(getApplicationContext(), "Error al conectar a la base de datos, reinicie la aplicación", Toast.LENGTH_SHORT).show();

        }

        etDNIRegistrarProfesor = (EditText) findViewById(R.id.etDNIRegistrarProfesor);

        etNombreRegistrarProfesor = (EditText) findViewById(R.id.etNombreRegistrarProfesor);

        etApellidosRegistrarProfesores = (EditText) findViewById(R.id.etApellidosRegistrarProfesor);

        etPasswordRegistrarProfesor = (EditText) findViewById(R.id.etPasswordRegistrarProfesor);

        btnRegistrarNuevoProfesor = findViewById(R.id.btnRegistrarNuevoProfesor);
        btnRegistrarNuevoProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dniProfesor_ = etDNIRegistrarProfesor.getText().toString();

                String nombreProfesor_ = etNombreRegistrarProfesor.getText().toString();

                String apellidosProfesor_ = etApellidosRegistrarProfesores.getText().toString();

                String passwordProfesor_ = etPasswordRegistrarProfesor.getText().toString();

                try{

                    if (dniProfesor_.isEmpty() || nombreProfesor_.isEmpty() || apellidosProfesor_.isEmpty() || passwordProfesor_.isEmpty()){

                        Toast.makeText(getApplicationContext(),"Debe rellenar todos los campos", Toast.LENGTH_SHORT).show();

                    }else{

                        insertProfesor(dniProfesor_, nombreProfesor_, apellidosProfesor_, passwordProfesor_);

                        Toast.makeText(getApplicationContext(),"Profesor/a Introducido", Toast.LENGTH_SHORT).show();

                        etDNIRegistrarProfesor.setText("");
                        etNombreRegistrarProfesor.setText("");
                        etApellidosRegistrarProfesores.setText("");
                        etPasswordRegistrarProfesor.setText("");
                    }

                }catch (Exception e){

                    Toast.makeText(getApplicationContext(),"Error al introducir al profesorado", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    public void insertProfesor(String dni, String nombre, String apellidos, String password){

        try {

            String dniProfesor = dni;
            String nombreProfesor = nombre;
            String apellidosProfesor = apellidos;
            String passwordProfesor = password;



            String sql = "INSERT INTO profesores VALUES (?,?,?,?)";
            PreparedStatement stmts = connection.prepareStatement(sql);
            stmts.setString(1, dniProfesor);
            stmts.setString(2, nombreProfesor);
            stmts.setString(3, apellidosProfesor);
            stmts.setString(4, passwordProfesor);
            stmts.executeUpdate();


        }catch (Exception e){

            Toast.makeText(getApplicationContext(), "Error al introducir al profesorado", Toast.LENGTH_SHORT).show();


        }

    }
}
