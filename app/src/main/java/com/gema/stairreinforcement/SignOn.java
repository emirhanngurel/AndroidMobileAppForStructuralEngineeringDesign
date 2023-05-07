package com.gema.stairreinforcement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignOn extends AppCompatActivity {

    private EditText email, password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_on);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();

    }

    public void login(View v){
        String loginEmail = email.getText().toString().trim();
        String loginPwd = password.getText().toString().trim();

        if(loginEmail.isEmpty()){
            email.setError("Please provide an e-mail address.");
        }
        if(loginPwd.isEmpty()){
            password.setError("Please enter your password.");
        }
        else{
            mAuth.signInWithEmailAndPassword(loginEmail,loginPwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(SignOn.this, "Login successful.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignOn.this, HomePage.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignOn.this,"Login Failed! " + e.getMessage(),Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
    public void signup(View v){
        startActivity(new Intent(SignOn.this,SignUp.class));
        finish();
    }
}