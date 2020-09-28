package com.money.moneyreminder;

public interface MainActivityVu {
    void showErrorCode(String errorCode);

    void intentToMoneyActivity();

    void showProgressBar(boolean isShow);

    void enableSignButton(boolean isEnable);
}
