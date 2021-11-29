package com.noanails.tiendaappadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class Citas extends AppCompatActivity {
    private String servicioBBDD = "";
    private String fechaBBDD = "";
    private String horaBBDD = "";
    private String uId = "";
    private String id;
    private String idCita;
    ListAdapterUsuarios listAdapterUsuarios;
    FirebaseUser mAuth;
    DatabaseReference mDatabase;
    List<ListElemnt_Usuarios> elements;
    private String servicio="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas);
/*
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        id = mAuth.getUid();
        Button verCita = (Button) findViewById(R.id.buttonVerCitas);
        Button verCitaActual = (Button) findViewById(R.id.buttonVerCitasActules);
        Button volver = (Button) findViewById(R.id.buttonVolver2);
        elements = new ArrayList<>();

        verCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recogerCitas();
            }
        });

        verCitaActual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recogerCitasActualizadas();
            }
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Citas.this, Administracion.class));
            }
        });
    }

    //Se encarga de recger, comparar e insertar los datos junto con los elementos.
    public void recogerCitas() {
        mDatabase.child("Reservas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                elements.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {
                        servicioBBDD = snapshot.child("servicio").getValue().toString();
                        fechaBBDD = snapshot.child("fecha").getValue().toString();
                        horaBBDD = snapshot.child("hora").getValue().toString();
                        uId = snapshot.child("uId").getValue().toString();

                        if (uId.equals(id)) {
                            insertElements();
                        }
                    } catch (NullPointerException n) {
                        Toast.makeText(Citas.this, "No hay citas pendientes", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Citas.this, "Error BBDD", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Método encargado de crear e introducir los datos en cada elemento.
    public void insertElements() {
        elements.add(new ListElemnt_Usuarios(servicioBBDD, fechaBBDD, horaBBDD, null));
        ListAdapterUsuarios listAdapterUsuarios = new ListAdapterUsuarios(elements, this);
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapterUsuarios);
    }

    //Metodo para recoger todas las fechas desde el día de hoy incuido, las fechas pasadas no.
    public void recogerCitasActualizadas() {
        mDatabase.child("Reservas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Calendar calendario = Calendar.getInstance();
                int dia = calendario.get(Calendar.DAY_OF_MONTH);
                int mes = (calendario.get(Calendar.MONTH) + 1);
                int anyo = calendario.get(Calendar.YEAR);
                elements.clear();

                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        fechaBBDD = snapshot.child("fecha").getValue().toString();
                        horaBBDD = snapshot.child("hora").getValue().toString();
                        uId = snapshot.child("uId").getValue().toString();
                        servicioBBDD = snapshot.child("servicio").getValue().toString();
                        String extractFecha[] = fechaBBDD.split("/");
                        idCita = snapshot.getKey();
                        if (uId.equals(id)) {
                            if(((Integer.parseInt(extractFecha[2]) - anyo) >= 0) && ((Integer.parseInt(extractFecha[1]) - mes) >= 0) && ((Integer.parseInt(extractFecha[0]) - dia) >= 0)){
                                insertElementsActual();
                            }
                        }
                    }
                }catch (NullPointerException n){
                    Toast.makeText(Citas.this, "No hay citas pendientes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Citas.this, "Error BBDD", Toast.LENGTH_LONG).show();
            }
        });
    }

    //Método encargado de crear e introducir los datos en cada elemento.
    public void insertElementsActual() {
        elements.add(new ListElemnt_Usuarios(servicioBBDD,fechaBBDD, horaBBDD, idCita));
        listAdapterUsuarios = new ListAdapter2(elements, this);
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapterUsuarios);
    }*/
    }
}