package com.money.moneyreminder.sort;

import android.util.Log;

import com.money.moneyreminder.sort_list.presenter.SortTypeData;
import com.money.moneyreminder.tool.DataProvider;
import com.money.moneyreminder.tool.FirebaseHandler;
import com.money.moneyreminder.tool.FirebaseHandlerImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SortActivityPresenterImpl implements SortActivityPresenter {

    private SortActivityVu mView;

    private long choiceTimeMiles;

    private SortTypeData sortTypeData;

    private int totalMoney;

    private FirebaseHandler firebaseHandler;

    private boolean isIncome;

    public SortActivityPresenterImpl(SortActivityVu mView) {
        this.mView = mView;
        firebaseHandler = new FirebaseHandlerImpl();
    }

    @Override
    public void onActivityCreate(int totalMoney, boolean isIncome) {
        this.isIncome = isIncome;
        this.totalMoney = totalMoney;
        String currentTime = DataProvider.getInstance().getCurrentTime();
        mView.setCurrentTime(currentTime);
    }

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

        Log.i("Michael","我選擇的時間 : "+choiceTime);

        if (choiceTime == null || choiceTime.isEmpty()){
            String currentTime = DataProvider.getInstance().getCurrentTime();
            choiceTimeMiles = getTimeMiles(currentTime);
            mView.setCurrentTime(currentTime);
        }else {
            choiceTimeMiles = getTimeMiles(choiceTime);
            mView.setCurrentTime(choiceTime);
        }
    }

    private long getTimeMiles(String currentTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyy/MM/dd", Locale.TAIWAN);
        try{
            Date date = sdf.parse(currentTime);
            if (date == null){
                return 0;
            }
            return date.getTime();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
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
        if (choiceTimeMiles == 0){
            choiceTimeMiles = System.currentTimeMillis();
        }
        String description = mView.getDescription();

        if (description == null){
            description = "";
        }

        firebaseHandler.saveUserMoneyList(sortTypeData,choiceTimeMiles,totalMoney,isIncome,description);
        mView.closePage();
    }

    @Override
    public void onCatchSortTypeData(SortTypeData sortTypeData) {
        this.sortTypeData = sortTypeData;
        mView.showSortType(sortTypeData);
    }
}
