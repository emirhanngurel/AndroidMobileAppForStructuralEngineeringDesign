package com.gema.stairreinforcement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ForgotPassword extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();

    EditText emailAddress;
    String email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emailAddress = findViewById(R.id.email);

    }

    public void resetPassword(View v){
        email = emailAddress.getText().toString().trim();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Password reset mail send.",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ForgotPassword.this,SignOn.class));
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Send failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    public void back(View v){
        startActivity(new Intent(ForgotPassword.this,SignOn.class));
        finish();
    }
}