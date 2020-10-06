package com.money.moneyreminder.list_fragment;

import com.money.moneyreminder.list_fragment.sort_fragment.SortPercentData;
import com.money.moneyreminder.sort.MoneyObject;
import com.money.moneyreminder.tool.UserManager;

import java.util.Comparator;

import static com.money.moneyreminder.dialog.SettingDataSortDialogFragment.FAR;

public class SortComparatorForPicClass implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {

        SortPercentData sort1 = (SortPercentData)o1;
        SortPercentData sort2 = (SortPercentData)o2;


        if (sort1.getPercent() > sort2.getPercent()){
            return  -1;
        }
        return 1;
    }
}
