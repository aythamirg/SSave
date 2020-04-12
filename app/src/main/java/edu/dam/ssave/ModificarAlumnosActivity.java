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

public class ModificarAlumnosActivity extends AppCompatActivity {

    EditText etNombreModificarAlumno,  etApellidosModificarAlumno,  etDireccionModificarAlumno,  etTelefonoModificarAlumno,  etDNIModificarAlumno, etFechaNacimientoModificar_;

    Button btnModificarAlumno;

    Connection connection = null;

    ConexDB conexDB = new ConexDB();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_alumnos);


        connection = conexDB.conexionBD();

        if (connection == null) {

            Toast.makeText(getApplicationContext(), "Error al conectar a la base de datos, reinicie la aplicación", Toast.LENGTH_SHORT).show();

        }

        etNombreModificarAlumno = (EditText) findViewById(R.id.etNombreModificarAlumno);

        etApellidosModificarAlumno = (EditText) findViewById(R.id.etApellidosModificarAlumno);

        etDireccionModificarAlumno = (EditText) findViewById(R.id.etDireccionModificarAlumno);

        etTelefonoModificarAlumno = (EditText) findViewById(R.id.etTelefonoModificarAlumno);

        etDNIModificarAlumno  = (EditText) findViewById(R.id.etDNIModificarAlumno);

        etFechaNacimientoModificar_ = (EditText) findViewById(R.id.etFechaNacimientoModificarAlumno);

        btnModificarAlumno = (Button) findViewById(R.id.btnModificarNuevoAlumno);

        recibirDatosActivity();


        btnModificarAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombreAlumno = etNombreModificarAlumno.getText().toString();

                String apellidosAlumno = etApellidosModificarAlumno.getText().toString();

                String direccionAlumno = etDireccionModificarAlumno.getText().toString();

                String telefonoAlumno = etTelefonoModificarAlumno.getText().toString();

                String dniAlumno = etDNIModificarAlumno.getText().toString();

                String fecha_nacimientoAlumno = etFechaNacimientoModificar_.getText().toString();



                if (!nombreAlumno.isEmpty()){


                    modificarAlumnoNombre(dniAlumno,nombreAlumno);

                }if (!apellidosAlumno.isEmpty()){

                    modificarAlumnoApellidos(dniAlumno,apellidosAlumno);

                }if (!direccionAlumno.isEmpty()){

                    modificarAlumnoDireccion(dniAlumno,direccionAlumno);

                }if (!telefonoAlumno.isEmpty()){

                    modificarAlumnoTelefono(dniAlumno,telefonoAlumno);

                }if (!fecha_nacimientoAlumno.isEmpty()){

                    modificarAlumnoFechaN(dniAlumno,fecha_nacimientoAlumno);

                }

                Toast.makeText(getApplicationContext(), "Dato/s modificado/s", Toast.LENGTH_SHORT).show();



            }
        });

    }

    public void modificarAlumnoNombre(String dni, String datoModificar){

        try {
            String dato = datoModificar;
            String dniAlumno = dni;

            String sql = "UPDATE alumnos SET nombre = ? WHERE dni = ?;";
            PreparedStatement stmts = connection.prepareStatement(sql);
            stmts.setString(1,dato);
            stmts.setString(2,dniAlumno);
            stmts.executeUpdate();

        }catch (Exception e){

            Toast.makeText(getApplicationContext(), "Nombre erróneo", Toast.LENGTH_SHORT).show();

        }

    }
    public void modificarAlumnoApellidos(String dni, String datoModificar){

        try {
            String dato = datoModificar;
            String dniAlumno = dni;

            String sql = "UPDATE alumnos SET apellidos = ? WHERE dni = ?;";
            PreparedStatement stmts = connection.prepareStatement(sql);
            stmts.setString(1,dato);
            stmts.setString(2,dniAlumno);
            stmts.executeUpdate();
        }catch (Exception e){

            Toast.makeText(getApplicationContext(), "Apellidos erróneo", Toast.LENGTH_SHORT).show();

        }
    }

    public void modificarAlumnoDireccion(String dni, String datoModificar){

        try {
            String dato = datoModificar;
            String dniAlumno = dni;

            String sql = "UPDATE alumnos SET direccion = ? WHERE dni = ?;";
            PreparedStatement stmts = connection.prepareStatement(sql);
            stmts.setString(1,dato);
            stmts.setString(2,dniAlumno);
            stmts.executeUpdate();

        }catch (Exception e){

            Toast.makeText(getApplicationContext(), "Dirección errónea", Toast.LENGTH_SHORT).show();

        }
    }

    public void modificarAlumnoTelefono(String dni, String datoModificar){

        try {
            String dato = datoModificar;
            String dniAlumno = dni;

            String sql = "UPDATE alumnos SET telefono = ? WHERE dni = ?;";
            PreparedStatement stmts = connection.prepareStatement(sql);
            stmts.setString(1,dato);
            stmts.setString(2,dniAlumno);
            stmts.executeUpdate();
        }catch (Exception e){

            Toast.makeText(getApplicationContext(), "Telefono erróneo", Toast.LENGTH_SHORT).show();

        }
    }

    public void modificarAlumnoFechaN(String dni, String fechaNacimiento){

        try {

            String dniAlumno = dni;
            String fechaN_ = fechaNacimiento;
            java.sql.Date fechaSQL = java.sql.Date.valueOf(fechaN_);

            String sql = "UPDATE alumnos SET fecha_nacimiento = ? WHERE dni = ?;";
            PreparedStatement stmts = connection.prepareStatement(sql);
            stmts.setDate(1,fechaSQL);
            stmts.setString(2,dniAlumno);
            stmts.executeUpdate();
        }catch (Exception e){

            Toast.makeText(getApplicationContext(), "Formato de fecha erróneo", Toast.LENGTH_SHORT).show();
        }
    }

    public void recibirDatosActivity(){

        Bundle exras = getIntent().getExtras();


        if (exras == null){

            Toast.makeText(getApplicationContext(), "Error al pasar datos de una activity a otra", Toast.LENGTH_SHORT).show();

        }else {


            String d1 = exras.getString("datoDniAlumno");

            etDNIModificarAlumno.setText(d1);
        }

    }
}
