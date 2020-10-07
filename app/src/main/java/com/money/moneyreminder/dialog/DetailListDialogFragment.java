package com.money.moneyreminder.dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.money.moneyreminder.R;
import com.money.moneyreminder.sort.MoneyData;
import com.money.moneyreminder.sort.MoneyObject;
import com.money.moneyreminder.tool.MoneyReminderApplication;

import java.util.ArrayList;
import java.util.Locale;

public class DetailListDialogFragment extends MoneyReminderDialogFragment {


    private String sortType;

    private static final String SORT_TYPE = "sortType";

    public static final String MONEY_LIST = "money_list";

    private TextView tvTitle;

    private RecyclerView recyclerView;

    private ArrayList<MoneyData> moneyObjectArrayList;

    public static DetailListDialogFragment newInstance(String sortType, ArrayList<MoneyData> moneyObjectArrayList) {
        Bundle args = new Bundle();
        DetailListDialogFragment fragment = new DetailListDialogFragment();
        args.putString(SORT_TYPE,sortType);
        args.putSerializable(MONEY_LIST,moneyObjectArrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_list_item_layout,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tvTitle = view.findViewById(R.id.detail_list_item_title);
        recyclerView = view.findViewById(R.id.detail_list_item_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(MoneyReminderApplication.getInstance().getApplicationContext()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null){
            return;
        }
        sortType = getArguments().getString(SORT_TYPE,"");
        moneyObjectArrayList = (ArrayList<MoneyData>) getArguments().getSerializable(MONEY_LIST);
        if (moneyObjectArrayList == null){
            Log.i("Michael","moneyArray is null");
            return;
        }
        Log.i("Michael","接收到的 sortType : "+sortType + " , moenyArray size is "+moneyObjectArrayList.size());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvTitle.setText(String.format(Locale.getDefault(),"%s的明細",sortType));
        DetailListAdapter adapter = new DetailListAdapter(moneyObjectArrayList);
        recyclerView.setAdapter(adapter);
    }
}
