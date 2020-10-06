package com.money.moneyreminder.user_fragment.view_presenter;

import com.money.moneyreminder.tool.SecondSortAdapter;

import java.util.ArrayList;

public interface ViewPresenter {
    int getItemViewType(int position);

    int getItemCount();

    void onBindBudgetViewHolder(BudgetViewHolder holder, int position);

    void onBindAccountViewHolder(AccountViewHolder holder, int position);

    void setData(long budgetMoney, long totalExpenditure, int expenditurePercent, long monthMoney, ArrayList<String> accountItemArray);

    void setOnSettingButtonClickListener(BudgetViewHolder holder, BudgetViewHolder.OnSettingButtonClickListener listener);

    void setOnAccountItemClickListener(AccountViewHolder holder, SecondSortAdapter.OnAccountItemClickListener onAccountItemClickListener);
}
