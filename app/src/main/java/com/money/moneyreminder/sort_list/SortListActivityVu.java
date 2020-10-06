package com.money.moneyreminder.sort_list;

import com.money.moneyreminder.sort_list.presenter.SortData;
import com.money.moneyreminder.sort_list.presenter.SortTypeData;

import java.util.ArrayList;

public interface SortListActivityVu {
    void setRecyclerView(SortData sortData);

    void closePage();

    void showProgress(boolean isShow);

    void showAddFragmentDialog();

    void showToast(String message);

    void saveSortType(SortTypeData sortTypeData, String describe);

    void showSecondSortDialog(String edContent);

    void showSecondSortRecyclerView(ArrayList<String> contentArray);

    void refreshView(int position);
}
