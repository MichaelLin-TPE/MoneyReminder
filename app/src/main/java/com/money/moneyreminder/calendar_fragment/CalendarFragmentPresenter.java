package com.money.moneyreminder.calendar_fragment;

import java.text.ParseException;

public interface CalendarFragmentPresenter {
    void onActivityCreated();

    void onLeftArrowClickListener();

    void onRightArrowClickListener();

    void onCalendarItemClickListener(String date);
}
