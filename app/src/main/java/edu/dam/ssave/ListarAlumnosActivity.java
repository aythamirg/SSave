package edu.dam.ssave;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import java.sql.Connection;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ListarAlumnosActivity extends AppCompatActivity {

    ListView lvAlumnosInformacion;

    ConexDB conexDB = new ConexDB();

    Connection connection = null;

    Statement stmt = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_alumnos);

        connection = conexDB.conexionBD();

        if (connection == null) {

            Toast.makeText(getApplicationContext(), "Error al conectar a la base de datos, reinicie la aplicación", Toast.LENGTH_SHORT).show();

        }

        lvAlumnosInformacion = (ListView) findViewById(R.id.lvAlumnosInformacion);

        ArrayList<String> listadoAlumnos = new ArrayList<>();

        ArrayAdapter adaptadorAlumnos = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listadoAlumnos);

        lvAlumnosInformacion.setAdapter(adaptadorAlumnos);

        try{

            stmt = connection.createStatement();
            String sql = "SELECT nombre FROM alumnos;";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){

                String datosALumno = rs.getString(1);

                listadoAlumnos.add(datosALumno);

            }

        }catch (Exception e){

            Toast.makeText(getApplicationContext(), "Error al conectar a la base de datos, reinicie la aplicación", Toast.LENGTH_SHORT).show();


        }

        lvAlumnosInformacion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String resultadoItem = parent.getItemAtPosition(position).toString();

                String resultadoConsulta = null;

                try{

                    String sql = "SELECT dni FROM alumnos WHERE nombre = ?;";
                    PreparedStatement pstm = connection.prepareStatement(sql);
                    pstm.setString(1,resultadoItem);
                    ResultSet rs = pstm.executeQuery();

                    while (rs.next()){

                        resultadoConsulta = rs.getString("dni");

                    }

                    pstm.executeUpdate();
                }catch (Exception e){

                }


                alertOneButton(resultadoItem,resultadoConsulta);

            }
        });
    }

    public void alertOneButton(String nombreAlumno, final String dniAlumno_) {

        new AlertDialog.Builder(ListarAlumnosActivity.this)
                .setTitle(nombreAlumno)
                .setMessage("Elige una acción: ")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();


            }
        }).setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Intent intent = new Intent(ListarAlumnosActivity.this, ModificarAlumnosActivity.class);

                intent.putExtra("datoDniAlumno",dniAlumno_);

                startActivity(intent);


            }
        }).show();
    }
}
