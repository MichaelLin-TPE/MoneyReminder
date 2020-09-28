package com.money.moneyreminder.calendar_fragment;

import android.util.Log;

import com.money.moneyreminder.tool.DataProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalendarFragmentPresenterImpl implements CalendarFragmentPresenter{

    private CalendarFragmentVu mView;

    private int currentYear , currentMonth;

    public CalendarFragmentPresenterImpl(CalendarFragmentVu mView) {
        this.mView = mView;
    }

    @Override
    public void onActivityCreated()  {
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.TAIWAN);
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM",Locale.TAIWAN);
        currentYear = Integer.parseInt(yearFormat.format(new Date(System.currentTimeMillis())));
        currentMonth = Integer.parseInt(monthFormat.format(new Date(System.currentTimeMillis())));
        if (DataProvider.getInstance().getDateList(currentYear,currentMonth) == null){
            Log.i("Michael","dateList is null");
            return;
        }
        mView.showCalendarView(DataProvider.getInstance().getDateList(currentYear,currentMonth));
    }
}
