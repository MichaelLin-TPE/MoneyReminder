package com.money.moneyreminder.list_fragment.sort_fragment;

import android.util.Log;

import com.google.gson.Gson;
import com.money.moneyreminder.R;
import com.money.moneyreminder.list_fragment.SortComparatorForPicClass;
import com.money.moneyreminder.sort.MoneyData;
import com.money.moneyreminder.sort.MoneyObject;
import com.money.moneyreminder.sort_list.presenter.SortTypeData;
import com.money.moneyreminder.tool.MoneyReminderApplication;

import java.util.ArrayList;
import java.util.Collections;

public class SortFragmentPresenterImpl implements SortFragmentPresenter {

    private SortFragmentVu mView;

    private ArrayList<MoneyObject> moneyObjectArrayList;

    private boolean isIncome;

    public SortFragmentPresenterImpl(SortFragmentVu mView) {
        this.mView = mView;
    }

    @Override
    public void onActivityCreated(ArrayList<MoneyObject> moneyObjectArrayList, boolean isIncome) {

        if (moneyObjectArrayList == null || moneyObjectArrayList.isEmpty()){

            mView.showNoDataView(true);

            return;
        }

        this.moneyObjectArrayList = moneyObjectArrayList;
        this.isIncome = isIncome;
        mView.showNoDataView(false);

        int totalCaseCount = 0;

        for (MoneyObject object : moneyObjectArrayList){
            for (MoneyData data : object.getMoneyDataArrayList()){
                if (data.isIncome() == isIncome){
                    totalCaseCount++;
                }
            }
        }

        ArrayList<SortPercentData> sortTypeArray = new ArrayList<>();
        for (MoneyObject object :moneyObjectArrayList){
            for (MoneyData data : object.getMoneyDataArrayList()){
                if (data.isIncome() == isIncome){
                    if (sortTypeArray.size() == 0){
                        SortPercentData sortPercentData = new SortPercentData();
                        sortPercentData.setIconUrl(data.getSortTypeIcon());
                        sortPercentData.setNumberOfCase(1);
                        sortPercentData.setTitle(data.getSortType());
                        sortPercentData.setPercent(0);
                        sortPercentData.setTotalMoney(data.getTotalMoney());
                        sortTypeArray.add(sortPercentData);
                        continue;
                    }

                    boolean isDataRepeat = false;
                    for (SortPercentData sortType : sortTypeArray){
                        if (sortType.getTitle().equals(data.getSortType())){
                            sortType.setTotalMoney(sortType.getTotalMoney()+data.getTotalMoney());
                            sortType.setNumberOfCase(sortType.getNumberOfCase()+1);
                            float value = (float) sortType.getNumberOfCase() / totalCaseCount;
                            int math = (int) (value * 100);
                            sortType.setPercent(math);
                            isDataRepeat = true;
                            break;
                        }
                    }
                    if (!isDataRepeat){
                        SortPercentData sortPercentData = new SortPercentData();
                        sortPercentData.setIconUrl(data.getSortTypeIcon());
                        sortPercentData.setNumberOfCase(1);
                        sortPercentData.setTitle(data.getSortType());
                        sortPercentData.setPercent(0);
                        sortPercentData.setTotalMoney(data.getTotalMoney());
                        sortTypeArray.add(sortPercentData);
                    }
                }
            }
        }
        for (SortPercentData data : sortTypeArray){
            if (data.getNumberOfCase() == 1){
                float value = (float) data.getNumberOfCase() / totalCaseCount;
                int math = (int) (value * 100);
                data.setPercent(math);
            }
        }

        if (sortTypeArray.isEmpty()){
            mView.showNoDataView(true);
            return;
        }


        if (mView.getSortAnalysisType().equals(MoneyReminderApplication.getInstance().getApplicationContext().getString(R.string.long_analysis))){

            //排順序
            Collections.sort(sortTypeArray,new SortComparatorForPicClass());

            mView.showNoDataView(false);
            mView.changeView(false);

            mView.setRecyclerView(sortTypeArray,isIncome);

            return;
        }
        mView.changeView(true);
        mView.showNoDataView(false);
        mView.pieChart(sortTypeArray);





    }

    @Override
    public void onNumberOfCaseButtonClickListener(String sortType) {
        //
        ArrayList<MoneyData> moneyDataArrayList = new ArrayList<>();

        for (MoneyObject object : moneyObjectArrayList){

            if (object.getMoneyDataArrayList() == null || object.getMoneyDataArrayList().isEmpty()){
                continue;
            }
            for (MoneyData data : object.getMoneyDataArrayList()){
                if (data.isIncome() == isIncome && data.getSortType().equals(sortType)){
                    moneyDataArrayList.add(data);
                }
            }
        }
        mView.showDetailListDialog(sortType,moneyDataArrayList);

    }

    @Override
    public void onPieChatItemClickListener(String label) {
        ArrayList<MoneyData> moneyDataArrayList = new ArrayList<>();

        for (MoneyObject object : moneyObjectArrayList){

            if (object.getMoneyDataArrayList() == null || object.getMoneyDataArrayList().isEmpty()){
                continue;
            }
            for (MoneyData data : object.getMoneyDataArrayList()){
                if (data.isIncome() == isIncome && data.getSortType().equals(label)){
                    moneyDataArrayList.add(data);
                }
            }
        }
        mView.showDetailListDialog(label,moneyDataArrayList);
    }
}
