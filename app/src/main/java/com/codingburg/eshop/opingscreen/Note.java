package com.codingburg.eshop.opingscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.codingburg.eshop.R;
import com.codingburg.eshop.home.MainActivity;
import com.codingburg.eshop.showproducts.ShowProducts;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

public class Note extends AppCompatActivity {
    private String text;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    CarouselView  carouselView4;
    int[] sampleImages1 = {R.drawable.i1, R.drawable.i13, R.drawable.i14};
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        next =findViewById(R.id.next);
        carouselView4 = (CarouselView) findViewById(R.id.carouselView4);
        carouselView4.setImageListener(imageListener2);
        carouselView4.setPageCount(sampleImages1.length);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT, "done");
        editor.apply();

    }
    ImageListener imageListener2 = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages1[position]);
        }
    };
}