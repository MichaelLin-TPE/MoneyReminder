package com.money.moneyreminder.calendar_fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.money.moneyreminder.R;
import com.money.moneyreminder.calendar_fragment.date_presenter.CalendarAdapter;
import com.money.moneyreminder.calendar_fragment.date_presenter.DatePresenter;
import com.money.moneyreminder.calendar_fragment.date_presenter.DatePresenterImpl;
import com.money.moneyreminder.tool.DataProvider;

import java.util.ArrayList;


public class CalendarFragment extends Fragment implements CalendarFragmentVu {

    private CalendarFragmentPresenter presenter;

    private DatePresenter datePresenter;

    private RecyclerView recyclerView;

    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
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
        recyclerView = view.findViewById(R.id.calendar_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(context,7));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.onActivityCreated();
    }

    @Override
    public void showCalendarView(ArrayList<String> dateList) {

        ArrayList<String> weekDayArray = DataProvider.getInstance().getWeekDayArray();

        datePresenter.setData(weekDayArray,dateList);

        CalendarAdapter adapter = new CalendarAdapter(datePresenter);
        recyclerView.setAdapter(adapter);

    }
}