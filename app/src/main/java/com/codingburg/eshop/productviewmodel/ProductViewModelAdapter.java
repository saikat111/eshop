package com.codingburg.eshop.productviewmodel;

import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codingburg.eshop.R;
import com.codingburg.eshop.model.ModelAdapterList;
import com.codingburg.eshop.model.ModelDataList;
import com.codingburg.eshop.productdetails.ProductDetails;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class ProductViewModelAdapter   extends FirestoreRecyclerAdapter<ProductViewModel, ProductViewModelAdapter.DataViewHolder> {
    public ProductViewModelAdapter(@NonNull FirestoreRecyclerOptions<ProductViewModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductViewModelAdapter.DataViewHolder holder, int position, @NonNull ProductViewModel model) {
        holder.productPrice.setText(model.getPrice());
        holder.productmodel.setText(model.getName());
        holder.productid.setText(model.getId());
        holder.productcategory.setText(model.getCategory());
        holder.time.setText(model.getTime());
        try{
            holder.previousprice.setText(model.previousprice);
            holder.previousprice.setPaintFlags(holder.previousprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Picasso.get().load(model.getImage()).resize(200,150).centerInside().into(holder.productImage);
    }

    @NonNull
    @Override
    public ProductViewModelAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item2, parent, false);
        return new ProductViewModelAdapter.DataViewHolder(view);
    }

    public class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView productImage;
        TextView productPrice, productid ,productmodel ,productcategory, time, previousprice;
        public DataViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            productImage = itemView.findViewById(R.id.imageView);
            productPrice = itemView.findViewById(R.id.price);
            productmodel = itemView.findViewById(R.id.model);
            productid = itemView.findViewById(R.id.id);
            productcategory = itemView.findViewById(R.id.category);
            time = itemView.findViewById(R.id.time);
            previousprice = itemView.findViewById(R.id.previceprice);
        }
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ProductDetails.class);
            intent.putExtra("id", productid.getText().toString());
            intent.putExtra("category", productcategory.getText().toString());
            view.getContext().startActivity(intent);
        }
    }
}
