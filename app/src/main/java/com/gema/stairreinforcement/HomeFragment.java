package com.gema.stairreinforcement;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.gema.stairreinforcement.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    int numOfSteps;
    int rise;
    int runOfStep;
    int widthOfStair;
    int thickness;

    int tru;
    int tri;
    double ss;
    double meter;
    double yard;
    double feet;
    String email;
    String date;



    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        View view = binding.getRoot();


        Button calculateButton = (Button) binding.calculate;
        calculateButton.setOnClickListener(v -> {

            try{
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }catch (Exception e){
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }



            try{
                numOfSteps = Integer.parseInt(binding.numOfSteps.getText().toString());
                rise = Integer.parseInt(binding.rise.getText().toString());
                runOfStep = Integer.parseInt(binding.run.getText().toString());
                widthOfStair = Integer.parseInt(binding.width.getText().toString());
                thickness = Integer.parseInt(binding.thickness.getText().toString());

            }catch(NumberFormatException ex){
                binding.numOfSteps.setError("Please enter a value");
                binding.rise.setError("Please enter a value");
                binding.run.setError("Please enter a value");
                binding.width.setError("Please enter a value");
                binding.thickness.setError("Please enter a value");
                numOfSteps = 0;
                rise = 0;
                runOfStep = 0;
                widthOfStair = 0;
                thickness = 0;
            }

            int amount;

            try{
                tru = runOfStep * numOfSteps; // Total Run
                tri = tri = rise * numOfSteps; // Total Rise
                amount = rise / runOfStep;
            }catch(ArithmeticException ae){
                tru = 0;
                tri = 0;
                amount = 0;
            }

            double rad = Math.atan(amount);
            ss = rad * 180/Math.PI; // Stair slope
            double total;
            double cal;

            if(numOfSteps == 1){
                cal = rise*runOfStep*widthOfStair; // mm3
                feet = cal * 0.0000000353146667214886; // ft3
                yard = cal* 0.00000000130795061931439; //yd3
                meter = cal * 0.000000001; //m3
            }
            else{
                total = ((rise*runOfStep*widthOfStair)/2 + Math.sqrt(rise*rise + runOfStep*runOfStep) * widthOfStair * thickness)*(numOfSteps-1)+rise*runOfStep*widthOfStair;
                feet = total * 0.0000000353146667214886; // ft3;
                yard = total * 0.00000000130795061931439; //yd3
                meter = total * 0.000000001; //m3
            }
            binding.totalRise.setText(Integer.toString(tri));
            binding.totalRun.setText(Integer.toString(tru));
            binding.stairSlope.setText(Double.toString(ss));
            binding.cubicMeter.setText(Double.toString(meter));
            binding.cubicFeet.setText(Double.toString(feet));
            binding.cubicYards.setText(Double.toString(yard));

            if(TextUtils.isEmpty(binding.numOfSteps.getText().toString())
                    || TextUtils.isEmpty(binding.rise.getText().toString())
                    || TextUtils.isEmpty(binding.run.getText().toString())
                    || TextUtils.isEmpty(binding.width.getText().toString())
                    || TextUtils.isEmpty(binding.thickness.getText().toString())
            )
            {
            binding.linearLayout.setVisibility(View.INVISIBLE);
            binding.table.setVisibility(View.INVISIBLE);
            }
            else {
                binding.linearLayout.setVisibility(View.VISIBLE);
                binding.table.setVisibility(View.VISIBLE);
                binding.save.setVisibility(View.VISIBLE);
                binding.calculate2.setVisibility(View.VISIBLE);
            }


        });

        Button saveButton = (Button) binding.save;
        saveButton.setOnClickListener(v -> {

            if(user!=null){
                for(UserInfo profile: user.getProviderData()){
                    email = profile.getEmail();
                }
            }
            Date today = Calendar.getInstance().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy EEE hh:mm");
            date = sdf.format(today);

            Map<String, Object> cal = new HashMap<>();
            cal.put("numOfSteps",numOfSteps);
            cal.put("rise",rise);
            cal.put("runOfStep",runOfStep);
            cal.put("widthOfStair",widthOfStair);
            cal.put("thickness",thickness);
            cal.put("tru",tru);
            cal.put("tri",tri);
            cal.put("ss",ss);
            cal.put("feet",feet);
            cal.put("yard",yard);
            cal.put("meter",meter);
            cal.put("email" , email);
            cal.put("date", new Date());


            db.collection("calculation").add(cal)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getActivity(), "Calculations saved successfully with ID " + documentReference.getId(), Toast.LENGTH_SHORT).show();
                            saveButton.setClickable(false);
                            saveButton.setBackgroundColor(Color.parseColor("#808080"));
                            saveButton.setTextColor(Color.parseColor("#FFFFFF"));
                            binding.newcalc.setVisibility(View.VISIBLE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(),"Error saving calculations to database " + e,Toast.LENGTH_SHORT).show();
                        }
                    });



        });

        Button newCalcButton = (Button) binding.newcalc;
        newCalcButton.setOnClickListener(v -> {

            binding.linearLayout.setVisibility(View.INVISIBLE);
            binding.table.setVisibility(View.INVISIBLE);
            binding.save.setVisibility(View.INVISIBLE);
            binding.calculate2.setVisibility(View.INVISIBLE);
            binding.newcalc.setVisibility(View.INVISIBLE);
            binding.rise.getText().clear();
            binding.run.getText().clear();
            binding.numOfSteps.getText().clear();
            binding.width.getText().clear();
            binding.thickness.getText().clear();
            saveButton.setClickable(true);
            saveButton.setBackgroundColor(Color.parseColor("#03DAC6"));
            saveButton.setTextColor(Color.parseColor("#000000"));





        });






        return view;

    }
}