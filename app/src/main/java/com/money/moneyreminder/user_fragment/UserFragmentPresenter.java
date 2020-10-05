package com.money.moneyreminder.user_fragment;

public interface UserFragmentPresenter {
    void onActivityCreated();

    void onSettingButtonClickListener();

    void onSetBudgetMoneyConfirmClickListener(String toString);
}
