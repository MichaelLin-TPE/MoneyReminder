package com.money.moneyreminder.list_fragment;

import android.util.Log;

import com.money.moneyreminder.sort.MoneyData;
import com.money.moneyreminder.sort.MoneyObject;
import com.money.moneyreminder.tool.DateDTO;
import com.money.moneyreminder.tool.DateStringProvider;
import com.money.moneyreminder.tool.FirebaseHandler;
import com.money.moneyreminder.tool.FirebaseHandlerImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class ListFragmentPresenterImpl implements ListFragmentPresenter {

    private ListFragmentVu mView;

    private FirebaseHandler firebaseHandler;

    private boolean isIncome, isDelete;

    private SimpleDateFormat simpleDateFormat;

    private String currentMonth;

    private ArrayList<MoneyData> removeDataArray;

    private ArrayList<MoneyObject> moneyArrayList;

    public ListFragmentPresenterImpl(ListFragmentVu mView) {
        this.mView = mView;
        removeDataArray = new ArrayList<>();
        firebaseHandler = new FirebaseHandlerImpl();
        simpleDateFormat = new SimpleDateFormat("yyyy/MM", Locale.TAIWAN);
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
            moneyArrayList = dataArray;
            Log.i("Michael", "有資料 : " + dataArray.size());
            int incomeMoney = 0;
            int expenditure = 0;
            ArrayList<MoneyObject> moneyDataArrayList = new ArrayList<>();
            for (MoneyObject object : dataArray) {
                String saveDate = simpleDateFormat.format(new Date(object.getTimeMiles()));
                if (saveDate.equals(currentMonth)) {
                    incomeMoney += object.getInComeMoney();
                    expenditure += object.getExpenditureMoney();
                    moneyDataArrayList.add(object);
                }
            }

            mView.showTopIncomeData(incomeMoney, expenditure);
            mView.showSortTabLayout(moneyDataArrayList, isIncome, isDelete);
        }

        @Override
        public void onFail(String errorCode) {
            Log.i("Michael", "沒資料");
        }
    };

    @Override
    public void onAddMoneyButtonClickListener(boolean isEditMode) {
        if (!isEditMode) {
            mView.intentToCalculatorActivity();
            return;
        }

        if (!isDelete) {
            Log.i("Michael", "更新ViewPagerAdapter");
            mView.resetFloatingButton();
            firebaseHandler.getUserMoneyData(onGetUserMoneyDataListener);
            return;
        }
        //這邊做刪除
        if (moneyArrayList == null || moneyArrayList.isEmpty() || removeDataArray.isEmpty()) {
            mView.resetFloatingButton();
            firebaseHandler.getUserMoneyData(onGetUserMoneyDataListener);
            return;
        }

        for (MoneyObject moneyObject : moneyArrayList) {
            for (MoneyData moneyData : removeDataArray) {
                Iterator<MoneyData> moneyDataIterator = moneyObject.getMoneyDataArrayList().iterator();
                while (moneyDataIterator.hasNext()) {
                    MoneyData data = moneyDataIterator.next();
                    if (data.getTimeMiles() == moneyData.getTimeMiles()
                            && data.getTotalMoney() == moneyData.getTotalMoney()
                            && data.getSortType().equals(moneyData.getSortType())
                            && data.getDescription().equals(moneyData.getDescription())) {
                        Log.i("Michael", "會刪除的資料有 ： 類別：" + data.getSortType() + " , money : " + data.getTotalMoney());
                        moneyDataIterator.remove();
                    }
                }
            }
        }



        removeDataArray = new ArrayList<>();
        isDelete = true;
        mView.resetFloatingButton();
        firebaseHandler.updateUserMoneyList(moneyArrayList);

    }

    @Override
    public void onRadioMoneySortButtonClickListener(boolean isIncome) {
        this.isIncome = isIncome;
        firebaseHandler.getUserMoneyData(onGetUserMoneyDataListener);
    }

    @Override
    public void onTabSelectedListener(String month, String year) {
        currentMonth = year + "/" + month;
        firebaseHandler.getUserMoneyData(onGetUserMoneyDataListener);
    }

    @Override
    public void onDetailItemCheckBoxCheckListener(MoneyData data, boolean isChecked) {
        if (isChecked) {
            isDelete = true;
            removeDataArray.add(data);
            mView.changeFloatingButtonView(isDelete);

            Log.i("Michael", "removeDataArray size : " + removeDataArray.size());
            return;
        }
        if (removeDataArray.size() == 0) {
            return;
        }
        for (MoneyData moneyData : removeDataArray) {
            if (moneyData.getSortType().equals(data.getSortType()) && moneyData.getTimeMiles() == moneyData.getTimeMiles() && moneyData.getTotalMoney() == moneyData.getTotalMoney()) {
                removeDataArray.remove(moneyData);
                break;
            }
        }
        if (removeDataArray.size() == 0) {
            isDelete = false;
            mView.changeFloatingButtonView(false);
        }

        Log.i("Michael", "刪除完的 removeDataArray size : " + removeDataArray.size());
    }

    @Override
    public void onDetailItemLongPressListener() {
        mView.changeFloatingButtonView(false);
    }

    @Override
    public void onCatchErrorCode(String message) {
        mView.showErrorCode(message);
    }


    private DateStringProvider.OnDateStringProvideListener onDateStringProvideListener = new DateStringProvider.OnDateStringProvideListener() {
        @Override
        public void onSuccess(ArrayList<DateDTO> dateString) {
            mView.showProgress(false);
            String currentYear = new SimpleDateFormat("yyyy", Locale.TAIWAN).format(new Date(System.currentTimeMillis()));
            String currentDate = new SimpleDateFormat("MM", Locale.TAIWAN).format(new Date(System.currentTimeMillis()));
            mView.showTabLayout(currentYear, currentDate, dateString);
        }
    };
}
