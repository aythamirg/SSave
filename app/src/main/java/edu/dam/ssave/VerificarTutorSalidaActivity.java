package edu.dam.ssave;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class VerificarTutorSalidaActivity extends AppCompatActivity {

    ListView lvVerificarTutoresSalidas;

    ConexDB conexDB = new ConexDB();

    Connection connection = null;

    ArrayList<String> listadoTutores = new ArrayList<>();

    String dniAlumnado_ = null;

    String dniProfesor_ = null;


    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificar_tutor_salida);

        connection = conexDB.conexionBD();

        if (connection == null) {

            Toast.makeText(getApplicationContext(), "Error al conectar a la base de datos, reinicie la aplicación", Toast.LENGTH_SHORT).show();

        }

        recibirDatosActivityAlumnado();
        recibirDatosActivityProfesor();



        lvVerificarTutoresSalidas = (ListView) findViewById(R.id.lvVerificarAutorizados);

        ArrayAdapter adaptadorTutores = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listadoTutores);

        lvVerificarTutoresSalidas.setAdapter(adaptadorTutores);

        listarTutores(dniAlumnado_);


        lvVerificarTutoresSalidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String resultadoItemTutor = parent.getItemAtPosition(position).toString();

                alertOneButton(dniAlumnado_,resultadoItemTutor,dniProfesor_);




            }
        });
    }

    public void alertOneButton(final String dniAlumno, final String dniTutor, final String dniProfesor) {

        new AlertDialog.Builder(VerificarTutorSalidaActivity.this)
                .setTitle("Salida del centro")
                .setMessage("¿Desea realizar este proceso? ")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();


                    }
                }).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                insertarSalidaCentro(dniAlumno,dniTutor,dniProfesor);
                Intent intent = new Intent(VerificarTutorSalidaActivity.this, VentanaPrincipalProfesorActivity.class);

                intent.putExtra("datoDniProfesor",dniProfesor_);

                startActivity(intent);
                finish();




            }
        }).show();
    }


    public void listarTutores(String dniAlumnado){

        String dni_Alumnado = dniAlumnado;

        String resultadoConsulta;

            try {

                String sql = "SELECT dnitutor FROM autorizacion WHERE dnialumno = ?;";
                PreparedStatement stmts = connection.prepareStatement(sql);
                stmts.setString(1, dni_Alumnado);
                ResultSet rs = stmts.executeQuery();

                while (rs.next()){

                    resultadoConsulta = rs.getString("dnitutor");

                    listadoTutores.add(resultadoConsulta);
                }

                stmts.executeUpdate();



            } catch (Exception e) {



            }
    }

    public void insertarSalidaCentro(String dniAlumno, String dniTutor, String dniProfesor){

        String dni_Alumno = dniAlumno;
        String dni_Tutor = dniTutor;
        String dni_Profesor = dniProfesor;

        java.sql.Date fechaSQL = java.sql.Date.valueOf(date);

        try{

            String sql = "INSERT INTO salidas (dnialumno, dnitutor, dniprofesor, fecha) VALUES (?,?,?,?);";
            PreparedStatement stmts = connection.prepareStatement(sql);
            stmts.setString(1, dni_Alumno);
            stmts.setString(2,dni_Tutor);
            stmts.setString(3,dni_Profesor);
            stmts.setDate(4,fechaSQL);
            stmts.executeUpdate();






        }catch (Exception e){



        }





    }

    public void recibirDatosActivityAlumnado() {

        Bundle exras = getIntent().getExtras();

        if (exras == null){

            Toast.makeText(getApplicationContext(), "Error al pasar datos de una activity a otra", Toast.LENGTH_SHORT).show();

        }else {

            String d1 = exras.getString("datoDniAlumnoSalidas");

            dniAlumnado_ = d1;
        }





    }

    public void recibirDatosActivityProfesor(){

        Bundle exras = getIntent().getExtras();

        if (exras == null){

            Toast.makeText(getApplicationContext(), "Error al pasar datos de una activity a otra", Toast.LENGTH_SHORT).show();

        }else {

            String d2 = exras.getString("datoDniProfesorSalidas");

            dniProfesor_ = d2;


        }



    }
}
