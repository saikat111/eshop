package com.codingburg.eshop.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.codingburg.eshop.R;
import com.google.firebase.database.FirebaseDatabase;

public class PleaseLogin extends AppCompatActivity {
    private Button login;
    private String id ,category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_please_login);
        login = findViewById(R.id.login);
        FirebaseDatabase.getInstance().goOffline();
       try{
           id = getIntent().getExtras().getString("id");
           category = getIntent().getExtras().getString("category");
       }
       catch (Exception e){

       }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(category !=null ){
                    Intent intent = new Intent(getApplicationContext(), PhoneNumber.class);
                    intent.putExtra("category", category);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
                else if(category == null){
                    Intent intent = new Intent(getApplicationContext(), PhoneNumber.class);
                    startActivity(intent);
                }



            }
        });
    }
}