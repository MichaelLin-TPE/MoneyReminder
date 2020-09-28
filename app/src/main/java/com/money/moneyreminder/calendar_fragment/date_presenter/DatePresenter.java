package com.money.moneyreminder.calendar_fragment.date_presenter;

import java.util.ArrayList;

public interface DatePresenter {
    void setData(ArrayList<String> weekDayArray, ArrayList<String> dateList);

    int getItemCount();

    int getItemViewType(int position);

    void onBindWeekDayHolder(WeekDayViewHolder holder, int position);

    void onBindDateListHolder(DateListViewHolder holder, int position);
}
