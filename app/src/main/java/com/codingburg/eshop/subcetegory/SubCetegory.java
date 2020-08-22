package com.codingburg.eshop.subcetegory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.codingburg.eshop.R;
import com.codingburg.eshop.cart.Cart;
import com.codingburg.eshop.cetegory.CetegoryAdapter;
import com.codingburg.eshop.cetegory.CetegoryModel;
import com.codingburg.eshop.home.MainActivity;
import com.codingburg.eshop.profile.Profile;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class SubCetegory extends AppCompatActivity {
    private RecyclerView recyclerView3;
    private CetegoryAdapter modelAdapter5;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products);
        id = getIntent().getExtras().getString("id");
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
                if(itemIndex == 0){
                    Intent intent = new Intent(getApplicationContext(), Profile.class);
                    startActivity(intent);
                }
                if(itemIndex == 1){
                    Intent intent = new Intent(getApplicationContext(), Cart.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                if(itemIndex == 0){
                    Intent intent = new Intent(getApplicationContext(), Profile.class);
                    startActivity(intent);
                }
            }
        });
        FirebaseFirestore find = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = find.collection("category");
        Query query = collectionReference.orderBy("sub-category").startAt(id).endAt(id + "\uf8ff");
        // home product show
        recyclerView3 = (RecyclerView) findViewById(R.id.recyclerView10);
//        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new GridLayoutManager(this, 3 ,GridLayoutManager.VERTICAL, false));
        FirestoreRecyclerOptions<CetegoryModel> options3 =
                new FirestoreRecyclerOptions.Builder<CetegoryModel>()
                        .setQuery(query, CetegoryModel.class)
                        .build();
        modelAdapter5 = new CetegoryAdapter(options3);
        recyclerView3.setAdapter(modelAdapter5);
    }
    @Override
    protected void onStart() {
        super.onStart();
        modelAdapter5.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        modelAdapter5.stopListening();
    }

}