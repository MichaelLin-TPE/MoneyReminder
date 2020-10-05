package com.money.moneyreminder.user_fragment.view_presenter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.money.moneyreminder.R;
import com.money.moneyreminder.tool.MoneyReminderApplication;
import com.money.moneyreminder.tool.SecondSortAdapter;

import java.util.ArrayList;

public class AccountViewHolder extends RecyclerView.ViewHolder {

    private RecyclerView recyclerView;

    public AccountViewHolder(@NonNull View itemView) {
        super(itemView);
        recyclerView = itemView.findViewById(R.id.account_item_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(MoneyReminderApplication.getInstance().getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(MoneyReminderApplication.getInstance().getApplicationContext(),DividerItemDecoration.VERTICAL));
    }

    public void setData(ArrayList<String> accountItemArray) {
        SecondSortAdapter adapter = new SecondSortAdapter();
        adapter.setSecondSortContentArray(accountItemArray);
        recyclerView.setAdapter(adapter);
    }
}
