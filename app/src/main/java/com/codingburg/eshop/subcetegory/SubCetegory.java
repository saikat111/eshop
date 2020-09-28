package com.codingburg.eshop.subcetegory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.codingburg.eshop.R;
import com.codingburg.eshop.authentication.PleaseLogin;
import com.codingburg.eshop.cart.Cart;
import com.codingburg.eshop.cetegory.CetegoryAdapter;
import com.codingburg.eshop.cetegory.CetegoryModel;
import com.codingburg.eshop.home.MainActivity;
import com.codingburg.eshop.productdetails.ProductDetails;
import com.codingburg.eshop.profile.Profile;
import com.codingburg.eshop.search.Seach;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.facebook.ads.AdSize;
import com.facebook.ads.AudienceNetworkAds;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import java.util.ArrayList;
import java.util.List;

public class SubCetegory extends AppCompatActivity {
    private RecyclerView recyclerView3;
    private CetegoryAdapter modelAdapter5;
    private String id;
    private AdView mAdView;
    private  String userId;
    private FirebaseAuth mAuth;
    private ImageSlider  imageSlider2;
    private AdView  mAdView6, mAdView7, mAdView8;
    private com.facebook.ads.AdView adView, adView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_category);
        FirebaseDatabase.getInstance().goOnline();
        id = getIntent().getExtras().getString("id");

        AudienceNetworkAds.initialize(this);
        adView = new com.facebook.ads.AdView(this, getString(R.string.fb_banner1), AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        adContainer.addView(adView);
        adView.loadAd();
        adView2 = new com.facebook.ads.AdView(this, getString(R.string.fb_banner1),  AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer2 = (LinearLayout) findViewById(R.id.banner_container2);
        adContainer2.addView(adView2);
        adView2.loadAd();



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

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView7 = findViewById(R.id.adView7);
        AdRequest adRequest7 = new AdRequest.Builder().build();
        mAdView7.loadAd(adRequest);
        mAdView8 = findViewById(R.id.adView8);
        AdRequest adRequest8 = new AdRequest.Builder().build();
        mAdView8.loadAd(adRequest);

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
                if(itemIndex == 0) {
                    if (userId == null) {
                        Intent intent = new Intent(getApplicationContext(), PleaseLogin.class);
                        startActivity(intent);
                        return;
                    } else {
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
        FirebaseFirestore find = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = find.collection("category");
        Query query = collectionReference.orderBy("category").startAt(id).endAt(id + "\uf8ff");
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
        MenuItem item2 = menu.findItem(R.id.refresh);
        item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                finish();
                startActivity(getIntent());
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        if (adView2 != null) {
            adView2.destroy();
        }
        super.onDestroy();
    }
}