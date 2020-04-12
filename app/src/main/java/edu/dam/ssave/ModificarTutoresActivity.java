package edu.dam.ssave;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ModificarTutoresActivity extends AppCompatActivity {

    EditText etNombreModificarTutor,  etApellidosModificarTutor,  etDireccionModificarTutor,  etTelefonoModificarTutor,  etDNIModificarTutor, etFechaNacimientoModificarTutor_;

    Button btnModificarTutor, btnEliminarTutor;

    Connection connection = null;

    ConexDB conexDB = new ConexDB();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_tutores);


        connection = conexDB.conexionBD();

        if (connection == null) {

            Toast.makeText(getApplicationContext(), "Error al conectar a la base de datos, reinicie la aplicación", Toast.LENGTH_SHORT).show();

        }

        etNombreModificarTutor = (EditText) findViewById(R.id.etNombreModificarTutor);

        etApellidosModificarTutor = (EditText) findViewById(R.id.etApellidosModificarTutor);

        etDireccionModificarTutor = (EditText) findViewById(R.id.etDireccionModificarTutor);

        etTelefonoModificarTutor = (EditText) findViewById(R.id.etTelefonoModificarTutor);

        etDNIModificarTutor  = (EditText) findViewById(R.id.etDNIModificarTutor);

        etFechaNacimientoModificarTutor_ = (EditText) findViewById(R.id.etFechaNacimientoModificarTutor);

        btnEliminarTutor = (Button) findViewById(R.id.btnEliminarNuevoTutor);

        btnModificarTutor = (Button) findViewById(R.id.btnModificarNuevoTutor);

        recibirDatosActivity();


        btnEliminarTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dniTutor = etDNIModificarTutor.getText().toString();

                alertOneButtonEliminar(dniTutor);


            }
        });


        btnModificarTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombreTutor = etNombreModificarTutor.getText().toString();

                String apellidosTutor = etApellidosModificarTutor.getText().toString();

                String direccionTutor = etDireccionModificarTutor.getText().toString();

                String telefonoTutor = etTelefonoModificarTutor.getText().toString();

                String dniTutor = etDNIModificarTutor.getText().toString();

                String fecha_nacimientoTutor = etFechaNacimientoModificarTutor_.getText().toString();



                if (!nombreTutor.isEmpty()){


                    modificarTutorNombre(dniTutor,nombreTutor);

                }if (!apellidosTutor.isEmpty()){

                    modificarTutorApellidos(dniTutor,apellidosTutor);

                }if (!direccionTutor.isEmpty()){

                    modificarTutorDireccion(dniTutor,direccionTutor);

                }if (!telefonoTutor.isEmpty()){

                    modificarTutorTelefono(dniTutor,telefonoTutor);

                }if (!fecha_nacimientoTutor.isEmpty()){

                    modificarTutorFechaN(dniTutor,fecha_nacimientoTutor);

                }


                Toast.makeText(getApplicationContext(), "Dato/s modificado/s", Toast.LENGTH_SHORT).show();



            }
        });

    }

    public void modificarTutorNombre(String dni, String datoModificar){

        try {
            String dato = datoModificar;
            String dniTutor = dni;

            String sql = "UPDATE tutores SET nombre = ? WHERE dni = ?;";
            PreparedStatement stmts = connection.prepareStatement(sql);
            stmts.setString(1,dato);
            stmts.setString(2,dniTutor);
            stmts.executeUpdate();

        }catch (Exception e){

            Toast.makeText(getApplicationContext(), "Nombre erróneo", Toast.LENGTH_SHORT).show();
        }

    }
    public void modificarTutorApellidos(String dni, String datoModificar){

        try {
            String dato = datoModificar;
            String dniTutor = dni;

            String sql = "UPDATE tutores SET apellidos = ? WHERE dni = ?;";
            PreparedStatement stmts = connection.prepareStatement(sql);
            stmts.setString(1,dato);
            stmts.setString(2,dniTutor);
            stmts.executeUpdate();
        }catch (Exception e){

            Toast.makeText(getApplicationContext(), "apellidos erróneos", Toast.LENGTH_SHORT).show();
        }
    }

    public void modificarTutorDireccion(String dni, String datoModificar){

        try {
            String dato = datoModificar;
            String dniTutor = dni;

            String sql = "UPDATE tutores SET direccion = ? WHERE dni = ?;";
            PreparedStatement stmts = connection.prepareStatement(sql);
            stmts.setString(1,dato);
            stmts.setString(2,dniTutor);
            stmts.executeUpdate();

        }catch (Exception e){

            Toast.makeText(getApplicationContext(), "Dirección errónea", Toast.LENGTH_SHORT).show();

        }
    }

    public void modificarTutorTelefono(String dni, String datoModificar){

        try {
            String dato = datoModificar;
            String dniTutor = dni;

            String sql = "UPDATE tutores SET telefono = ? WHERE dni = ?;";
            PreparedStatement stmts = connection.prepareStatement(sql);
            stmts.setString(1,dato);
            stmts.setString(2,dniTutor);
            stmts.executeUpdate();
        }catch (Exception e){

            Toast.makeText(getApplicationContext(), "Teléfono erróneo", Toast.LENGTH_SHORT).show();

        }
    }

    public void modificarTutorFechaN(String dni, String fechaNacimiento){

        try {

            String dniTutor = dni;
            String fechaN_ = fechaNacimiento;
            java.sql.Date fechaSQL = java.sql.Date.valueOf(fechaN_);

            String sql = "UPDATE tutores SET fecha_nacimiento = ? WHERE dni = ?;";
            PreparedStatement stmts = connection.prepareStatement(sql);
            stmts.setDate(1,fechaSQL);
            stmts.setString(2,dniTutor);
            stmts.executeUpdate();
        }catch (Exception e){

            Toast.makeText(getApplicationContext(), "Formato de fecha no permitido", Toast.LENGTH_SHORT).show();
        }
    }

    public void recibirDatosActivity(){

        Bundle exras = getIntent().getExtras();

        if (exras == null){

            Toast.makeText(getApplicationContext(), "Error al pasar datos de una activity a otra", Toast.LENGTH_SHORT).show();

        }else {

            String d1 = exras.getString("datoDniTutor");

            etDNIModificarTutor.setText(d1);

        }

    }

    public void alertOneButtonEliminar( final String dniTutor_) {

        new AlertDialog.Builder(ModificarTutoresActivity.this)
                .setTitle(dniTutor_)
                .setMessage("Seguro que quiere eliminar a este usuario: ")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();


                    }
                }).setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String dniTutor = dniTutor_;

                        try{

                            String sql = "DELETE FROM tutores WHERE dni = ?;";
                            PreparedStatement stmts = connection.prepareStatement(sql);
                            stmts.setString(1,dniTutor);
                            stmts.executeUpdate();

                        }catch (Exception e){

                            Toast.makeText(getApplicationContext(), "Error al eliminar", Toast.LENGTH_SHORT).show();

                        }
                    }
        }).show();
    }
}
