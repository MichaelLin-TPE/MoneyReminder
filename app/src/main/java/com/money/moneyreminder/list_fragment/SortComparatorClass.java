package com.money.moneyreminder.list_fragment;

import com.money.moneyreminder.sort.MoneyObject;
import com.money.moneyreminder.tool.UserManager;

import java.util.Comparator;

import static com.money.moneyreminder.dialog.SettingDataSortDialogFragment.FAR;

public class SortComparatorClass implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {

        MoneyObject money = (MoneyObject)o1;
        MoneyObject money1 = (MoneyObject)o2;

        String sortType = UserManager.getInstance().getSortType();
        if (sortType.equals(FAR)){
            if (money.getTimeMiles() > money1.getTimeMiles()){
                return  -1;
            }
            return 1;
        }
        if (money.getTimeMiles() > money1.getTimeMiles()){
            return  1;
        }
        return -1;
    }
}
