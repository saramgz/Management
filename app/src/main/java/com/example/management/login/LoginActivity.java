package com.example.management.login;


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

import com.example.management.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button btnGoLogIn,btnGoToSingUp,btnBackToSingIn,btnLogIn,btnSingUp;
    RelativeLayout relative_welcome,relative_singIn,relative_singUp,relative_progress;
    FirebaseFirestore db;
    TextInputLayout emailSU,passSU,passReUS;
    boolean status = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //findView
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


        //code
        db = FirebaseFirestore.getInstance();


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
                relative_progress.setVisibility(View.VISIBLE);
                //Toast.makeText(LoginActivity.this, emailSU.getEditText().getText().toString(), Toast.LENGTH_SHORT).show();
                if (!emailSU.getEditText().getText().toString().equals("") && !passSU.getEditText().getText().toString().equals("") && !passReUS.getEditText().getText().toString().equals("")
                        && passSU.getEditText().getText().toString().equals(passReUS.getEditText().getText().toString()))
                {
                    if (isUser(emailSU.getEditText().getText().toString())) {
                        addNewUser(emailSU.getEditText().getText().toString() , passReUS.getEditText().getText().toString());
                    }

                }

                relative_progress.setVisibility(View.GONE);
            }
        });

    }
    public void addNewUser(String email , String pass){
        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("password", pass);

        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(LoginActivity.this, documentReference.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                        Toast.makeText(LoginActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public boolean isUser(String sEmail){
        status = true;
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.get("email").equals(sEmail)) {
                                    Toast.makeText(LoginActivity.this, "این ایمیل قبلا ثبت شده است", Toast.LENGTH_SHORT).show();
                                    status = false;
                                    break;
                                }

                                //Log.d("TAG", document.getId() + " => " + document.get("email"));
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
        return status;
    }
}







//        Fragment welcomeFragment = new LoginFragment();
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.add(R.id.frame_login,welcomeFragment).commit();
//
//        Button btn;
//        btn = findViewById(R.id.btn_go_to_login);