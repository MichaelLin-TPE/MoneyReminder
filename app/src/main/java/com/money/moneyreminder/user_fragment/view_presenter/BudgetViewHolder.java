package com.money.moneyreminder.user_fragment.view_presenter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.money.moneyreminder.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BudgetViewHolder extends RecyclerView.ViewHolder {

    private final TextView tvBudget,tvTotalExpenditure,tvExpenditurePercent,tvMonthMoney,tvTitle;

    private OnSettingButtonClickListener listener;

    private final Button btnSetting;

    public void setOnSettingButtonClickListener(OnSettingButtonClickListener listener){
        this.listener = listener;
    }

    public BudgetViewHolder(@NonNull View itemView) {
        super(itemView);
        tvBudget = itemView.findViewById(R.id.budget_item_budget_money);
        tvTotalExpenditure = itemView.findViewById(R.id.budget_item_expenditure);
        tvExpenditurePercent = itemView.findViewById(R.id.budget_item_percent);
        tvMonthMoney = itemView.findViewById(R.id.budget_item_month);
        btnSetting = itemView.findViewById(R.id.budget_item_btn_set);
        tvTitle = itemView.findViewById(R.id.budget_item_title);
    }

    public void setData(long budgetMoney, long totalExpenditure, int expenditurePercent, long monthMoney) {

        String currentMonth = new SimpleDateFormat("MM",Locale.TAIWAN).format(new Date(System.currentTimeMillis()));
        tvTitle.setText(String.format(Locale.getDefault(),"%s 月預算",currentMonth));


        tvBudget.setText(String.format(Locale.getDefault(),"目前預算 : $%d",budgetMoney));
        tvTotalExpenditure.setText(String.format(Locale.getDefault(),"目前支出 : $%d",totalExpenditure));
        tvExpenditurePercent.setText(String.format(Locale.getDefault(),"支出率 : %d%%",expenditurePercent));
        tvMonthMoney.setText(String.format(Locale.getDefault(),"平均支出(天) : $%d",monthMoney));

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick();
            }
        });
    }

    public interface OnSettingButtonClickListener{
        void onClick();
    }
}
