package com.example.management;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.management.login.LoginActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

import static com.example.management.InOutActivity.random;

public class EditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private DatabaseReference mDatabase;
    private TextInputLayout t1,t2,t3,t5;
    TextView t4;
    private RecyclerView recyclerView;
    DatabaseReference mbase;
    Button input,output;
    DataAdapter adapter;
    String uid,money,subject,date,category,ex;
    ImageView delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        t1 = findViewById(R.id.monetETedit);
        t2 = findViewById(R.id.subjectETedit);
        t3 = findViewById(R.id.categoryETedit);
        t4 = findViewById(R.id.dateETedit);
        t5 = findViewById(R.id.exETedit);
        input = findViewById(R.id.btnInputedit);
        output = findViewById(R.id.btnOutedit);
        delete = findViewById(R.id.imgRemove);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        money = getIntent().getStringExtra("money");
        uid = getIntent().getStringExtra("uid");
        subject = getIntent().getStringExtra("subject");
        category = getIntent().getStringExtra("category");
        date = getIntent().getStringExtra("date");
        ex = getIntent().getStringExtra("ex");

        t1.getEditText().setText(money);
        t2.getEditText().setText(subject);
        t3.getEditText().setText(category);
        t4.setText(date);
        t5.getEditText().setText(ex);

        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDate();
            }
        });

        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Info data = new Info(t2.getEditText().getText().toString(),t3.getEditText().getText().toString(),t4.getText().toString(),
                        t5.getEditText().getText().toString(),uid,loadUser(),"in",Integer.parseInt(t1.getEditText().getText().toString()));

                mDatabase.child("users").child(uid)
                        .setValue(data, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull @NotNull DatabaseReference ref) {
                               if (error == null){
                                   Toast.makeText(EditActivity.this, "باموفقیت انجام شد.", Toast.LENGTH_SHORT).show();
                                   startActivity(new Intent(EditActivity.this,AppActivity.class));
                               }else {
                                   Toast.makeText(EditActivity.this, "خطا : "+error.toString(), Toast.LENGTH_SHORT).show();
                               }


                            }

                        });
            }
        });
        output.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Info data = new Info(t2.getEditText().getText().toString(),t3.getEditText().getText().toString(),t4.getText().toString(),
                        t5.getEditText().getText().toString(),uid,loadUser(),"out",Integer.parseInt(t1.getEditText().getText().toString()));

                mDatabase.child("users").child(uid)
                        .setValue(data, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull @NotNull DatabaseReference ref) {
                               if (error == null){
                                   Toast.makeText(EditActivity.this, "باموفقیت انجام شد.", Toast.LENGTH_SHORT).show();
                                   startActivity(new Intent(EditActivity.this,AppActivity.class));
                               }else {
                                   Toast.makeText(EditActivity.this, "خطا : "+error.toString(), Toast.LENGTH_SHORT).show();
                               }


                            }

                        });
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Info data = new Info("","","","","","","",0);

                mDatabase.child("users").child(uid)
                        .setValue(data, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull @NotNull DatabaseReference ref) {
                               if (error == null){
                                   Toast.makeText(EditActivity.this, "باموفقیت انجام شد.", Toast.LENGTH_SHORT).show();
                                   startActivity(new Intent(EditActivity.this,AppActivity.class));
                               }else {
                                   Toast.makeText(EditActivity.this, "خطا : "+error.toString(), Toast.LENGTH_SHORT).show();
                               }


                            }

                        });
            }
        });

    }
    public String loadUser(){
        SharedPreferences prefs = getSharedPreferences("shared_pref", MODE_PRIVATE);
        String email = prefs.getString("email", null);
        if (email == null ) {
            startActivity(new Intent(EditActivity.this, LoginActivity.class));
            return "ERR";
        }
        else return email;

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