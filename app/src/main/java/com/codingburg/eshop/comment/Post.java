package com.codingburg.eshop.comment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;


import com.codingburg.eshop.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ServerTimestamp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Post extends AppCompatDialogFragment {
    private EditText postData;
    private  FirebaseAuth firebaseAuth;
    private CollectionReference collectionReference;
    private String userId, pushKey, commrntkey;
    private ProgressDialog progressDialog;
    private DocumentReference currentUserDb;
    private String nameForSave ;

    public Post(String pushKey) {
        this.pushKey = pushKey;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Context context;
        firebaseAuth = FirebaseAuth.getInstance();
        collectionReference = FirebaseFirestore.getInstance().collection("product");
        userId = firebaseAuth.getCurrentUser().getUid();
        commrntkey = FirebaseFirestore.getInstance().collection("product").document(pushKey).collection("comments").document().getId();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String formattedDate = df.format(c.getTime());



        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.content, null);
                builder.setView(view)
                .setTitle("post")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Write your review", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progressDialog.show();
                        final String userPost = postData.getText().toString();

                        currentUserDb = FirebaseFirestore.getInstance().collection("Users").document(userId).collection("info").document(userId);
                        currentUserDb.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                if(error !=null){
                                    return;
                                }
                                if (value.exists()) {
                                    Map<String, Object> map = (Map<String, Object>) value.getData();
                                    if (map.get("name") != null) {
                                        nameForSave = map.get("name").toString();
                                    }
                                    Map data = new HashMap();
                                    data.put("userid", userId);
                                    data.put("key", pushKey);
                                    data.put("post", userPost);
                                    data.put("date", formattedDate);
                                    data.put("name", nameForSave);
                                    collectionReference.document(pushKey).collection("comments").document(commrntkey).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            progressDialog.dismiss();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                        }
                                    });

                                }
                            }
                        });

                    }
                });
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("Please wait...");
        postData = view.findViewById(R.id.data);
        return  builder.create();
    }

}
