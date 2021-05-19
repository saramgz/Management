package com.example.management;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

public class AppActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DataAdapter adapter;
    static DatabaseReference mDatabase;
    RelativeLayout btnAdd;
    static TextView allMoney,inMoney,outMoney;
    int all=0,in=0,out=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        recyclerView = findViewById(R.id.recycler1);
        btnAdd = findViewById(R.id.btnAddNew);
        allMoney = findViewById(R.id.txtAllMoney);
        inMoney = findViewById(R.id.txt_in_money);
        outMoney = findViewById(R.id.txt_out_money);

        setMoney(12,12);
        String str = loadUser();
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("users")
                .orderByChild("email")
                .equalTo(str)
                .limitToLast(50);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        query.addChildEventListener(childEventListener);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Info> options =
                new FirebaseRecyclerOptions.Builder<Info>()
                        .setQuery(query, Info.class)
                        .build();

        adapter = new DataAdapter(options);
        recyclerView.setAdapter(adapter);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AppActivity.this,InOutActivity.class));
            }
        });


    }

    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }


    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }



    public void setMoney(int _in,int _out){
//
        allMoney.setText(String.valueOf(0));
        inMoney.setText(String.valueOf(0));
        outMoney.setText(String.valueOf(0));
        all = 0;
        out = Integer.parseInt(outMoney.getText().toString());
        in = Integer.parseInt(inMoney.getText().toString());




        String user = loadUser();

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("users")
                .orderByChild("email")
                .equalTo(user)
                .limitToLast(50);

        ChildEventListener childEventListenerr = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("TAG", "onChildAdded:" + dataSnapshot.getKey());
                mDatabase.child("users").child(dataSnapshot.getKey()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            Log.d("firebase",task.getResult().toString() );
                            if (String.valueOf(task.getResult().child("status").getValue()).equals("out")) {
                                out = out+Integer.parseInt(task.getResult().child("money").getValue().toString());
                            }
                            else if (String.valueOf(task.getResult().child("status").getValue()).equals("in")){
                                in = in+Integer.parseInt(task.getResult().child("money").getValue().toString());
                            }

                        }
                        outMoney.setText(String.valueOf(out));
                        inMoney.setText(String.valueOf(in));
                        allMoney.setText(String.valueOf(in-out));

                    }
                });

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) { }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) { }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "postComments:onCancelled", databaseError.toException());
                Toast.makeText(AppActivity.this, "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        query.addChildEventListener(childEventListenerr);


    }
    public void setDefault(){
        allMoney.setText("0");
        inMoney.setText("0");
        outMoney.setText("0");
    }
    public String loadUser(){
        SharedPreferences prefs = getSharedPreferences("shared_pref", MODE_PRIVATE);
        return prefs.getString("email", null);

    }
}