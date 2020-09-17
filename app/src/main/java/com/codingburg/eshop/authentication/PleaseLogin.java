package com.codingburg.eshop.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.codingburg.eshop.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class PleaseLogin extends AppCompatActivity {
    private Button login;
    private String id ,category;
    private LinearLayout fb;
    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;

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

        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        fb = findViewById(R.id.fb);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(PleaseLogin.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException error) {
                    }
                });
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(category !=null ){
                                Intent intent = new Intent(getApplicationContext(), NameAndGender.class);
                                intent.putExtra("category", category);
                                intent.putExtra("id", id);
                                startActivity(intent);
                            }
                            else if(category == null){
                                Intent intent = new Intent(getApplicationContext(), NameAndGender.class);
                                startActivity(intent);
                            }
                        } else {

                            Toast.makeText(PleaseLogin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}