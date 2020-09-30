package com.money.moneyreminder.calendar_fragment.date_presenter;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.money.moneyreminder.R;
import com.money.moneyreminder.sort.MoneyObject;
import com.money.moneyreminder.tool.DataProvider;
import com.money.moneyreminder.tool.DbConvertTool;
import java.util.ArrayList;
import java.util.Locale;

public class DateListViewHolder extends RecyclerView.ViewHolder {


    private TextView tvItem,tvIncome,tvExpenditure;


    public DateListViewHolder(@NonNull View itemView) {
        super(itemView);
        tvItem = itemView.findViewById(R.id.date_list_item);
        tvItem.setWidth(DbConvertTool.getInstance().convertDb());
        tvIncome = itemView.findViewById(R.id.date_list_income);
        tvExpenditure = itemView.findViewById(R.id.date_list_expenditure);
    }

    public void setData(String date, ArrayList<MoneyObject> moneyDateArray) {
        tvItem.setText(date);
        if (moneyDateArray == null){
            tvIncome.setText("");
            tvExpenditure.setText("");
            return;
        }

        if (date == null || date.isEmpty()){
            tvIncome.setText("");
            tvExpenditure.setText("");
            return;
        }

        MoneyObject moneyObject = null;
        for (MoneyObject data : moneyDateArray){
            String dataTime = DataProvider.getInstance().getTime(data.getTimeMiles());
            if (dataTime.equals(date)){
                moneyObject = data;
                break;
            }
        }
        if (moneyObject == null){
            tvIncome.setText("");
            tvExpenditure.setText("");
            return;
        }
        tvIncome.setText("");
        tvExpenditure.setText("");
        Log.i("Michael","日期："+date+" , 收入 : "+moneyObject.getInComeMoney()+" , 支出 : "+moneyObject.getExpenditureMoney());
        if (moneyObject.getInComeMoney() != 0){
            tvIncome.setVisibility(View.VISIBLE);
            tvIncome.setText(String.format(Locale.getDefault(),"%d",moneyObject.getInComeMoney()));
        }else {
            tvIncome.setVisibility(View.INVISIBLE);
        }
        if (moneyObject.getExpenditureMoney() != 0){
            tvExpenditure.setVisibility(View.VISIBLE);
            tvExpenditure.setText(String.format(Locale.getDefault(),"-%d",moneyObject.getExpenditureMoney()));
        }else {
            tvExpenditure.setVisibility(View.INVISIBLE);
        }

    }
}
