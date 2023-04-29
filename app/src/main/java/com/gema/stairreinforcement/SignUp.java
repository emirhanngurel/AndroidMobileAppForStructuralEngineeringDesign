package com.gema.stairreinforcement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email, password,name,surname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.signupemail);
        password = findViewById(R.id.signuppwd);
        name = findViewById(R.id.signuppwd2);
        surname=findViewById(R.id.signuppwd3);


    }

    public void signup(View v) {
        String signUpEmail = email.getText().toString().trim();
        String signUpPass = password.getText().toString().trim();


        if(signUpEmail.isEmpty()){
            email.setError("E-Mail field cannot be empty!");
        }
        if(signUpPass.isEmpty()){
            password.setError("Password field cannot be empty!");
        }
        else{
            mAuth.createUserWithEmailAndPassword(signUpEmail,signUpPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name.toString().trim() + surname.toString().trim())
                                .build();
                        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    System.out.println("User profile updated.");
                                }
                            }
                        });
                        Toast.makeText(SignUp.this,"Registration completed.",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUp.this,SignOn.class));
                        finish();

                    }else{
                        Toast.makeText(SignUp.this,"Registration failed." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

}