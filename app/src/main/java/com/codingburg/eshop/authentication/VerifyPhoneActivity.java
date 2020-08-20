package com.codingburg.eshop.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codingburg.eshop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;


import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {

    private String verificationId;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private EditText editText;
    private String phonenumber;
    private String id ,category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        FirebaseDatabase.getInstance().goOffline();
        try{
            id = getIntent().getExtras().getString("id");
            category = getIntent().getExtras().getString("category");
        }
        catch (Exception e){

        }
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar);
        editText = findViewById(R.id.editTextCode);
         phonenumber = getIntent().getStringExtra("phonenumber");
        sendVerificationCode(phonenumber);
        findViewById(R.id.buttonSignIn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try{
                    String code = editText.getText().toString().trim();
                    if (code.isEmpty() || code.length() < 6) {
                        editText.setError("Enter code...");
                        editText.requestFocus();
                        return;
                    }
                    verifyCode(code);
                } catch (Exception e) {
                    Toast.makeText(VerifyPhoneActivity.this, "Error", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    private void verifyCode(String code) {
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            signInWithCredential(credential);
        }
        catch (Exception e){
            Toast.makeText(this, "Error, try againg",Toast.LENGTH_SHORT).show();
        }
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if(category != null) {
                                Intent intent = new Intent(VerifyPhoneActivity.this, NameAndGender.class);
                                intent.putExtra("phone", phonenumber);
                                intent.putExtra("category", category);
                                intent.putExtra("id", id);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                            else if(category == null){
                                Intent intent = new Intent(VerifyPhoneActivity.this, NameAndGender.class);
                                intent.putExtra("phone", phonenumber);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                            else {
                            Toast.makeText(VerifyPhoneActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(String number) {
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                editText.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };


}