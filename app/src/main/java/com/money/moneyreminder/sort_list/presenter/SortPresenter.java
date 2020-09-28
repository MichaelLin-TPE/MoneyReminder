package com.money.moneyreminder.sort_list.presenter;

public interface SortPresenter {
    void setData(SortData sortDataArray);

    int getItemPosition(int position);

    int getItemCount();

    void onBindCreateViewHolder(CreateViewHolder holder, int position);

    void onBindRecentlyViewHolder(RecentlyViewHolder holder,int position);

    void setOnSortTypeSelectListener(CreateViewHolder holder, CreateAdapter.OnSortTypeSelectListener createListener);

    void setOnSortTypeRecentlyListener(RecentlyViewHolder holder, RecentlyAdapter.OnSortTypeRecentlySelectListener recentlyListener);
}
