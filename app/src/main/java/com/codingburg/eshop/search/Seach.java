package com.codingburg.eshop.search;

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
import com.codingburg.eshop.cart.Cart;
import com.codingburg.eshop.home.MainActivity;
import com.codingburg.eshop.productviewmodel.ProductViewModel;
import com.codingburg.eshop.productviewmodel.ProductViewModelAdapter;
import com.codingburg.eshop.profile.Profile;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class Seach extends AppCompatActivity {
    private RecyclerView recyclerView3;
    private ProductViewModelAdapter modelAdapter3;
    private  String category;
    private CollectionReference databaseReferenced, search;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products);
        FirebaseDatabase.getInstance().goOffline();
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
        //buttom navigation
        // home product show
        databaseReferenced = FirebaseFirestore.getInstance().collection("new");
        recyclerView3 = (RecyclerView) findViewById(R.id.recyclerView10);
//        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new GridLayoutManager(this, 2 ,GridLayoutManager.VERTICAL, false));
        FirestoreRecyclerOptions<ProductViewModel> options3 =
                new FirestoreRecyclerOptions.Builder<ProductViewModel>()
                        .setQuery(databaseReferenced, ProductViewModel.class)
                        .build();
        modelAdapter3 = new ProductViewModelAdapter(options3);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchNow(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchNow(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    private void searchNow(String s) {
        db = FirebaseFirestore.getInstance();
        search = db.collection("product");
        Query query = search.orderBy("tag").startAt(s).endAt(s + "\uf8ff");
        FirestoreRecyclerOptions<ProductViewModel> options3 =
                new FirestoreRecyclerOptions.Builder<ProductViewModel>()
                        .setQuery(query, ProductViewModel.class)
                        .build();
        modelAdapter3 = new ProductViewModelAdapter(options3);
        modelAdapter3.startListening();
        recyclerView3.setAdapter(modelAdapter3);
        //

    }
}