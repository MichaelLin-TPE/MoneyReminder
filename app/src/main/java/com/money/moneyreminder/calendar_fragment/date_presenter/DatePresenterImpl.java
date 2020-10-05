package com.money.moneyreminder.calendar_fragment.date_presenter;

import android.util.Log;

import com.money.moneyreminder.sort.MoneyObject;

import java.util.ArrayList;

public class DatePresenterImpl implements DatePresenter {

    private ArrayList<String> weekDayArray,dateList;

    private ArrayList<MoneyObject> moneyDateArray;

    public static final int SHOW_WEEK_DAY = 0;

    public static final int SHOW_DATE_LIST = 1;

    @Override
    public void setData(ArrayList<String> weekDayArray, ArrayList<String> dateList, ArrayList<MoneyObject> moneyDateArray) {

        this.weekDayArray = weekDayArray;
        this.dateList = dateList;
        this.moneyDateArray = moneyDateArray;

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
        holder.setData(dateList.get(itemPosition),moneyDateArray);
    }

    @Override
    public void onCalendarItemClickListener(DateListViewHolder holder, DateListViewHolder.OnCalendarItemClickListener listener) {
        Log.i("Michael","clickListener 傳入 datePresenter");
        holder.setOnCalendarItemClickListener(listener);
    }
}
