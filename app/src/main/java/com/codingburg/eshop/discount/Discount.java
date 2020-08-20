package com.codingburg.eshop.discount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.codingburg.eshop.R;
import com.codingburg.eshop.authentication.PleaseLogin;
import com.codingburg.eshop.cart.Cart;
import com.codingburg.eshop.home.MainActivity;
import com.codingburg.eshop.productviewmodel.ProductViewModel;
import com.codingburg.eshop.productviewmodel.ProductViewModelAdapter;
import com.codingburg.eshop.profile.Profile;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class Discount extends AppCompatActivity {
    private RecyclerView recyclerView3;
    private DiscountAdapter modelAdapter3;
    private FirebaseAuth mAuth;
    private String userId;
    private  FirebaseFirestore db;
    private CollectionReference discountItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        try {
            userId = mAuth.getCurrentUser().getUid();
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
                if(itemIndex == 0){
                    if(userId == null){
                        Intent intent = new Intent(getApplicationContext(), PleaseLogin.class);
                        startActivity(intent);
                        return;
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(), Profile.class);
                        startActivity(intent);
                    }
                }
                if(itemIndex == 1){
                    if(userId == null){
                        Intent intent = new Intent(getApplicationContext(), PleaseLogin.class);
                        startActivity(intent);
                        return;
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(), Cart.class);
                        startActivity(intent);
                    }
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
        //buttom navigation

        // home product show
      discountItems = db.collection("discount").document("items").collection("list");
        recyclerView3 = (RecyclerView) findViewById(R.id.recyclerView10);
//        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new GridLayoutManager(this, 2 ,GridLayoutManager.VERTICAL, false));
        FirestoreRecyclerOptions<DiscountModel> options3 =
                new FirestoreRecyclerOptions.Builder<DiscountModel>()
                        .setQuery(discountItems, DiscountModel.class)
                        .build();
        modelAdapter3 = new DiscountAdapter(options3);
        recyclerView3.setAdapter(modelAdapter3);
        // home product show

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

}