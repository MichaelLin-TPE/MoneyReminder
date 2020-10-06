package com.money.moneyreminder.user_fragment;

public interface UserFragmentPresenter {
    void onActivityCreated();

    void onSettingButtonClickListener();

    void onSetBudgetMoneyConfirmClickListener(String toString);

    void onAccountItemClickListener(String itemName);

    void onLogoutConfirmClickListener();

    void onDataSortClickListener(String sortType);
}
