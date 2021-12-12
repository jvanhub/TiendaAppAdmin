package com.noanails.tiendaappadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.SearchEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import clasesObjeto.ListAdapterCitas;
import clasesObjeto.ListAdapterUsuarios;
import clasesObjeto.ListElement_Usuarios;

public class Usuarios extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private String nombreBBDD, ap1BBDD, ap2BBDD, nTelfBBDD, emailBBDD,idUsuario;
    private SearchView svu;
    FirebaseUser mAuth;
    DatabaseReference mDatabase;
    ListAdapterUsuarios listAdapterUsuarios;
    List<ListElement_Usuarios> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        svu = findViewById(R.id.SearchViewUsu);
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Button volver = (Button) findViewById(R.id.buttonVolver2);
        elements = new ArrayList<>();

        recogerUsuarios();
        initListener();

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Usuarios.this, Administracion.class));
            }
        });
    }

    private void initListener(){
        svu.setOnQueryTextListener(Usuarios.this);
    }

    //Se encarga de recoger, comparar e insertar los datos junto con los elementos.
    public void recogerUsuarios() {
        mDatabase.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                elements.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {
                        nombreBBDD = snapshot.child("nombres").getValue().toString();
                        ap1BBDD = snapshot.child("apellidos").getValue().toString();
                        ap2BBDD = snapshot.child("apellidos2").getValue().toString();
                        nTelfBBDD = snapshot.child("n_telefonos").getValue().toString();
                        emailBBDD = snapshot.child("emails").getValue().toString();
                        idUsuario = snapshot.getKey();
                        insertElements();
                    } catch (NullPointerException n) {
                        Toast.makeText(Usuarios.this, "No hay usuarios registrados", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Usuarios.this, "Error BBDD", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Método encargado de crear e introducir los datos en cada elemento.
    public void insertElements() {
        elements.add(new ListElement_Usuarios(nombreBBDD, ap1BBDD, ap2BBDD,nTelfBBDD,emailBBDD,idUsuario));
        listAdapterUsuarios = new ListAdapterUsuarios(elements, this);
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapterUsuarios);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        //listAdapterUsuarios.filter(newText);
        return false;
    }
}