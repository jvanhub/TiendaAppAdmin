package com.noanails.tiendaappadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Esta clase es la encargada de dar funcionalidad al activity_administracion.
 */
public class Administracion extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administracion);

        mAuth = FirebaseAuth.getInstance();
        Button usuarios = (Button) findViewById(R.id.button_pedir);
        Button ver_cita = (Button) findViewById(R.id.button_ver);
        Button cerrar_sesion = (Button) findViewById(R.id.buttonCerrarSession);

        /**
         * Evento para acceder a la clase y activity de "usuarios" al pulsar el botón "Usuarios".
         */
        usuarios.setOnClickListener(v -> startActivity(new Intent(Administracion.this, Usuarios.class)));

        /**
         * Evento para acceder a la clase y activity de "citas" al pulsar el botón "Citas Usuarios".
         */
        ver_cita.setOnClickListener(v -> startActivity(new Intent(Administracion.this, Citas.class)));

        /**
         * Evento para acceder a la clase y activity de "Login" al pulsar el botón "Cerrar sesión" que además cierra la sesión
         * en el servidor mediante el método signOut.
         */
        cerrar_sesion.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(Administracion.this, Login.class));
            finish();
        });
    }
}