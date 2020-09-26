package com.codingburg.eshop.home;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.codingburg.eshop.R;
import com.codingburg.eshop.authentication.PleaseLogin;
import com.codingburg.eshop.cetegory.CetegoryAdapter;
import com.codingburg.eshop.cetegory.CetegoryModel;
import com.codingburg.eshop.offer.ProductViewModelAdapterOffer;
import com.codingburg.eshop.offer.ProductViewModelOffer;
import com.codingburg.eshop.productdetails.ProductDetails;
import com.codingburg.eshop.productviewmodel.ProductViewModelAdapter;
import com.codingburg.eshop.profile.Profile;
import com.codingburg.eshop.cart.Cart;
import com.codingburg.eshop.model.ModelAdapter;
import com.codingburg.eshop.model.ModelAdapterList;
import com.codingburg.eshop.model.ModelData;
import com.codingburg.eshop.model.ModelDataList;
import com.codingburg.eshop.search.Seach;
import com.codingburg.eshop.showproducts.ShowProducts;
import com.codingburg.eshop.subcetegory.SubCetegory;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.facebook.ads.*;
import com.facebook.ads.AudienceNetworkAds;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
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
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.onesignal.OneSignal;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ImageSlider imageSlider, imageSlider2;
    CarouselView carouselView ,carouselView2, carouselView3,carouselView4;
    String[] sampleImages = new String[2];
    String[] sampleImages2 = new String[2];
    private  RecyclerView recyclerView, recyclerView2, recyclerView3, recyclerView5, recyclerviewphone, recyclerviewoffer;
    private RecyclerView.LayoutManager RecyclerViewLayoutManager, RecyclerViewLayoutManager2;
    private ModelAdapter modelAdapter, modelAdapter2;
    private CetegoryAdapter modelAdapter5;
    private ModelAdapterList modelAdapter3, modelAdapterPhone;
    LinearLayoutManager HorizontalLayout;
    private FirebaseAuth mAuth;
    private  String userId;
    private  FirebaseFirestore db;
    private DocumentReference offerTop, discount;
    private ImageView men, women, kids;
    int[] sampleImages1 = {R.drawable.image_6,R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4, R.drawable.image_5};
    int[] sampleImages3 = {R.drawable.c1,R.drawable.c2, R.drawable.c3, R.drawable.c4, R.drawable.c5, R.drawable.c6};
    private AdView mAdView, mAdView2, mAdView3, mAdView4, mAdView5, mAdView6, mAdView7, mAdView8;
    private  Toolbar toolbar;
    private Button bazzer;
    private   String show;
    private  Query phoneQ, offer;
    private ProductViewModelAdapterOffer modelAdapteroffer;
    private com.facebook.ads.AdView adView, adView2, adView3,adView4,adView5,adView6;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AudienceNetworkAds.initialize(this);
        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        FirebaseDatabase.getInstance().goOffline();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        men = findViewById(R.id.men);
        women = findViewById(R.id.women);
        kids = findViewById(R.id.kids);
        mAuth =FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        new DrawerBuilder().withActivity(this).build();
        bazzer = findViewById(R.id.bazzer);
        AudienceNetworkAds.initialize(this);
        adView = new com.facebook.ads.AdView(this, getString(R.string.fb_banner1), AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        adContainer.addView(adView);
        adView.loadAd();
        adView2 = new com.facebook.ads.AdView(this, getString(R.string.fb_banner1),  AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer2 = (LinearLayout) findViewById(R.id.banner_container2);
        adContainer2.addView(adView2);
        adView2.loadAd();
        adView3 = new com.facebook.ads.AdView(this, getString(R.string.fb_banner1),  AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer3 = (LinearLayout) findViewById(R.id.banner_container3);
        adContainer3.addView(adView3);
        adView3.loadAd();
        adView4 = new com.facebook.ads.AdView(this, getString(R.string.fb_banner1),  AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer4 = (LinearLayout) findViewById(R.id.banner_container4);
        adContainer4.addView(adView4);
        adView4.loadAd();
        adView5 = new com.facebook.ads.AdView(this, getString(R.string.fb_banner1),  AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer5 = (LinearLayout) findViewById(R.id.banner_container5);
        adContainer5.addView(adView5);
        adView5.loadAd();
        adView6 = new com.facebook.ads.AdView(this, getString(R.string.fb_banner1),  AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer6 = (LinearLayout) findViewById(R.id.banner_container6);
        adContainer6.addView(adView6);
        adView6.loadAd();






        AdLoader adLoader5 = new AdLoader.Builder(this, getString(R.string.native_ID_1))
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        TemplateView template = findViewById(R.id.my_template5);
                        template.setNativeAd(unifiedNativeAd);
                    }
                })
                .build();

        adLoader5.loadAd(new AdRequest.Builder().build());

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

        AdLoader adLoader4 = new AdLoader.Builder(this, getString(R.string.native_ID_1))
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
        mAdView3 = findViewById(R.id.adView3);
        AdRequest adRequest3 = new AdRequest.Builder().build();
        mAdView3.loadAd(adRequest);
        mAdView4 = findViewById(R.id.adView4);
        AdRequest adRequest4 = new AdRequest.Builder().build();
        mAdView4.loadAd(adRequest);
        mAdView7 = findViewById(R.id.adView7);
        AdRequest adRequest7 = new AdRequest.Builder().build();
        mAdView7.loadAd(adRequest);
        mAdView8 = findViewById(R.id.adView8);
        AdRequest adRequest8 = new AdRequest.Builder().build();
        mAdView8.loadAd(adRequest);




//ads

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName(getString(R.string.app_name)).withIcon(getResources().getDrawable(R.drawable.logo))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
        final PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Facebook");
        final PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("Youtube");
        final PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(2).withName("Logout");
        final PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(2).withName("Privacy Policy");
        final PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(2).withName("Terms & Conditions");

//create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        item1,
                        item2,
                        item3,
                        item4,
                        item5

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem == item1){
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.fb)));
                            startActivity(browserIntent);
                        }
                        if (drawerItem == item2){
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.youtube)));
                            startActivity(browserIntent);
                        }
                        if (drawerItem == item3){
                            try {
                                mAuth.signOut();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (drawerItem == item4){
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://shulovshopbd.blogspot.com/p/privacy-policy-shulov-shop-bd.html"));
                            startActivity(browserIntent);
                        }
                        if (drawerItem == item5){
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://shulovshopbd.blogspot.com/p/terms-conditions-shulov-shop-bd.html"));
                            startActivity(browserIntent);
                        }
                        return true;
                    }
                })
                .build();
        //image silde one and two
        imageSlider = findViewById(R.id.image_slider);
        final ArrayList<String> id = new ArrayList<>();
        final ArrayList<String> category = new ArrayList<>();
        final List<SlideModel> remoteImages = new ArrayList<>();
        FirebaseFirestore.getInstance().collection("promote").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(QueryDocumentSnapshot documentSnapshot : value){
                    remoteImages.add(new SlideModel(documentSnapshot.get("image").toString(), ScaleTypes.FIT));
                    id.add(documentSnapshot.get("id").toString());
                    category.add(documentSnapshot.get("category").toString());
                }
                imageSlider.setImageList(remoteImages, ScaleTypes.FIT);
                imageSlider.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onItemSelected(int i) {
                        Intent intent = new Intent(getApplicationContext(), ProductDetails.class);
                        intent.putExtra("id", id.get(i));
                        intent.putExtra("category", category.get(i));
                        startActivity(intent);
                    }
                });
            }
        });
        imageSlider2 = findViewById(R.id.image_slider2);
        final ArrayList<String> id2 = new ArrayList<>();
        final ArrayList<String> category2 = new ArrayList<>();
        final List<SlideModel> remoteImages2 = new ArrayList<>();
        FirebaseFirestore.getInstance().collection("discount").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(QueryDocumentSnapshot documentSnapshot : value){
                    remoteImages2.add(new SlideModel(documentSnapshot.get("image").toString(), ScaleTypes.FIT));
                    id2.add(documentSnapshot.get("id").toString());
                    category2.add(documentSnapshot.get("category").toString());

                }
                imageSlider2.setImageList(remoteImages2, ScaleTypes.FIT);
                imageSlider2.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onItemSelected(int i) {
                        Intent intent = new Intent(getApplicationContext(), ProductDetails.class);
                        intent.putExtra("id", id2.get(i));
                        intent.putExtra("category", category2.get(i));
                        startActivity(intent);
                    }
                });
            }
        });
        //image silde one and two

        try {
            userId = mAuth.getCurrentUser().getUid();
        } catch (Exception e) {
            e.printStackTrace();
        }
        bazzer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShowProducts.class);
                intent.putExtra("category", "bazzer");
                startActivity(intent);
            }
        });
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
                intent.putExtra("id", "child");
                startActivity(intent);
            }
        });
        carouselView3 = (CarouselView) findViewById(R.id.carouselView3);
        carouselView3.setImageListener(imageListener);
        carouselView3.setPageCount(sampleImages1.length);

        carouselView3.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getApplicationContext(), ShowProducts.class);
                intent.putExtra("category", "games");
               startActivity(intent);
            }
        });
        carouselView4 = (CarouselView) findViewById(R.id.carouselView4);
        carouselView4.setImageListener(imageListener2);
        carouselView4.setPageCount(sampleImages3.length);
        carouselView4.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getApplicationContext(), ShowProducts.class);
                intent.putExtra("category", "course");
                startActivity(intent);
            }
        });
        //buttom navigation
        SpaceNavigationView spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
//        spaceNavigationView.setSpaceBackgroundColor(ContextCompat.getColor(this, R.color.gnt_blue));
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

        //offer



//recyclerView new cetegory
        db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("category");
        Query categoryName = collectionReference.orderBy("id");
        recyclerView5 = (RecyclerView)findViewById(R.id.recyclerview4);
        recyclerView5.setLayoutManager(new GridLayoutManager(this, 3 ,GridLayoutManager.VERTICAL, false));
        FirestoreRecyclerOptions<CetegoryModel> options5 =
                new FirestoreRecyclerOptions.Builder<CetegoryModel>()
                        .setQuery(categoryName, CetegoryModel.class)
                        .build();
        modelAdapter5 = new CetegoryAdapter(options5);
        recyclerView5.setAdapter(modelAdapter5);
//recyclerView new cetegory


//        //recyclerView new shop
//        CollectionReference shop =db.collection("shop");
//        com.google.firebase.firestore.Query shopId = shop.orderBy("id");
//        recyclerviewshop = (RecyclerView)findViewById(R.id.recyclerviewshop);
//        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerviewshop.setLayoutManager(RecyclerViewLayoutManager);
//        HorizontalLayout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerviewshop.setLayoutManager(HorizontalLayout);
//        FirestoreRecyclerOptions<ShopModel> options6 =
//                new FirestoreRecyclerOptions.Builder<ShopModel>()
//                        .setQuery(shopId, ShopModel.class)
//                        .build();
//
//        shopAdapter = new ShopAdapter(options6);
//        recyclerviewshop.setAdapter(shopAdapter);
////recyclerView new shop

//offer

        recyclerviewoffer = (RecyclerView) findViewById(R.id.recyclervieowffer);
        recyclerviewoffer.setHasFixedSize(true);
        recyclerviewoffer.setLayoutManager(new LinearLayoutManager(this));
        CollectionReference offerc = db.collection("product");
        offer = offerc.whereEqualTo("category", "Service");
        FirestoreRecyclerOptions<ProductViewModelOffer> optionoffer =
                new FirestoreRecyclerOptions.Builder<ProductViewModelOffer>()
                        .setQuery(offer, ProductViewModelOffer.class)
                        .build();
        modelAdapteroffer = new ProductViewModelAdapterOffer(optionoffer);
        recyclerviewoffer.setAdapter(modelAdapteroffer);
        modelAdapteroffer.startListening();
        //offer



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

        // phone product show
        DocumentReference reference = FirebaseFirestore.getInstance().collection("show").document("showhome");
     reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
         @Override
         public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
             if(error != null){
                 return;
             }
             if(value != null && value.exists()){
                 Map<String, Object> map = (Map<String, Object>) value.getData();
                 if(map.get("category") != null){
                      show = map.get("category").toString();
                 }
                 recyclerviewphone = (RecyclerView) findViewById(R.id.recyclerviewphone);
                 CollectionReference phone = db.collection("product");
                 phoneQ = phone.whereEqualTo("category", show);
                 recyclerviewphone.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2 ,GridLayoutManager.VERTICAL, false));
                 FirestoreRecyclerOptions<ModelDataList> optionsphone =
                         new FirestoreRecyclerOptions.Builder<ModelDataList>()
                                 .setQuery(phoneQ, ModelDataList.class)
                                 .build();
                 modelAdapterPhone = new ModelAdapterList(optionsphone);
                 recyclerviewphone.setAdapter(modelAdapterPhone);
                 modelAdapterPhone.startListening();
             }
         }
     });

//        recyclerView3.setHasFixedSize(true);
//        recyclerView3.setLayoutManager(new LinearLayoutManager(this));

        // phone product show


    }
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages1[position]);
        }
    };
    ImageListener imageListener2 = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages3[position]);
        }
    };
    @Override
    protected void onStart() {
        super.onStart();
        modelAdapteroffer.startListening();
        modelAdapter.startListening();
        modelAdapter2.startListening();
        modelAdapter3.startListening();
        modelAdapter5.startListening();


//        shopAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        modelAdapteroffer.startListening();
        modelAdapter.stopListening();
        modelAdapter2.stopListening();
        modelAdapter3.stopListening();
        modelAdapter5.stopListening();
        modelAdapteroffer.stopListening();
//        shopAdapter.stopListening();
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
    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        if (adView2 != null) {
            adView2.destroy();
        }
        if (adView3 != null) {
            adView3.destroy();
        }
        if (adView4 != null) {
            adView4.destroy();
        }
        if (adView5 != null) {
            adView5.destroy();
        }
        if (adView6 != null) {
            adView6.destroy();
        }
        super.onDestroy();
    }
}
