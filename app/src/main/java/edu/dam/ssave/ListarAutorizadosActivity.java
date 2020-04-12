package edu.dam.ssave;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ListarAutorizadosActivity extends AppCompatActivity {


    ListView lvAutorizadosInformacion;

    ConexDB conexDB = new ConexDB();

    Connection connection = null;

    Statement stmt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_autorizados);


        connection = conexDB.conexionBD();

        if (connection == null) {

            Toast.makeText(getApplicationContext(), "Error al conectar a la base de datos, reinicie la aplicación", Toast.LENGTH_SHORT).show();

        }

        lvAutorizadosInformacion = (ListView) findViewById(R.id.lvAutorizadosInformacion);

        ArrayList<String> listadoAutorizados = new ArrayList<>();

        ArrayAdapter adaptadorAutorizados = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listadoAutorizados);

        lvAutorizadosInformacion.setAdapter(adaptadorAutorizados);

        try{

            stmt = connection.createStatement();
            String sql = "SELECT dnialumno,dnitutor FROM autorizacion;";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){

                String dato1 = rs.getString(1);
                String dato2 = rs.getString(2);


                String datosAutorizado = "Alumno: " + dato1 + "\n" + "Tutor/a : " + dato2;

                listadoAutorizados.add(datosAutorizado);

            }

        }catch (Exception e){

            Toast.makeText(getApplicationContext(), "Error al conectar a la base de datos, reinicie la aplicación", Toast.LENGTH_SHORT).show();


        }
    }
}
