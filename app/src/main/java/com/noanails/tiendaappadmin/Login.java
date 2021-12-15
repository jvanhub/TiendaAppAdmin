package com.noanails.tiendaappadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Esta clase es la encargada de dar funcionalidad al activity_login.
 */
public class Login extends AppCompatActivity {
    //Variabes de los activitys
    private EditText et_email, et_pass;
    private TextView resPass;

    //Variables de recogida de datos.
    private String email, pass = "";

    //Variable emails autorizados.
    private String emailAutorizado = "x.proof.delta@gmail.com";

    //Instancia de la clase FirebaseAuth.
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        et_email = (EditText) findViewById(R.id.editTextEmail1);
        et_pass = (EditText) findViewById(R.id.editTextTextPass);
        Button entrar = (Button) findViewById(R.id.buttonEntrar);
        resPass = (TextView) findViewById(R.id.textViewResPass);

        /**
         * Método OnClick encargado de validad email.
         */
        resPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = et_email.getText().toString();
                if (!email.isEmpty()) {
                    resPass();
                } else {
                    Toast.makeText(Login.this, "Por favor, ingrese el Email.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**
         * Evento que accede a la clase y activity de "Bienvenida" al pulsar el botón "Entrar".
         */
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vista) {
                email = et_email.getText().toString();
                pass = et_pass.getText().toString();
                if (email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(Login.this, "Completa los campos", Toast.LENGTH_LONG).show();
                } else {
                    if (email.equals(emailAutorizado)) {
                        login();
                    } else {
                        Toast.makeText(Login.this, "Introduzca un email válido.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    /**
     * Método encargado de validar al usuario.
     */
    public void login() {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mUser = mAuth.getCurrentUser();
                if (task.isSuccessful()) {
                    if (mUser.isEmailVerified()) {
                        startActivity(new Intent(Login.this, Administracion.class));
                    } else {
                        Toast.makeText(Login.this, "Verifique su email, en el email de verificación que se envió al registrarse.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Login.this, "No se pudo iniciar sesion, compruebe los datos.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Método encargado de enviar email para poder restablecer contraseña.
     */
    public void resPass() {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Login.this, "Se ha enviado un correo de restablecimiento de contraseña.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login.this, "No se ha podido enviar correo de restablecimiento.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}