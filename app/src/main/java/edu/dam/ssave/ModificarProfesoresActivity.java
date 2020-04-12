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

public class ModificarProfesoresActivity extends AppCompatActivity {

    EditText etNombreModificarProfesor, etApellidosModificarProfesor, etPasswordModificarProfesor, etDNIModificarProfesor;

    Button btnModificarProfesor;

    Connection connection = null;

    ConexDB conexDB = new ConexDB();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_profesores);


        connection = conexDB.conexionBD();

        if (connection == null) {

            Toast.makeText(getApplicationContext(), "Error al conectar a la base de datos, reinicie la aplicación", Toast.LENGTH_SHORT).show();

        }

        etNombreModificarProfesor = (EditText) findViewById(R.id.etNombreModificarProfesor);

        etApellidosModificarProfesor = (EditText) findViewById(R.id.etApellidosModificarProfesor);

        etPasswordModificarProfesor = (EditText) findViewById(R.id.etPasswordModificarProfesor);

        etDNIModificarProfesor = (EditText) findViewById(R.id.etDNIModificarProfesor);

        btnModificarProfesor = (Button) findViewById(R.id.btnModificarNuevoProfesor);

        recibirDatosActivity();


        btnModificarProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombreProfesor = etNombreModificarProfesor.getText().toString();

                String apellidosProfesor = etApellidosModificarProfesor.getText().toString();

                String passwordProfesor = etPasswordModificarProfesor.getText().toString();

                String dniProfesor = etDNIModificarProfesor.getText().toString();


                if (!nombreProfesor.isEmpty()) {


                    modificarProfesorNombre(dniProfesor, nombreProfesor);

                }
                if (!apellidosProfesor.isEmpty()) {

                    modificarProfesorApellidos(dniProfesor, apellidosProfesor);

                }
                if (!passwordProfesor.isEmpty()) {

                    modificarProfesorPassword(dniProfesor, passwordProfesor);

                }


                Toast.makeText(getApplicationContext(), "Dato/s modificado/s", Toast.LENGTH_SHORT).show();





            }
        });

    }

    public void modificarProfesorNombre(String dni, String datoModificar) {

        try {

            String dato = datoModificar;
            String dniProfesor = dni;

            String sql = "UPDATE profesores SET nombre = ? WHERE dni = ?;";
            PreparedStatement stmts = connection.prepareStatement(sql);
            stmts.setString(1, dato);
            stmts.setString(2, dniProfesor);
            stmts.executeUpdate();

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "Nombre erróneo", Toast.LENGTH_SHORT).show();

        }

    }

    public void modificarProfesorApellidos(String dni, String datoModificar) {

        try {

            String dato = datoModificar;
            String dniProfesor = dni;

            String sql = "UPDATE profesores SET apellidos = ? WHERE dni = ?;";
            PreparedStatement stmts = connection.prepareStatement(sql);
            stmts.setString(1, dato);
            stmts.setString(2, dniProfesor);
            stmts.executeUpdate();

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "Apellidos erróneos", Toast.LENGTH_SHORT).show();
        }
    }

    public void modificarProfesorPassword(String dni, String datoModificar) {

        try {
            String dato = datoModificar;
            String dniProfesor = dni;

            String sql = "UPDATE profesores SET contrasena = ? WHERE dni = ?;";
            PreparedStatement stmts = connection.prepareStatement(sql);
            stmts.setString(1, dato);
            stmts.setString(2, dniProfesor);
            stmts.executeUpdate();
        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "Password erróneo", Toast.LENGTH_SHORT).show();
        }
    }

    public void recibirDatosActivity() {

        Bundle exras = getIntent().getExtras();


        if (exras == null){

            Toast.makeText(getApplicationContext(), "Error al pasar datos de una activity a otra", Toast.LENGTH_SHORT).show();

        }else {

            String d1 = exras.getString("datoDniProfesor");

            etDNIModificarProfesor.setText(d1);

        }

    }
}
