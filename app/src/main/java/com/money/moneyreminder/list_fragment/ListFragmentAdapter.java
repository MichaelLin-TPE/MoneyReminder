package com.money.moneyreminder.list_fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.money.moneyreminder.list_fragment.sort_fragment.DetailListFragment;
import com.money.moneyreminder.list_fragment.sort_fragment.SortFragment;
import com.money.moneyreminder.sort.MoneyData;
import com.money.moneyreminder.sort.MoneyObject;

import java.util.ArrayList;

public class ListFragmentAdapter extends FragmentStatePagerAdapter {

    private ArrayList<MoneyObject> moneyDataArrayList;

    private boolean isIncome;

    public ListFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        if (position == 0){
            return SortFragment.newInstance(moneyDataArrayList,isIncome);
        }else {
            return DetailListFragment.newInstance(moneyDataArrayList,isIncome);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public void setData(ArrayList<MoneyObject> moneyDataArrayList, boolean isIncome) {
        this.moneyDataArrayList = moneyDataArrayList;
        this.isIncome = isIncome;
    }
}
