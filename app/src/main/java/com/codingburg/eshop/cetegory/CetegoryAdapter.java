package com.codingburg.eshop.cetegory;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codingburg.eshop.R;
import com.codingburg.eshop.showproducts.ShowProducts;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class CetegoryAdapter extends FirestoreRecyclerAdapter<CetegoryModel, CetegoryAdapter.DataViewHolder> {

    public CetegoryAdapter(@NonNull FirestoreRecyclerOptions<CetegoryModel> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull CetegoryAdapter.DataViewHolder holder, int position, @NonNull CetegoryModel model) {
        holder.productmodel.setText(model.getName());
        holder.productcategory.setText(model.getCategory());
        Picasso.get().load(model.getImage())
                .into(holder.productImage);

    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cetagory, parent, false);
        return new CetegoryAdapter.DataViewHolder(view);
    }

    public class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView productImage;
        TextView  productid ,productmodel ,productcategory;
        public DataViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            productmodel = itemView.findViewById(R.id.model);
            productid = itemView.findViewById(R.id.id);
            productcategory = itemView.findViewById(R.id.category);
            productImage = itemView.findViewById(R.id.imageView);
        }
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ShowProducts.class);
            intent.putExtra("category", productcategory.getText().toString());
            view.getContext().startActivity(intent);
        }
    }
}
