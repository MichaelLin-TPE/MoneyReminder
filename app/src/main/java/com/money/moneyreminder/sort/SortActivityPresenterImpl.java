package com.money.moneyreminder.sort;

import android.util.Log;

import com.money.moneyreminder.sort_list.presenter.SortTypeData;
import com.money.moneyreminder.tool.DataProvider;
import com.money.moneyreminder.tool.FirebaseHandler;
import com.money.moneyreminder.tool.FirebaseHandlerImpl;
import com.money.moneyreminder.tool.MichaelLog;

import java.util.ArrayList;

public class SortActivityPresenterImpl implements SortActivityPresenter {

    private SortActivityVu mView;

    private long choiceTimeMiles;

    private SortTypeData sortTypeData;

    private int totalMoney;

    private FirebaseHandler firebaseHandler;

    private boolean isIncome;

    private String currentDate;

    public SortActivityPresenterImpl(SortActivityVu mView) {
        this.mView = mView;
        firebaseHandler = new FirebaseHandlerImpl();
    }

    @Override
    public void onActivityCreate(int totalMoney, boolean isIncome,String currentDate) {
        this.isIncome = isIncome;
        this.totalMoney = totalMoney;
        this.currentDate = currentDate;

        if (currentDate == null || currentDate.isEmpty()){
            String currentTime = DataProvider.getInstance().getCurrentTime();
            mView.setCurrentTime(currentTime);
            firebaseHandler.getUserDescribeData(onFireStoreCatchListener);
            return;
        }
        mView.setCurrentTime(currentDate);
        firebaseHandler.getUserDescribeData(onFireStoreCatchListener);

    }

    private FirebaseHandler.OnFireStoreCatchListener<ArrayList<String>> onFireStoreCatchListener = new FirebaseHandler.OnFireStoreCatchListener<ArrayList<String>>() {
        @Override
        public void onSuccess(ArrayList<String> describeArray) {
            MichaelLog.i("有描述資料");
            mView.showTvNoData(false);
            mView.setDescribeRecyclerView(describeArray);
        }

        @Override
        public void onFail(String errorCode) {
            MichaelLog.i("無描述資料");
            mView.showTvNoData(true);
            mView.setDescribeRecyclerView(null);
        }
    };

    @Override
    public void setOnSortAreaClickListener() {
        mView.intentToSortListActivity();
    }

    @Override
    public void setOnDateAreaClickListener() {
        mView.showDatePicker();
    }

    @Override
    public void onDateConfirmClickListener(String choiceTime) {

        MichaelLog.i("我選擇的時間 : "+choiceTime);

        if (choiceTime == null || choiceTime.isEmpty()){
            String currentTime = DataProvider.getInstance().getCurrentTime();
            choiceTimeMiles = DataProvider.getInstance().getTimeMiles(currentTime);
            mView.setCurrentTime(currentTime);
        }else {
            choiceTimeMiles = DataProvider.getInstance().getTimeMiles(choiceTime);
            mView.setCurrentTime(choiceTime);
        }
    }



    @Override
    public void onBackButtonClickListener() {
        mView.closePage();
    }

    @Override
    public void onSaveButtonClickListener() {
        if (sortTypeData == null){
            mView.showToast("請選取您要的分類");
            return;
        }

        if (currentDate == null || currentDate.isEmpty()){
            if (choiceTimeMiles == 0){
                choiceTimeMiles = System.currentTimeMillis();
                saveUserMoneyData();
                return;
            }
            saveUserMoneyData();
            return;
        }
        choiceTimeMiles = DataProvider.getInstance().getTimeMiles(currentDate);
        saveUserMoneyData();

    }

    private void saveUserMoneyData(){
        String description = mView.getDescription();

        if (description == null){
            description = "";
        }

        firebaseHandler.saveUserMoneyList(sortTypeData,choiceTimeMiles,totalMoney,isIncome,description);
        firebaseHandler.saveUserDescription(description);

        mView.closePage();
    }

    @Override
    public void onCatchSortTypeData(SortTypeData sortTypeData, String describe) {
        this.sortTypeData = sortTypeData;
        mView.showSortType(sortTypeData);
        mView.setDescribe(describe);
    }

    @Override
    public void onDescribeItemClickListener(String describe) {
        mView.setDescribe(describe);
    }
}
