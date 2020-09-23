package com.codingburg.eshop.authentication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


import com.codingburg.eshop.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.FirebaseDatabase;

public class PhoneNumber extends AppCompatActivity {
    private Spinner spinner;
    private EditText editText;
    private String id ,category;
    private InterstitialAd mInterstitialAd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
        try{
            id = getIntent().getExtras().getString("id");
            category = getIntent().getExtras().getString("category");
        }
        catch (Exception e){

        }
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.instatianlads1));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

        FirebaseDatabase.getInstance().goOffline();
        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));
        editText = findViewById(R.id.editTextPhone);
        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
                String number = editText.getText().toString().trim();
                if (number.isEmpty()) {
                    editText.setError("Valid number is required");
                    editText.requestFocus();
                    return;
                }
                String phoneNumber = "+" + code + number;
                if(category !=null ){
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {

                    }
                    Intent intent = new Intent(PhoneNumber.this, VerifyPhoneActivity.class);
                    intent.putExtra("phonenumber", phoneNumber);
                    intent.putExtra("category", category);
                    intent.putExtra("id", id);
                    startActivity(intent);

                }
                else if(category == null){
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {

                    }
                    Intent intent = new Intent(PhoneNumber.this, VerifyPhoneActivity.class);
                    intent.putExtra("phonenumber", phoneNumber);
                    startActivity(intent);

                }




            }
        });
    }
}
