package com.money.moneyreminder.list_fragment;

import android.util.Log;

import com.money.moneyreminder.sort.MoneyData;
import com.money.moneyreminder.sort.MoneyObject;
import com.money.moneyreminder.tool.DataProvider;
import com.money.moneyreminder.tool.DateDTO;
import com.money.moneyreminder.tool.DateStringProvider;
import com.money.moneyreminder.tool.FirebaseHandler;
import com.money.moneyreminder.tool.FirebaseHandlerImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ListFragmentPresenterImpl implements ListFragmentPresenter {

    private ListFragmentVu mView;

    private FirebaseHandler firebaseHandler;

    private boolean isIncome;

    private SimpleDateFormat simpleDateFormat;

    private String currentMonth;

    public ListFragmentPresenterImpl(ListFragmentVu mView) {
        this.mView = mView;
        firebaseHandler = new FirebaseHandlerImpl();
        simpleDateFormat = new SimpleDateFormat("yyyy/MM",Locale.TAIWAN);
        currentMonth = simpleDateFormat.format(new Date(System.currentTimeMillis()));
    }

    @Override
    public void onActivityCreated(boolean isIncome) {
        this.isIncome = isIncome;
        mView.showProgress(true);
        DateStringProvider dateStringProvider = new DateStringProvider();
        dateStringProvider.execute();
        dateStringProvider.setOnDateStringProvideListener(onDateStringProvideListener);
//        firebaseHandler.getUserMoneyData(onGetUserMoneyDataListener);
    }

    private FirebaseHandler.OnFireStoreCatchListener<ArrayList<MoneyObject>> onGetUserMoneyDataListener = new FirebaseHandler.OnFireStoreCatchListener<ArrayList<MoneyObject>>() {
        @Override
        public void onSuccess(ArrayList<MoneyObject> dataArray) {
            Log.i("Michael","有資料 : "+dataArray.size());
            int incomeMoney = 0;
            int expenditure = 0;
            ArrayList<MoneyObject> moneyDataArrayList = new ArrayList<>();
            for (MoneyObject object : dataArray){
                String saveDate = simpleDateFormat.format(new Date(object.getTimeMiles()));
                if (saveDate.equals(currentMonth)){
                    incomeMoney += object.getInComeMoney();
                    expenditure += object.getExpenditureMoney();
                    moneyDataArrayList.add(object);
                }
            }
            mView.showTopIncomeData(incomeMoney,expenditure);
            mView.showSortTabLayout(moneyDataArrayList,isIncome);
        }

        @Override
        public void onFail(String errorCode) {
            Log.i("Michael","沒資料");
        }
    };

    @Override
    public void onAddMoneyButtonClickListener() {
        mView.intentToCalculatorActivity();
    }

    @Override
    public void onRadioMoneySortButtonClickListener(boolean isIncome) {
        this.isIncome = isIncome;
        firebaseHandler.getUserMoneyData(onGetUserMoneyDataListener);
    }

    @Override
    public void onTabSelectedListener(String month, String year) {
        currentMonth = year+"/"+month;
        Log.i("Michael","點擊後的日期 : "+currentMonth);
        firebaseHandler.getUserMoneyData(onGetUserMoneyDataListener);
    }

    private DateStringProvider.OnDateStringProvideListener onDateStringProvideListener = new DateStringProvider.OnDateStringProvideListener() {
        @Override
        public void onSuccess(ArrayList<DateDTO> dateString) {
            mView.showProgress(false);
            String currentYear = new SimpleDateFormat("yyyy", Locale.TAIWAN).format(new Date(System.currentTimeMillis()));

            String currentDate = new SimpleDateFormat("MM",Locale.TAIWAN).format(new Date(System.currentTimeMillis()));
            mView.showTabLayout(currentYear,currentDate,dateString);
        }
    };
}
