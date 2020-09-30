package com.money.moneyreminder.list_fragment.sort_fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.money.moneyreminder.R;
import com.money.moneyreminder.sort.MoneyObject;

import java.util.ArrayList;


public class SortFragment extends Fragment implements SortFragmentVu {

    private SortFragmentPresenter presenter;

    private RecyclerView recyclerView;

    private Context context;

    private ArrayList<MoneyObject> moneyObjectArrayList;

    private boolean isIncome;

    private TextView tvNoData;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public static SortFragment newInstance(ArrayList<MoneyObject> moneyObjectArrayList,boolean isIncome) {
        SortFragment fragment = new SortFragment();
        Bundle args = new Bundle();
        args.putSerializable("money",moneyObjectArrayList);
        args.putBoolean("is_income",isIncome);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
        initBundle();
    }

    private void initBundle() {
        if (getArguments() == null){
            Log.i("Michael","getArguments is null");
            return;
        }
        moneyObjectArrayList = (ArrayList<MoneyObject>) getArguments().getSerializable("money");
        isIncome = getArguments().getBoolean("is_income",false);
    }

    private void initPresenter() {
        presenter = new SortFragmentPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sort, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.sort_fragment_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        tvNoData = view.findViewById(R.id.sort_fragment_no_data);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.onActivityCreated(moneyObjectArrayList,isIncome);
    }

    @Override
    public void setRecyclerView(ArrayList<SortPercentData> sortTypeArray, boolean isIncome) {
        SortTypePercentAdapter adapter = new SortTypePercentAdapter(sortTypeArray,isIncome);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showNoDataView(boolean isShow) {
        tvNoData.setVisibility(isShow ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(isShow ? View.GONE : View.VISIBLE);
    }
}