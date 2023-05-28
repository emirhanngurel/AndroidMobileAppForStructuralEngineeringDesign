package com.gema.stairreinforcement;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.SimpleDateFormat;

public class CalculationAdapter extends FirestoreRecyclerAdapter<Calculation,CalculationAdapter.calViewHolder> {


    public CalculationAdapter(FirestoreRecyclerOptions<Calculation> options){
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull CalculationAdapter.calViewHolder holder, int position, @NonNull Calculation model) {

        holder.numOfSteps.setText("Number of steps: " + model.getNumOfSteps());
        holder.rise.setText("Rise of step: " + model.getRise());
        holder.runOfStep.setText("Run of step: " + model.getRunOfStep());
        holder.widthOfStair.setText("Width of stair: " + model.getWidthOfStair());
        holder.thickness.setText("Throat thickness: " + model.getThickness());
        holder.tru.setText("Total run: " + model.getTru());
        holder.tri.setText("Total rise: " + model.getTri());
        holder.ss.setText("Stair slope: " + model.getSs() + '°');
        holder.meter.setText("Cubic Meters: " + model.getMeter() + " m³");
        holder.yard.setText("Cubic yards: " + model.getYard() + " yd³");
        holder.feet.setText("Cubic Feets: " + model.getFeet() + " ft³");
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy EEE hh:mm");
        holder.date.setText(sdf.format(model.getDate()));
        holder.lira.setText("Estimated Cost (TRY): " + model.getLira());
        holder.dollar.setText("Estimated Cost (USD): " + model.getDollar());
        holder.euro.setText("Estimated Cost (EUR): " + model.getEuro());

    }


    @NonNull
    @Override
    public calViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new CalculationAdapter.calViewHolder(view);

    }

    class calViewHolder extends RecyclerView.ViewHolder{
        TextView numOfSteps, rise, runOfStep, widthOfStair, thickness, tru, tri, ss, meter, yard, feet, date,
        lira, dollar, euro;


        public calViewHolder(@NonNull View itemView) {
            super(itemView);
            numOfSteps = itemView.findViewById(R.id.idNumOfSteps);
            rise = itemView.findViewById(R.id.idRiseOfStep);
            runOfStep = itemView.findViewById(R.id.idRunOfStep);
            widthOfStair = itemView.findViewById(R.id.idwidthOfStair);
            thickness = itemView.findViewById(R.id.idThickness);
            tru = itemView.findViewById(R.id.idTotalRun);
            tri = itemView.findViewById(R.id.idTotalRise);
            ss = itemView.findViewById(R.id.idStairSlope);
            meter = itemView.findViewById(R.id.idCubicMeter);
            yard = itemView.findViewById(R.id.idCubicYards);
            feet = itemView.findViewById(R.id.idCubicFeet);
            date = itemView.findViewById(R.id.idDate);
            lira = itemView.findViewById(R.id.idTurkishLira);
            dollar = itemView.findViewById(R.id.idUSDollar);
            euro = itemView.findViewById(R.id.idEuro);

        }
    }
}
