package com.codingburg.eshop.model;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codingburg.eshop.R;
import com.codingburg.eshop.productdetails.ProductDetails;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class ModelAdapterList  extends FirestoreRecyclerAdapter<ModelDataList, ModelAdapterList.DataViewHolder> {
    public ModelAdapterList(@NonNull FirestoreRecyclerOptions<ModelDataList> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull DataViewHolder holder, int position, @NonNull ModelDataList model) {
        holder.productPrice.setText(model.getPrice());
        holder.productmodel.setText(model.getName());
        holder.productid.setText(model.getId());
        holder.productcategory.setText(model.getCategory());
        Picasso.get().load(model.getImage()).into(holder.productImage);
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ModelAdapterList.DataViewHolder(view);
    }

    public class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView productImage;
        TextView productPrice, productid ,productmodel ,productcategory;
        public DataViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            productImage = itemView.findViewById(R.id.imageView);
            productPrice = itemView.findViewById(R.id.price);
            productmodel = itemView.findViewById(R.id.model);
            productid = itemView.findViewById(R.id.id);
            productcategory = itemView.findViewById(R.id.category);
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
