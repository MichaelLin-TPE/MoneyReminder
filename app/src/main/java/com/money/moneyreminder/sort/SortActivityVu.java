package com.money.moneyreminder.sort;

import com.money.moneyreminder.sort_list.presenter.SortTypeData;

import java.util.ArrayList;

public interface SortActivityVu {
    void setCurrentTime(String currentTime);

    void showDatePicker();

    void closePage();

    void showToast(String message);

    String getDescription();

    void intentToSortListActivity();

    void showSortType(SortTypeData sortTypeData);

    void setDescribeRecyclerView(ArrayList<String> describeArray);

    void setDescribe(String describe);

    void showTvNoData(boolean isShow);
}
