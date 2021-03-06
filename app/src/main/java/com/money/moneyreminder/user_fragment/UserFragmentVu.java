package com.money.moneyreminder.user_fragment;

import java.util.ArrayList;

public interface UserFragmentVu {
    void setTitle(String title);

    void showRecyclerView(long budgetMoney, long totalExpenditure, int i, long monthMoney, ArrayList<String> accountItemArray);

    void showBudgetDialog();

    void showToast(String message);

    void showProgressBar(boolean isShow);

    void showLogoutDialog();

    void intentToMainActivity();

    void showDataSortDialog();

    void saveSortType(String sortType);

    void showDayRangeDialog();

    void saveDayRange(String sortType);

    void showSortAnalysisDialog();

    void saveSortAnalysis(String sortType);

    void updateRecyclerView(ArrayList<String> accountSettingArray);
}
