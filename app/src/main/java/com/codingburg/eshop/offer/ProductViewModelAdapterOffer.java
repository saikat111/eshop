package com.codingburg.eshop.offer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codingburg.eshop.R;
import com.codingburg.eshop.productdetails.ProductDetails;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.rewarded.RewardedAd;

import com.squareup.picasso.Picasso;

public class ProductViewModelAdapterOffer extends FirestoreRecyclerAdapter<ProductViewModelOffer, ProductViewModelAdapterOffer.DataViewHolder> {
    private InterstitialAd mInterstitialAd;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ProductViewModelAdapterOffer(@NonNull FirestoreRecyclerOptions<ProductViewModelOffer> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductViewModelAdapterOffer.DataViewHolder holder, int position, @NonNull ProductViewModelOffer model) {

        holder.productPrice.setText(model.getPrice());
        holder.productmodel.setText(model.getName());
        holder.productid.setText(model.getId());
        holder.productcategory.setText(model.getCategory());
        try{
            holder.previousprice.setText(model.previousprice);
            holder.previousprice.setPaintFlags(holder.previousprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Picasso.get().load(model.getImage())
                .fit()
                .centerInside()
                .into(holder.productImage);
    }

    @NonNull
    @Override
    public ProductViewModelAdapterOffer.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item, parent, false);
        return new ProductViewModelAdapterOffer.DataViewHolder(view);
    }

    public class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView productImage;
        TextView productPrice, productid ,productmodel ,productcategory, previousprice;
        public DataViewHolder(@NonNull final View itemView) {
            super(itemView);
            mInterstitialAd = new com.google.android.gms.ads.InterstitialAd(itemView.getContext());
            mInterstitialAd.setAdUnitId("ca-app-pub-3884253968161128/1379237141");
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            mInterstitialAd.setAdListener(new com.google.android.gms.ads.AdListener() {
                @Override
                public void onAdClosed() {
                    // Load the next interstitial.
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }

            });
            itemView.setOnClickListener(this);
            productImage = itemView.findViewById(R.id.imageView);
            productPrice = itemView.findViewById(R.id.price);
            productmodel = itemView.findViewById(R.id.model);
            productid = itemView.findViewById(R.id.id);
            productcategory = itemView.findViewById(R.id.category);
            previousprice = itemView.findViewById(R.id.previceprice);
        }
        @Override
        public void onClick(final View view) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {

            }
                        Intent intent = new Intent(view.getContext(), OfferDetail.class);
                        intent.putExtra("id", productid.getText().toString());
                        intent.putExtra("category", productcategory.getText().toString());
                        view.getContext().startActivity(intent);

        }

    }


}
