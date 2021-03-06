package com.noanails.tiendaappadmin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Esta clase es la encargada de dar funcionalidad al activity_modificar_citas.
 */
public class Modificar_Citas extends AppCompatActivity {

    private RadioButton hora9, hora10, hora11, hora12, hora15, hora16, hora17, hora18, hora19;
    private RadioGroup rg;
    private Button confirmar, fecha, volver;
    private String fechaCompletaTv, horaCita, fechaBBDD, horaBBDD, id, servicioBBDD, idRefTablaButton, textHint, nombreBBDD, nTelfBBDD, emailBBDD, idUsuario = "";
    int radioId;
    RadioButton selectedbutton;
    Bundle bundle;
    ArrayList<RadioButton> arrayRadioButtons = new ArrayList<>();
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_citas);

        //Recibe datos (id del boton) desde ListaAdapter2.java -> bindData.
        bundle = getIntent().getExtras();
        idRefTablaButton = bundle.getString("boton");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        rg = (RadioGroup) findViewById(R.id.radioGroup_M);
        fecha = (Button) findViewById(R.id.button_fechaMod);

        confirmar = (Button) findViewById(R.id.button_confirmar_m);
        volver = (Button) findViewById(R.id.buttonVolver_m);
        hora9 = (RadioButton) findViewById(R.id.radiobutton9_00m);
        hora10 = (RadioButton) findViewById(R.id.radiobutton10_00m);
        hora11 = (RadioButton) findViewById(R.id.radiobutton11_00m);
        hora12 = (RadioButton) findViewById(R.id.radiobutton12_00m);
        hora15 = (RadioButton) findViewById(R.id.radiobutton15_00m);
        hora16 = (RadioButton) findViewById(R.id.radiobutton16_00m);
        hora17 = (RadioButton) findViewById(R.id.radiobutton17_00m);
        hora18 = (RadioButton) findViewById(R.id.radiobutton18_00m);
        hora19 = (RadioButton) findViewById(R.id.radiobutton19_00m);

        //A??ade al array los referencias del RadioButton.
        arrayRadioButtons.add(hora9);
        arrayRadioButtons.add(hora10);
        arrayRadioButtons.add(hora11);
        arrayRadioButtons.add(hora12);
        arrayRadioButtons.add(hora15);
        arrayRadioButtons.add(hora16);
        arrayRadioButtons.add(hora17);
        arrayRadioButtons.add(hora18);
        arrayRadioButtons.add(hora19);

        calendarDate();

        /**
         * Evento que accede al m??todo "calendarDate()" para seleccionar fecha.
         */
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarDate();
            }
        });

        /**
         * Evento que accede al m??todo para que cuando pulsamos sobre el bot??n "Confirmar" haga las
         * comprobaciones y acceda al m??todo "modificador()"
         */
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioId = rg.getCheckedRadioButtonId();
                selectedbutton = findViewById(radioId);

               try {
                    if (fechaCompletaTv.equals("")) {
                    } else if (rg.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(Modificar_Citas.this, "SELECCIONE HORA", Toast.LENGTH_LONG).show();
                    } else {
                        modificador();
                    }
               }catch (NullPointerException np){
                    Toast.makeText(Modificar_Citas.this, "SELECCIONE FECHA", Toast.LENGTH_LONG).show();
                }
            }
        });

        /**
         * Evento que accede a la clase y activity de "Bienvenida" al pulsar el bot??n "Vovler".
         */
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Modificar_Citas.this, Citas.class));
            }
        });
    }

    /**
     * M??todo para que cuando pulsamos muestre el calendario.
     */
    public void calendarDate() {
        Calendar calendario = Calendar.getInstance();
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        int mes = calendario.get(Calendar.MONTH);
        int anyo = calendario.get(Calendar.YEAR);

        //Cuadro del calendario.
        DatePickerDialog datePikerDialog = new DatePickerDialog(Modificar_Citas.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fechaCompletaTv = dayOfMonth + "/" + (month + 1) + "/" + year;
                textHint = fecha.getText().toString() + " ";
                fecha.setText(textHint + fechaCompletaTv);
                extraerValores();
            }
        }
                , anyo, mes, dia);
        datePikerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#8AB7E6")));

        //Fecha m??nima, para evitar citas de d??as anteriores al actual.
        datePikerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePikerDialog.show();
    }

    /**
     * M??todo que realiza la consulta en la base de datos para recoger los datos.
     */
    public void extraerValores() {
        mDatabase.child("Reservas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    mDatabase.child("Reservas").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            fechaBBDD = snapshot.child("fecha").getValue().toString();
                            horaBBDD = snapshot.child("hora").getValue().toString();
                            comparador();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Modificar_Citas.this, "Error BBDD", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                retornarEstado();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Modificar_Citas.this, "Error BBDD", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * M??todo que recorre el array y compara los datos en la base de datos para desactivar radioButtons.
     */
    public void comparador() {
        String textoRB;
        RadioButton selectedbutton;

        for (int i = 0; i < arrayRadioButtons.size(); i++) {
            selectedbutton = findViewById(arrayRadioButtons.get(i).getId());
            textoRB = selectedbutton.getText().toString();

            if (fechaBBDD.equals(fechaCompletaTv) && horaBBDD.equals(textoRB)) {
                selectedbutton.setEnabled(false);
            }
        }
    }

    /**
     * M??todo que activa de nuevo los radioButtons cada vez que cambias de fecha.
     */
    public void retornarEstado() {
        String textoRB;
        RadioButton selectedbutton;
        for (int i = 0; i < arrayRadioButtons.size(); i++) {
            selectedbutton = findViewById(arrayRadioButtons.get(i).getId());
            selectedbutton.setEnabled(true);
        }
        rg.clearCheck();
    }

    /**
     * M??todo que modifica los campos en la base de datos.
     */
    public void modificador() {
        mDatabase.child("Reservas").child(idRefTablaButton).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                servicioBBDD = snapshot.child("servicio").getValue().toString();
                nombreBBDD = snapshot.child("nombre").getValue().toString();
                nTelfBBDD = snapshot.child("telefono").getValue().toString();
                emailBBDD = snapshot.child("email").getValue().toString();
                id = snapshot.child("uId").getValue().toString();

                Calendar calendario = Calendar.getInstance();
                int dia = calendario.get(Calendar.DAY_OF_MONTH);
                int mes = (calendario.get(Calendar.MONTH) + 1);
                int anyo = calendario.get(Calendar.YEAR);
                String extractFecha[] = fechaBBDD.split("/");
                if (Integer.parseInt(extractFecha[2]) - anyo < 0) {
                } else if (Integer.parseInt(extractFecha[1]) - mes < 0) {
                } else if (Integer.parseInt(extractFecha[0]) - dia < 0) {
                } else {
                    horaCita = selectedbutton.getText().toString();
                    Map<String, Object> map = new HashMap<>();
                    map.put("servicio", servicioBBDD);
                    map.put("fecha", fechaCompletaTv);
                    map.put("hora", horaCita);
                    map.put("uId", id);
                    map.put("nombre", nombreBBDD);
                    map.put("telefono", nTelfBBDD);
                    map.put("email", emailBBDD);
                    mDatabase.child("Reservas").child(idRefTablaButton).setValue(map);
                    startActivity(new Intent(Modificar_Citas.this, Citas.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Modificar_Citas.this, "Error BBDD", Toast.LENGTH_LONG).show();
            }
        });
    }
}

