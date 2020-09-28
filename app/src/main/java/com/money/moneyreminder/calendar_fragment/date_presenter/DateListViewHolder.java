package com.money.moneyreminder.calendar_fragment.date_presenter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.money.moneyreminder.R;
import com.money.moneyreminder.tool.MoneyReminderApplication;

public class DateListViewHolder extends RecyclerView.ViewHolder {


    private TextView tvItem;

    private Context context;

    public DateListViewHolder(@NonNull View itemView) {
        super(itemView);
        context = MoneyReminderApplication.getInstance().getApplicationContext();
        tvItem = itemView.findViewById(R.id.week_day_item);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float screenWidth = (float) context.getResources().getDisplayMetrics().widthPixels;
        float singleItemSize = (float) screenWidth / 7 / metrics.density;
        int screenDp = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,singleItemSize,context.getResources().getDisplayMetrics());
        tvItem.setWidth(screenDp);
        tvItem.setHeight(screenDp);
    }

    public void setData(String date) {
        tvItem.setText(date);
        tvItem.setBackground(ContextCompat.getDrawable(context,R.drawable.date_list_background));
    }
}
