package com.money.moneyreminder.sort_list.presenter;

import android.util.Log;

import java.util.ArrayList;

public class SortPresenterImpl implements SortPresenter {

    public static final int RECENTLY = 0;

    public static final int CREATE_LIST = 1;

    private ArrayList<SortRecentlyData> recentlyArray;

    private ArrayList<SortCreateData> createArray;

    @Override
    public void setData(SortData sortData) {
        if (sortData == null){
            Log.i("Michael","sortData is null");
            return;
        }
        recentlyArray = sortData.getRecentlyData();
        createArray = sortData.getCreateData();
    }

    @Override
    public int getItemPosition(int position) {
        if (position == 0){
            return RECENTLY;
        }
        return CREATE_LIST;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public void onBindCreateViewHolder(CreateViewHolder holder, int position) {
        holder.setData(createArray);
    }

    @Override
    public void onBindRecentlyViewHolder(RecentlyViewHolder holder, int position) {
        holder.setData(recentlyArray);
    }

    @Override
    public void setOnSortTypeSelectListener(CreateViewHolder holder, CreateAdapter.OnSortTypeSelectListener createListener) {
        holder.setOnSortTypeSelectListener(createListener);
    }

    @Override
    public void setOnSortTypeRecentlyListener(RecentlyViewHolder holder, RecentlyAdapter.OnSortTypeRecentlySelectListener recentlyListener) {
        holder.setOnSortTypeRecentlyListener(recentlyListener);
    }
}
