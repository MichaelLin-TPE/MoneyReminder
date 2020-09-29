package com.money.moneyreminder.calendar_fragment;

import com.money.moneyreminder.sort.MoneyObject;

import java.util.ArrayList;

public interface CalendarFragmentVu {
    void showCalendarView(ArrayList<String> dateList, ArrayList<MoneyObject> moneyDateArray);

    void showErrorCode(String errorCode);

    void setTime(String currentDate);
}
