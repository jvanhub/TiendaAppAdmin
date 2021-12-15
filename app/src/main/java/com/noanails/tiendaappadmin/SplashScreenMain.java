package com.noanails.tiendaappadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Esta clase es la encargada de dar funcionalidad al activity_splash_screen.
 */
public class SplashScreenMain extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        /**
         * Se encarga de verificar si el usuario verifico el email.
         */
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                if (mUser != null){
                    if (mUser.isEmailVerified()){
                        startActivity(new Intent(SplashScreenMain.this, Administracion.class));
                        finish();
                    }else {
                        startActivity(new Intent(SplashScreenMain.this, Login.class));
                        finish();
                    }
                }else{
                    startActivity(new Intent(SplashScreenMain.this, Login.class));
                    finish();
                }
            }
        };

        //Se encarga de mantener X tiempo el activity abierto.
        Timer tiempo = new Timer();
        tiempo.schedule(tt,2500);
    }
}