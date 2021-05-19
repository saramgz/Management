package com.example.management;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.management.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btn = findViewById(R.id.btn_go_to_login);
        if (!loadUser()) {
            startActivity(new Intent(MainActivity.this, AppActivity.class));
            finish();
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
            }
        });
    }
    public boolean loadUser(){
        SharedPreferences prefs = getSharedPreferences("shared_pref", MODE_PRIVATE);
        String st= prefs.getString("email", null);
        return (st == null);

    }
}