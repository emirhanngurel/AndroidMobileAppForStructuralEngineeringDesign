package com.gema.stairreinforcement;

import static android.content.Context.INPUT_METHOD_SERVICE;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.gema.stairreinforcement.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;




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


            int numOfSteps;
            int rise;
            int runOfStep;
            int widthOfStair;
            int thickness;


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

            int tru;
            int tri;
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
            double ss = rad * 180/Math.PI; // Stair slope
            double meter;
            double yard;
            double feet;
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

            binding.totalRun.setVisibility(View.VISIBLE);
            binding.totalRun2.setVisibility(View.VISIBLE);
            binding.totalRise.setVisibility(View.VISIBLE);
            binding.totalRise2.setVisibility(View.VISIBLE);
            binding.stairSlope.setVisibility(View.VISIBLE);
            binding.stairSlope2.setVisibility(View.VISIBLE);
            binding.cubicMeter.setVisibility(View.VISIBLE);
            binding.cubicMeter2.setVisibility(View.VISIBLE);
            binding.cubicFeet.setVisibility(View.VISIBLE);
            binding.cubicFeet2.setVisibility(View.VISIBLE);
            binding.cubicYards.setVisibility(View.VISIBLE);
            binding.cubicYards2.setVisibility(View.VISIBLE);
            binding.calculate2.setVisibility(View.VISIBLE);

            binding.save.setVisibility(View.VISIBLE);



        });






        return view;

    }
}