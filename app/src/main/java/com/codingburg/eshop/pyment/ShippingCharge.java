package com.codingburg.eshop.pyment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.codingburg.eshop.R;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class ShippingCharge extends AppCompatActivity {
    private Button next;
    private AdView mAdView, mAdView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_charge);
        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pyment.class);
                startActivity(intent);
            }
        });
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
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
}