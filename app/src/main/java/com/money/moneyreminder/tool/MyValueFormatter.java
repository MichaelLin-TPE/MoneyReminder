package com.money.moneyreminder.tool;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.DecimalFormat;

public class MyValueFormatter extends PercentFormatter {

    private DecimalFormat decimalFormat;

    private PieChart pieChart;

    public MyValueFormatter(DecimalFormat decimalFormat, PieChart pieChart) {
        this.decimalFormat = decimalFormat;
        this.pieChart = pieChart;
    }

    @Override
    public String getFormattedValue(float value) {
        return decimalFormat.format(value) + "%";
    }

    @Override
    public String getPieLabel(float value, PieEntry pieEntry) {
        if ( pieChart != null && pieChart.isUsePercentValuesEnabled()){
            return getFormattedValue(value);
        }else {
            return decimalFormat.format(value);
        }
    }
}
