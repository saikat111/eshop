package com.codingburg.eshop.productdetails;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codingburg.eshop.R;
import com.codingburg.eshop.authentication.PleaseLogin;

import com.codingburg.eshop.model.ModelAdapter;
import com.codingburg.eshop.model.ModelAdapterList;
import com.codingburg.eshop.model.ModelData;
import com.codingburg.eshop.model.ModelDataList;

import com.codingburg.eshop.search.Seach;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetails extends AppCompatActivity {
    private ImageSlider imageSlider, imageSlider2;
    private Button  addcart, add, remove;
    private TextView price, name, quantity ,total, time, location, details, getname, getnuber;
    private int quantityValue =1;
    private String id , categoryfrom;
    private DocumentReference productDb;
    private CollectionReference showCatagoryProducts;
    private CarouselView carouselView;
    String[] sampleImages = new String[3];
    private RecyclerView recyclerView,  recyclerView3;
    private RecyclerView.LayoutManager RecyclerViewLayoutManager;
    private ModelAdapter modelAdapter;
    private ModelAdapterList modelAdapter3;
    LinearLayoutManager HorizontalLayout;
    private  String userId;
    private  FirebaseAuth firebaseAuth,mAuth;
    private DatabaseReference cartUserDb,userDb;
    private String userName,userName2, phoneNumber,phoneNumber2, key,offerone;
    private ProgressDialog progressDialog;
    private AdView mAdView, mAdView2 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        FirebaseDatabase.getInstance().goOffline();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        //ads
        AdLoader adLoader = new AdLoader.Builder(this, getString(R.string.native_ID_1))
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        TemplateView template = findViewById(R.id.my_template);
                        template.setNativeAd(unifiedNativeAd);
                    }
                })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());

        AdLoader adLoader2 = new AdLoader.Builder(this, getString(R.string.native_ID_2))
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        TemplateView template = findViewById(R.id.my_template2);
                        template.setNativeAd(unifiedNativeAd);
                    }
                })
                .build();

        adLoader2.loadAd(new AdRequest.Builder().build());

        AdLoader adLoader3 = new AdLoader.Builder(this, getString(R.string.native_ID_2))
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        TemplateView template = findViewById(R.id.my_template3);
                        template.setNativeAd(unifiedNativeAd);
                    }
                })
                .build();

        adLoader3.loadAd(new AdRequest.Builder().build());

        AdLoader adLoader4 = new AdLoader.Builder(this, getString(R.string.native_ID_2))
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        TemplateView template = findViewById(R.id.my_template4);
                        template.setNativeAd(unifiedNativeAd);
                    }
                })
                .build();

        adLoader4.loadAd(new AdRequest.Builder().build());

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView2 = findViewById(R.id.adView2);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdView2.loadAd(adRequest);

        //ads


        id = getIntent().getExtras().getString("id");
        categoryfrom = getIntent().getExtras().getString("category");
        getname = findViewById(R.id.getname);
        getnuber = findViewById(R.id.getnumber);
        addcart = findViewById(R.id.addcard);
        add = findViewById(R.id.add);
        remove = findViewById(R.id.remove);
        price = findViewById(R.id.price);
        name = findViewById(R.id.model);
        quantity = findViewById(R.id.quantity);
        total = findViewById(R.id.total);
        location = findViewById(R.id.location);
        time = findViewById(R.id.time);
        details = findViewById(R.id.details);
        quantity.setText("1");
        firebaseAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        final String[] getKey = new String[1];
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding to your cart..");



        try {
            userId = firebaseAuth.getCurrentUser().getUid();
        }
        catch (Exception e){
            e.printStackTrace();
        }


        imageSlider = findViewById(R.id.image_slider);
        final ArrayList<String> idP = new ArrayList<>();
        final ArrayList<String> category = new ArrayList<>();
        final List<SlideModel> remoteImages = new ArrayList<>();
        FirebaseFirestore.getInstance().collection("promote").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(QueryDocumentSnapshot documentSnapshot : value){
                    remoteImages.add(new SlideModel(documentSnapshot.get("image").toString(), ScaleTypes.FIT));
                    idP.add(documentSnapshot.get("id").toString());
                    category.add(documentSnapshot.get("category").toString());
                }
                imageSlider.setImageList(remoteImages, ScaleTypes.FIT);
                imageSlider.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onItemSelected(int i) {
                        Intent intent = new Intent(getApplicationContext(), ProductDetails.class);
                        intent.putExtra("id", idP.get(i));
                        intent.putExtra("category", category.get(i));
                        startActivity(intent);
                    }
                });
            }
        });

        productDb = FirebaseFirestore.getInstance().collection("product").document(id);
        getProductDetails();
        getDiscoutItems();
        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                FirebaseDatabase.getInstance().goOnline();
                try{
                    key = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("cart").push().getKey();
                    cartUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("cart").child(key);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                String totalammout = total.getText().toString();
                String productname = name.getText().toString();
                String productQuantity = quantity.getText().toString();
                if(userId == null ){
                    Intent intent = new Intent(getApplicationContext(), PleaseLogin.class);
                    intent.putExtra("category", category);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    return;
                }
                if(totalammout.equals(null) || productname.equals(null) || productQuantity.equals(null)){
                    return;
                }
                else if(totalammout != null || productname != null ||productQuantity != null ){
                    productDb.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            if(error != null){
                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                                return;
                            }
                            if(value.exists()){
                                Map<String, Object> map = (Map<String, Object>) value.getData();
                                if(map.get("image")!=null)
                                {
                                    offerone = (String) map.get("image");
                                    Map cartInfo =new HashMap();
                                    cartInfo.put("image", offerone);
                                    cartUserDb.updateChildren(cartInfo);
                                }
                            }
                        }
                    });
                    Map cartInfo =new HashMap();
                    cartInfo.put("category", category);
                    cartInfo.put("id", id);
                    cartInfo.put("totalprice", totalammout);
                    cartInfo.put("name", productname);
                    cartInfo.put("quantity", productQuantity);
                    cartInfo.put("key", key);
                    cartInfo.put("image", offerone);
                    cartUserDb.updateChildren(cartInfo).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            FirebaseDatabase.getInstance().goOffline();
                            Toast.makeText(getApplicationContext(), "addedto your cart", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            FirebaseDatabase.getInstance().goOffline();
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), "Try againg...", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String value =  quantity.getText().toString();
              int intValue = Integer.parseInt(value);
              int result = intValue + quantityValue;
              quantity.setText(String.valueOf(result));
              String totalp =  price.getText().toString();
              int totalpInt = Integer.parseInt(totalp);
              int finalPrice = totalpInt * result;
              total.setText(String.valueOf(finalPrice));

            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value =  quantity.getText().toString();
                int intValue = Integer.parseInt(value);
                int result = intValue - quantityValue;
                if(result <0 ){
                    return;
                }
                quantity.setText(String.valueOf(result));
                String totalp =  price.getText().toString();
                int totalpInt = Integer.parseInt(totalp);
                int finalPrice = totalpInt * result;
                if(finalPrice < 0){
                    return;
                }
                total.setText(String.valueOf(finalPrice));

            }
        });
        //recyclerView show products
        showCatagoryProducts = FirebaseFirestore.getInstance().collection("product");
        Query query = showCatagoryProducts.whereEqualTo("category", categoryfrom);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(RecyclerViewLayoutManager);
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);
        FirestoreRecyclerOptions<ModelData> options =
                new FirestoreRecyclerOptions.Builder<ModelData>()
                        .setQuery(query, ModelData.class)
                        .build();
        modelAdapter = new ModelAdapter(options);
        recyclerView.setAdapter(modelAdapter);
//recyclerView new products
        // home product show
        recyclerView3 = (RecyclerView) findViewById(R.id.recyclerView3);
//        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new GridLayoutManager(this, 2 ,GridLayoutManager.VERTICAL, false));
       FirestoreRecyclerOptions<ModelDataList> options3 =
                new FirestoreRecyclerOptions.Builder<ModelDataList>()
                        .setQuery(FirebaseFirestore.getInstance().collection("top"), ModelDataList.class)
                        .build();
        modelAdapter3 = new ModelAdapterList(options3);
        recyclerView3.setAdapter(modelAdapter3);
        // home product show

    }

    private void getProductDetails() {
      productDb.addSnapshotListener(new EventListener<DocumentSnapshot>() {
          @Override
          public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
              if(error != null){
                  Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                  return;
              }
              if (value != null && value.exists()) {
                  Map<String, Object> map = (Map<String, Object>) value.getData();
                  if(map.get("name") != null){
                      String getValue = map.get("name").toString();
                      name.setText(getValue);
                  }
                  if(map.get("price") != null){
                      String getValue = map.get("price").toString();
                      price.setText(getValue);
                      total.setText(getValue);
                  }
                  if(map.get("time") != null){
                      String getValue = map.get("time").toString();
                      time.setText(getValue);
                  }
                  if(map.get("location") != null){
                      String getValue = map.get("location").toString();
                      location.setText(getValue);
                  }
                  if(map.get("description") != null){
                      String getValue = map.get("description").toString();
                      details.setText(getValue);
                  }
              }

          }
      });
    }
    private void getDiscoutItems() {
        productDb.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                    return;
                }
                if (value != null && value.exists()) {
                    Map<String , Object>map = (Map<String, Object>) value.getData();
                    if(map.get("image")!=null){
                        String offerone = (String) map.get("image");
                        sampleImages[0] = offerone;
                    }
                    if(map.get("imagetwo")!=null){
                        String offertwo = (String) map.get("imagetwo");
                        sampleImages[1] = offertwo;
                    }
                    if(map.get("imagethree")!=null){
                        String offerthree = (String) map.get("imagethree");
                        sampleImages[2] = offerthree;
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

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        modelAdapter.startListening();
        modelAdapter3.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        modelAdapter.stopListening();
        modelAdapter3.stopListening();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_bar, menu);
        MenuItem item = menu.findItem(R.id.search);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(getApplicationContext(), Seach.class);
                startActivity(intent);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}