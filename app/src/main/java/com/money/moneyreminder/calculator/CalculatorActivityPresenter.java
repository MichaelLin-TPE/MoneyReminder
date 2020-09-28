package com.money.moneyreminder.calculator;

public interface CalculatorActivityPresenter {
    void onActivityCreate();

    void onNumberItemClickListener(String number);

    void onBackButtonClickListener();

    void onMathItemClickListener(String mathItem);

    void onSetIsIncome(boolean isIncome);
}
