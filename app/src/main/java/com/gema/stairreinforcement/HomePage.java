package com.gema.stairreinforcement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gema.stairreinforcement.databinding.ActivityMainPageBinding;
import com.gema.stairreinforcement.databinding.FragmentHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePage extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private ActivityMainPageBinding binding;
    private FragmentHomeBinding bindingHome;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.getMenu().getItem(1).setChecked(true);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch(item.getItemId()){
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    break;
                case R.id.reports:
                    replaceFragment(new ReportsFragment());
                    break;
            }

            return true;
        });







    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.constraintLayout2,fragment);
        fragmentTransaction.commit();
    }

    public void calculate(View v){
        /*
        int num = Integer.parseInt(binding.numOfSteps.getText().toString());
        double rise = Double.parseDouble(binding.rise.getText().toString());
        double run = Double.parseDouble(binding.run.getText().toString());
        double width = Double.parseDouble(binding.width.getText().toString());
        */


    }



}