package com.money.moneyreminder.list_fragment;

public interface ListFragmentPresenter {
    void onActivityCreated(boolean isIncome);

    void onAddMoneyButtonClickListener();

    void onRadioMoneySortButtonClickListener(boolean isIncome);

    void onTabSelectedListener(String month, String year);
}
