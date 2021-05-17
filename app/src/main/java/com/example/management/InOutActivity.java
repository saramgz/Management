package com.example.management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
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

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

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


        mDatabase = FirebaseDatabase.getInstance().getReference();


        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Time time = null;
                Info data = new Info(t1.getEditText().getText().toString(),t2.getEditText().getText().toString(),t3.getEditText().getText().toString(),
                        t4.getEditText().getText().toString(),t5.getEditText().getText().toString());

                mDatabase.child("users").child(random())
                        .setValue(data);
            }
        });



    }

    public String loadUser(){
        SharedPreferences prefs = getSharedPreferences("shared_pref", MODE_PRIVATE);
        return prefs.getString("email", null);

    }



    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
//            tempChar = generator(20,45);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

}