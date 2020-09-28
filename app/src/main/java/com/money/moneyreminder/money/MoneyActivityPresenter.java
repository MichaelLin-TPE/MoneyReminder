package com.money.moneyreminder.money;

public interface MoneyActivityPresenter {
    void onLogoutButtonClickListener();

    void onLogoutConfirmClickListener();

    void onActivityCreate();

    void onTabSelectClickListener(int tabItemPosition);
}
