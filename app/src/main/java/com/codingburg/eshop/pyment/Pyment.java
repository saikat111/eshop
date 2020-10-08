package com.codingburg.eshop.pyment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codingburg.eshop.R;
import com.codingburg.eshop.cart.Cart;
import com.codingburg.eshop.home.MainActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Pyment extends AppCompatActivity {
    private String ammount;
    private TextView total, type,typ2, sim1,sim2,rsim1, rsim2,helpnumber;
    private FirebaseAuth firebaseAuth;
    private String userId;
    private DatabaseReference admin,userDb;
    private Button order;
    private EditText number, addrress, contact;
    private DatabaseReference numberDb;
    private ProgressDialog progressDialog;
    private com.google.android.gms.ads.InterstitialAd mInterstitialAd;
    private AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pyment);
        total = findViewById(R.id.total);
        type = findViewById(R.id.type);
        typ2 = findViewById(R.id.type2);
        sim1 = findViewById(R.id.sim1);
        sim2 = findViewById(R.id.sim2);
        rsim1 = findViewById(R.id.rsim1);
        rsim2 = findViewById(R.id.rsim2);
        order = findViewById(R.id.order);
        contact = findViewById(R.id.contact);
        number = findViewById(R.id.number);
        helpnumber = findViewById(R.id.helpnumber);
        addrress = findViewById(R.id.address);
        firebaseAuth = FirebaseAuth.getInstance();
        builder = new AlertDialog.Builder(this);
        progressDialog = new ProgressDialog(this);
        mInterstitialAd = new com.google.android.gms.ads.InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.instatianlads1));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });
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
        getNumber();
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOrder();
            }
        });

    }

    private void getNumber() {

        numberDb = FirebaseDatabase.getInstance().getReference().child("number").child("bkash");

         numberDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Map<String , Object> map = (Map<String, Object>) snapshot.getValue();
                    if(map.get("sim1") !=null){
                        String num1 = map.get("sim1").toString();
                        sim1.setText(num1);
                    }
                    if(map.get("smi2") !=null){
                        String num2 = map.get("smi2").toString();
                        sim2.setText(num2);
                    }
                    if(map.get("type") !=null){
                        String type1 = map.get("type").toString();
                        type.setText(type1);
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        numberDb = FirebaseDatabase.getInstance().getReference().child("number").child("roket");
        numberDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Map<String , Object> map = (Map<String, Object>) snapshot.getValue();
                    if(map.get("sim1") !=null){
                        String num1 = map.get("sim1").toString();
                        rsim1.setText(num1);
                    }
                    if(map.get("smi2") !=null){
                        String num2 = map.get("smi2").toString();
                        rsim2.setText(num2);
                    }
                    if(map.get("type") !=null){
                        String type1 = map.get("type").toString();
                        typ2.setText(type1);
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        numberDb = FirebaseDatabase.getInstance().getReference().child("number").child("helpnumber");
        numberDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Map<String , Object> map = (Map<String, Object>) snapshot.getValue();
                    if(map.get("number") !=null){
                        String num1 = map.get("number").toString();
                        helpnumber.setText(num1);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

    private void getOrder() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String formattedDate = df.format(c.getTime());
        final String getnumber = number.getText().toString();
        final String getAddress =addrress.getText().toString();
        final String getContactNumber =contact.getText().toString();
        if(getnumber == null){
            Toast.makeText(getApplicationContext(), "আপনার নম্বর লিখুন", Toast.LENGTH_SHORT).show();

            return;
        }
        if(getAddress == null){
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
                         data.put("personal-number", getContactNumber);
                         data.put("date", formattedDate);
                         admin.child("details").updateChildren(data);
                         Map userorderdata = new HashMap();
                         userorderdata.put("ordernumber", key);
                        userorderdata.put("totaltk", total.getText().toString());
                        userorderdata.put("status","Pending");
                        userorderdata.put("date", formattedDate);
                        userdata.updateChildren(userorderdata);
                         delete.removeValue();
                         userDb.removeValue();
                         progressDialog.dismiss();
                        builder.setMessage("আমাদের কাছ থেকে কেনাকাটা করার জন্য ধন্যবাদ")
                                .setCancelable(false)
                                .setPositiveButton("পরবর্তী পৃষ্ঠা", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        if (mInterstitialAd.isLoaded()) {
                                            mInterstitialAd.show();
                                        } else {

                                        }
                                    }
                                })
                                .setNegativeButton("পরবর্তী পৃষ্ঠা", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //  Action for 'NO' Button
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        dialog.cancel();
                                        if (mInterstitialAd.isLoaded()) {
                                            mInterstitialAd.show();
                                        } else {

                                        }
                                    }
                                });
                        //Creating dialog box
                        AlertDialog alert = builder.create();
                        //Setting the title manually
                        alert.setTitle("আপনার অর্ডার সম্পন্ন হয়েছে!");
                        alert.show();
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