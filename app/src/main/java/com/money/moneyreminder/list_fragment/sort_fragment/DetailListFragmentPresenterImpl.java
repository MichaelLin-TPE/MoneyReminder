package com.money.moneyreminder.list_fragment.sort_fragment;

import com.money.moneyreminder.sort.MoneyData;
import com.money.moneyreminder.sort.MoneyObject;
import com.money.moneyreminder.tool.FirebaseHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class DetailListFragmentPresenterImpl implements DetailListFragmentPresenter {

    private DetailListFragmentVu mView;

    private FirebaseHandler firebaseHandler;

    public DetailListFragmentPresenterImpl(DetailListFragmentVu mView) {
        this.mView = mView;
    }

    @Override
    public void onActivityCreated(ArrayList<MoneyObject> moneyDataArrayList, boolean isIncome) {

        if (moneyDataArrayList == null || moneyDataArrayList.isEmpty()){
            mView.showNoData(true);
            return;
        }
        mView.showNoData(false);

        Iterator<MoneyObject> it = moneyDataArrayList.iterator();
        if (isIncome){
            while (it.hasNext()){
                MoneyObject object = it.next();
                Iterator<MoneyData> money = object.getMoneyDataArrayList().iterator();
                boolean isDataFound = false;
                while (money.hasNext()){
                    MoneyData data = money.next();
                    if (data.isIncome()){
                        isDataFound = true;
                    }
                }
                if (!isDataFound){
                    it.remove();
                }
            }
        }else {
            while (it.hasNext()){
                MoneyObject object = it.next();
                Iterator<MoneyData> money = object.getMoneyDataArrayList().iterator();
                boolean isDataFound = false;
                while (money.hasNext()){
                    MoneyData data = money.next();
                    if (!data.isIncome()){
                        isDataFound = true;
                    }
                }
                if (!isDataFound){
                    it.remove();
                }
            }
        }

        if (moneyDataArrayList.isEmpty()){
            mView.showNoData(true);
            return;
        }
        mView.showNoData(false);
        mView.setRecyclerView(moneyDataArrayList,isIncome);
    }

    @Override
    public void onDetailChildItemClickListener(MoneyData data) {
        //這個再想一下

    }

}
