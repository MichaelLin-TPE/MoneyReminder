package com.money.moneyreminder.list_fragment.sort_fragment;

import com.money.moneyreminder.sort.MoneyObject;

import java.util.ArrayList;

public interface DetailListFragmentVu {
    void setRecyclerView(ArrayList<MoneyObject> moneyDataArrayList, boolean isIncome);

    void showNoData(boolean isShow);
}
