package com.money.moneyreminder.list_fragment.sort_fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.money.moneyreminder.R;
import com.money.moneyreminder.list_fragment.CustomDecoration;
import com.money.moneyreminder.sort.MoneyData;
import com.money.moneyreminder.sort.MoneyObject;

import java.util.ArrayList;


public class DetailListFragment extends Fragment implements DetailListFragmentVu{

    private DetailListFragmentPresenter presenter;

    private RecyclerView recyclerView;

    private ArrayList<MoneyObject> moneyDataArray;

    private boolean isIncome,isEditMode;

    private Context context;

    private TextView tvNoData;

    private DetailAdapter adapter;

    private DetailChildAdapter.OnDetailChildItemClickListener onDetailChildItemClickListener;

    public DetailListFragment setOnDetailChildItemClickListener(DetailChildAdapter.OnDetailChildItemClickListener onDetailChildItemClickListener){
        this.onDetailChildItemClickListener = onDetailChildItemClickListener;
        return this;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public static DetailListFragment newInstance(ArrayList<MoneyObject> moneyDataArrayList, boolean isIncome, boolean isEditMode) {
        DetailListFragment fragment = new DetailListFragment();
        Bundle args = new Bundle();
        args.putSerializable("data",moneyDataArrayList);
        args.putBoolean("is_income",isIncome);
        args.putBoolean("is_edit",isEditMode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
        if (getArguments() == null){
            return;
        }
        moneyDataArray = (ArrayList<MoneyObject>) getArguments().getSerializable("data");
        isIncome = getArguments().getBoolean("is_income",false);
        isEditMode = getArguments().getBoolean("is_edit",false);
        if (moneyDataArray == null){
            return;
        }

        Log.i("Michael","取得資料長度 : "+moneyDataArray.size()+" 是否有收入 : "+isIncome);
    }

    private void initPresenter() {
        presenter = new DetailListFragmentPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_list, container, false);
        // Inflate the layout for this fragment
        initView(view);
        return view;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.detail_fragment_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        int pix = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,20,context.getResources().getDisplayMetrics());

        recyclerView.addItemDecoration(new CustomDecoration(pix));
        tvNoData = view.findViewById(R.id.detail_fragment_no_data);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.onActivityCreated(moneyDataArray,isIncome,isEditMode);
    }

    @Override
    public void setRecyclerView(ArrayList<MoneyObject> moneyDataArrayList, boolean isIncome, boolean isEditMode) {
        this.moneyDataArray = moneyDataArrayList;
        this.isIncome = isIncome;
        adapter = new DetailAdapter();
        adapter.setData(moneyDataArrayList,isIncome,isEditMode);
        recyclerView.setAdapter(adapter);
        adapter.setOnDetailChildItemClickListener(new DetailChildAdapter.OnDetailChildItemClickListener() {
            @Override
            public void onClick(MoneyData data) {
                presenter.onDetailChildItemClickListener(data);
            }

            @Override
            public void onLongPress() {
                onDetailChildItemClickListener.onLongPress();
                presenter.onDetailChildItemLongPressListener();
            }

            @Override
            public void onCheckBoxChecked(MoneyData data, boolean isChecked) {
                onDetailChildItemClickListener.onCheckBoxChecked(data,isChecked);

            }
        });
    }

    @Override
    public void showNoData(boolean isShow) {
        tvNoData.setVisibility(isShow ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(isShow ? View.GONE : View.VISIBLE);
    }

    @Override
    public void changeDetailChildItemView(boolean isChange) {
        adapter.setData(moneyDataArray,isIncome,isChange);
        adapter.notifyDataSetChanged();
    }
}