package com.noanails.tiendaappadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Modificar_Usuarios extends AppCompatActivity {
    Bundle bundle;
    private Button btConfir, btVolver;
    private EditText etNombre, etAp1, etAp2, etTelf, etEmail;
    private String nombreBBDD, ap1BBDD, ap2BBDD, nTelfBBDD, emailBBDD,nombre, ap1, ap2, nTelf,email,idUsu, idRefTablaButton="";
    FirebaseAuth mmAuth;
    DatabaseReference mmDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_usuarios);

        //Recibe datos (id del boton) desde ListaAdapter.java -> bindData.
        bundle = getIntent().getExtras();
        idRefTablaButton = bundle.getString("boton");

        mmAuth = FirebaseAuth.getInstance();
        mmDatabase = FirebaseDatabase.getInstance().getReference();
        btConfir = findViewById(R.id.buttonModConfir);
        btVolver = findViewById(R.id.buttonModVolver);
        etNombre = findViewById(R.id.editTextModNombre);
        etAp1 = findViewById(R.id.editTextModApe1);
        etAp2 = findViewById(R.id.editTextModApe2);
        etTelf = findViewById(R.id.editTextModPhone);
        etEmail = findViewById(R.id.editTextModEmail);

        mmDatabase.child("Usuarios").child(idRefTablaButton).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                idRefTablaButton = snapshot.getKey();
                nombreBBDD = snapshot.child("nombres").getValue().toString();
                ap1BBDD = snapshot.child("apellidos").getValue().toString();
                ap2BBDD = snapshot.child("apellidos2").getValue().toString();
                nTelfBBDD = snapshot.child("n_telefonos").getValue().toString();
                emailBBDD = snapshot.child("emails").getValue().toString();
                etNombre.setText(nombreBBDD);
                etAp1.setText(ap1BBDD);
                etAp2.setText(ap2BBDD);
                etTelf.setText(nTelfBBDD);
                etEmail.setText(emailBBDD);
            }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        btConfir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = etNombre.getText().toString();
                ap1 = etAp1.getText().toString();
                ap2 = etAp2.getText().toString();
                nTelf = etTelf.getText().toString();
                email = etEmail.getText().toString();

                if(nombre.isEmpty()||ap1.isEmpty()||ap2.isEmpty()||nTelf.isEmpty()){
                }else if(nTelf.length() <9 || nTelf.length() >9){
                    Toast.makeText(Modificar_Usuarios.this, "Número de telefono incorrecto", Toast.LENGTH_SHORT).show();
                }else{
                    modificarDatosBBDD();
                }
            }
        });
        btVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Modificar_Usuarios.this, Usuarios.class));
            }
        });
    }

    public void modificarDatosBBDD(){
        Map<String, Object> map = new HashMap<>();
        map.put("nombres", nombre);
        map.put("apellidos", ap1);
        map.put("apellidos2", ap2);
        map.put("n_telefonos", nTelf);
        map.put("emails", emailBBDD);
        mmDatabase.child("Usuarios").child(idRefTablaButton).setValue(map);
        startActivity(new Intent(Modificar_Usuarios.this, Usuarios.class));
        Toast.makeText(Modificar_Usuarios.this, "Se ha completado el cambio", Toast.LENGTH_LONG).show();
    }
}