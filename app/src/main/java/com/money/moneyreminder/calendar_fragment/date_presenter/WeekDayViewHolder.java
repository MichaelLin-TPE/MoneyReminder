package com.money.moneyreminder.calendar_fragment.date_presenter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.money.moneyreminder.R;
import com.money.moneyreminder.tool.DbConvertTool;
import com.money.moneyreminder.tool.MoneyReminderApplication;

public class WeekDayViewHolder extends RecyclerView.ViewHolder {

    private TextView tvItem;

    public WeekDayViewHolder(@NonNull View itemView) {
        super(itemView);


        tvItem = itemView.findViewById(R.id.week_day_item);
        tvItem.setWidth(DbConvertTool.getInstance().convertDb());
        tvItem.setHeight(DbConvertTool.getInstance().convertDb());
    }

    public void setData(String day) {
        tvItem.setText(day);
    }
}
