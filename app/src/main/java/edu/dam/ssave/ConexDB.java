package edu.dam.ssave;

import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexDB {

    Connection conexion = null;


    public Connection conexionBD(){
        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection("jdbc:postgresql://192.168.1.16:5432/pruebasaplicacion1", "postgres", "1234");

        }catch (Exception er){


        }
        return  conexion;
    }

    //Creamos la funcion para Cerrar la Conexion
    protected  void cerrar_conexion(Connection con)throws  Exception{
        con.close();

    }
}
