package com.money.moneyreminder.tool;

import com.money.moneyreminder.dialog.SecondSortData;
import com.money.moneyreminder.sort.MoneyObject;
import com.money.moneyreminder.sort_list.IconData;
import com.money.moneyreminder.sort_list.presenter.SortCreateData;
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

    void setPersonalRecentlySortType(SortCreateData sortCreateData);

    void saveUserSecondSortData(ArrayList<String> secondSortContentArray, String sortTitle);

    void getSecondSortArray(OnFireStoreCatchListener<ArrayList<SecondSortData>> onFireStoreCatchListener);

    void getBudgetMoney(OnFireStoreCatchListener<Long> onCatchBudgetListener);

    String getUserDisplayName();

    void saveUserBudgetMoney(int budget);


    public interface OnFireStoreCatchListener<T>{
        void onSuccess(T data);
        void onFail (String errorCode);
    }
}
