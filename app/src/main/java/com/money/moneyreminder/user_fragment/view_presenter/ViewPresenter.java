package com.money.moneyreminder.user_fragment.view_presenter;

public interface ViewPresenter {
    int getItemViewType(int position);

    int getItemCount();

    void onBindBudgetViewHolder(BudgetViewHolder holder, int position);

    void onBindAccountViewHolder(AccountViewHolder holder, int position);

    void setData(long budgetMoney, long totalExpenditure, int expenditurePercent, long monthMoney);

    void setOnSettingButtonClickListener(BudgetViewHolder holder, BudgetViewHolder.OnSettingButtonClickListener listener);
}
