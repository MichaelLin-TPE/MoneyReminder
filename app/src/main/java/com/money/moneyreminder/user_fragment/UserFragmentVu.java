package com.money.moneyreminder.user_fragment;

public interface UserFragmentVu {
    void setTitle(String title);

    void showRecyclerView(long budgetMoney, long totalExpenditure, int i, long monthMoney);

    void showBudgetDialog();

    void showToast(String message);
}
