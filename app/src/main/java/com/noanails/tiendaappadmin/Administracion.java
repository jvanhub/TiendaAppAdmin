package com.noanails.tiendaappadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Administracion extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administracion);

        mAuth = FirebaseAuth.getInstance();
        Button pedir_cita = (Button) findViewById(R.id.button_pedir);
        Button ver_cita = (Button) findViewById(R.id.button_ver);
        Button cerrar_sesion = (Button) findViewById(R.id.buttonCerrarSession);
        Button perfil = (Button) findViewById(R.id.button_perfil);

        pedir_cita.setOnClickListener(v -> startActivity(new Intent(Administracion.this, Usuarios.class)));

        ver_cita.setOnClickListener(v -> startActivity(new Intent(Administracion.this, Citas.class)));

        cerrar_sesion.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(Administracion.this, Login.class));
            finish();
        });
    }
}