package com.money.moneyreminder.tool;

import com.money.moneyreminder.sort.MoneyData;
import com.money.moneyreminder.sort.MoneyObject;
import com.money.moneyreminder.sort_list.IconData;
import com.money.moneyreminder.sort_list.presenter.SortData;
import com.money.moneyreminder.sort_list.presenter.SortTypeData;

import java.util.ArrayList;

public interface FirebaseHandler {
    String getUserEmail();

    void saveUserMoneyList(SortTypeData sortType, long choiceTimeMiles, int totalMoney, boolean isIncome, String description);

    void getUserSortData(OnFireStoreCatchListener<SortData> onFireStoreCatchListener);

    void getIconApi(OnFireStoreCatchListener<ArrayList<IconData>> onFireStoreCatchListener);

    void setPersonalSortType(String edContent, String iconUrl);

    void getUserMoneyData(OnFireStoreCatchListener<ArrayList<MoneyObject>> onGetUserMoneyDataListener);

    void updateUserMoneyList(ArrayList<MoneyObject> moneyArrayList);

    void saveUserDescription(String description);

    void getUserDescribeData(OnFireStoreCatchListener<ArrayList<String>> onFireStoreCatchListener);


    public interface OnFireStoreCatchListener<T>{
        void onSuccess(T data);
        void onFail (String errorCode);
    }
}
