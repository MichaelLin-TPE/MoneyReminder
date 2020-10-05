package com.money.moneyreminder.user_fragment;

import java.util.ArrayList;

public interface UserFragmentVu {
    void setTitle(String title);

    void showRecyclerView(long budgetMoney, long totalExpenditure, int i, long monthMoney, ArrayList<String> accountItemArray);

    void showBudgetDialog();

    void showToast(String message);
}
