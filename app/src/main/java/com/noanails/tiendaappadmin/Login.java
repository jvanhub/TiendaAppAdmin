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

public class Login extends AppCompatActivity {
    //Variabes de los activitys
    private EditText et_email;
    private EditText et_pass;
    private TextView resPass;

    //Variables de recogida de datos.
    private String email = "";
    private String pass = "";

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
        Button registro = (Button) findViewById(R.id.buttonRegistro);
        resPass = (TextView) findViewById(R.id.textViewResPass);

        resPass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                email = et_email.getText().toString();
                if (!email.isEmpty()){
                    resPass();
                }else{
                    Toast.makeText(Login.this, "Por favor, ingrese el Email.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Método OnClick del button Entrar.
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vista) {
                email = et_email.getText().toString();
                pass = et_pass.getText().toString();
                if (email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(Login.this, "Completa los campos", Toast.LENGTH_LONG).show();
                } else {
                    login();
                }
            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Registro.class));
            }
        });
    }

    //Método para validar usuario.
    public void login() {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mUser = mAuth.getCurrentUser();
                if (task.isSuccessful()) {
                    if(mUser.isEmailVerified()){
                        startActivity(new Intent(Login.this, Administracion.class));
                    }else {
                        Toast.makeText(Login.this, "Verifique su email, en el email de verificación que se envió al registrarse.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Login.this, "No se pudo iniciar sesion, compruebe los datos.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Método para enviar correo para poder restablecer contraseña.
    public void resPass(){
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Login.this, "Se ha enviado un correo de restablecimiento de contraseña.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Login.this, "No se ha podido enviar correo de restablecimiento.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}