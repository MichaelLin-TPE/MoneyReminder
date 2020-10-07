package com.money.moneyreminder.dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.money.moneyreminder.R;
import com.money.moneyreminder.sort.MoneyData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.ViewHolder> {

    private ArrayList<MoneyData> moneyDataArrayList;

    public DetailListAdapter(ArrayList<MoneyData> moneyDataArrayList) {
        this.moneyDataArrayList = moneyDataArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_list_child_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MoneyData data = moneyDataArrayList.get(position);
        holder.tvTime.setText(new SimpleDateFormat("MM/dd",Locale.TAIWAN).format(new Date(data.getTimeMiles())));
        holder.tvNote.setText(data.getDescription());
        holder.tvMoney.setText(String.format(Locale.getDefault(),"$%d",data.getTotalMoney()));
    }

    @Override
    public int getItemCount() {
        return moneyDataArrayList == null ? 0 : moneyDataArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTime, tvNote,tvMoney;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.detail_list_child_time);
            tvNote = itemView.findViewById(R.id.detail_list_child_noted);
            tvMoney = itemView.findViewById(R.id.detail_list_child_money);
        }
    }
}
