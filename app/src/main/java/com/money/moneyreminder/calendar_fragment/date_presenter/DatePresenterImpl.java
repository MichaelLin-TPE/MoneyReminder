package com.money.moneyreminder.calendar_fragment.date_presenter;

import android.util.Log;

import java.util.ArrayList;

public class DatePresenterImpl implements DatePresenter {

    private ArrayList<String> weekDayArray,dateList;

    public static final int SHOW_WEEK_DAY = 0;

    public static final int SHOW_DATE_LIST = 1;

    @Override
    public void setData(ArrayList<String> weekDayArray, ArrayList<String> dateList) {

        this.weekDayArray = weekDayArray;
        this.dateList = dateList;

    }

    @Override
    public int getItemCount() {
        return weekDayArray.size()+dateList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (position < weekDayArray.size()){
            return SHOW_WEEK_DAY;
        }
        return SHOW_DATE_LIST;
    }

    @Override
    public void onBindWeekDayHolder(WeekDayViewHolder holder, int position) {
        holder.setData(weekDayArray.get(position));
    }

    @Override
    public void onBindDateListHolder(DateListViewHolder holder, int position) {
        int itemPosition = position - weekDayArray.size();
        Log.i("Michael","DateList開始位置 : "+itemPosition);
        holder.setData(dateList.get(itemPosition));
    }
}
