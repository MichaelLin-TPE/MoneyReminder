package com.money.moneyreminder.user_fragment.view_presenter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.money.moneyreminder.R;
import com.money.moneyreminder.tool.MoneyReminderApplication;
import com.money.moneyreminder.dialog.SecondSortAdapter;
import com.money.moneyreminder.tool.UserManager;

import java.util.ArrayList;

public class AccountViewHolder extends RecyclerView.ViewHolder {

    private RecyclerView recyclerView;

    private SecondSortAdapter.OnAccountItemClickListener listener;

    public void setOnAccountItemClickListener(SecondSortAdapter.OnAccountItemClickListener listener){
        this.listener = listener;
    }

    public AccountViewHolder(@NonNull View itemView) {
        super(itemView);
        recyclerView = itemView.findViewById(R.id.account_item_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(MoneyReminderApplication.getInstance().getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(MoneyReminderApplication.getInstance().getApplicationContext(),DividerItemDecoration.VERTICAL));
    }

    public void setData(final ArrayList<String> itemArray, final ArrayList<String> accountItemArray) {
        final AccountSettingAdapter adapter = new AccountSettingAdapter();
        adapter.setSecondSortContentArray(itemArray,accountItemArray);
        recyclerView.setAdapter(adapter);
        adapter.setOnAccountItemClickListener(new AccountSettingAdapter.OnAccountItemClickListener() {
            @Override
            public void onClick(String itemName) {
                listener.onClick(itemName);
                adapter.notifyDataSetChanged();
            }
        });
    }



}
