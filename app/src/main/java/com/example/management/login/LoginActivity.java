package com.example.management.login;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    static FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        relative_singIn = findViewById(R.id.relative_singin);
        btnGoToSingUp = findViewById(R.id.btnGoToSingUp);
        btnLogIn = findViewById(R.id.btn_login);

        emailLogin  = findViewById(R.id.outlinedTextField);
        passLogin = findViewById(R.id.passLogin);


        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();





        btnGoToSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
                finish();
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
                                    saveUser(emailLogin.getEditText().getText().toString());
                                    startActivity(new Intent(LoginActivity.this, AppActivity.class));
                                    finish();
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

    public void saveUser(String email){
            sharedPreferences = getSharedPreferences("shared_pref",MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString("email",email);
            editor.apply();

    }

}
