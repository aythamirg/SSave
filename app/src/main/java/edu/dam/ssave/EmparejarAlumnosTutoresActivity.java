package edu.dam.ssave;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class EmparejarAlumnosTutoresActivity extends AppCompatActivity {

    EditText etDNITutorEmparejar, etDNIAlumnoEmparejar;

    Button btnRegistrarNuevoEnlanceAlumnoTutor;

    ConexDB conexDB = new ConexDB();

    Connection connection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emparejar_alumnos_tutores);


        connection = conexDB.conexionBD();

        if (connection == null) {

            Toast.makeText(getApplicationContext(), "Error al conectar a la base de datos, reinicie la aplicación", Toast.LENGTH_SHORT).show();

        }


        etDNIAlumnoEmparejar = (EditText) findViewById(R.id.etDNIEmparejarAlumno);

        etDNITutorEmparejar = (EditText) findViewById(R.id.etDNIEmparejarTutor);


        btnRegistrarNuevoEnlanceAlumnoTutor = (Button) findViewById(R.id.btnRegistrarNuevoEmparejamientoAumnoTutor);
        btnRegistrarNuevoEnlanceAlumnoTutor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String dniAlumnoET = etDNIAlumnoEmparejar.getText().toString();

                String dniTutorET = etDNITutorEmparejar.getText().toString();

                if (!dniAlumnoET.isEmpty() || !dniTutorET.isEmpty()){

                    emparejarAlumnoTutor(dniAlumnoET,dniTutorET);

                    etDNIAlumnoEmparejar.setText("");
                    etDNITutorEmparejar.setText("");

                }else {

                    Toast.makeText(getApplicationContext(), "Debe rellenar todos los campos", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void emparejarAlumnoTutor(String dniAlumno, String dniTutor){

        String dniAlumno_ = dniAlumno;

        String dniTutor_ = dniTutor;

        try{

            String sql = "INSERT INTO autorizacion (dnialumno, dnitutor) VALUES (?,?)";
            PreparedStatement stmts = connection.prepareStatement(sql);
            stmts.setString(1, dniAlumno_);
            stmts.setString(2, dniTutor_);
            stmts.executeUpdate();

        }catch (Exception e){

            Toast.makeText(getApplicationContext(),"Error en la vinculación", Toast.LENGTH_SHORT).show();
        }
    }
}
