package com.codingburg.eshop.cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.codingburg.eshop.R;
import com.codingburg.eshop.home.MainActivity;
import com.codingburg.eshop.profile.Profile;
import com.codingburg.eshop.pyment.ShippingCharge;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import java.util.Map;

public class Cart extends AppCompatActivity {
    private RecyclerView recyclerView3;
    private CardAdapter modelAdapter3;
    private FirebaseAuth firebaseAuth;
    private String userId;
    private TextView total;
    private Button buy;
    private DatabaseReference userDb;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading.......");
        progressDialog.show();
        FirebaseDatabase.getInstance().goOnline();
        buy = findViewById(R.id.buy);
        total = findViewById(R.id.total);
        firebaseAuth = FirebaseAuth.getInstance();
        try {
            userId = firebaseAuth.getCurrentUser().getUid();
            if (userId.equals(null)) {
                return;
            }
            userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("totalammountofcartitem");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //buttom navigation
        SpaceNavigationView spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("profile", R.drawable.ic_baseline_person_outline_24));
        spaceNavigationView.addSpaceItem(new SpaceItem("cart", R.drawable.cart));
        spaceNavigationView.setCentreButtonIcon(R.drawable.ic_baseline_home_24);
        spaceNavigationView.setCentreButtonColor(ContextCompat.getColor(this, R.color.space_white));
        spaceNavigationView.showIconOnly();
        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                if (itemIndex == 0) {
                    Intent intent = new Intent(getApplicationContext(), Profile.class);
                    startActivity(intent);
                }
                if (itemIndex == 1) {
                    Intent intent = new Intent(getApplicationContext(), Cart.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                if (itemIndex == 1) {
                    Intent intent = new Intent(getApplicationContext(), Cart.class);
                    startActivity(intent);
                }
            }
        });
        //buttom navigation

        getTotal();

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(getApplicationContext(), ShippingCharge.class);
                    if (Integer.parseInt(total.getText().toString()) == 15) {
                        startActivity(intent);
                        return;
                    }
                    if (total.getText().toString().equals(null) || Integer.parseInt(total.getText().toString()) < 199) {
                        Toast.makeText(getApplicationContext(), "Minimum order amount is 200 tk", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        // home product show
        recyclerView3 = (RecyclerView) findViewById(R.id.recyclerView3);
//        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<CardData> options3 =
                new FirebaseRecyclerOptions.Builder<CardData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("cart"), CardData.class)
                        .build();
        modelAdapter3 = new CardAdapter(options3);
        recyclerView3.setAdapter(modelAdapter3);
        // home product show
    }

    private void getTotal() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                userDb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                            if (map.get("totalammout") != null) {
                                String tk = map.get("totalammout").toString();
                                total.setText(tk);
                                progressDialog.dismiss();
                            }
                        } else {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
                    }
                });
            }
        }, 7000);


    }


    @Override
    protected void onStart() {
        super.onStart();
        modelAdapter3.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        modelAdapter3.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void processsearch(String s) {
        FirebaseRecyclerOptions<CardData> options3 =
                new FirebaseRecyclerOptions.Builder<CardData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("cart").orderByChild("name").startAt(s.toUpperCase()).endAt(s.toUpperCase() + "\uf8ff"), CardData.class)
                        .build();
        modelAdapter3 = new CardAdapter(options3);
        modelAdapter3.startListening();
        recyclerView3.setAdapter(modelAdapter3);
    }
}