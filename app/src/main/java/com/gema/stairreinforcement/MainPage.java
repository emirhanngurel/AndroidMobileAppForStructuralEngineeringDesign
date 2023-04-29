package com.gema.stairreinforcement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.gema.stairreinforcement.databinding.ActivityMainPageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class MainPage extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private ActivityMainPageBinding binding;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            for(UserInfo profile: user.getProviderData()){
                email = profile.getEmail();

            }
        }
        System.out.println(email);
        binding.textView.setText(email);
        binding.imageView.setImageResource(R.drawable.img);



    }
}