package edu.dam.ssave;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListarSalidasCentroActivity extends AppCompatActivity {

    private ConexDB conexDB = new ConexDB();

    private Connection connection = null;

    private ListView mListView;

    private Statement stmt = null;

    ArrayList<String> listadoSalidasAlumnos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_salidas_centro);

        connection = conexDB.conexionBD();

        if (connection == null) {

            Toast.makeText(getApplicationContext(), "Error al conectar a la base de datos, reinicie la aplicación", Toast.LENGTH_SHORT).show();

        }

        listarSalidasAlumnos();

        mListView = (ListView) findViewById(R.id.list_viewListarSalidasCentroAlumnado);

        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listadoSalidasAlumnos));

        mListView.setTextFilterEnabled(true);
    }




    public void listarSalidasAlumnos() {

        try {

            stmt = connection.createStatement();
            String sql = "SELECT dnialumno,dnitutor,dniprofesor,hora FROM salidas ORDER BY hora DESC;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                String dniAlumno = rs.getString(1);
                String dniTutor = rs.getString(2);
                String dniProfesor = rs.getString(3);
                String fecha = new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate(4));

                String mensajeSalida = "DNI Alumno: " + dniAlumno + "\n" + "DNI Tutor: " + dniTutor + "\n" + "DNI Profesor: " + dniProfesor + "\n" + "Fecha: " + fecha;

                listadoSalidasAlumnos.add(mensajeSalida);

            }
        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "Error al conectar a la base de datos, reinicie la aplicación", Toast.LENGTH_SHORT).show();

        }
    }
}
