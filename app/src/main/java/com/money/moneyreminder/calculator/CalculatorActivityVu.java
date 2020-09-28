package com.money.moneyreminder.calculator;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public interface CalculatorActivityVu {
    void showRecyclerView(ArrayList<String> numberArray, ArrayList<Drawable> mathArray);

    void setCalculatorContent(String number);

    void closePage();

    void clearView();

    String getTvContent();

    void setCalculatorContentEmpty();

    void intentToSortActivity(String tvContent, boolean isIncome);

    void upDateNumberList();

    void setCalculatorContentBack(String numberContent);

    void resetNumberList();

    void showToast(String message);
}
