package com.money.moneyreminder.list_fragment;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.money.moneyreminder.list_fragment.sort_fragment.DetailChildAdapter;
import com.money.moneyreminder.list_fragment.sort_fragment.DetailListFragment;
import com.money.moneyreminder.list_fragment.sort_fragment.SortFragment;
import com.money.moneyreminder.sort.MoneyObject;

import java.util.ArrayList;

public class ListFragmentAdapter extends FragmentStatePagerAdapter {

    private ArrayList<MoneyObject> moneyDataArrayList;

    private boolean isIncome,isEditMode;

    private DetailChildAdapter.OnDetailChildItemClickListener onDetailChildItemClickListener;

    public void setOnDetailChildItemClickListener(DetailChildAdapter.OnDetailChildItemClickListener onDetailChildItemClickListener){
        this.onDetailChildItemClickListener = onDetailChildItemClickListener;
    }

    public ListFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        if (position == 0){
            return SortFragment.newInstance(moneyDataArrayList,isIncome);
        }else {
            return DetailListFragment.newInstance(moneyDataArrayList,isIncome,isEditMode).setOnDetailChildItemClickListener(onDetailChildItemClickListener);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public void setData(ArrayList<MoneyObject> moneyDataArrayList, boolean isIncome, boolean isDelete) {
        this.moneyDataArrayList = moneyDataArrayList;
        this.isIncome = isIncome;
        this.isEditMode = isDelete;
    }
}
