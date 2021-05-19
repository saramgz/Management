package com.example.management;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
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

import org.jetbrains.annotations.NotNull;

import java.sql.Time;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class InOutActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private DatabaseReference mDatabase;
    private TextInputLayout t1,t2,t3,t5;
    TextView t4;
    private RecyclerView recyclerView;
    DatabaseReference mbase;
    Button input,output;
    DataAdapter adapter;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_out);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        t1 = findViewById(R.id.monetET);
        t2 = findViewById(R.id.subjectET);
        t3 = findViewById(R.id.categoryET);
        t4 = findViewById(R.id.dateET);
        t5 = findViewById(R.id.exET);
        input = findViewById(R.id.btnOut);
        output = findViewById(R.id.btnInput);


        mDatabase = FirebaseDatabase.getInstance().getReference();


        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDate();
            }
        });
        output.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uid = random();
                Info data = new Info(t2.getEditText().getText().toString(),t3.getEditText().getText().toString(),t4.getText().toString(),
                        t5.getEditText().getText().toString(),uid,loadUser(),"in",Integer.parseInt(t1.getEditText().getText().toString()));

                mDatabase.child("users").child(uid)
                        .setValue(data, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull @NotNull DatabaseReference ref) {
                                if (error == null){
                                    Toast.makeText(InOutActivity.this, "با موفقیت ثبت شد.", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(InOutActivity.this,AppActivity.class));
                                }else {
                                    Toast.makeText(InOutActivity.this, "خطا در ثبت!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });


        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uid = random();
                Info data = new Info(t2.getEditText().getText().toString(),t3.getEditText().getText().toString(),t4.getText().toString(),
                        t5.getEditText().getText().toString(),uid,loadUser(),"out",Integer.parseInt(t1.getEditText().getText().toString()));

                mDatabase.child("users").child(uid)
                        .setValue(data, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull @NotNull DatabaseReference ref) {
                                if (error == null){
                                    Toast.makeText(InOutActivity.this, "با موفقیت ثبت شد.", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(InOutActivity.this,AppActivity.class));
                                }else {
                                    Toast.makeText(InOutActivity.this, "خطا در ثبت!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });



    }

    public String loadUser(){
        SharedPreferences prefs = getSharedPreferences("shared_pref", MODE_PRIVATE);
        return prefs.getString("email", null);

    }



    public static String random() {
        int result;
        Random r = new Random();
        char[] chh = new char[16];
        for (int i = 0 ; i<16 ; i++){
            result = r.nextInt(25) + 65;
            chh[i] = (char) result;
        }
        return String.valueOf(chh);
    }
    public void getDate(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        String date = i + "/" + i1 + "/" + i2;
        t4.setText(date);
    }
}