package com.money.moneyreminder.sort_list.presenter;

import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.money.moneyreminder.R;
import com.money.moneyreminder.list_fragment.CustomDecoration;
import com.money.moneyreminder.tool.MoneyReminderApplication;

import java.util.ArrayList;

public class CreateViewHolder extends RecyclerView.ViewHolder {

    private ImageView ivIcon;

    private TextView tvTitle,tvNoData;

    private RecyclerView recyclerView;

    private CreateAdapter.OnSortTypeSelectListener createListener;

    public CreateViewHolder(@NonNull View itemView) {
        super(itemView);
        ivIcon = itemView.findViewById(R.id.sort_list_item_icon);
        tvTitle = itemView.findViewById(R.id.sort_list_item_title);
        recyclerView = itemView.findViewById(R.id.sort_list_item_recycler_view);
        tvNoData = itemView.findViewById(R.id.sort_list_item_no_data);
        recyclerView.setLayoutManager(new GridLayoutManager(MoneyReminderApplication.getInstance().getApplicationContext(),3));
        int pix = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10,MoneyReminderApplication.getInstance().getApplicationContext().getResources().getDisplayMetrics());
        recyclerView.addItemDecoration(new CustomDecoration(pix));
        tvNoData.setVisibility(View.GONE);
        ivIcon.setImageResource(R.drawable.create);
        tvTitle.setText(R.string.create);
    }

    public void setData(final ArrayList<SortCreateData> createArray) {
        if (createArray == null){
            tvNoData.setVisibility(View.VISIBLE);
            return;
        }
        final CreateAdapter adapter = new CreateAdapter(createArray);
        recyclerView.setAdapter(adapter);
        adapter.setOnSortTypeSelectListener(new CreateAdapter.OnSortTypeSelectListener() {
            @Override
            public void onSelected(SortCreateData data) {
                adapter.notifyDataSetChanged();
                createListener.onSelected(data);
            }
        });
    }

    public void setOnSortTypeSelectListener(CreateAdapter.OnSortTypeSelectListener createListener) {
        this.createListener = createListener;
    }
}
