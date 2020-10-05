package com.money.moneyreminder.user_fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.money.moneyreminder.R;
import com.money.moneyreminder.user_fragment.view_presenter.AccountViewHolder;
import com.money.moneyreminder.user_fragment.view_presenter.BudgetViewHolder;
import com.money.moneyreminder.user_fragment.view_presenter.ViewPresenter;

import static com.money.moneyreminder.user_fragment.view_presenter.ViewPresenterImpl.ACCOUNT;
import static com.money.moneyreminder.user_fragment.view_presenter.ViewPresenterImpl.BUDGET;

public class UserAdapter extends RecyclerView.Adapter {

    private ViewPresenter viewPresenter;

    private BudgetViewHolder.OnSettingButtonClickListener listener;

    public void setOnSettingButtonClickListener(BudgetViewHolder.OnSettingButtonClickListener listener){
        this.listener = listener;
    }

    public void setViewPresenter(ViewPresenter viewPresenter) {
        this.viewPresenter = viewPresenter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        switch (viewType){
            case BUDGET:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_budget_layout,parent,false);
                return new BudgetViewHolder(view);
            case ACCOUNT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_account_setting_layout,parent,false);
                return new AccountViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BudgetViewHolder){
            viewPresenter.onBindBudgetViewHolder((BudgetViewHolder)holder,position);
            viewPresenter.setOnSettingButtonClickListener((BudgetViewHolder)holder,listener);
        }
        if (holder instanceof AccountViewHolder){
            viewPresenter.onBindAccountViewHolder((AccountViewHolder)holder,position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return viewPresenter.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return viewPresenter.getItemCount();
    }
}
