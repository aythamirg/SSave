package edu.dam.ssave;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class SalidasCentroAlumnadoActivity extends Activity
        implements SearchView.OnQueryTextListener {

    private ConexDB conexDB = new ConexDB();

    private Connection connection = null;

    private SearchView mSearchView;

    private ListView mListView;

    private Statement stmt = null;

    ArrayList<String> listadoAlumnos = new ArrayList<>();

    ArrayList<String> listadoDNiAlumnos = new ArrayList<>();

    ArrayList<String> listadoDNIALumnosNoDisponibles = new ArrayList<>();

    String dniProfesor_ = null;

    String fechaActual = new SimpleDateFormat("yyyy-MM-dd").format(new Date());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_salidas_centro_alumnado);

        connection = conexDB.conexionBD();

        if (connection == null) {

            Toast.makeText(getApplicationContext(), "Error al conectar a la base de datos, reinicie la aplicación", Toast.LENGTH_SHORT).show();

        }

        recibirDatosActivity();

        listarAlumnos();

        listadoAllDNIALumnos();
        listadoSalidasComprobar();
        listadoDniDefinitivo();


        mSearchView = (SearchView) findViewById(R.id.search_view);

        mListView = (ListView) findViewById(R.id.list_viewSalidasCentroAlumnado);

        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listadoDNiAlumnos));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String resultadoItem = parent.getItemAtPosition(position).toString();

                alertOneButton(resultadoItem);

            }
        });



        mListView.setTextFilterEnabled(true);

        setupSearchView();
    }

    private void setupSearchView() {

        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Buscar Alumno");

    }

    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            mListView.clearTextFilter();
        } else {
            mListView.setFilterText(newText.toString());
        }
        return true;
    }

    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    public void listarAlumnos(){

        try{

            stmt = connection.createStatement();
            String sql = "SELECT nombre,dni FROM alumnos;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){


                String datosALumnoMostrar = rs.getString(1);
                listadoAlumnos.add(datosALumnoMostrar);

            }
        }catch (Exception e){



        }
    }

    public void alertOneButton(final String dniAlumno_) {

        new AlertDialog.Builder(SalidasCentroAlumnadoActivity.this)
                .setTitle(dniAlumno_)
                .setMessage("¿Estás seguro?: ")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();


                    }
                }).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Intent intent = new Intent(SalidasCentroAlumnadoActivity.this, VerificarTutorSalidaActivity.class);

                intent.putExtra("datoDniAlumnoSalidas",dniAlumno_);

                intent.putExtra("datoDniProfesorSalidas",dniProfesor_);

                startActivity(intent);

                finish();


            }
        }).show();
    }

    public void recibirDatosActivity(){

        Bundle exras = getIntent().getExtras();


        if (exras == null){

            Toast.makeText(getApplicationContext(), "Error al pasar datos de una activity a otra", Toast.LENGTH_SHORT).show();

        }else {

            String d1 = exras.getString("datoDniProfesor");

            dniProfesor_ = d1;


        }





    }

    public void listadoAllDNIALumnos(){


        try{

            stmt = connection.createStatement();
            String sql = "SELECT dni FROM alumnos;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){


                String datosDniAlumnos = rs.getString(1);
                listadoDNiAlumnos.add(datosDniAlumnos);

            }

        }catch (Exception e){

        }

    }

    public void listadoSalidasComprobar(){

        try{

            stmt = connection.createStatement();
            String sql = "SELECT dnialumno,fecha FROM salidas;";
            ResultSet rs = stmt.executeQuery(sql);


            while (rs.next()){


                String datosDniAlumnos = rs.getString(1);
                Date datosFechaSalidad = rs.getDate(2);
                String datosFechaString = new SimpleDateFormat("yyyy-MM-dd").format(datosFechaSalidad);


                for (int j = 0; j < listadoDNiAlumnos.size(); j++) {

                    String dniComprobar = listadoDNiAlumnos.get(j);



                    if (dniComprobar.equals(datosDniAlumnos) && fechaActual.equals(datosFechaString)){


                        listadoDNIALumnosNoDisponibles.add(listadoDNiAlumnos.get(j));


                    }

                }

            }


        }catch (Exception e){



        }


    }

    public void listadoDniDefinitivo(){

        for (int f = 0; f <listadoDNIALumnosNoDisponibles.size() ; f++) {


            for (int i = 0; i < listadoDNiAlumnos.size(); i++) {

                String dniComprobar = listadoDNIALumnosNoDisponibles.get(f);

                if (dniComprobar.equals(listadoDNiAlumnos.get(i))) {

                    listadoDNiAlumnos.remove(i);

                }

            }

        }



    }
}