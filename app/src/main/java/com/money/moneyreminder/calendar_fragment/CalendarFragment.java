package com.money.moneyreminder.calendar_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.money.moneyreminder.R;
import com.money.moneyreminder.calculator.CalculatorActivity;
import com.money.moneyreminder.calendar_fragment.date_presenter.CalendarAdapter;
import com.money.moneyreminder.calendar_fragment.date_presenter.DateListViewHolder;
import com.money.moneyreminder.calendar_fragment.date_presenter.DatePresenter;
import com.money.moneyreminder.calendar_fragment.date_presenter.DatePresenterImpl;
import com.money.moneyreminder.sort.MoneyObject;
import com.money.moneyreminder.tool.DataProvider;
import com.money.moneyreminder.dialog.ErrorDialog;

import java.util.ArrayList;


public class CalendarFragment extends Fragment implements CalendarFragmentVu {

    private CalendarFragmentPresenter presenter;

    private DatePresenter datePresenter;

    private RecyclerView recyclerView;

    private CalendarAdapter adapter;

    private TextView tvTime;

    private ImageView ivRight,ivLeft;

    private Context context;

    private FragmentActivity fragmentActivity;

    public static final String CURRENT_TIME = "currentTime";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        this.fragmentActivity = (FragmentActivity) context;
    }

    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }

    private void initPresenter() {
        presenter = new CalendarFragmentPresenterImpl(this);
        datePresenter = new DatePresenterImpl();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tvTime = view.findViewById(R.id.calendar_time);
        ivLeft = view.findViewById(R.id.calendar_left_arrow);
        ivRight = view.findViewById(R.id.calendar_right_arrow);
        recyclerView = view.findViewById(R.id.calendar_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(context,7));

        //網格 直接貼上還不知道怎麼做的
        recyclerView.addItemDecoration(new CustomGridDivider(2,R.color.black));

        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onLeftArrowClickListener();
            }
        });
        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onRightArrowClickListener();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.onActivityCreated();
    }

    @Override
    public void showCalendarView(ArrayList<String> dateList, ArrayList<MoneyObject> moneyDateArray) {

        ArrayList<String> weekDayArray = DataProvider.getInstance().getWeekDayArray();
        datePresenter.setData(weekDayArray,dateList,moneyDateArray);

        if (adapter == null){
            adapter = new CalendarAdapter(datePresenter);
            recyclerView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
        adapter.setOnCalendarItemClickListener(new DateListViewHolder.OnCalendarItemClickListener() {
            @Override
            public void onClick(String date) {
                presenter.onCalendarItemClickListener(date);
            }
        });


    }

    @Override
    public void showErrorCode(String errorCode) {
        ErrorDialog.newInstance(errorCode).show(fragmentActivity.getSupportFragmentManager(),"dialog");
    }

    @Override
    public void setTime(String currentDate) {
        tvTime.setText(currentDate);
    }

    @Override
    public void intentToCalculatorActivity(String currentTime) {
        Intent it = new Intent(context, CalculatorActivity.class);
        it.putExtra(CURRENT_TIME,currentTime);
        context.startActivity(it);
    }
}