package com.money.moneyreminder.list_fragment.sort_fragment;

import com.money.moneyreminder.sort.MoneyData;
import com.money.moneyreminder.sort.MoneyObject;

import java.util.ArrayList;

public interface DetailListFragmentPresenter {
    void onActivityCreated(ArrayList<MoneyObject> moneyDataArrayList, boolean isIncome);

    void onDetailChildItemClickListener(MoneyData data);
}
