package com.money.moneyreminder.money;

public interface MoneyActivityVu {
    void intentToMainActivity();

    void showLogoutConfirmDialog();

    void showTabLayout();

    void replaceFragment(int tabItemPosition);
}
