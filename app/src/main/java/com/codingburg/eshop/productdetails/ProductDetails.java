package com.codingburg.eshop.productdetails;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codingburg.eshop.comment.Post;
import com.codingburg.eshop.R;
import com.codingburg.eshop.authentication.PleaseLogin;

import com.codingburg.eshop.cart.Cart;

import com.codingburg.eshop.comment.CommentAdapter;
import com.codingburg.eshop.comment.CommentData;
import com.codingburg.eshop.model.ModelAdapter;
import com.codingburg.eshop.model.ModelAdapterList;
import com.codingburg.eshop.model.ModelData;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetails extends AppCompatActivity {
    private ImageSlider imageSlider;
    private Button addcart, add, remove;
    private TextView price, name, quantity, total, time, location, details, getname, getnuber, previousprice, discount;
    private int quantityValue = 1;
    private String id, categoryfrom;
    private DocumentReference productDb;
    private CollectionReference showCatagoryProducts;
    private RecyclerView recyclerView, recyclerView3;
    private RecyclerView.LayoutManager RecyclerViewLayoutManager;
    private ModelAdapter modelAdapter;
    private ModelAdapterList modelAdapter3;
    LinearLayoutManager HorizontalLayout;
    private String userId;
    private FirebaseAuth firebaseAuth, mAuth;
    private DatabaseReference cartUserDb;
    private String key, offerone;
    private ProgressDialog progressDialog;
    private AdView mAdView, mAdView2;
    private ImageView edit;
    private RecyclerView recyclerviewreviews;
    private CommentAdapter modelAdapter5;
    private FirebaseFirestore db;
    private ImageView imageproduct;
    private EditText ordernote;
    private String getOrderNote;
    private AlertDialog.Builder builder;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        builder = new AlertDialog.Builder(this);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        //ads
        AudienceNetworkAds.initialize(this);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView2 = findViewById(R.id.adView1);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdView2.loadAd(adRequest1);
        AdLoader adLoader3 = new AdLoader.Builder(this, getString(R.string.native_ID_2))
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        TemplateView template = findViewById(R.id.my_template4);
                        template.setNativeAd(unifiedNativeAd);
                    }
                })
                .build();

        adLoader3.loadAd(new AdRequest.Builder().build());


        //ads
        interstitialAd = new InterstitialAd(this, getString(R.string.fb_int_1));


        id = getIntent().getExtras().getString("id");
        categoryfrom = getIntent().getExtras().getString("category");
        getname = findViewById(R.id.getname);
        ordernote = findViewById(R.id.ordernote);
        edit = findViewById(R.id.edit);
        discount = findViewById(R.id.discount);
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
        previousprice = findViewById(R.id.previceprice);
        imageproduct = findViewById(R.id.imageproduct);
        try {
            userId = firebaseAuth.getCurrentUser().getUid();
        } catch (Exception e) {
            e.printStackTrace();
        }
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userId == null) {
                    Toast.makeText(getApplicationContext(), "Please login", Toast.LENGTH_SHORT).show();
                    return;
                }
                Post exampleDialog = new Post(getIntent().getExtras().getString("id"));
                exampleDialog.show(getSupportFragmentManager(), "example dialog");
            }
        });
        imageSlider = findViewById(R.id.image_slider);
        final ArrayList<String> idP = new ArrayList<>();
        final ArrayList<String> category = new ArrayList<>();
        final List<SlideModel> remoteImages = new ArrayList<>();
        FirebaseFirestore.getInstance().collection("promote").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (QueryDocumentSnapshot documentSnapshot : value) {
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
                builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);
                FirebaseDatabase.getInstance().goOnline();
                try {
                    getOrderNote = ordernote.getText().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.show();

                try {
                    key = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("cart").push().getKey();
                    cartUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("cart").child(key);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    String totalammout = total.getText().toString();
                    String productname = name.getText().toString();
                    String productQuantity = quantity.getText().toString();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

                }
                String totalammout = total.getText().toString();
                String productname = name.getText().toString();
                String productQuantity = quantity.getText().toString();
                if (userId == null) {
                    Intent intent = new Intent(getApplicationContext(), PleaseLogin.class);
                    intent.putExtra("category", categoryfrom);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    return;
                }
                if (totalammout == null || productname == null || productQuantity == null) {
                    return;
                } else if (totalammout != null || productname != null || productQuantity != null) {
                    productDb.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                                return;
                            }
                            if (value.exists()) {
                                Map<String, Object> map = (Map<String, Object>) value.getData();
                                if (map.get("image") != null) {
                                    offerone = (String) map.get("image");
                                    Map cartInfo = new HashMap();
                                    cartInfo.put("image", offerone);
                                    cartUserDb.updateChildren(cartInfo);
                                }
                            }
                        }
                    });
                    Map cartInfo = new HashMap();
                    cartInfo.put("category", categoryfrom);
                    cartInfo.put("id", id);
                    cartInfo.put("totalprice", totalammout);
                    cartInfo.put("name", productname);
                    cartInfo.put("quantity", productQuantity);
                    cartInfo.put("key", key);
                    cartInfo.put("note", getOrderNote);
                    cartInfo.put("image", offerone);
                    cartUserDb.updateChildren(cartInfo).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Toast.makeText(getApplicationContext(), "added to your cart", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            FirebaseDatabase.getInstance().goOffline();
                            builder.setMessage(getString(R.string.alert))
                                    .setCancelable(false)
                                    .setPositiveButton(getString(R.string.addmore), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
                                                @Override
                                                public void onInterstitialDisplayed(Ad ad) {
                                                    // Interstitial ad displayed callback

                                                }

                                                @Override
                                                public void onInterstitialDismissed(Ad ad) {
                                                    finish();
                                                    startActivity(getIntent());
                                                }

                                                @Override
                                                public void onError(Ad ad, AdError adError) {
                                                    finish();
                                                    startActivity(getIntent());
                                                }

                                                @Override
                                                public void onAdLoaded(Ad ad) {
                                                    interstitialAd.show();
                                                }

                                                @Override
                                                public void onAdClicked(Ad ad) {


                                                }

                                                @Override
                                                public void onLoggingImpression(Ad ad) {
                                                    finish();
                                                    startActivity(getIntent());

                                                }
                                            };

                                            // For auto play video ads, it's recommended to load the ad
                                            // at least 30 seconds before it is shown
                                            interstitialAd.loadAd(
                                                    interstitialAd.buildLoadAdConfig()
                                                            .withAdListener(interstitialAdListener)
                                                            .build());

                                        }
                                    })
                                    .setNegativeButton(getString(R.string.butnow), new DialogInterface.OnClickListener() {
                                        public void onClick(final DialogInterface dialog, int id) {
                                            //  Action for 'NO' Button
                                            InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
                                                @Override
                                                public void onInterstitialDisplayed(Ad ad) {
                                                    // Interstitial ad displayed callback

                                                }

                                                @Override
                                                public void onInterstitialDismissed(Ad ad) {
                                                    Intent intent = new Intent(getApplicationContext(), Cart.class);
                                                    startActivity(intent);
                                                    dialog.cancel();

                                                }

                                                @Override
                                                public void onError(Ad ad, AdError adError) {
                                                    Intent intent = new Intent(getApplicationContext(), Cart.class);
                                                    startActivity(intent);
                                                    dialog.cancel();

                                                }

                                                @Override
                                                public void onAdLoaded(Ad ad) {
                                                    interstitialAd.show();
                                                }

                                                @Override
                                                public void onAdClicked(Ad ad) {


                                                }

                                                @Override
                                                public void onLoggingImpression(Ad ad) {
                                                    Intent intent = new Intent(getApplicationContext(), Cart.class);
                                                    startActivity(intent);
                                                    dialog.cancel();

                                                }
                                            };

                                            // For auto play video ads, it's recommended to load the ad
                                            // at least 30 seconds before it is shown
                                            interstitialAd.loadAd(
                                                    interstitialAd.buildLoadAdConfig()
                                                            .withAdListener(interstitialAdListener)
                                                            .build());

                                        }
                                    });
                            //Creating dialog box
                            AlertDialog alert = builder.create();
                            //Setting the title manually
                            alert.setTitle("সতর্কতা!");
                            alert.show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            FirebaseDatabase.getInstance().goOffline();
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), "Try againg...", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            FirebaseDatabase.getInstance().goOffline();
                        }
                    });
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = quantity.getText().toString();
                int intValue = Integer.parseInt(value);
                int result = intValue + quantityValue;
                quantity.setText(String.valueOf(result));
                String totalp = price.getText().toString();
                int totalpInt = Integer.parseInt(totalp);
                int finalPrice = totalpInt * result;
                total.setText(String.valueOf(finalPrice));

            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = quantity.getText().toString();
                int intValue = Integer.parseInt(value);
                int result = intValue - quantityValue;
                if (result < 0) {
                    return;
                }
                quantity.setText(String.valueOf(result));
                String totalp = price.getText().toString();
                int totalpInt = Integer.parseInt(totalp);
                int finalPrice = totalpInt * result;
                if (finalPrice < 0) {
                    return;
                }
                total.setText(String.valueOf(finalPrice));

            }
        });
        //recyclerView show products
        showCatagoryProducts = FirebaseFirestore.getInstance().collection("product");
        Query query = showCatagoryProducts.whereEqualTo("category", categoryfrom);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
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
        //comments
        db = FirebaseFirestore.getInstance();
        CollectionReference newProducts = db.collection("product").document(id).collection("comments");
        recyclerviewreviews = (RecyclerView) findViewById(R.id.recyclerviewreviews);
        recyclerviewreviews.setLayoutManager(new LinearLayoutManager(this));
        FirestoreRecyclerOptions<CommentData> options4 =
                new FirestoreRecyclerOptions.Builder<CommentData>()
                        .setQuery(newProducts, CommentData.class)
                        .build();
        modelAdapter5 = new CommentAdapter(options4);
        recyclerviewreviews.setAdapter(modelAdapter5);


    }

    private void getProductDetails() {
        productDb.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (value != null && value.exists()) {
                    Map<String, Object> map = (Map<String, Object>) value.getData();
                    if (map.get("name") != null) {
                        String getValue = map.get("name").toString();
                        name.setText(getValue);
                    }
                    if (map.get("price") != null) {
                        String getValue = map.get("price").toString();
                        price.setText(getValue);
                        total.setText(getValue);
                    }
                    if (map.get("time") != null) {
                        String getValue = map.get("time").toString();
                        time.setText(getValue);
                    }
                    if (map.get("location") != null) {
                        String getValue = map.get("location").toString();
                        location.setText(getValue);
                    }
                    if (map.get("description") != null) {
                        String getValue = map.get("description").toString();
                        details.setText(getValue);
                    }
                    if (map.get("previousprice") != null) {
                        String getValue = map.get("previousprice").toString();
                        previousprice.setText(getValue);
                        previousprice.setPaintFlags(previousprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                    if (map.get("discount") != null) {
                        String getValue = map.get("discount").toString();
                        discount.setText(getValue);
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
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (value != null && value.exists()) {
                    Map<String, Object> map = (Map<String, Object>) value.getData();
                    if (map.get("image") != null) {
                        String offerone = (String) map.get("image");
                        Glide.with(getApplicationContext()).load(offerone).into(imageproduct);

                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        modelAdapter.startListening();
        modelAdapter5.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        modelAdapter.stopListening();
        modelAdapter5.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart, menu);
        MenuItem item = menu.findItem(R.id.cart2);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (userId == null) {
                    Intent intent = new Intent(getApplicationContext(), PleaseLogin.class);
                    startActivity(intent);
                    return false;
                } else {
                    Intent intent = new Intent(getApplicationContext(), Cart.class);
                    startActivity(intent);
                }

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

}