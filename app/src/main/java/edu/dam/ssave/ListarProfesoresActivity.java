package edu.dam.ssave;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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

public class ListarProfesoresActivity extends AppCompatActivity {

    ListView lvProfesoresInformacion;

    ConexDB conexDB = new ConexDB();

    Connection connection = null;

    Statement stmt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_profesores);


        connection = conexDB.conexionBD();

        if (connection == null) {

            Toast.makeText(getApplicationContext(), "Error al conectar a la base de datos, reinicie la aplicación", Toast.LENGTH_SHORT).show();

        }

        lvProfesoresInformacion = (ListView) findViewById(R.id.lvProfesoresInformacion);

        ArrayList<String> listadoProfesores = new ArrayList<>();

        ArrayAdapter adaptadorProfesores = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listadoProfesores);

        lvProfesoresInformacion.setAdapter(adaptadorProfesores);

        try{

            stmt = connection.createStatement();
            String sql = "SELECT nombre FROM profesores;";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){

                String datosProfesor = rs.getString(1);

                listadoProfesores.add(datosProfesor);

            }

        }catch (Exception e){

            Toast.makeText(getApplicationContext(), "Error al conectar a la base de datos, reinicie la aplicación", Toast.LENGTH_SHORT).show();


        }

        lvProfesoresInformacion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String resultadoItem = parent.getItemAtPosition(position).toString();

                String resultadoConsulta = null;

                try{

                    String sql = "SELECT dni FROM profesores WHERE nombre = ?;";
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

    public void alertOneButton(String nombreProfesor, final String dniProfesor_) {

        new AlertDialog.Builder(ListarProfesoresActivity.this)
                .setTitle(nombreProfesor)
                .setMessage("Elige una acción: ")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();


                    }
                }).setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Intent intent = new Intent(ListarProfesoresActivity.this, ModificarProfesoresActivity.class);

                intent.putExtra("datoDniProfesor",dniProfesor_);

                startActivity(intent);


            }
        }).show();
    }
}
