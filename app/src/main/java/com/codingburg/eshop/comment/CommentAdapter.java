package com.codingburg.eshop.comment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.codingburg.eshop.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class CommentAdapter extends FirestoreRecyclerAdapter<CommentData, CommentAdapter.DataViewHolder> {

    public CommentAdapter(@NonNull FirestoreRecyclerOptions<CommentData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final DataViewHolder holder, int position, @NonNull final CommentData model) {
        Picasso.get().load(model.getImage()).into(holder.imgView_proPic);
        holder.tv_status.setText(model.getPost());
        holder.tv_name.setText(model.getName());
        holder.date.setText(model.getDate());
        
    }
    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comments, parent, false);
        return new DataViewHolder(view);
    }
    public class DataViewHolder extends RecyclerView.ViewHolder  {
        ImageView imgView_proPic;
        TextView tv_name, tv_status, date;
        public DataViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            imgView_proPic = itemView.findViewById(R.id.imgView_proPic);
            tv_status = itemView.findViewById(R.id.tv_status);
            date = itemView.findViewById(R.id.date);
        }
    }
}
