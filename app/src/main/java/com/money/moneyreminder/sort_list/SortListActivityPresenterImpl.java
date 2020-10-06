package com.money.moneyreminder.sort_list;

import com.money.moneyreminder.sort_list.presenter.SortCreateData;
import com.money.moneyreminder.sort_list.presenter.SortData;
import com.money.moneyreminder.sort_list.presenter.SortRecentlyData;
import com.money.moneyreminder.sort_list.presenter.SortTypeData;
import com.money.moneyreminder.tool.FirebaseHandler;
import com.money.moneyreminder.tool.FirebaseHandlerImpl;
import com.money.moneyreminder.dialog.SecondSortData;

import java.util.ArrayList;

public class SortListActivityPresenterImpl implements SortListActivityPresenter {

    private SortListActivityVu mView;

    private FirebaseHandler firebaseHandler;

    private SortCreateData sortCreateData;

    private SortRecentlyData sortRecentlyData;

    private String describe;

    private static final int CREATE = 1;

    private static final int RECENTLY = 0;

    public SortListActivityPresenterImpl(SortListActivityVu mView) {
        this.mView = mView;
        firebaseHandler = new FirebaseHandlerImpl();
    }

    @Override
    public void onActivityCreate() {
        firebaseHandler.getUserSortData(onFireStoreCatchListener);
    }

    @Override
    public void onAddButtonClickListener() {
        mView.showAddFragmentDialog();
    }

    @Override
    public void onBackButtonClickListener() {
        mView.closePage();
    }

    @Override
    public void onSortTypeConfirmClickListener(String edContent, String iconUrl) {
        firebaseHandler.setPersonalSortType(edContent,iconUrl);
        mView.showSecondSortDialog(edContent);
    }

    @Override
    public void onSortTypeSelectListener(SortCreateData data) {
        this.sortCreateData = data;
        sortRecentlyData = null;
        mView.refreshView(RECENTLY);
        firebaseHandler.getSecondSortArray(onCatchSecondSortArrayListener);
    }

    private FirebaseHandler.OnFireStoreCatchListener<ArrayList<SecondSortData>> onCatchSecondSortArrayListener = new FirebaseHandler.OnFireStoreCatchListener<ArrayList<SecondSortData>>() {
        @Override
        public void onSuccess(ArrayList<SecondSortData> secondSortDataArrayList) {
            ArrayList<String> contentArray = new ArrayList<>();
            for (SecondSortData data : secondSortDataArrayList){
                if (sortCreateData != null){
                    if (data.getSortTitle().equals(sortCreateData.getSortType())){
                        contentArray.addAll(data.getContentArray());
                    }
                    continue;
                }
                if (sortRecentlyData != null){
                    if (data.getSortTitle().equals(sortRecentlyData.getSortType())){
                        contentArray.addAll(data.getContentArray());
                    }
                }
            }
            mView.showSecondSortRecyclerView(contentArray);
        }

        @Override
        public void onFail(String errorCode) {
            mView.showSecondSortRecyclerView(null);
        }
    };

    @Override
    public void onSortTypeRecentlyListener(SortRecentlyData data) {
        this.sortRecentlyData = data;
        sortCreateData = null;
        mView.refreshView(CREATE);
        firebaseHandler.getSecondSortArray(onCatchSecondSortArrayListener);
    }

    @Override
    public void onSaveButtonClickListener() {

        if (describe == null){
            describe = "";
        }

        if (sortRecentlyData == null && sortCreateData == null){
            mView.showToast("請選擇一個分類");
            return;
        }
        //當使用主分類的資料是空的時候做這
        if (sortCreateData == null){
            SortTypeData data = new SortTypeData();
            data.setIconUrl(sortRecentlyData.getIconUrl());
            data.setSortType(sortRecentlyData.getSortType());
            mView.saveSortType(data,describe);
            return;
        }

        //當最近使用的資料是空的時候做這
        SortTypeData data = new SortTypeData();
        data.setIconUrl(sortCreateData.getIconUrl());
        data.setSortType(sortCreateData.getSortType());
        firebaseHandler.setPersonalRecentlySortType(sortCreateData);
        mView.saveSortType(data,describe);

    }

    @Override
    public void onSecondSortSaveButtonClickListener(ArrayList<String> secondSortContentArray, String sortTitle) {
        firebaseHandler.saveUserSecondSortData(secondSortContentArray,sortTitle);
        firebaseHandler.getSecondSortArray(onCatchSecondSortArrayListener);
    }

    @Override
    public void onDescriptionItemClickListener(String content) {
        this.describe = content;
    }

    @Override
    public void onAddIconClickListener() {
        String sortTitle;
        if (sortCreateData == null && sortRecentlyData == null){
            mView.showToast("請先選擇主分類");
            return;
        }
        sortTitle = sortCreateData == null ? sortRecentlyData.getSortType() : sortCreateData.getSortType();

        mView.showSecondSortDialog(sortTitle);
    }

    private FirebaseHandler.OnFireStoreCatchListener<SortData> onFireStoreCatchListener = new FirebaseHandler.OnFireStoreCatchListener<SortData>() {
        @Override
        public void onSuccess(SortData sortData) {
            mView.showProgress(false);
            mView.setRecyclerView(sortData);
        }

        @Override
        public void onFail(String errorCode) {
            mView.showProgress(false);
            mView.setRecyclerView(null);
        }
    };
}
