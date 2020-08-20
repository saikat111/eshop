package com.codingburg.eshop.discount;

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
import com.codingburg.eshop.productviewmodel.ProductViewModel;
import com.codingburg.eshop.productviewmodel.ProductViewModelAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class DiscountAdapter extends FirestoreRecyclerAdapter<DiscountModel, DiscountAdapter.DataViewHolder> {

    public DiscountAdapter(@NonNull FirestoreRecyclerOptions<DiscountModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull DiscountAdapter.DataViewHolder holder, int position, @NonNull DiscountModel model) {
        Picasso.get().load(model.getImage()).into(holder.productImage);
        holder.productid.setText(model.getId());
        holder.productcategory.setText(model.getCategory());

    }

    @NonNull
    @Override
    public DiscountAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_offer, parent, false);
        return new DiscountAdapter.DataViewHolder(view);
    }

    public class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView productImage;
        TextView productPrice, productid ,productmodel ,productcategory, time, location;
        public DataViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            productImage = itemView.findViewById(R.id.imageView);
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
