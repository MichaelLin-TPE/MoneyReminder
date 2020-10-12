package com.money.moneyreminder.sort_list.presenter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.money.moneyreminder.R;
import com.money.moneyreminder.tool.MichaelLog;
import com.money.moneyreminder.tool.MoneyReminderApplication;

import java.util.ArrayList;

public class SecondSortViewHolder extends RecyclerView.ViewHolder {

    private RecyclerView recyclerView;

    private TextView tvNoData;

    private ImageView ivAddIcon;

    private OnAddIconClickListener onAddIconClickListener;

    private SecondSortContentAdapter.OnDescriptionItemClickListener listener;


    public void setOnAddIconClickListener(OnAddIconClickListener onAddIconClickListener){
        this.onAddIconClickListener = onAddIconClickListener;
    }

    public void setOnDescriptionItemClickListener(SecondSortContentAdapter.OnDescriptionItemClickListener listener){
        this.listener = listener;
    }

    public SecondSortViewHolder(@NonNull View itemView) {
        super(itemView);
        recyclerView = itemView.findViewById(R.id.second_sort_list_recycler_view);
        ivAddIcon = itemView.findViewById(R.id.second_sort_list_icon_add);
        recyclerView.setLayoutManager(new LinearLayoutManager(MoneyReminderApplication.getInstance().getApplicationContext()));
        tvNoData = itemView.findViewById(R.id.second_sort_list_no_data);
    }

    public void setData(ArrayList<String> contentArray) {
        final SecondSortContentAdapter adapter = new SecondSortContentAdapter();
        adapter.setSecondSortContentArray(contentArray);
        recyclerView.setAdapter(adapter);
        tvNoData.setVisibility((contentArray == null || contentArray.isEmpty()) ? View.VISIBLE : View.GONE);
        adapter.setOnDescriptionItemClickListener(new SecondSortContentAdapter.OnDescriptionItemClickListener() {
            @Override
            public void onClick(String content) {
                MichaelLog.i("次分類點擊");
                adapter.notifyDataSetChanged();
                listener.onClick(content);
            }
        });
        ivAddIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddIconClickListener.onClick();
            }
        });
    }

    public interface OnAddIconClickListener{
        void onClick();
    }
}
