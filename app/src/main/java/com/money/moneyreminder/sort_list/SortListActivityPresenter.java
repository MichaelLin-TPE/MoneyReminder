package com.money.moneyreminder.sort_list;

import com.money.moneyreminder.sort_list.presenter.SortCreateData;
import com.money.moneyreminder.sort_list.presenter.SortRecentlyData;

public interface SortListActivityPresenter {
    void onActivityCreate();

    void onAddButtonClickListener();

    void onBackButtonClickListener();

    void onSortTypeConfirmClickListener(String edContent, String iconUrl);

    void onSortTypeSelectListener(SortCreateData data);

    void onSortTypeRecentlyListener(SortRecentlyData data);

    void onSaveButtonClickListener();
}
