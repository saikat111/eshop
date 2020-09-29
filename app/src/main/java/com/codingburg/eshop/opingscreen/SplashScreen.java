package com.codingburg.eshop.opingscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.codingburg.eshop.R;
import com.codingburg.eshop.home.MainActivity;
import com.google.firebase.database.FirebaseDatabase;

import static com.codingburg.eshop.opingscreen.Note.SHARED_PREFS;


public class SplashScreen extends AppCompatActivity {
private  int SPLASH_TIME_OUT = 3000;
    private String text;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        FirebaseDatabase.getInstance().goOffline();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                text = sharedPreferences.getString(TEXT, "");
                if(text.equals("done")){
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
               else {
                    Intent intent = new Intent(SplashScreen.this, Note.class);
                    startActivity(intent);
                    finish();
                    return;
                }



            }
        }, SPLASH_TIME_OUT);
    }

}