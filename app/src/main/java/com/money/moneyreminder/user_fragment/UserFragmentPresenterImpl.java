package com.money.moneyreminder.user_fragment;

import android.util.Log;

import com.money.moneyreminder.LoginHandler;
import com.money.moneyreminder.LoginHandlerImpl;
import com.money.moneyreminder.sort.MoneyObject;
import com.money.moneyreminder.tool.DataProvider;
import com.money.moneyreminder.tool.FirebaseHandler;
import com.money.moneyreminder.tool.FirebaseHandlerImpl;
import com.money.moneyreminder.tool.MichaelLog;
import com.money.moneyreminder.tool.UserManager;

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

    private static final String DATE_RANGE = "設定日期區間";

    private static final String LOGOUT = "登出";

    private static final String DATA_SORT = "設定資料排序";

    private static final String SORT_ANALYSIS = "設定分析圖";

    private LoginHandler loginHandler;

    public UserFragmentPresenterImpl(UserFragmentVu mView) {
        this.mView = mView;
        firebaseHandler = new FirebaseHandlerImpl();
        loginHandler = new LoginHandlerImpl();
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
        ArrayList<String> accountSettingArray = new ArrayList<>();
        accountSettingArray.add(UserManager.getInstance().getDayRange());
        accountSettingArray.add(UserManager.getInstance().getSortType());
        accountSettingArray.add(UserManager.getInstance().getSortAnalysisType());
        mView.updateRecyclerView(accountSettingArray);
        firebaseHandler.saveUserBudgetMoney(budget);
        firebaseHandler.getBudgetMoney(onCatchBudgetListener);
    }

    @Override
    public void onAccountItemClickListener(String itemName) {
        MichaelLog.i("點擊了什麼按鈕："+itemName);
        switch (itemName){
            case DATE_RANGE:
                mView.showDayRangeDialog();
                break;
            case DATA_SORT:
                mView.showDataSortDialog();
                break;
            case SORT_ANALYSIS:
                mView.showSortAnalysisDialog();
                break;
            case LOGOUT:
                mView.showLogoutDialog();
                break;
        }
    }

    @Override
    public void onLogoutConfirmClickListener() {
        loginHandler.onDoLogOut(onGoogleLogoutListener);
    }

    @Override
    public void onDataSortClickListener(String sortType) {
        MichaelLog.i("選擇了什麼排序："+sortType);
        mView.saveSortType(sortType);
        ArrayList<String> accountSettingArray = new ArrayList<>();
        accountSettingArray.add(UserManager.getInstance().getDayRange());
        accountSettingArray.add(UserManager.getInstance().getSortType());
        accountSettingArray.add(UserManager.getInstance().getSortAnalysisType());
        mView.updateRecyclerView(accountSettingArray);
    }

    @Override
    public void onDayRangeButtonClickListener(String sortType) {
        MichaelLog.i("選擇了什麼區間："+sortType);
        mView.saveDayRange(sortType);
        ArrayList<String> accountSettingArray = new ArrayList<>();
        accountSettingArray.add(UserManager.getInstance().getDayRange());
        accountSettingArray.add(UserManager.getInstance().getSortType());
        accountSettingArray.add(UserManager.getInstance().getSortAnalysisType());
        mView.updateRecyclerView(accountSettingArray);
    }

    @Override
    public void onSortAnalysisButtonClickListener(String sortType) {
        mView.saveSortAnalysis(sortType);
        ArrayList<String> accountSettingArray = new ArrayList<>();
        accountSettingArray.add(UserManager.getInstance().getDayRange());
        accountSettingArray.add(UserManager.getInstance().getSortType());
        accountSettingArray.add(UserManager.getInstance().getSortAnalysisType());
        mView.updateRecyclerView(accountSettingArray);
    }

    private LoginHandler.OnGoogleLogoutListener onGoogleLogoutListener = new LoginHandler.OnGoogleLogoutListener() {
        @Override
        public void onLogoutSuccess() {
            mView.intentToMainActivity();
        }
    };

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
            mView.showProgressBar(false);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM",Locale.TAIWAN);
            SimpleDateFormat dayFormat = new SimpleDateFormat("dd",Locale.TAIWAN);
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
            int currentDay = Integer.parseInt(dayFormat.format(new Date(System.currentTimeMillis())));
            int restOfDay = monthMaxDay - currentDay;
            monthMoney = (budgetMoney - totalExpenditure) / restOfDay;
            if (monthMoney < 0){
                monthMoney = 0;
            }
            if (expenditurePercent <= 0){
                expenditurePercent = 0;
            }
            MichaelLog.i("達成率 : "+expenditurePercent);
            expenditurePercent = expenditurePercent * 100;
            mView.showRecyclerView(budgetMoney,totalExpenditure,(int)expenditurePercent,monthMoney,DataProvider.getInstance().getAccountItemArray());
        }

        @Override
        public void onFail(String errorCode) {
            totalExpenditure = 0;
            expenditurePercent = 0;
            monthMoney = 0;
            mView.showProgressBar(false);
            mView.showRecyclerView(budgetMoney,totalExpenditure,(int)expenditurePercent,monthMoney,DataProvider.getInstance().getAccountItemArray());
        }
    };
}
