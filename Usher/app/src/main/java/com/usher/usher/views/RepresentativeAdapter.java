package com.usher.usher.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.drawable.Drawable;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.usher.usher.R;
import com.usher.usher.models.RepresentativeVO;

import java.util.ArrayList;
import java.util.List;

public class RepresentativeAdapter extends RecyclerView.Adapter<RepresentativeAdapter.RepresentativeViewHolder>{

    ArrayList<RepresentativeVO> listRepresentative;
    Float presentism;
    private Context context;

    public RepresentativeAdapter(ArrayList<RepresentativeVO> listRepresentative, Context context) {
        this.listRepresentative = listRepresentative;
        this.context = context;
    }

    //Cada uno de lso datos a referenciar va a ser obsrvado por los 3 metodos del Adapter
    @NonNull
    @Override
    public RepresentativeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_representative,null,false);

        //Asi abajo el ViewHolder recive este view
        return new RepresentativeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepresentativeViewHolder holder, int position) {
        List<PieEntry> pieEntry = new ArrayList<>();

        holder.aNameAndSurnameRepresentative.setText(listRepresentative.get(position).getNameAndSurname());
        holder.aBlockRepresentative.setText(listRepresentative.get(position).getBlock());
        if (listRepresentative.get(position).getPresentism() > 0.5) {
            holder.aPresenceSituationRepresentative.setText( "PRESENTE en un " + listRepresentative.get(position).getPresentism()*100 + "%");
            holder.aPresenceSituationRepresentative.setTextColor(context.getResources().getColor(R.color.colorAccent));
        } else {
            holder.aPresenceSituationRepresentative.setText( "AUSENTE EN UN " + listRepresentative.get(position).getAusentism()*100 + "%");
            holder.aPresenceSituationRepresentative.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }

        //holder.aBlockRepresentative.setText(listRepresentative.get(position).getBlock());
        List<Integer> colors = new ArrayList<>();
        colors.add(context.getResources().getColor(R.color.colorAccent));
        colors.add(context.getResources().getColor(R.color.colorPrimary));

        pieEntry.add(new PieEntry(listRepresentative.get(position).getPresentism(), "P"));
        pieEntry.add(new PieEntry(listRepresentative.get(position).getAusentism(), "A"));

        PieDataSet pieDataSet = new PieDataSet(pieEntry, ".");

        pieDataSet.setValueTextSize(10);
        pieDataSet.setDrawValues(true);
        pieDataSet.setDrawIcons(false);
        pieDataSet.setHighlightEnabled(false);

        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter(holder.aPresentismGraph));

        holder.aPresentismGraph.animateXY(1000,1000);
        holder.aPresentismGraph.setData(pieData);
        holder.aPresentismGraph.setUsePercentValues(true);
        holder.aPresentismGraph.getDescription().setEnabled(false);
        holder.aPresentismGraph.setDrawHoleEnabled(false);
        holder.aPresentismGraph.setDrawSliceText(false);


        Legend legend = holder.aPresentismGraph.getLegend();
        legend.setEnabled(false);
        //holder.aPresentismGraph.setHoleRadius(50000f);
        //holder.aPresentismGraph.setTransparentCircleRadius(1f);
        /*

        holder.aPresentismGraph.setEntryLabelTextSize(0);
        holder.aPresentismGraph.getData().setDrawValues(false);
        holder.aPresentismGraph.setDrawMarkers(false);
        holder.aPresentismGraph.setDrawEntryLabels(false);

        holder.aPresentismGraph.layout(100,100,-100,-50);



        holder.aPresentismGraph.getCircleBox().set(100,100,-100,-100);
        float radium = holder.aPresentismGraph.getRadius();
        MPPointF center = holder.aPresentismGraph.getCenterCircleBox();
        int i = 1;*/
        //holder.aPresentismGraph.invalidate();

        /**Glide.with(holder.aPhotoRepresentative)
                .load(listRepresentative.get(position).getPresentism())
                .circleCrop()
                .into(holder.aPhotoRepresentative);
         */

    }

    @Override
    public int getItemCount() {
        return listRepresentative.size();
    }

    public class RepresentativeViewHolder extends RecyclerView.ViewHolder {
        //Se encarga de cargar los elementos graficos, tenemos las referencias graficas
        ImageView aPhotoRepresentative;
        TextView aNameAndSurnameRepresentative, aBlockRepresentative, aPresenceSituationRepresentative;
        PieChart aPresentismGraph;

        public RepresentativeViewHolder(@NonNull View itemView) {
            super(itemView);
            aNameAndSurnameRepresentative = itemView.findViewById(R.id.vNameAndSurnameRepresentative);
            aBlockRepresentative = itemView.findViewById(R.id.vBlockRepresentative);
            aPhotoRepresentative = itemView.findViewById(R.id.vPhotoRepresentative);
            aPresenceSituationRepresentative = itemView.findViewById(R.id.vPresenceSituationRepresentative);
            aPresentismGraph = itemView.findViewById(R.id.vPresentismGraph);
        }
    }
}
