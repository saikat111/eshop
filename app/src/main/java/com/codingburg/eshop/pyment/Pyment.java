package com.codingburg.eshop.pyment;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codingburg.eshop.R;
import com.codingburg.eshop.home.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Pyment extends AppCompatActivity {
    private String ammount;
    private TextView total;
    private FirebaseAuth firebaseAuth;
    private String userId;
    private DatabaseReference admin,userDb;
    private Button order;
    private EditText number, addrress;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pyment);
        total = findViewById(R.id.total);
        order = findViewById(R.id.order);
        number = findViewById(R.id.number);
        addrress = findViewById(R.id.address);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        try {
            userId = firebaseAuth.getCurrentUser().getUid();
            if(userId.equals(null)){
                return;
            }
            userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("totalammountofcartitem");
        } catch (Exception e) {
            e.printStackTrace();
        }
        getTotal();
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOrder();
            }
        });

    }

    private void getOrder() {
        final String getnumber = number.getText().toString();
        final String getAddress =addrress.getText().toString();
        if(getnumber.equals(null)){
            Toast.makeText(getApplicationContext(), "আপনার নম্বর লিখুন", Toast.LENGTH_SHORT).show();

            return;
        }
        if(getAddress.equals(null)){
            Toast.makeText(getApplicationContext(), "দয়া করে আপনার ঠিকানা লিখুন", Toast.LENGTH_SHORT).show();
            return;
        }
        final String key = FirebaseDatabase.getInstance().getReference().child("admin").child("user").push().getKey();
        admin = FirebaseDatabase.getInstance().getReference().child("admin").child("order").child(key);
        final DatabaseReference delete = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("cart");
        progressDialog.setMessage("অনুগ্রহপূর্বক অপেক্ষা করুন");
        progressDialog.show();
        delete.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                admin.child("cart-list").setValue(snapshot.getValue()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DatabaseReference userdata = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("order").child(key);
                        DatabaseReference user =  FirebaseDatabase.getInstance().getReference().child("admin").child("user").child(key);

                        Map admindata = new HashMap();
                        admindata.put("orderid", key);
                        admindata.put("userid", userId);
                        user.setValue(admindata);
                         Map data = new HashMap();
                         data.put("userid", userId);
                         data.put("total-tk", total.getText().toString());
                         data.put("address", getAddress);
                         data.put("bkash-number", getnumber);
                         admin.child("details").updateChildren(data);
                         Map userorderdata = new HashMap();
                         userorderdata.put("ordernumber", key);
                        userorderdata.put("totaltk", total.getText().toString());
                        userorderdata.put("status","Pending");
                        userdata.updateChildren(userorderdata);
                         delete.removeValue();
                         userDb.removeValue();
                         progressDialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "অনুগ্রহপূর্বক আবার চেষ্টা করুন", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Pyment.class);
                        startActivity(intent);
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void getTotal() {
                userDb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Map<String , Object> map = (Map<String, Object>) snapshot.getValue();
                            if(map.get("totalammout") != null){
                                String tk = map.get("totalammout").toString();
                                total.setText(tk);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }
}