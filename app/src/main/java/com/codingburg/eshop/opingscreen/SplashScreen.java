package com.codingburg.eshop.opingscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.codingburg.eshop.R;
import com.codingburg.eshop.home.MainActivity;
import com.google.firebase.database.FirebaseDatabase;


public class SplashScreen extends AppCompatActivity {
private  int SPLASH_TIME_OUT = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        FirebaseDatabase.getInstance().goOffline();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                        finish();



            }
        }, SPLASH_TIME_OUT);
    }

}