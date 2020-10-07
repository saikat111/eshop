package com.codingburg.eshop.cart;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.codingburg.eshop.R;
import com.codingburg.eshop.home.MainActivity;
import com.codingburg.eshop.productdetails.ProductDetails;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class CardAdapter  extends FirebaseRecyclerAdapter<CardData, CardAdapter.DataViewHolder> {
    private int count =0 ;
    private DatabaseReference userCardDb,userCardDb2, userCardDb4;
    private FirebaseAuth firebaseAuth;
    private  String userId;
    private  String value, totalp, Totalammout;
    private int quantityValue =1;
    private DocumentReference userCardDb3;



    public CardAdapter(@NonNull FirebaseRecyclerOptions<CardData> options) {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull final CardAdapter.DataViewHolder holder, final int position, @NonNull final CardData model) {
        FirebaseDatabase.getInstance().goOnline();
      firebaseAuth = FirebaseAuth.getInstance();
      userId = firebaseAuth.getCurrentUser().getUid();
        userCardDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("totalammountofcartitem");
        holder.productPrice.setText(model.getTotalprice());
        holder.productmodel.setText(model.getName());
        holder.productid.setText(model.getId());
        holder.productcategory.setText(model.getCategory());
        holder.quantity.setText(model.getQuantity());
        holder.key.setText(model.getKey());
        Picasso.get().load(model.getImage()).resize(150,100).into(holder.productImage);
       try{

           count = count + Integer.parseInt(model.getTotalprice()) ;
            Totalammout = String.valueOf(count);
           addTotalPriceToDb(Totalammout);
       }
       catch (Exception e){
           e.printStackTrace();
       }

    }

    private void addTotalPriceToDb(String Totalammout) {
        Map cardAmount = new HashMap();
        cardAmount.put("totalammout", Totalammout);
        userCardDb.updateChildren(cardAmount);
    }

    @NonNull
    @Override
    public CardAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);
        return new CardAdapter.DataViewHolder(view);
    }

    public class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView productImage, add, remove, delete;
        TextView productPrice, productid ,productmodel ,productcategory, quantity, key;
        public DataViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            productImage = itemView.findViewById(R.id.imageView);
            productPrice = itemView.findViewById(R.id.price);
            productmodel = itemView.findViewById(R.id.model);
            productid = itemView.findViewById(R.id.id);
            productcategory = itemView.findViewById(R.id.category);
            quantity = itemView.findViewById(R.id.quantity);
            key = itemView.findViewById(R.id.key);
            add = itemView.findViewById(R.id.add);
            delete = itemView.findViewById(R.id.removecart);
            remove = itemView.findViewById(R.id.remove);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userCardDb2 = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("cart").child(key.getText().toString());
                    userCardDb4 = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("totalammountofcartitem");
                    userCardDb2.removeValue();
                    userCardDb4.removeValue();
                    Intent intent = new Intent(view.getContext(), Cart.class);
                    view.getContext().startActivity(intent);
                }
            });

            add.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View view) {
                    userCardDb2 = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("cart").child(key.getText().toString());
                    userCardDb2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                                if(map.get("quantity") !=null){
                                    value =  map.get("quantity").toString();
                                }

                                userCardDb3 = FirebaseFirestore.getInstance().collection("product").document(productid.getText().toString());
                                userCardDb3.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot data, @Nullable FirebaseFirestoreException error) {
                                        if(error != null){
                                            Toast.makeText(view.getContext(), error.toString(), Toast.LENGTH_LONG).show();
                                            return;
                                        }
                                        if(data.exists()) {
                                            Map<String, Object> map = (Map<String, Object>) data.getData();
                                            if(map.get("price") !=null){
                                                totalp =  map.get("price").toString();

                                            }
                                            try{
                                                int intValue = Integer.parseInt(value);
                                                int result = intValue + quantityValue;
                                                int totalpInt = Integer.parseInt(totalp);
                                                int finalPrice = totalpInt * result;
                                                Map updatevalue = new HashMap();
                                                updatevalue.put("quantity", String.valueOf(result));
                                                updatevalue.put("totalprice", String.valueOf(finalPrice));
                                                userCardDb2.updateChildren(updatevalue);
                                                Intent intent = new Intent(view.getContext(), Cart.class);
                                                view.getContext().startActivity(intent);
                                            }
                                            catch (Exception e)  {
                                                e.printStackTrace();
                                            }

                                        }

                                    }
                                });

                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            });

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    userCardDb2 = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("cart").child(key.getText().toString());
                    userCardDb2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                                if(map.get("quantity") !=null){
                                    value =  map.get("quantity").toString();
                                }

                                userCardDb3 = FirebaseFirestore.getInstance().collection("product").document(productid.getText().toString());
                                userCardDb3.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot data, @Nullable FirebaseFirestoreException error) {
                                        if(error != null){
                                            Toast.makeText(view.getContext(), error.toString(), Toast.LENGTH_LONG).show();
                                            return;
                                        }    if(data.exists()) {
                                            Map<String, Object> map = (Map<String, Object>) data.getData();
                                            if(map.get("price") !=null){
                                                totalp =  map.get("price").toString();
                                            }

                                            try{
                                                int intValue = Integer.parseInt(value);
                                                int result = intValue - quantityValue;
                                                if(result <=0 ){
                                                    userCardDb2.removeValue();
                                                    Intent intent = new Intent(view.getContext(), Cart.class);
                                                    view.getContext().startActivity(intent);
                                                    return;
                                                }
                                                int totalpInt = Integer.parseInt(totalp);
                                                int finalPrice = totalpInt * result;
                                                if(finalPrice <=0){
                                                    return;
                                                }
                                                Map updatevalue = new HashMap();
                                                updatevalue.put("quantity", String.valueOf(result));
                                                updatevalue.put("totalprice", String.valueOf(finalPrice));
                                                userCardDb2.updateChildren(updatevalue);
                                                Intent intent = new Intent(view.getContext(), Cart.class);
                                                view.getContext().startActivity(intent);
                                            }
                                            catch (Exception e)  {
                                                e.printStackTrace();

                                            }
                                        }

                                    }
                                });

                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                }
            });
        }
        @Override
        public void onClick(View view) {
//            Intent intent = new Intent(view.getContext(), ProductDetails.class);
//            intent.putExtra("id", productid.getText().toString());
//            intent.putExtra("category", productcategory.getText().toString());
//            view.getContext().startActivity(intent);

        }
    }
}
