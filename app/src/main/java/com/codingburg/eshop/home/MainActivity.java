package com.codingburg.eshop.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.codingburg.eshop.R;
import com.codingburg.eshop.authentication.PleaseLogin;
import com.codingburg.eshop.cetegory.CetegoryAdapter;
import com.codingburg.eshop.cetegory.CetegoryModel;
import com.codingburg.eshop.offer.ProductViewModelAdapterOffer;
import com.codingburg.eshop.offer.ProductViewModelOffer;
import com.codingburg.eshop.opingscreen.Note;
import com.codingburg.eshop.opingscreen.Tutorial;
import com.codingburg.eshop.productdetails.ProductDetails;
import com.codingburg.eshop.profile.Profile;
import com.codingburg.eshop.cart.Cart;
import com.codingburg.eshop.model.ModelAdapter;
import com.codingburg.eshop.model.ModelAdapterList;
import com.codingburg.eshop.model.ModelData;
import com.codingburg.eshop.model.ModelDataList;
import com.codingburg.eshop.search.Seach;
import com.codingburg.eshop.showproducts.ShowProducts;
import com.codingburg.eshop.subcetegory.SubCetegory;
import com.codingburg.eshop.topCetegory.CetegoryAdapterTo;
import com.codingburg.eshop.topCetegory.CetegoryModelTop;
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
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
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
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ImageSlider imageSlider;
    private RecyclerView  recyclerView3, recyclerView5, recyclerviewphone, recyclerviewoffer, recyclerView6, recyclerView7,recyclerView8;
    private RecyclerView.LayoutManager RecyclerViewLayoutManager, RecyclerViewLayoutManager2;
    private ModelAdapter modelAdapter, modelAdapter2;
    private CetegoryAdapter modelAdapter5, modelAdapterfood,modelAdapterlan;
    private CetegoryAdapterTo modelAdapterTop;
    private ModelAdapterList modelAdapter3, modelAdapterPhone;
    LinearLayoutManager HorizontalLayout;
    private FirebaseAuth mAuth;
    private String userId;
    private FirebaseFirestore db;
    private Toolbar toolbar;
    private String show;
    private Query phoneQ, offer;
    private ProductViewModelAdapterOffer modelAdapteroffer;
    private InterstitialAd interstitialAd;
    private com.google.android.gms.ads.InterstitialAd mInterstitialAd;
    private RewardedAd rewardedAd;
    String[] sampleImages = new String[3];
    private CarouselView carouselView;
    private DocumentReference foodimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AudienceNetworkAds.initialize(this);
//        AdSettings.addTestDevice("HASHED ID");
        interstitialAd = new InterstitialAd(this, getString(R.string.fb_int_1));
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
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
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        new DrawerBuilder().withActivity(this).build();
        AudienceNetworkAds.initialize(this);
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
        mInterstitialAd = new com.google.android.gms.ads.InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.instatianlads1));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });
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
        final PrimaryDrawerItem item6 = new PrimaryDrawerItem().withIdentifier(2).withName("Donate");
        final PrimaryDrawerItem item7 = new PrimaryDrawerItem().withIdentifier(2).withName("How to buy?");
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
                        item5,
                        item6,
                        item7
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem == item1) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.fb)));
                            startActivity(browserIntent);
                        }
                        if (drawerItem == item2) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.youtube)));
                            startActivity(browserIntent);
                        }
                        if (drawerItem == item3) {
                            try {
                                mAuth.signOut();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (drawerItem == item4) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://shulovshopbd.blogspot.com/p/privacy-policy-shulov-shop-bd.html"));
                            startActivity(browserIntent);
                        }
                        if (drawerItem == item5) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://shulovshopbd.blogspot.com/p/terms-conditions-shulov-shop-bd.html"));
                            startActivity(browserIntent);
                        }
                        if (drawerItem == item6) {
                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                            } else {

                            }
                            Intent browserIntent = new Intent(getApplicationContext(), Note.class);
                            startActivity(browserIntent);
                        }
                        if (drawerItem == item7) {
                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                            } else {

                            }
                            Intent browserIntent = new Intent(getApplicationContext(), Tutorial.class);
                            startActivity(browserIntent);
                        }
                        return true;
                    }
                })
                .build();

        //food slider

        foodimage = FirebaseFirestore.getInstance().collection("foodslideimage").document("imageone");
        foodimage.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error !=null){
                    return;
                }
                if (value.exists()) {
                    Map<String, Object> map = (Map<String, Object>) value.getData();
                    if (map.get("imageone") != null) {
                        String imageone = map.get("imageone").toString();
                        sampleImages[0] = imageone;
                    }
                    if (map.get("imagetwo") != null) {
                        String imagetwo = map.get("imagetwo").toString();
                        sampleImages[1] = imagetwo;
                    }
                    if (map.get("imagethree") != null) {
                        String imagethree = map.get("imagethree").toString();
                        sampleImages[2] = imagethree;
                    }
                    carouselView = (CarouselView) findViewById(R.id.carouselView);
                    ImageListener imageListener = new ImageListener() {
                        @Override
                        public void setImageForPosition(int position, ImageView imageView) {
                            Picasso.get().load(sampleImages[position]).into(imageView);
                        }
                    };
                    carouselView.setImageListener(imageListener);
                    carouselView.setPageCount(sampleImages.length);
                    carouselView.setImageClickListener(new ImageClickListener() {
                        @Override
                        public void onClick(int position) {
                            Intent intent = new Intent(getApplicationContext(), SubCetegory.class);
                            intent.putExtra("id", "food");
                            startActivity(intent);
                        }
                    });
                }
            }
        });
        //food slider


        //image silde one and two
        imageSlider = findViewById(R.id.image_slider);
        final ArrayList<String> id = new ArrayList<>();
        final ArrayList<String> category = new ArrayList<>();
        final List<SlideModel> remoteImages = new ArrayList<>();
        FirebaseFirestore.getInstance().collection("promote").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (QueryDocumentSnapshot documentSnapshot : value) {
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
        //image silde one and two

        try {
            userId = mAuth.getCurrentUser().getUid();
        } catch (Exception e) {
            e.printStackTrace();
        }
        rewardedAd = new RewardedAd(this,
                getString(R.string.videoads1));

        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {

            }
            @Override
            public void onRewardedAdFailedToLoad(LoadAdError adError) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        };
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
                if (itemIndex == 0) {
                    if (userId == null) {
                        Intent intent = new Intent(getApplicationContext(), PleaseLogin.class);
                        startActivity(intent);
                        return;
                    } else {
                        if (rewardedAd.isLoaded()) {
                            Activity activityContext = MainActivity.this;
                            RewardedAdCallback adCallback = new RewardedAdCallback() {
                                @Override
                                public void onRewardedAdOpened() {

                                }
                                @Override
                                public void onRewardedAdClosed() {
                                    Intent intent = new Intent(getApplicationContext(), Profile.class);
                                    startActivity(intent);
                                }
                                @Override
                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                    Intent intent = new Intent(getApplicationContext(), Profile.class);
                                    startActivity(intent);
                                }
                                @Override
                                public void onRewardedAdFailedToShow(com.google.android.gms.ads.AdError adError) {
                                    Intent intent = new Intent(getApplicationContext(), Profile.class);
                                    startActivity(intent);
                                }
                            };
                            rewardedAd.show(activityContext, adCallback);
                        } else {

                        }

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
        //buttom navigation
        //offer
        //food


        FirebaseFirestore find = FirebaseFirestore.getInstance();
        CollectionReference collectionReferencefood = find.collection("category");
        Query queryfood = collectionReferencefood.orderBy("category").startAt("food").endAt("food" + "\uf8ff");
        recyclerView7 = (RecyclerView) findViewById(R.id.recyclerviewfood);
        recyclerView7.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        FirestoreRecyclerOptions<CetegoryModel> options7 =
                new FirestoreRecyclerOptions.Builder<CetegoryModel>()
                        .setQuery(queryfood, CetegoryModel.class)
                        .build();
        modelAdapterfood = new CetegoryAdapter(options7);
        recyclerView7.setAdapter(modelAdapterfood);
        //food
        FirebaseFirestore find2 = FirebaseFirestore.getInstance();
        CollectionReference collectionReferencelan = find2.collection("category");
        Query querylan = collectionReferencelan.whereEqualTo("category", "food-lunch");
        recyclerView8 = (RecyclerView) findViewById(R.id.recyclerviewl);
        recyclerView8.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        FirestoreRecyclerOptions<CetegoryModel> options8 =
                new FirestoreRecyclerOptions.Builder<CetegoryModel>()
                        .setQuery(querylan, CetegoryModel.class)
                        .build();
        modelAdapterlan = new CetegoryAdapter(options8);
        recyclerView8.setAdapter(modelAdapterlan);
        //recyclerView top cetegory
        db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference1 = db.collection("category");
        Query categoryName1 = collectionReference1.whereEqualTo("top", "yes");
        recyclerView6 = (RecyclerView) findViewById(R.id.recyclerviewh);
        HorizontalLayout
                = new LinearLayoutManager(
                MainActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView6.setLayoutManager(HorizontalLayout);
        FirestoreRecyclerOptions<CetegoryModelTop> options6 =
                new FirestoreRecyclerOptions.Builder<CetegoryModelTop>()
                        .setQuery(categoryName1, CetegoryModelTop.class)
                        .build();
        modelAdapterTop = new CetegoryAdapterTo(options6);
        recyclerView6.setAdapter(modelAdapterTop);
//recyclerView top cetegory

//recyclerView new cetegory
        db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("category");
        Query querycategoryName = collectionReference.whereLessThan("id", "50");
        recyclerView5 = (RecyclerView) findViewById(R.id.recyclerview4);
        recyclerView5.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        FirestoreRecyclerOptions<CetegoryModel> options5 =
                new FirestoreRecyclerOptions.Builder<CetegoryModel>()
                        .setQuery(querycategoryName, CetegoryModel.class)
                        .build();
        modelAdapter5 = new CetegoryAdapter(options5);
        recyclerView5.setAdapter(modelAdapter5);
//recyclerView new cetegory
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
        //offer
        // phone product show
        DocumentReference reference = FirebaseFirestore.getInstance().collection("show").document("showhome");
        reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }
                if (value != null && value.exists()) {
                    Map<String, Object> map = (Map<String, Object>) value.getData();
                    if (map.get("category") != null) {
                        show = map.get("category").toString();
                    }
                    recyclerviewphone = (RecyclerView) findViewById(R.id.recyclerviewphone);
                    CollectionReference phone = db.collection("product");
                    phoneQ = phone.whereEqualTo("category", show);
                    recyclerviewphone.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false));
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


    @Override
    protected void onStart() {
        super.onStart();
        modelAdapteroffer.startListening();
        modelAdapter5.startListening();
        modelAdapterTop.startListening();
        modelAdapterfood.startListening();
        modelAdapterlan.startListening();
//        shopAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        modelAdapteroffer.stopListening();
        modelAdapter5.stopListening();
        modelAdapterTop.stopListening();
        modelAdapterfood.stopListening();
        modelAdapterlan.stopListening();
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
        MenuItem item2 = menu.findItem(R.id.refresh);
        item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }
}
