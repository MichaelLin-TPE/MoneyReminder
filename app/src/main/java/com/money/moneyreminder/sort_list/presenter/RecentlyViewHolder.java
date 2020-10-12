package com.money.moneyreminder.sort_list.presenter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.money.moneyreminder.R;
import com.money.moneyreminder.tool.MoneyReminderApplication;

import java.util.ArrayList;

public class RecentlyViewHolder extends RecyclerView.ViewHolder {

    private ImageView ivIcon;

    private TextView tvTitle,tvNoData;

    private RecyclerView recyclerView;

    private RecentlyAdapter.OnSortTypeRecentlySelectListener listener;

    public RecentlyViewHolder(@NonNull View itemView) {
        super(itemView);
        ivIcon = itemView.findViewById(R.id.sort_list_item_icon);
        tvTitle = itemView.findViewById(R.id.sort_list_item_title);
        recyclerView = itemView.findViewById(R.id.sort_list_item_recycler_view);
        tvNoData = itemView.findViewById(R.id.sort_list_item_no_data);
        recyclerView.setLayoutManager(new GridLayoutManager(MoneyReminderApplication.getInstance().getApplicationContext(),3));
        tvNoData.setVisibility(View.GONE);
        ivIcon.setImageResource(R.drawable.refresh);
        tvTitle.setText(R.string.recently);
    }

    public void setData(ArrayList<SortRecentlyData> recentlyArray) {
        if (recentlyArray == null || recentlyArray.isEmpty()){
            tvNoData.setVisibility(View.VISIBLE);
            return;
        }
        final RecentlyAdapter adapter = new RecentlyAdapter(recentlyArray);
        recyclerView.setAdapter(adapter);
        adapter.setOnSortTypeSelectListener(new RecentlyAdapter.OnSortTypeRecentlySelectListener() {
            @Override
            public void onSelected(SortRecentlyData data) {
                listener.onSelected(data);
                adapter.notifyDataSetChanged();
            }
        });

    }

    public void setOnSortTypeRecentlyListener(RecentlyAdapter.OnSortTypeRecentlySelectListener recentlyListener) {
        this.listener = recentlyListener;
    }
}
