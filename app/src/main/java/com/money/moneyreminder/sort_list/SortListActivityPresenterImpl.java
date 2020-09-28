package com.money.moneyreminder.sort_list;

import com.money.moneyreminder.sort_list.presenter.SortCreateData;
import com.money.moneyreminder.sort_list.presenter.SortData;
import com.money.moneyreminder.sort_list.presenter.SortRecentlyData;
import com.money.moneyreminder.sort_list.presenter.SortTypeData;
import com.money.moneyreminder.tool.FirebaseHandler;
import com.money.moneyreminder.tool.FirebaseHandlerImpl;

public class SortListActivityPresenterImpl implements SortListActivityPresenter {

    private SortListActivityVu mView;

    private FirebaseHandler firebaseHandler;

    private SortCreateData sortCreateData;

    private SortRecentlyData sortRecentlyData;

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
    }

    @Override
    public void onSortTypeSelectListener(SortCreateData data) {
        this.sortCreateData = data;
    }

    @Override
    public void onSortTypeRecentlyListener(SortRecentlyData data) {
        this.sortRecentlyData = data;
    }

    @Override
    public void onSaveButtonClickListener() {
        if (sortRecentlyData == null && sortCreateData == null){
            mView.showToast("請選擇一個分類");
            return;
        }
        if (sortCreateData == null){
            SortTypeData data = new SortTypeData();
            data.setIconUrl(sortRecentlyData.getIconUrl());
            data.setSortType(sortRecentlyData.getSortType());
            mView.saveSortType(data);
            return;
        }
        SortTypeData data = new SortTypeData();
        data.setIconUrl(sortCreateData.getIconUrl());
        data.setSortType(sortCreateData.getSortType());
        mView.saveSortType(data);

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
            mView.setEmptyRecyclerView();
        }
    };
}
