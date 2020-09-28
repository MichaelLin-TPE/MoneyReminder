package com.money.moneyreminder.sort_list;

import com.money.moneyreminder.sort_list.presenter.SortCreateData;
import com.money.moneyreminder.sort_list.presenter.SortData;
import com.money.moneyreminder.sort_list.presenter.SortRecentlyData;
import com.money.moneyreminder.sort_list.presenter.SortTypeData;

public interface SortListActivityVu {
    void setRecyclerView(SortData sortData);

    void setEmptyRecyclerView();

    void closePage();

    void showProgress(boolean isShow);

    void showAddFragmentDialog();

    void showToast(String message);

    void saveSortType(SortTypeData sortTypeData);
}
