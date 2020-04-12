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

public class ListarTutoresActivity extends AppCompatActivity {

    ListView lvTutoresInformacion;

    ConexDB conexDB = new ConexDB();

    Connection connection = null;

    Statement stmt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_tutores);

        connection = conexDB.conexionBD();

        if (connection == null) {

            Toast.makeText(getApplicationContext(), "Error al conectar a la base de datos, reinicie la aplicación", Toast.LENGTH_SHORT).show();

        }

        lvTutoresInformacion = (ListView) findViewById(R.id.lvTutoresInformacion);

        ArrayList<String> listadoTutores = new ArrayList<>();

        ArrayAdapter adaptadorTutores = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listadoTutores);

        lvTutoresInformacion.setAdapter(adaptadorTutores);

        try{

            stmt = connection.createStatement();
            String sql = "SELECT nombre FROM tutores;";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){

                String datosTutor = rs.getString(1);

                listadoTutores.add(datosTutor);

            }

        }catch (Exception e){

            Toast.makeText(getApplicationContext(), "Error al conectar a la base de datos, reinicie la aplicación", Toast.LENGTH_SHORT).show();


        }

        lvTutoresInformacion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String resultadoItem = parent.getItemAtPosition(position).toString();

                String resultadoConsulta = null;

                try{

                    String sql = "SELECT dni FROM tutores WHERE nombre = ?;";
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
    public void alertOneButton(String nombreTutor, final String dniTutor_) {

        new AlertDialog.Builder(ListarTutoresActivity.this)
                .setTitle(nombreTutor)
                .setMessage("Elige una acción: ")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();


                    }
                }).setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Intent intent = new Intent(ListarTutoresActivity.this, ModificarTutoresActivity.class);

                intent.putExtra("datoDniTutor",dniTutor_);

                startActivity(intent);


            }
        }).show();
    }
}
