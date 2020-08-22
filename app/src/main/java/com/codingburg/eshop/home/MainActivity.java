package com.codingburg.eshop.home;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.codingburg.eshop.R;

import com.codingburg.eshop.authentication.PleaseLogin;
import com.codingburg.eshop.cetegory.Category;
import com.codingburg.eshop.cetegory.CetegoryAdapter;
import com.codingburg.eshop.cetegory.CetegoryModel;
import com.codingburg.eshop.discount.Discount;

import com.codingburg.eshop.shop.ShopAdapter;
import com.codingburg.eshop.shop.ShopModel;
import com.codingburg.eshop.profile.Profile;

import com.codingburg.eshop.cart.Cart;

import com.codingburg.eshop.model.ModelAdapter;
import com.codingburg.eshop.model.ModelAdapterList;
import com.codingburg.eshop.model.ModelData;
import com.codingburg.eshop.model.ModelDataList;

import com.codingburg.eshop.search.Seach;
import com.codingburg.eshop.subcetegory.SubCetegory;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.appbar.MaterialToolbar;
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
import com.google.firebase.firestore.Query;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    CarouselView carouselView ,carouselView2;
    TextView seeall;
    String[] sampleImages = new String[2];
    String[] sampleImages2 = new String[2];
    private  RecyclerView recyclerView, recyclerView2, recyclerView3, recyclerView5, recyclerviewshop;
    private RecyclerView.LayoutManager RecyclerViewLayoutManager, RecyclerViewLayoutManager2;
    private ModelAdapter modelAdapter, modelAdapter2;
    private ShopAdapter shopAdapter;
    private CetegoryAdapter modelAdapter5;
    private ModelAdapterList modelAdapter3;
    LinearLayoutManager HorizontalLayout;
    private FirebaseAuth mAuth;
    private  String userId;
    MaterialToolbar toolbar;
    private  TextView seeallaerro;
    private  FirebaseFirestore db;
    private DocumentReference offerTop, discount;
    private ImageView men, women, kids;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        men = findViewById(R.id.men);
        women = findViewById(R.id.women);
        kids = findViewById(R.id.kids);
        seeall = findViewById(R.id.seeall);
        seeallaerro = findViewById(R.id.seeallaerro);
        mAuth =FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseDatabase.getInstance().goOffline();

        try {
            userId = mAuth.getCurrentUser().getUid();
        } catch (Exception e) {
            e.printStackTrace();
        }
        men.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SubCetegory.class);
                intent.putExtra("id", "men");
                startActivity(intent);
            }
        });
        women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SubCetegory.class);
                intent.putExtra("id", "women");
                startActivity(intent);
            }
        });
        kids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SubCetegory.class);
                intent.putExtra("id", "baby");
                startActivity(intent);
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





        seeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Category.class);
                startActivity(intent);
            }
        });
        seeallaerro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Category.class);
                startActivity(intent);
            }
        });
        //buttom navigation
         toolbar =  findViewById(R.id.topAppBar);
        toolbar.setOnMenuItemClickListener(new androidx.appcompat.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.search){
                    Intent intent = new Intent(getApplicationContext(), Seach.class);
                    startActivity(intent);
                }
                return false;
            }
        });

        //offer
        getDiscoutItems();
        getOfferItems();
        //1st recycel


//recyclerView new cetegory
        db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("category");
        Query categoryName = collectionReference.orderBy("id");
        recyclerView5 = (RecyclerView)findViewById(R.id.recyclerview4);
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView5.setLayoutManager(RecyclerViewLayoutManager);
        HorizontalLayout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView5.setLayoutManager(HorizontalLayout);
        FirestoreRecyclerOptions<CetegoryModel> options5 =
                new FirestoreRecyclerOptions.Builder<CetegoryModel>()
                        .setQuery(categoryName, CetegoryModel.class)
                        .build();
        modelAdapter5 = new CetegoryAdapter(options5);
        recyclerView5.setAdapter(modelAdapter5);
//recyclerView new cetegory


        //recyclerView new shop
        CollectionReference shop =db.collection("shop");
        com.google.firebase.firestore.Query shopId = shop.orderBy("id");
        recyclerviewshop = (RecyclerView)findViewById(R.id.recyclerviewshop);
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerviewshop.setLayoutManager(RecyclerViewLayoutManager);
        HorizontalLayout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerviewshop.setLayoutManager(HorizontalLayout);
        FirestoreRecyclerOptions<ShopModel> options6 =
                new FirestoreRecyclerOptions.Builder<ShopModel>()
                        .setQuery(shopId, ShopModel.class)
                        .build();

        shopAdapter = new ShopAdapter(options6);
        recyclerviewshop.setAdapter(shopAdapter);
//recyclerView new shop





//recyclerView new products
        CollectionReference newProducts = db.collection("new");
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(RecyclerViewLayoutManager);
        HorizontalLayout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);
        FirestoreRecyclerOptions<ModelData> options =
                new FirestoreRecyclerOptions.Builder<ModelData>()
                        .setQuery(newProducts, ModelData.class)
                        .build();
        modelAdapter = new ModelAdapter(options);
        recyclerView.setAdapter(modelAdapter);
//recyclerView new products

//recyclerView top products
        CollectionReference topProducts = db.collection("top");
        recyclerView2 = (RecyclerView)findViewById(R.id.recyclerview2);
        RecyclerViewLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        recyclerView2.setLayoutManager(RecyclerViewLayoutManager2);
        HorizontalLayout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(HorizontalLayout);
        FirestoreRecyclerOptions<ModelData> options2 =
                new FirestoreRecyclerOptions.Builder<ModelData>()
                        .setQuery(topProducts, ModelData.class)
                        .build();
        modelAdapter2 = new ModelAdapter(options2);
        recyclerView2.setAdapter(modelAdapter2);
//recyclerView top products

 // home product show
        recyclerView3 = (RecyclerView) findViewById(R.id.recyclerView3);
//        recyclerView3.setHasFixedSize(true);
//        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
        recyclerView3.setLayoutManager(new GridLayoutManager(this, 2 ,GridLayoutManager.VERTICAL, false));
        FirestoreRecyclerOptions<ModelDataList> options3 =
                new FirestoreRecyclerOptions.Builder<ModelDataList>()
                        .setQuery(db.collection("homepage"), ModelDataList.class)
                        .build();
        modelAdapter3 = new ModelAdapterList(options3);
        recyclerView3.setAdapter(modelAdapter3);
        // home product show
    }
    @Override
    protected void onStart() {
        super.onStart();
        modelAdapter.startListening();
        modelAdapter2.startListening();
        modelAdapter3.startListening();
        modelAdapter5.startListening();
        shopAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        modelAdapter.stopListening();
        modelAdapter2.stopListening();
        modelAdapter3.stopListening();
        modelAdapter5.stopListening();
        shopAdapter.stopListening();
    }

    private void getOfferItems() {

       offerTop = db.collection("offer").document("newtopoffer");
       offerTop.addSnapshotListener(new EventListener<DocumentSnapshot>() {
           @Override
           public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
               if(error != null){
                   return;
               }
               if(value.exists()){
                   Map<String , Object>map = (Map<String, Object>) value.getData();
                   if(map.get("imageone")!=null){
                       String offerone = (String) map.get("imageone");
                       sampleImages2[0] = offerone;
                   }
                   if(map.get("imagetwo")!=null){
                       String offertwo = (String) map.get("imagetwo");
                       sampleImages2[1] = offertwo;
                   }

               }
               carouselView2 = (CarouselView) findViewById(R.id.carouselView2);
               ImageListener imageListener2 = new ImageListener() {
                   @Override
                   public void setImageForPosition(int position, ImageView imageView) {
                       Picasso.get().load(sampleImages2[position]).fit().centerCrop().into(imageView);
                   }
               };
               carouselView2.setImageListener(imageListener2);
               carouselView2.setPageCount(sampleImages2.length);
               carouselView2.setImageClickListener(new ImageClickListener() {
                   @Override
                   public void onClick(int position) {

                   }
               });

           }
       });

    }

    private void getDiscoutItems() {
        discount = db.collection("discount").document("newdiscount");
        discount.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    return;
                }
                if(value.exists()){
                    Map<String , Object>map = (Map<String, Object>) value.getData();
                    if(map.get("imageone")!=null){
                        String offerone = (String) map.get("imageone");
                        sampleImages[0] = offerone;
                    }
                    if(map.get("imagetwo")!=null){
                        String offertwo = (String) map.get("imagetwo");
                        sampleImages[1] = offertwo;
                    }

                }
                carouselView = (CarouselView) findViewById(R.id.carouselView);
                ImageListener imageListener = new ImageListener() {
                    @Override
                    public void setImageForPosition(int position, ImageView imageView) {
                        Picasso.get().load(sampleImages[position]).fit().centerCrop().into(imageView);
                    }
                };
                carouselView.setImageListener(imageListener);
                carouselView.setPageCount(sampleImages.length);
                carouselView.setImageClickListener(new ImageClickListener() {
                    @Override
                    public void onClick(int position) {
                        if(position == 0 || position == 1){
                            Intent intent = new Intent(getApplicationContext(), Discount.class);
                            startActivity(intent);
                        }
                    }
                });

            }
        });

    }

}
