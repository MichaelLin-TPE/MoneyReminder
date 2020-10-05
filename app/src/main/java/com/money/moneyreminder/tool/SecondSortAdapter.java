package com.money.moneyreminder.tool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.money.moneyreminder.R;

import java.util.ArrayList;

public class SecondSortAdapter extends RecyclerView.Adapter<SecondSortAdapter.ViewHolder> {

    private ArrayList<String> secondSortContentArray;

    public SecondSortAdapter() {

    }

    public void setSecondSortContentArray(ArrayList<String> secondSortContentArray) {
        this.secondSortContentArray = secondSortContentArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sort_description_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String content = secondSortContentArray.get(position);
        holder.tvItem.setText(content);
    }

    @Override
    public int getItemCount() {
        return secondSortContentArray == null ? 0 : secondSortContentArray.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.sort_description_item);
        }
    }
}
