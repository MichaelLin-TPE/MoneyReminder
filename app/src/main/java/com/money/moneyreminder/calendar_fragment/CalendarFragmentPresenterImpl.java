package com.money.moneyreminder.calendar_fragment;

import android.util.Log;

import com.money.moneyreminder.sort.MoneyObject;
import com.money.moneyreminder.tool.DataProvider;
import com.money.moneyreminder.tool.FirebaseHandler;
import com.money.moneyreminder.tool.FirebaseHandlerImpl;
import com.money.moneyreminder.tool.MichaelLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CalendarFragmentPresenterImpl implements CalendarFragmentPresenter{

    private CalendarFragmentVu mView;

    private int currentYear , currentMonth;

    private FirebaseHandler firebaseHandler;

    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.TAIWAN);
    private SimpleDateFormat monthFormat = new SimpleDateFormat("MM",Locale.TAIWAN);

    public CalendarFragmentPresenterImpl(CalendarFragmentVu mView) {
        this.mView = mView;
        firebaseHandler = new FirebaseHandlerImpl();
    }

    @Override
    public void onActivityCreated()  {
        currentYear = Integer.parseInt(yearFormat.format(new Date(System.currentTimeMillis())));
        currentMonth = Integer.parseInt(monthFormat.format(new Date(System.currentTimeMillis())));
        if (DataProvider.getInstance().getDateList(currentYear,currentMonth) == null){
            return;
        }
        firebaseHandler.getUserMoneyData(onFireStoreCatchListener);
    }

    @Override
    public void onLeftArrowClickListener() {
        currentMonth --;
        if (currentMonth <= 0){
            currentMonth = 12;
            currentYear --;
        }
        firebaseHandler.getUserMoneyData(onFireStoreCatchListener);
    }

    @Override
    public void onRightArrowClickListener() {
        currentMonth ++;
        if (currentMonth >= 13){
            currentMonth = 1;
            currentYear ++;
        }
        firebaseHandler.getUserMoneyData(onFireStoreCatchListener);
    }

    @Override
    public void onCalendarItemClickListener(String date) {

        String month;
        if (currentMonth < 10){
            month = "0"+currentMonth;
        }else {
            month = currentMonth+"";
        }

        String currentTime = currentYear+"/"+month+"/"+date;
        mView.intentToCalculatorActivity(currentTime);
    }

    private FirebaseHandler.OnFireStoreCatchListener<ArrayList<MoneyObject>> onFireStoreCatchListener = new FirebaseHandler.OnFireStoreCatchListener<ArrayList<MoneyObject>>() {
        @Override
        public void onSuccess(ArrayList<MoneyObject> moneyDateArray) {
            String currentDate;
            if (currentMonth < 10){
                currentDate = currentYear+"/0"+currentMonth;
            }else {
                currentDate = currentYear+"/"+currentMonth;
            }
            mView.setTime(currentDate);
            MichaelLog.i("currentDate : "+currentDate);
            ArrayList<MoneyObject> dataArray = new ArrayList<>();
            for (MoneyObject data : moneyDateArray){
                String dataTime = DataProvider.getInstance().getYearAndDate(data.getTimeMiles());
                if (dataTime.equals(currentDate)){
                    dataArray.add(data);
                }
            }
            mView.showCalendarView(DataProvider.getInstance().getDateList(currentYear,currentMonth),dataArray);
        }

        @Override
        public void onFail(String errorCode) {
            String currentDate;
            if (currentMonth < 10){
                currentDate = currentYear+"/0"+currentMonth;
            }else {
                currentDate = currentYear+"/"+currentMonth;
            }
            mView.setTime(currentDate);
            mView.showCalendarView(DataProvider.getInstance().getDateList(currentYear,currentMonth),null);
        }
    };
}
