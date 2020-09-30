package com.money.moneyreminder.list_fragment;

import com.money.moneyreminder.sort.MoneyData;

public interface ListFragmentPresenter {
    void onActivityCreated(boolean isIncome);

    void onAddMoneyButtonClickListener(boolean isEditMode);

    void onRadioMoneySortButtonClickListener(boolean isIncome);

    void onTabSelectedListener(String month, String year);

    void onDetailItemCheckBoxCheckListener(MoneyData data, boolean isChecked);

    void onDetailItemLongPressListener();

    void onCatchErrorCode(String message);
}
