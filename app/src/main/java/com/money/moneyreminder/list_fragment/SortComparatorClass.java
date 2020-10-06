package com.money.moneyreminder.list_fragment;

import com.google.common.collect.Comparators;
import com.money.moneyreminder.sort.MoneyObject;

import java.util.Comparator;

public class SortComparatorClass implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {

        MoneyObject money = (MoneyObject)o1;
        MoneyObject money1 = (MoneyObject)o2;

        if (money.getTimeMiles() > money1.getTimeMiles()){
            return  1;
        }
        return -1;
    }
}
