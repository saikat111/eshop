package com.codingburg.eshop.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codingburg.eshop.R;
import com.codingburg.eshop.authentication.PleaseLogin;
import com.codingburg.eshop.cart.Cart;
import com.codingburg.eshop.home.MainActivity;
import com.codingburg.eshop.model.ModelAdapter;
import com.codingburg.eshop.model.ModelAdapterList;
import com.codingburg.eshop.model.ModelDataList;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import java.util.Map;

public class Profile extends AppCompatActivity {
    private  ProfileAdapter modelAdapter3;
    private FirebaseAuth mAuth;
    private String userId;
    private TextView number, name;
    private DocumentReference currentUserDb;
    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase.getInstance().goOnline();
        setContentView(R.layout.activity_profile);
        number = findViewById(R.id.number);
        logout = findViewById(R.id.logout);
        name = findViewById(R.id.name);
        mAuth = FirebaseAuth.getInstance();
        try {
            userId = mAuth.getCurrentUser().getUid();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
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
                    if (userId == null) {
                        Intent intent = new Intent(getApplicationContext(), PleaseLogin.class);
                        startActivity(intent);
                        return;
                    } else {
                        Intent intent = new Intent(getApplicationContext(), Profile.class);
                        startActivity(intent);
                    }
                }
                if (itemIndex == 1) {
                    if (userId == null) {
                        Intent intent = new Intent(getApplicationContext(), PleaseLogin.class);
                        startActivity(intent);
                        return;
                    } else {
                        Intent intent = new Intent(getApplicationContext(), Cart.class);
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                if (itemIndex == 0) {
                    Intent intent = new Intent(getApplicationContext(), Profile.class);
                    startActivity(intent);
                }
            }
        });
        getData();
// home product show
        try{
            RecyclerView    recyclerView3 = (RecyclerView) findViewById(R.id.recyclerView3);
            recyclerView3.setLayoutManager(new LinearLayoutManager(this));
            FirebaseRecyclerOptions<ProfileModel> options3 =
                    new FirebaseRecyclerOptions.Builder<ProfileModel>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("order"), ProfileModel.class)
                            .build();
            modelAdapter3 = new ProfileAdapter(options3);
            recyclerView3.setAdapter(modelAdapter3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // home product show
    }
    private void getData() {
        try{
            currentUserDb = FirebaseFirestore.getInstance().collection("Users").document(userId).collection("info").document(userId);


        currentUserDb.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error !=null){
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    return;
                }
                if(value.exists()){
                    Map<String, Object> map = (Map<String, Object>)value.getData();
                    if(map.get("contract") != null)
                    {
                        String getNumber = (String) map.get("contract");
                        number.setText(getNumber);
                    }
                    if(map.get("name") != null)
                    {
                        String getnmae = (String) map.get("name");
                        name.setText(getnmae);
                    }
                }
            }
        });
        } catch (Exception e) {
            Intent intent = new Intent(getApplicationContext(), PleaseLogin.class);
            startActivity(intent);
            return;
        }


    }
    @Override
    protected void onStart() {
        super.onStart();
        try{
            modelAdapter3.startListening();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    protected void onStop() {
        super.onStop();
        try{
            modelAdapter3.stopListening();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}