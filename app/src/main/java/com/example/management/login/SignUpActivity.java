package com.example.management.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.management.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import static com.example.management.login.LoginActivity.mAuth;

public class SignUpActivity extends AppCompatActivity {

    TextInputLayout emailSU,passSU,passReUS;
    RelativeLayout relative_progress,relative_singUp;
    Button btnBackToLogin,btnSingUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        emailSU = findViewById(R.id.outlinedTextField_singUp);
        passSU = findViewById(R.id.input_password);
        passReUS = findViewById(R.id.input_password_re);
        relative_progress = findViewById(R.id.relative_progress);
        btnBackToLogin = findViewById(R.id.btnBackToSingIn);
        btnSingUp = findViewById(R.id.btn_shingUp);



        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                finish();
            }
        });


        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!emailSU.getEditText().getText().toString().equals("") && !passSU.getEditText().getText().toString().equals("") && passSU.getEditText().getText().toString().equals(passReUS.getEditText().getText().toString()))
                {
                    relative_progress.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(emailSU.getEditText().getText().toString(), passSU.getEditText().getText().toString())
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        relative_progress.setVisibility(View.GONE);
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(SignUpActivity.this, "ثبت نام شما با موفیقت انجام شد", Toast.LENGTH_LONG).show();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                        relative_progress.setVisibility(View.GONE);
                                        Toast.makeText(SignUpActivity.this, "خطا",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }else {
                    relative_progress.setVisibility(View.GONE);
                    Toast.makeText(SignUpActivity.this, "مثادیر خاسته شده را به درستی وارد نمایید.", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }
}