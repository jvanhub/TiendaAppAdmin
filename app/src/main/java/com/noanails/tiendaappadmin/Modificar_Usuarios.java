package com.noanails.tiendaappadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

public class Modificar_Usuarios extends AppCompatActivity {
    Bundle bundle;
    private String idRefTablaButton;
    private Button btConfir, btVolver;
    private EditText etNombre, etAp1, etAp2, etTelf;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    private String nombreBBDD, ap1BBDD, ap2BBDD, nTelfBBDD, emailBBDD;
    private String nombre, ap1, ap2, nTelf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_usuarios);

        //Recibe datos (id del boton) desde ListaAdapter.java -> bindData.
        bundle = getIntent().getExtras();
        idRefTablaButton = bundle.getString("boton");
    }

    public void modificarDatosBBDD(){

        Map<String, Object> map = new HashMap<>();
        map.put("nombres", nombre);
        map.put("apellidos", ap1);
        map.put("apellidos2", ap2);
        map.put("n_telefonos", nTelf);
        map.put("emails", emailBBDD);
        mDatabase.child("Usuarios").child(mAuth.getUid()).setValue(map);
        startActivity(new Intent(Modificar_Usuarios.this, Usuarios.class));
        Toast.makeText(Modificar_Usuarios.this, "Se ha completado el cambio", Toast.LENGTH_LONG).show();
    }
}