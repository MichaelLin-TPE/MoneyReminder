package com.money.moneyreminder.list_fragment.sort_fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.money.moneyreminder.R;
import com.money.moneyreminder.sort.MoneyData;
import com.money.moneyreminder.sort.MoneyObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {

    private ArrayList<MoneyObject> moneyDataArrayList;

    private Context context;

    private boolean isIncome;

    private DetailChildAdapter.OnDetailChildItemClickListener listener;

    private ArrayList<MoneyData> incomeArray;
    private ArrayList<MoneyData> expenditureArray;
    private boolean isEditMode;

    public void setOnDetailChildItemClickListener(DetailChildAdapter.OnDetailChildItemClickListener listener){
        this.listener = listener;
    }


    public void setData(ArrayList<MoneyObject> moneyDataArrayList,boolean isIncome,boolean isEditMode){
        this.moneyDataArrayList = moneyDataArrayList;
        this.isIncome = isIncome;
        this.isEditMode = isEditMode;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.TAIWAN);
        MoneyObject data = moneyDataArrayList.get(position);
        String time = simpleDateFormat.format(new Date(data.getTimeMiles()));
        holder.tvTime.setText(time);
        holder.tvIncome.setText(String.format(Locale.getDefault(),"收入 : %d",data.getInComeMoney()));
        holder.tvExpenditure.setText(String.format(Locale.getDefault(),"支出 : %d",data.getExpenditureMoney()));

        incomeArray = new ArrayList<>();
        expenditureArray = new ArrayList<>();
        for (MoneyData moneyData : data.getMoneyDataArrayList()){
            if (moneyData.isIncome()){
                incomeArray.add(moneyData);
            }else {
                expenditureArray.add(moneyData);
            }
        }

        changeViewForIsIncome(isIncome,holder);

    }

    private void changeViewForIsIncome(boolean isIncome, ViewHolder holder) {
        holder.tvExpenditure.setVisibility(isIncome ? View.GONE : View.VISIBLE);
        holder.tvIncome.setVisibility(isIncome ? View.VISIBLE : View.GONE);
        DetailChildAdapter adapter = new DetailChildAdapter(isIncome ? incomeArray : expenditureArray,isEditMode);
        holder.recyclerView.setAdapter(adapter);
        adapter.setOnDetailChildItemClickListener(new DetailChildAdapter.OnDetailChildItemClickListener() {
            @Override
            public void onClick(MoneyData data) {
                listener.onClick(data);
            }

            @Override
            public void onLongPress() {
                listener.onLongPress();
            }

            @Override
            public void onCheckBoxChecked(MoneyData data, boolean isChecked) {
                listener.onCheckBoxChecked(data,isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moneyDataArrayList == null || moneyDataArrayList.isEmpty() ? 0 : moneyDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTime,tvIncome,tvExpenditure;

        private RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.detail_item_time);
            tvIncome = itemView.findViewById(R.id.detail_item_income);
            tvExpenditure = itemView.findViewById(R.id.detail_item_expenditure);
            recyclerView = itemView.findViewById(R.id.detail_item_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        }
    }
}
