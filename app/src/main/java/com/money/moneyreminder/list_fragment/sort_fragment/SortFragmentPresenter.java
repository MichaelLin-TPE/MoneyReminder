package com.money.moneyreminder.list_fragment.sort_fragment;

import com.money.moneyreminder.sort.MoneyObject;

import java.util.ArrayList;

public interface SortFragmentPresenter {
    void onActivityCreated(ArrayList<MoneyObject> moneyObjectArrayList, boolean isIncome);

    void onNumberOfCaseButtonClickListener(String sortType);
}
