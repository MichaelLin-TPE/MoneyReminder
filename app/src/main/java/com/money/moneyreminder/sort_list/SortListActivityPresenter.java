package com.money.moneyreminder.sort_list;

import com.money.moneyreminder.sort_list.presenter.SortCreateData;
import com.money.moneyreminder.sort_list.presenter.SortRecentlyData;

import java.util.ArrayList;

public interface SortListActivityPresenter {
    void onActivityCreate();

    void onAddButtonClickListener();

    void onBackButtonClickListener();

    void onSortTypeConfirmClickListener(String edContent, String iconUrl);

    void onSortTypeSelectListener(SortCreateData data);

    void onSortTypeRecentlyListener(SortRecentlyData data);

    void onSaveButtonClickListener();

    void onSecondSortSaveButtonClickListener(ArrayList<String> secondSortContentArray, String sortTitle);

    void onDescriptionItemClickListener(String content);

    void onAddIconClickListener();
}
