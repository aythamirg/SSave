package edu.dam.ssave;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ListarEntradasCentroActivity extends AppCompatActivity {

    private ConexDB conexDB = new ConexDB();

    private Connection connection = null;

    private ListView mListView;

    private Statement stmt = null;

    ArrayList<String> listadoEntradasAlumnos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_entradas_centro);

        connection = conexDB.conexionBD();

        if (connection == null) {

            Toast.makeText(getApplicationContext(), "Error al conectar a la base de datos, reinicie la aplicación", Toast.LENGTH_SHORT).show();

        }

        listarEntradasAlumnos();

        mListView = (ListView) findViewById(R.id.list_viewListarEntradasCentroAlumnado);

        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listadoEntradasAlumnos));

        mListView.setTextFilterEnabled(true);
    }

    public void listarEntradasAlumnos() {

        try {

            stmt = connection.createStatement();
            String sql = "SELECT dnialumno,dnitutor,dniprofesor,hora FROM entradas ORDER BY hora DESC;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                String dniAlumno = rs.getString(1);
                String dniTutor = rs.getString(2);
                String dniProfesor = rs.getString(3);
                String fecha = new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate(4));

                String mensajeSalida = "DNI Alumno: " + dniAlumno + "\n" + "DNI Tutor: " + dniTutor + "\n" + "DNI Profesor: " + dniProfesor + "\n" + "Fecha: " + fecha;

                listadoEntradasAlumnos.add(mensajeSalida);

            }
        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "Error al conectar a la base de datos, reinicie la aplicación", Toast.LENGTH_SHORT).show();


        }
    }
}
