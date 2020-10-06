package com.money.moneyreminder.list_fragment;

import android.util.Log;

import com.money.moneyreminder.sort.MoneyData;
import com.money.moneyreminder.sort.MoneyObject;
import com.money.moneyreminder.tool.DataProvider;
import com.money.moneyreminder.tool.DateDTO;
import com.money.moneyreminder.tool.DateStringProvider;
import com.money.moneyreminder.tool.FirebaseHandler;
import com.money.moneyreminder.tool.FirebaseHandlerImpl;
import com.money.moneyreminder.tool.UserManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import static com.money.moneyreminder.dialog.SettingDayRangeDialogFragment.FIVE;
import static com.money.moneyreminder.dialog.SettingDayRangeDialogFragment.ONE;

public class ListFragmentPresenterImpl implements ListFragmentPresenter {

    private ListFragmentVu mView;

    private FirebaseHandler firebaseHandler;

    private boolean isIncome, isDelete;

    private SimpleDateFormat simpleDateFormat;

    private String currentMonth,firstDay,endDay;

    private ArrayList<MoneyData> removeDataArray;

    private ArrayList<MoneyObject> moneyArrayList;

    public ListFragmentPresenterImpl(ListFragmentVu mView) {
        this.mView = mView;
        removeDataArray = new ArrayList<>();
        firebaseHandler = new FirebaseHandlerImpl();
        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.TAIWAN);
        currentMonth = simpleDateFormat.format(new Date(System.currentTimeMillis()));
    }

    @Override
    public void onActivityCreated(boolean isIncome) {
        this.isIncome = isIncome;
        mView.showProgress(true);
        DateStringProvider dateStringProvider = new DateStringProvider();
        dateStringProvider.execute();
        dateStringProvider.setOnDateStringProvideListener(onDateStringProvideListener);
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

                long firstMiles = DataProvider.getInstance().getTimeMiles(firstDay);
                long endMiles = DataProvider.getInstance().getTimeMiles(endDay);
                Date cDate = new Date(object.getTimeMiles());
                Date firstDate = new Date(firstMiles);
                Date endDate = new Date(endMiles);
                if (cDate.after(firstDate) && cDate.before(endDate)) {
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

            int incomeMoney = 0;
            int expenditure = 0;
            mView.showTopIncomeData(incomeMoney, expenditure);
            mView.showSortTabLayout(null, isIncome, isDelete);

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
        firebaseHandler.getUserMoneyData(onGetUserMoneyDataListener);

    }

    @Override
    public void onRadioMoneySortButtonClickListener(boolean isIncome) {
        this.isIncome = isIncome;
        firebaseHandler.getUserMoneyData(onGetUserMoneyDataListener);
    }

    @Override
    public void onTabSelectedListener(String firstDay, String endDay) {
        this.firstDay = firstDay;
        this.endDay = endDay;
        currentMonth = new SimpleDateFormat("yyyy/MM/dd",Locale.TAIWAN).format(new Date(System.currentTimeMillis()));
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
            String currentTime = new SimpleDateFormat("yyyy/MM/dd", Locale.TAIWAN).format(new Date(System.currentTimeMillis()));
            int selectIndex = 0;
            if (UserManager.getInstance().getDayRange().equals(ONE)) {
                for (int i = 0; i < dateString.size(); i++) {
                    if (dateString.get(i).getYear().equals(currentYear) && dateString.get(i).getMonth().equals(currentDate)) {
                        selectIndex = i;
                    }
                }
                mView.showTabLayout(selectIndex, dateString);
                return;
            }
            //以下是五號開始或十號開始
            for (int i = 0; i < dateString.size(); i++) {
                String firstDay = dateString.get(i).getYear()+"/"+dateString.get(i).getFirstDateOfMonth();
                String endDay = dateString.get(i).getYear()+"/"+dateString.get(i).getEndDateOfMonth();
                long currentTimeMiles = DataProvider.getInstance().getTimeMiles(currentTime);
                long firstDayTimeMiles = DataProvider.getInstance().getTimeMiles(firstDay);
                long endDayTimeMiles = DataProvider.getInstance().getTimeMiles(endDay);
                Date cDate = new Date(currentTimeMiles);
                Date firstDate = new Date(firstDayTimeMiles);
                Date endDate = new Date(endDayTimeMiles);
                if (cDate.after(firstDate) && cDate.before(endDate)){
                    Log.i("Michael","現在日期："+currentTime+" , 第一天："+firstDay+" , 最後一天："+endDay);
                    selectIndex = i;
                    break;
                }
            }
            mView.showTabLayout(selectIndex, dateString);
        }
    };
}
