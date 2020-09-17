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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;


import com.codingburg.eshop.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class NewComment extends AppCompatDialogFragment {
    private EditText postData;
    private FirebaseAuth firebaseAuth;
    private CollectionReference collectionReference;
    private String userId, pushKey;
    private ProgressDialog progressDialog;
    private DocumentReference currentUserDb;
    private String nameForSave ;
    private String postKey;
    private int comments;
    private String image ="https://firebasestorage.googleapis.com/v0/b/pregnancy-adviser.appspot.com/o/logo1.png?alt=media&token=63f4ae3e-9c38-485f-af2c-0cc09f64b3d6";

    public NewComment(String postKey, int comments) {
        this.postKey = postKey;
        this.comments = comments;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Context context;
        firebaseAuth = FirebaseAuth.getInstance();
        collectionReference = FirebaseFirestore.getInstance().collection("post").document(postKey).collection("comments");
        userId = firebaseAuth.getCurrentUser().getUid();
        pushKey = collectionReference.document().getId();


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.content, null);
        builder.setView(view)
                .setTitle("Comment")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("comment", new DialogInterface.OnClickListener() {
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
                                    data.put("image", image);
                                    data.put("name", nameForSave);
                                    collectionReference.document(pushKey).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            String s;
                                            comments +=1;
                                            s = String.valueOf(comments);
                                            DocumentReference databaseReference = FirebaseFirestore.getInstance().collection("post").document(postKey);
                                            Map map1 = new HashMap();
                                            map1.put("comments", s);
                                            databaseReference.update(map1);
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
