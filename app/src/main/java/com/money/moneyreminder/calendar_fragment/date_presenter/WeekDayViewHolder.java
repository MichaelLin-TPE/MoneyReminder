package com.money.moneyreminder.calendar_fragment.date_presenter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.money.moneyreminder.R;
import com.money.moneyreminder.tool.DpConvertTool;

public class WeekDayViewHolder extends RecyclerView.ViewHolder {

    private TextView tvItem;

    public WeekDayViewHolder(@NonNull View itemView) {
        super(itemView);


        tvItem = itemView.findViewById(R.id.week_day_item);
        tvItem.setWidth(DpConvertTool.getInstance().convertDb());
        tvItem.setHeight(DpConvertTool.getInstance().convertDb());
    }

    public void setData(String day) {
        tvItem.setText(day);
    }
}
