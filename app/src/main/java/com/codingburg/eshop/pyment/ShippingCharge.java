package com.codingburg.eshop.pyment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.codingburg.eshop.R;
import com.facebook.ads.AdSize;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class ShippingCharge extends AppCompatActivity {
    private Button next;
    private AdView mAdView, mAdView3;
    private RewardedAd rewardedAd;
    private com.facebook.ads.AdView adView, adView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_charge);
        next = findViewById(R.id.next);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AudienceNetworkAds.initialize(this);
        adView = new com.facebook.ads.AdView(this, getString(R.string.fb_banner1), AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        adContainer.addView(adView);
        adView.loadAd();
        adView2 = new com.facebook.ads.AdView(this, getString(R.string.fb_banner1),  AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer2 = (LinearLayout) findViewById(R.id.banner_container2);
        adContainer2.addView(adView2);
        adView2.loadAd();


        rewardedAd = new RewardedAd(this,
                getString(R.string.videoads1));

        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {

            }
            @Override
            public void onRewardedAdFailedToLoad(LoadAdError adError) {
                Intent intent = new Intent(getApplicationContext(), Pyment.class);
                startActivity(intent);
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rewardedAd.isLoaded()) {
                    Activity activityContext = ShippingCharge.this;
                    RewardedAdCallback adCallback = new RewardedAdCallback() {
                        @Override
                        public void onRewardedAdOpened() {


                        }

                        @Override
                        public void onRewardedAdClosed() {

                            Intent intent = new Intent(getApplicationContext(), Pyment.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            Intent intent = new Intent(getApplicationContext(), Pyment.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onRewardedAdFailedToShow(AdError adError) {
                            Intent intent = new Intent(getApplicationContext(), Pyment.class);
                            startActivity(intent);
                        }
                    };
                    rewardedAd.show(activityContext, adCallback);
                } else {

                }

            }
        });
        AdLoader adLoader5 = new AdLoader.Builder(this, getString(R.string.native_ID_2))
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

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView3 = findViewById(R.id.adView3);
        AdRequest adRequest3 = new AdRequest.Builder().build();
        mAdView3.loadAd(adRequest);
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