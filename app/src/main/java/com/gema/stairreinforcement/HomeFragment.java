package com.gema.stairreinforcement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
            int numOfSteps = Integer.parseInt(binding.numOfSteps.getText().toString());
            int rise = Integer.parseInt(binding.rise.getText().toString());
            int runOfStep = Integer.parseInt(binding.run.getText().toString());
            int widthOfStair = Integer.parseInt(binding.width.getText().toString());
            int thickness = Integer.parseInt(binding.thickness.getText().toString());

            int tru = runOfStep * numOfSteps; // Total Run
            int tri = rise * numOfSteps; // Total Rise
            int amount = rise / runOfStep;
            double rad = Math.atan(amount);
            double ss = rad * 180/Math.PI; // Stair slope
            double meter;
            double yard;
            double feet;

            if(numOfSteps == 1){
                double cal = rise*runOfStep*widthOfStair; // mm3
                feet = cal * 0.0000000353146667214886; // ft3
                yard = cal* 0.00000000130795061931439; //yd3
                meter = cal * 0.000000001; //m3
            }
            else{
                int i = (numOfSteps - 1) + rise * runOfStep * widthOfStair;
                double cal0 = (rise * runOfStep * widthOfStair)/2;
                double cal1 = Math.sqrt(rise*rise + runOfStep*runOfStep)*widthOfStair*thickness;
                double cal2 = (numOfSteps-1)+rise*runOfStep*widthOfStair;
                double total = cal0 + cal1 * cal2;
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
        });






        return view;

    }
}