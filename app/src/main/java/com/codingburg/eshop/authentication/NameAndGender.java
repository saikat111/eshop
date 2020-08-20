package com.codingburg.eshop.authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.codingburg.eshop.R;
import com.codingburg.eshop.home.MainActivity;
import com.codingburg.eshop.productdetails.ProductDetails;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NameAndGender extends AppCompatActivity {
    private EditText nick_name, number ;
    private FirebaseAuth mAuth;
    private Button singin;
    private  String userId;
    private String phonenumber;
    private String id ,category;
    private DocumentReference currentUserDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_and_gender);
        FirebaseDatabase.getInstance().goOffline();
        try{
            id = getIntent().getExtras().getString("id");
            category = getIntent().getExtras().getString("category");
        }
        catch (Exception e){

        }
        nick_name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        singin = findViewById(R.id.singin);
        phonenumber = getIntent().getStringExtra("phone");
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        getUserInfo();
        singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nick_name.getText().toString();
                String getNumber = number.getText().toString();
                 currentUserDb = FirebaseFirestore.getInstance().collection("Users").document(userId).collection("info").document(userId);
                if(category !=null) {
                    Map userInfo = new HashMap();
                    userInfo.put("name", name);
                    userInfo.put("phone", phonenumber);
                    userInfo.put("contract", getNumber);
                    currentUserDb.set(userInfo);
                    Intent intent = new Intent(getApplicationContext(), ProductDetails.class);
                    intent.putExtra("category", category);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    finish();
                }
                else if(category == null){
                    Map userInfo = new HashMap();
                    userInfo.put("name", name);
                    userInfo.put("phone", phonenumber);
                    userInfo.put("contract", getNumber);
                    currentUserDb.set(userInfo);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void getUserInfo() {
        currentUserDb = FirebaseFirestore.getInstance().collection("Users").document(userId).collection("info").document(userId);
        currentUserDb.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error !=null){
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    return;
                }
                if (value.exists()) {
                    Map<String, Object> map = (Map<String, Object>) value.getData();
                    if (map.get("name") != null) {
                        String aboutForDisplay = map.get("name").toString();
                        nick_name.setText(aboutForDisplay);
                    }
                    if (map.get("contract") != null) {
                        String aboutForDisplay = map.get("contract").toString();
                        number.setText(aboutForDisplay);
                    }
                }
            }
        });
    }
}