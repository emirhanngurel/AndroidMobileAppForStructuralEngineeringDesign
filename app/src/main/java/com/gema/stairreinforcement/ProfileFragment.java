package com.gema.stairreinforcement;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gema.stairreinforcement.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private String email;

    public ProfileFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater,container,false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        View view = binding.getRoot();

        if(user!=null){
            for(UserInfo profile: user.getProviderData()){
                email = profile.getEmail();
            }

            binding.emailView.setText(email);
        }
        Button signOutButton = (Button) binding.signOutButton;
        signOutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getActivity(),"Logged out successfully.",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(),SignOn.class));
            getActivity().finish();
        });



        Button changePasswordButton = (Button) binding.changePasswordButton;
        changePasswordButton.setOnClickListener(v -> {

            EditText newPassword = (EditText) binding.editTextTextPassword;
            EditText newPassword2 = (EditText) binding.editTextTextPassword2;

            String newPasswordStr = String.valueOf(newPassword.getText());
            String newPassword2Str = String.valueOf(newPassword2.getText());


            if(newPasswordStr.equals(newPassword2Str)){
                assert user != null;
                user.updatePassword(newPasswordStr).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(),"Password changed.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else{
                Toast.makeText(getActivity(),"Passwords do not match.",Toast.LENGTH_SHORT).show();
            }

        });


        return view;
    }




}