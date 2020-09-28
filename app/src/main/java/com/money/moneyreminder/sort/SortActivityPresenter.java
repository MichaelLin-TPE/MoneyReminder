package com.money.moneyreminder.sort;

import com.money.moneyreminder.sort_list.presenter.SortTypeData;

public interface SortActivityPresenter {
    void onActivityCreate(int totalMoney, boolean isIncome);

    void setOnSortAreaClickListener();

    void setOnDateAreaClickListener();

    void onDateConfirmClickListener(String choiceTime);

    void onBackButtonClickListener();

    void onSaveButtonClickListener();

    void onCatchSortTypeData(SortTypeData sortTypeData);
}
