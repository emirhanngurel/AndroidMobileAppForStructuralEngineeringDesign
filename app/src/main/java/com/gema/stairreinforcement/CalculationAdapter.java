package com.gema.stairreinforcement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;


public class CalculationAdapter extends FirestoreRecyclerAdapter<Calculation,CalculationAdapter.calViewHolder> {

    private OnItemClickListener listener;
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener!= null){
                        try {
                            listener.onItemClick(getSnapshots().getSnapshot(position), position);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
            });

        }
    }

    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position) throws FileNotFoundException;
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public static class WrapContentLinearLayoutManager extends LinearLayoutManager {
        public WrapContentLinearLayoutManager(Context context) {
            super(context);
        }

        public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public WrapContentLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                Log.e("TAG", "meet a IOOBE in RecyclerView");
            }
        }
    }


}
