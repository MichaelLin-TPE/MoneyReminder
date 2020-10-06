package com.money.moneyreminder.list_fragment;

import com.money.moneyreminder.sort.MoneyObject;
import com.money.moneyreminder.tool.DateDTO;

import java.util.ArrayList;

public interface ListFragmentVu {
    void showTabLayout(int selectIndex, ArrayList<DateDTO> dateString);

    void showProgress(boolean isShow);

    void showSortTabLayout(ArrayList<MoneyObject> moneyDataArrayList, boolean isIncome, boolean isDelete);

    void intentToCalculatorActivity();

    void showTopIncomeData(int incomeMoney, int expenditure);

    void changeFloatingButtonView(boolean isChange);

    void resetFloatingButton();

    void showErrorCode(String message);
}
