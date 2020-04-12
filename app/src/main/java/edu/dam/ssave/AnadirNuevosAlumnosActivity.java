package edu.dam.ssave;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class AnadirNuevosAlumnosActivity extends AppCompatActivity {

    EditText etNombreRegistrarAlumno,  etApellidosRegistrarAlumno,  etDireccionRegistrarAlumno,  etTelefonoRegistrarAlumno,  etDNIRegistrarAlumno, etFechaNacimiento;

    Button btnRegistrarNuevoAlumno;

    ConexDB conexDB = new ConexDB();

    Connection connection = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_nuevos_alumnos);

        connection = conexDB.conexionBD();

        etDNIRegistrarAlumno = (EditText) findViewById(R.id.etDNIRegistrarAlumno);

        etNombreRegistrarAlumno = (EditText) findViewById(R.id.etNombreRegistrarAlumno);

        etApellidosRegistrarAlumno = (EditText) findViewById(R.id.etApellidosRegistrarAlumno);

        etDireccionRegistrarAlumno = (EditText) findViewById(R.id.etDireccionRegistrarAlumno);

        etTelefonoRegistrarAlumno = (EditText) findViewById(R.id.etTelefonoRegistrarAlumno);

        etFechaNacimiento = (EditText) findViewById(R.id.etFechaNacimientoRegistrarAlumno);

        btnRegistrarNuevoAlumno = findViewById(R.id.btnRegistrarNuevoAlumno);
        btnRegistrarNuevoAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dniAlumno_ = etDNIRegistrarAlumno.getText().toString();

                String nombreAlumno_ = etNombreRegistrarAlumno.getText().toString();

                String apellidosAlumno_ = etApellidosRegistrarAlumno.getText().toString();

                String direccionAlumno_ = etDireccionRegistrarAlumno.getText().toString();

                String telefonoAlumno_ = etTelefonoRegistrarAlumno.getText().toString();

                String fechaNacimientoAlumno_ = etFechaNacimiento.getText().toString();

                try{

                    if (dniAlumno_.isEmpty() || nombreAlumno_.isEmpty() || apellidosAlumno_.isEmpty() || direccionAlumno_.isEmpty() || telefonoAlumno_.isEmpty() || fechaNacimientoAlumno_.isEmpty()){

                        Toast.makeText(getApplicationContext(),"Debe rellenar todos los campos", Toast.LENGTH_SHORT).show();

                    }else{

                        insertAlumno(dniAlumno_, nombreAlumno_, apellidosAlumno_, direccionAlumno_, telefonoAlumno_, fechaNacimientoAlumno_);

                        Toast.makeText(getApplicationContext(),"Alumno Introducido", Toast.LENGTH_SHORT).show();

                        etDNIRegistrarAlumno.setText("");
                        etNombreRegistrarAlumno.setText("");
                        etApellidosRegistrarAlumno.setText("");
                        etDireccionRegistrarAlumno.setText("");
                        etTelefonoRegistrarAlumno.setText("");
                        etFechaNacimiento.setText("");

                    }

                }catch (Exception e){

                    Toast.makeText(getApplicationContext(),"Error al introducir al alumnado", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    public void insertAlumno(String dni, String nombre, String apellidos, String direccion, String numero, String fechaNacimiento){

        try {

            String dniAlumno = dni;
            String nombreAlumno = nombre;
            String apellidosAlumno = apellidos;
            String direccionAlumno = direccion;
            String numeroAlumno = numero;

            String fechaN_ = fechaNacimiento;
            java.sql.Date fechaSQL = java.sql.Date.valueOf(fechaN_);


            String sql = "INSERT INTO alumnos VALUES (?,?,?,?,?,?)";
            PreparedStatement stmts = connection.prepareStatement(sql);
            stmts.setString(1, dniAlumno);
            stmts.setString(2, nombreAlumno);
            stmts.setString(3, apellidosAlumno);
            stmts.setString(4, direccionAlumno);
            stmts.setString(5, numeroAlumno);
            stmts.setDate(6,fechaSQL);
            stmts.executeUpdate();



        }catch (Exception e){

            Toast.makeText(getApplicationContext(),"Error al introducir al alumnado",Toast.LENGTH_SHORT).show();

        }

    }
}
