package com.money.moneyreminder.sort_list.presenter;

import android.util.Log;

import java.util.ArrayList;

public class SortPresenterImpl implements SortPresenter {

    public static final int RECENTLY = 0;

    public static final int CREATE_LIST = 1;

    public static final int SECOND_SORT_LIST = 2;

    private ArrayList<SortRecentlyData> recentlyArray;

    private ArrayList<SortCreateData> createArray;

    private ArrayList<String> contentArray;

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
    public void setSecondSortData(ArrayList<String> contentArray) {
        this.contentArray = contentArray;
    }

    @Override
    public void onBindSecondSortViewHolder(SecondSortViewHolder holder, int position) {
        holder.setData(contentArray);
    }

    @Override
    public void setOnDescriptionItemClickListener(SecondSortViewHolder holder, SecondSortContentAdapter.OnDescriptionItemClickListener listener) {
        holder.setOnDescriptionItemClickListener(listener);
    }

    @Override
    public void setOnAddIconClickListener(SecondSortViewHolder holder, SecondSortViewHolder.OnAddIconClickListener onAddIconClickListener) {
        holder.setOnAddIconClickListener(onAddIconClickListener);
    }

    @Override
    public int getItemPosition(int position) {
        if (position == 0){
            return RECENTLY;
        }
        if (position == 1){
            return CREATE_LIST;
        }

        return SECOND_SORT_LIST;
    }

    @Override
    public int getItemCount() {

        return 3;
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
