package com.usher.usher;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.usher.usher.requests.BlockStatisticsRequest;
import com.usher.usher.services.StatisticsInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChartFragment extends Fragment {

    private LineChart mLineChart;
    public ChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        mLineChart = view.findViewById(R.id.barChart);
        getChart(getArguments().getString("method"));
        return view;
    }

    private void getChart(final String method){
        Call<List<BlockStatistics>> call = BlockStatisticsRequest.getApiClient().create(StatisticsInterface.class).getStatisticsInfo();

        call.enqueue(new Callback<List<BlockStatistics>>() {
            @Override
            public void onResponse(Call<List<BlockStatistics>> call, Response<List<BlockStatistics>> response) {
                if (response.body() != null){
                    if(method.equals("bars")){
                        List<Entry> entries = new ArrayList<>();

                        for(BlockStatistics line : response.body()){
                            entries.add(new BarEntry(line.getTime(),line.getTotal()));
                        }

                        LineDataSet lineDataSet = new LineDataSet(entries,"Representación cada 15 minutos");
                        lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                        lineDataSet.setValueTextSize(15f);
                        lineDataSet.setColor(ColorTemplate.getHoloBlue());
                        lineDataSet.setValueTextColor(ColorTemplate.getHoloBlue());
                        lineDataSet.setLineWidth(1.5f);
                        lineDataSet.setDrawCircles(false);
                        lineDataSet.setDrawValues(false);
                        lineDataSet.setFillAlpha(65);
                        lineDataSet.setFillColor(ColorTemplate.getHoloBlue());
                        lineDataSet.setHighLightColor(Color.rgb(244, 117, 117));
                        lineDataSet.setDrawCircleHole(false);

                        LineData lineData = new LineData(lineDataSet);
                        lineData.setValueTextColor(Color.GRAY);
                        lineData.setValueTextSize(9f);

                        mLineChart.setVisibility(View.VISIBLE);
                        mLineChart.animateY(5000);
                        mLineChart.setData(lineData);

                        Description description = new Description();
                        description.setText("Cantidad de Presentes");
                        description.setTextSize(15f);
                        mLineChart.setDescription(description);
                        mLineChart.invalidate();
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

}
