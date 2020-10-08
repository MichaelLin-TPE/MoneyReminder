package com.money.moneyreminder.list_fragment.sort_fragment;

import com.money.moneyreminder.sort.MoneyData;

import java.util.ArrayList;

public interface SortFragmentVu {
    void setRecyclerView(ArrayList<SortPercentData> sortTypeArray, boolean isIncome);

    void showNoDataView(boolean isShow);

    void showDetailListDialog(String sortType, ArrayList<MoneyData> moneyDataArrayList);

    String getSortAnalysisType();

    void pieChart(ArrayList<SortPercentData> sortTypeArray);
}
