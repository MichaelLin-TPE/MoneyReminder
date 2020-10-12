package com.money.moneyreminder.calendar_fragment.date_presenter;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.money.moneyreminder.R;
import com.money.moneyreminder.sort.MoneyObject;
import com.money.moneyreminder.tool.DataProvider;
import com.money.moneyreminder.tool.DpConvertTool;
import com.money.moneyreminder.tool.MichaelLog;

import java.util.ArrayList;
import java.util.Locale;

public class DateListViewHolder extends RecyclerView.ViewHolder {


    private TextView tvItem,tvIncome,tvExpenditure;

    private ConstraintLayout itemArea;


    private OnCalendarItemClickListener listener;

    public void setOnCalendarItemClickListener(OnCalendarItemClickListener listener){
        MichaelLog.i("clickListener DateListViewHolder");
        this.listener = listener;
    }


    public DateListViewHolder(@NonNull View itemView) {
        super(itemView);
        itemArea = itemView.findViewById(R.id.date_list_item_area);
        tvItem = itemView.findViewById(R.id.date_list_item);
        tvItem.setWidth(DpConvertTool.getInstance().convertDb());
        tvIncome = itemView.findViewById(R.id.date_list_income);
        tvExpenditure = itemView.findViewById(R.id.date_list_expenditure);
    }

    public void setData(final String date, ArrayList<MoneyObject> moneyDateArray) {
        tvItem.setText(date);
        itemArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(date);
            }
        });
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
        MichaelLog.i("日期："+date+" , 收入 : "+moneyObject.getInComeMoney()+" , 支出 : "+moneyObject.getExpenditureMoney());

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

    public interface OnCalendarItemClickListener{
        void onClick(String date);
    }
}
