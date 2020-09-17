package com.codingburg.eshop.profile;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codingburg.eshop.R;
import com.codingburg.eshop.model.ModelAdapter;
import com.codingburg.eshop.model.ModelAdapterList;
import com.codingburg.eshop.model.ModelData;
import com.codingburg.eshop.model.ModelDataList;
import com.codingburg.eshop.productdetails.ProductDetails;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class ProfileAdapter extends FirebaseRecyclerAdapter<ProfileModel, ProfileAdapter.DataViewHolder> {

    public ProfileAdapter(@NonNull FirebaseRecyclerOptions<ProfileModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProfileAdapter.DataViewHolder holder, int position, @NonNull ProfileModel model) {
        holder.productid.setText(model.getOrdernumber());
        holder.productPrice.setText(model.getTotaltk());
        holder.status.setText(model.getStatus());
        holder.date.setText(model.getDate());
    }

    @NonNull
    @Override
    public ProfileAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new ProfileAdapter.DataViewHolder(view);
    }

    public class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView productPrice, productid, status, date;
        public DataViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            productPrice = itemView.findViewById(R.id.price);
            productid = itemView.findViewById(R.id.id);
            status = itemView.findViewById(R.id.status);
            date = itemView.findViewById(R.id.date);
        }
        @Override
        public void onClick(View view) {

        }
    }
}
