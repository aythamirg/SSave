package edu.dam.ssave;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginProfesorActivity extends AppCompatActivity {

    EditText etdniLoginProfesor, etPasswordLoginProfesor;

    Button btnLoginProfesor, btnAjustesAdmin;

    ConexDB conexDB = new ConexDB();

    Connection connection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_profesor);


        connection = conexDB.conexionBD();


        if (connection == null) {

            Toast.makeText(getApplicationContext(), "Error al conectar a la base de datos, reinicie la aplicación", Toast.LENGTH_SHORT).show();

        }

        etdniLoginProfesor = (EditText) findViewById(R.id.etDNILoginProfesor);

        etPasswordLoginProfesor = (EditText) findViewById(R.id.etPasswordLoginProfesor);

        btnLoginProfesor = (Button) findViewById(R.id.btnLoginProfesor);

        btnAjustesAdmin = (Button) findViewById(R.id.btnAjustesAdmin);

        btnAjustesAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginProfesorActivity.this, LoginAdminActivity.class));

            }
        });

        btnLoginProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dniProfesorLogin = etdniLoginProfesor.getText().toString();
                String passwordProfesorLogin = etPasswordLoginProfesor.getText().toString();

                if (!dniProfesorLogin.isEmpty() || !passwordProfesorLogin.isEmpty()) {

                    loginProfesor(dniProfesorLogin, passwordProfesorLogin);
                } else {

                    Toast.makeText(getApplicationContext(), "Debe rellenar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    public void loginProfesor(String dniProfesor, String passwordProfesor) {

        String dniProfesor_ = dniProfesor;

        String passwordProfesor_ = passwordProfesor;

        try {


            String sql = "SELECT dni,contrasena FROM profesores WHERE dni = ? AND contrasena= ?;";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, dniProfesor_);
            pstm.setString(2, passwordProfesor_);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {

                Intent intent = new Intent(LoginProfesorActivity.this, VentanaPrincipalProfesorActivity.class);

                intent.putExtra("datoDniProfesor", dniProfesor_);

                startActivity(intent);

                finish();


            }

            pstm.executeUpdate();
        } catch (Exception e) {


        }
    }
}
