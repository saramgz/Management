package com.example.management.login;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.management.AppActivity;
import com.example.management.MainActivity;
import com.example.management.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.rpc.context.AttributeContext;

import org.jetbrains.annotations.NotNull;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button btnGoLogIn, btnGoToSingUp, btnBackToSingIn, btnLogIn, btnSingUp;
    RelativeLayout relative_welcome, relative_singIn, relative_singUp, relative_progress;
    static FirebaseFirestore db;
    static DatabaseReference root;
    TextInputLayout emailSU, passSU, passReUS,emailLogin,passLogin;
    boolean status = true;
    DatabaseReference mDatabase;
    static FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //findViewe(savedInstanceState);
        //        setContentView(
        btnGoLogIn = findViewById(R.id.btn_go_to_login);
        relative_welcome = findViewById(R.id.relative_welcome);
        relative_singIn = findViewById(R.id.relative_singin);
        relative_singUp = findViewById(R.id.relative_singUp);
        btnGoToSingUp = findViewById(R.id.btnGoToSingUp);
        btnBackToSingIn = findViewById(R.id.btnBackToSingIn);
        btnLogIn = findViewById(R.id.btn_login);
        btnSingUp = findViewById(R.id.btn_shingUp);
        emailSU = findViewById(R.id.outlinedTextField_singUp);
        passSU = findViewById(R.id.input_password);
        passReUS = findViewById(R.id.input_password_re);
        relative_progress = findViewById(R.id.relative_progress);
        emailLogin  = findViewById(R.id.outlinedTextField);
        passLogin = findViewById(R.id.passLogin);


        //code
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();



        //setClickEvent
        btnGoLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relative_welcome.setVisibility(View.GONE);
                relative_singUp.setVisibility(View.VISIBLE);
            }
        });

        btnGoToSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relative_singIn.setVisibility(View.GONE);
                relative_singUp.setVisibility(View.VISIBLE);
            }
        });

        btnBackToSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relative_singUp.setVisibility(View.GONE);
                relative_singIn.setVisibility(View.VISIBLE);
            }
        });

        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!emailSU.getEditText().getText().toString().equals("") && !passSU.getEditText().getText().toString().equals("") && passSU.getEditText().getText().toString().equals(passReUS.getEditText().getText().toString()))
                {
                    relative_progress.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(emailSU.getEditText().getText().toString(), passSU.getEditText().getText().toString())
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        relative_progress.setVisibility(View.GONE);
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(LoginActivity.this, "ثبت نام شما با موفیقت انجام شد", Toast.LENGTH_LONG).show();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        relative_singUp.setVisibility(View.GONE);
                                        relative_singIn.setVisibility(View.VISIBLE);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                        relative_progress.setVisibility(View.GONE);
                                        Toast.makeText(LoginActivity.this, "این ایمیل قبلا ثبت شده است.",
                                                Toast.LENGTH_LONG).show();
                                        updateUI();
                                    }
                                }
                            });
                }else {
                    relative_progress.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "مثادیر خاسته شده را به درستی وارد نمایید.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signInWithEmailAndPassword(emailLogin.getEditText().getText().toString(), passLogin.getEditText().getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("TAG", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startActivity(new Intent(LoginActivity.this, AppActivity.class));
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("TAG", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
    public void updateUI(){

    }
}








//        Fragment welcomeFragment = new LoginFragment();
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.add(R.id.frame_login,welcomeFragment).commit();
//
//        Button btn;
//        btn = findViewById(R.id.btn_go_to_login);