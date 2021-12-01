package com.noanails.tiendaappadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    private String idRefTablaButton;
    private Button btConfir, btVolver;
    private EditText etNombre, etAp1, etAp2, etTelf;
    FirebaseAuth mmAuth;
    DatabaseReference mmDatabase;
    private String nombreBBDD, ap1BBDD, ap2BBDD, nTelfBBDD, emailBBDD;
    private TextView tvNombre, tvAp, tvTelf, tvEail;
    private String nombre, ap1, ap2, nTelf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_usuarios);

        //Recibe datos (id del boton) desde ListaAdapter.java -> bindData.
        bundle = getIntent().getExtras();
        idRefTablaButton = bundle.getString("boton");

        mmAuth = FirebaseAuth.getInstance();
        mmDatabase = FirebaseDatabase.getInstance().getReference();

        tvNombre = findViewById(R.id.editTextModNombre);
        tvAp = findViewById(R.id.editTextModApe1);
        tvTelf = findViewById(R.id.editTextModApe2);
        tvEail = findViewById(R.id.editTextModPhone);

        mmDatabase.child("Usuarios").child(idRefTablaButton).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nombreBBDD = snapshot.child("nombres").getValue().toString();
                ap1BBDD = snapshot.child("apellidos").getValue().toString();
                ap2BBDD = snapshot.child("apellidos2").getValue().toString();
                nTelfBBDD = snapshot.child("n_telefonos").getValue().toString();
                emailBBDD = snapshot.child("emails").getValue().toString();
                tvNombre.setText(nombreBBDD);
                tvAp.setText(ap1BBDD +" "+ap2BBDD);
                tvTelf.setText(nTelfBBDD);
                tvEail.setText(emailBBDD);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        mmDatabase.child("Usuarios").child(mmAuth.getUid()).setValue(map);
        startActivity(new Intent(Modificar_Usuarios.this, Usuarios.class));
        Toast.makeText(Modificar_Usuarios.this, "Se ha completado el cambio", Toast.LENGTH_LONG).show();
    }
}