package com.money.moneyreminder.user_fragment;

import android.util.Log;

import com.money.moneyreminder.sort.MoneyObject;
import com.money.moneyreminder.tool.DataProvider;
import com.money.moneyreminder.tool.FirebaseHandler;
import com.money.moneyreminder.tool.FirebaseHandlerImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class UserFragmentPresenterImpl implements UserFragmentPresenter {

    private UserFragmentVu mView;

    private FirebaseHandler firebaseHandler;

    private long budgetMoney,totalExpenditure,monthMoney;

    private float expenditurePercent;

    private String currentMonth,currentYear,currentTime;

    public UserFragmentPresenterImpl(UserFragmentVu mView) {
        this.mView = mView;
        firebaseHandler = new FirebaseHandlerImpl();
    }

    @Override
    public void onActivityCreated() {
        currentYear = new SimpleDateFormat("yyyy", Locale.TAIWAN).format(new Date(System.currentTimeMillis()));
        currentMonth = new SimpleDateFormat("MM", Locale.TAIWAN).format(new Date(System.currentTimeMillis()));
        currentTime = currentYear+"/"+currentMonth;
        mView.setTitle(firebaseHandler.getUserDisplayName());
        firebaseHandler.getBudgetMoney(onCatchBudgetListener);
    }

    @Override
    public void onSettingButtonClickListener() {
        mView.showBudgetDialog();
    }

    @Override
    public void onSetBudgetMoneyConfirmClickListener(String budgetMoney) {
        if (budgetMoney.isEmpty()){
            mView.showToast("請輸入金額");
            return;
        }
        int budget = Integer.parseInt(budgetMoney);
        firebaseHandler.saveUserBudgetMoney(budget);
        firebaseHandler.getBudgetMoney(onCatchBudgetListener);
    }

    private FirebaseHandler.OnFireStoreCatchListener<Long> onCatchBudgetListener = new FirebaseHandler.OnFireStoreCatchListener<Long>() {
        @Override
        public void onSuccess(Long budgetMoney) {
            UserFragmentPresenterImpl.this.budgetMoney = budgetMoney;
            firebaseHandler.getUserMoneyData(onCatchUserMoneyData);
        }

        @Override
        public void onFail(String errorCode) {
            budgetMoney = 0;
            firebaseHandler.getUserMoneyData(onCatchUserMoneyData);
        }
    };

    private FirebaseHandler.OnFireStoreCatchListener<ArrayList<MoneyObject>> onCatchUserMoneyData = new FirebaseHandler.OnFireStoreCatchListener<ArrayList<MoneyObject>>() {
        @Override
        public void onSuccess(ArrayList<MoneyObject> userDataArray) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM",Locale.TAIWAN);
            totalExpenditure = 0;
            expenditurePercent = 0;
            monthMoney = 0;
            for (MoneyObject object : userDataArray){
                String dataTime = sdf.format(new Date(object.getTimeMiles()));
                if (dataTime.equals(currentTime)){
                    totalExpenditure += object.getExpenditureMoney();
                }
            }

            if (budgetMoney <= 0){
                expenditurePercent = 0;
            }else {
                expenditurePercent = (float)totalExpenditure / budgetMoney;
            }
            int monthMaxDay = DataProvider.getInstance().getMonthMaxDay(currentMonth,currentYear);
            monthMoney = (budgetMoney - totalExpenditure) / monthMaxDay;
            if (monthMoney < 0){
                monthMoney = 0;
            }
            if (expenditurePercent <= 0){
                expenditurePercent = 0;
            }
            Log.i("Michael","達成率 : "+expenditurePercent);
            expenditurePercent = expenditurePercent * 100;
            mView.showRecyclerView(budgetMoney,totalExpenditure,(int)expenditurePercent,monthMoney,DataProvider.getInstance().getAccountItemArray());
        }

        @Override
        public void onFail(String errorCode) {
            mView.showRecyclerView(budgetMoney,totalExpenditure,(int)expenditurePercent,monthMoney,DataProvider.getInstance().getAccountItemArray());
        }
    };
}
