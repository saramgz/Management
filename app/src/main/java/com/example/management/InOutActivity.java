package com.example.management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class InOutActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextInputLayout t1,t2,t3,t4,t5;
    private RecyclerView recyclerView;
    DatabaseReference mbase;
    Button input;
    DataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_out);

        t1 = findViewById(R.id.monetET);
        t2 = findViewById(R.id.subjectET);
        t3 = findViewById(R.id.categoryET);
        t4 = findViewById(R.id.dateET);
        t5 = findViewById(R.id.exET);
        input = findViewById(R.id.btnInput);

//        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


//        Map<String, Object> user = new HashMap<>();
//        user.put("first", "Ada");
//        user.put("last", "Lovelace");
//        user.put("born", 1815);

        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Info data = new Info(t1.getEditText().getText().toString(),t2.getEditText().getText().toString(),t3.getEditText().getText().toString(),
                        t4.getEditText().getText().toString(),t5.getEditText().getText().toString());

                mDatabase.child("users").child("cdcdsc").setValue(data);
            }
        });




//        mbase = FirebaseDatabase.getInstance().getReference();

//        Query query = FirebaseDatabase.getInstance()
//                .getReference()
//                .child("users")
//                .orderByChild("money")
//                .equalTo("123")
//                .limitToLast(50);
//
//        ChildEventListener childEventListener = new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
//                // ...
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
//                // ...
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                // ...
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
//                // ...
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // ...
//            }
//        };
//        query.addChildEventListener(childEventListener);


        //////////
//        recyclerView = findViewById(R.id.recycler1);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        FirebaseRecyclerOptions<Info> options =
//                new FirebaseRecyclerOptions.Builder<Info>()
//                        .setQuery(query, Info.class)
//                        .build();
////        Toast.makeText(this, options.getOwner().toString(), Toast.LENGTH_LONG).show();
//
//        adapter = new DataAdapter(options);
//        recyclerView.setAdapter(adapter);


    }




}