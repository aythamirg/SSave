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

public class AnadirNuevosTutoresActivity extends AppCompatActivity {

    EditText etNombreRegistrarTutor,  etApellidosRegistrarTutor,  etDireccionRegistrarTutor,  etTelefonoRegistrarTutor,  etDNIRegistrarTutor, etFechaNacimiento;

    Button btnRegistrarNuevoTutor;

    ConexDB conexDB = new ConexDB();

    Connection connection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_nuevos_tutores);


        connection = conexDB.conexionBD();

        if (connection == null) {

            Toast.makeText(getApplicationContext(), "Error al conectar a la base de datos, reinicie la aplicación", Toast.LENGTH_SHORT).show();

        }

        etDNIRegistrarTutor = (EditText) findViewById(R.id.etDNIRegistrarTutor);

        etNombreRegistrarTutor = (EditText) findViewById(R.id.etNombreRegistrarTutor);

        etApellidosRegistrarTutor = (EditText) findViewById(R.id.etApellidosRegistrarTutor);

        etDireccionRegistrarTutor = (EditText) findViewById(R.id.etDireccionRegistrarTutor);

        etTelefonoRegistrarTutor = (EditText) findViewById(R.id.etTelefonoRegistrarTutor);

        etFechaNacimiento = (EditText) findViewById(R.id.etFechaNacimientoRegistrarTutor);

        btnRegistrarNuevoTutor = findViewById(R.id.btnRegistrarNuevoTutor);
        btnRegistrarNuevoTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dniAlumno_ = etDNIRegistrarTutor.getText().toString();

                String nombreAlumno_ = etNombreRegistrarTutor.getText().toString();

                String apellidosAlumno_ = etApellidosRegistrarTutor.getText().toString();

                String direccionAlumno_ = etDireccionRegistrarTutor.getText().toString();

                String telefonoAlumno_ = etTelefonoRegistrarTutor.getText().toString();

                String fechaNacimientoAlumno_ = etFechaNacimiento.getText().toString();

                try{

                    if (dniAlumno_.isEmpty() || nombreAlumno_.isEmpty() || apellidosAlumno_.isEmpty() || direccionAlumno_.isEmpty() || telefonoAlumno_.isEmpty() || fechaNacimientoAlumno_.isEmpty()){

                        Toast.makeText(getApplicationContext(),"Debe rellenar todos los campos", Toast.LENGTH_SHORT).show();

                    }else{

                        insertTutores(dniAlumno_, nombreAlumno_, apellidosAlumno_, direccionAlumno_, telefonoAlumno_, fechaNacimientoAlumno_);

                        Toast.makeText(getApplicationContext(),"Tutor introducido con éxito", Toast.LENGTH_SHORT).show();

                        etDNIRegistrarTutor.setText("");
                        etNombreRegistrarTutor.setText("");
                        etApellidosRegistrarTutor.setText("");
                        etDireccionRegistrarTutor.setText("");
                        etTelefonoRegistrarTutor.setText("");
                        etFechaNacimiento.setText("");

                    }

                }catch (Exception e){

                    Toast.makeText(getApplicationContext(),"Error al introducir al Tutor", Toast.LENGTH_SHORT).show();

                }



            }
        });
    }

    public void insertTutores(String dni, String nombre, String apellidos, String direccion, String numero, String fechaNacimiento){

        try {

            String dniTutor = dni;
            String nombreTutor = nombre;
            String apellidosTutor = apellidos;
            String direccionTutor = direccion;
            String numeroTutor = numero;

            String fechaN_ = fechaNacimiento;
            java.sql.Date fechaSQL = java.sql.Date.valueOf(fechaN_);



            String sql = "INSERT INTO tutores VALUES (?,?,?,?,?,?)";
            PreparedStatement stmts = connection.prepareStatement(sql);
            stmts.setString(1, dniTutor);
            stmts.setString(2, nombreTutor);
            stmts.setString(3, apellidosTutor);
            stmts.setString(4, direccionTutor);
            stmts.setString(5, numeroTutor);
            stmts.setDate(6,fechaSQL);
            stmts.executeUpdate();

        }catch (Exception e){

            Toast.makeText(getApplicationContext(), "Error al introducir al tutor", Toast.LENGTH_SHORT).show();


        }

    }
}
