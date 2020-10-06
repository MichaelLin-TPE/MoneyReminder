package com.money.moneyreminder.user_fragment.view_presenter;

import com.money.moneyreminder.dialog.SecondSortAdapter;

import java.util.ArrayList;

public class ViewPresenterImpl implements ViewPresenter {

    public static final int BUDGET = 0;

    public static final int ACCOUNT = 1;

    private long budgetMoney,totalExpenditure,expenditurePercent,monthMoney;

    private ArrayList<String> accountItemArray;

    @Override
    public void setData(long budgetMoney, long totalExpenditure, int expenditurePercent, long monthMoney, ArrayList<String> accountItemArray) {
        this.budgetMoney = budgetMoney;
        this.totalExpenditure = totalExpenditure;
        this.expenditurePercent = expenditurePercent;
        this.monthMoney = monthMoney;
        this.accountItemArray = accountItemArray;
    }

    @Override
    public void setOnSettingButtonClickListener(BudgetViewHolder holder, BudgetViewHolder.OnSettingButtonClickListener listener) {
        holder.setOnSettingButtonClickListener(listener);
    }

    @Override
    public void setOnAccountItemClickListener(AccountViewHolder holder, SecondSortAdapter.OnAccountItemClickListener onAccountItemClickListener) {
        holder.setOnAccountItemClickListener(onAccountItemClickListener);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return BUDGET;
        }
        if (position == 1){
            return ACCOUNT;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public void onBindBudgetViewHolder(BudgetViewHolder holder, int position) {
        holder.setData(budgetMoney,totalExpenditure,(int)expenditurePercent,monthMoney);
    }

    @Override
    public void onBindAccountViewHolder(AccountViewHolder holder, int position){
        holder.setData(accountItemArray);
    }


}
