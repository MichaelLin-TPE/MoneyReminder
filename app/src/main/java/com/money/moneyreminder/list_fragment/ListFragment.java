package com.money.moneyreminder.list_fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.money.moneyreminder.R;
import com.money.moneyreminder.calculator.CalculatorActivity;
import com.money.moneyreminder.sort.MoneyObject;
import com.money.moneyreminder.tool.DataProvider;
import com.money.moneyreminder.tool.DateDTO;

import java.util.ArrayList;
import java.util.Locale;


public class ListFragment extends Fragment implements ListFragmentVu {

    private ListFragmentPresenter presenter;

    private TabLayout tabLayout, sortTabLayout;

    private int finalSelectIndex;

    private ProgressBar progressBar;

    private RadioButton radIncome;

    private RadioButton radExpenditure;



    private Context context;

    private ListFragmentAdapter listFragmentAdapter;

    private ViewPager viewPager;

    private boolean isIncome;

    private FloatingActionButton floatingActionButton;

    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }

    private void initPresenter() {
        presenter = new ListFragmentPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        initView(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void initView(View view) {
        floatingActionButton = view.findViewById(R.id.list_add_money_button);
        viewPager = view.findViewById(R.id.list_view_pager);
        tabLayout = view.findViewById(R.id.list_tab_layout);
        sortTabLayout = view.findViewById(R.id.list_sort_tab);
        progressBar = view.findViewById(R.id.list_progress);
        radExpenditure = view.findViewById(R.id.list_radio_expenditure);
        radIncome = view.findViewById(R.id.list_radio_in_come);
        radExpenditure.setBackground(ContextCompat.getDrawable(context, R.drawable.radio_button_selector));
        radIncome.setBackground(null);
        radExpenditure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {

                if (isCheck) {
                    isIncome = false;
                    radIncome.setBackground(null);
                    radIncome.setChecked(false);
                    presenter.onRadioMoneySortButtonClickListener(isIncome);
                    radExpenditure.setBackground(ContextCompat.getDrawable(context, R.drawable.radio_button_selector));
                }
            }
        });
        radIncome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                if (isCheck) {
                    isIncome = true;
                    radExpenditure.setBackground(null);
                    radExpenditure.setChecked(false);
                    presenter.onRadioMoneySortButtonClickListener(isIncome);
                    radIncome.setBackground(ContextCompat.getDrawable(context, R.drawable.radio_button_selector));
                }
            }
        });
        sortTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(sortTabLayout));
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onAddMoneyButtonClickListener();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.onActivityCreated(isIncome);
    }


    @Override
    public void showTabLayout(String currentYear, String currentDate, final ArrayList<DateDTO> dateStringArray) {
        tabLayout.removeAllTabs();
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        int selectIndex = 0;
        for (int i = 0; i < dateStringArray.size(); i++) {
            if (dateStringArray.get(i).getYear().equals(currentYear) && dateStringArray.get(i).getMonth().equals(currentDate)) {
                selectIndex = i;
            }
        }

        for (DateDTO dateDTO : dateStringArray) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setCustomView(prepare(dateDTO.getTabString()));
            tab.setText(dateDTO.getTabString());
            tabLayout.addTab(tab);
        }
        Log.i("Michael", "selectIndex = " + selectIndex);


        finalSelectIndex = selectIndex;
        tabLayout.post(tabLayoutRunnable);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                String month = dateStringArray.get(tab.getPosition()).getMonth();
                String year = dateStringArray.get(tab.getPosition()).getYear();
                presenter.onTabSelectedListener(month,year);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private Runnable tabLayoutRunnable = new Runnable() {
        @Override
        public void run() {
            if (tabLayout.getTabAt(finalSelectIndex) == null) {
                Log.i("Michael", "tabItem is null ");
                return;
            }
            TabLayout.Tab tab = tabLayout.getTabAt(finalSelectIndex);
            if (tab == null) {
                Log.i("Michael", "tabItem is null ");
                return;
            }
            tab.select();
        }
    };


    @Override
    public void showProgress(boolean isShow) {
        progressBar.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showSortTabLayout(ArrayList<MoneyObject> moneyDataArrayList, boolean isIncome) {
        Log.i("Michael", "是否是收入 : " + isIncome);


        listFragmentAdapter = new ListFragmentAdapter(getFragmentManager());
        listFragmentAdapter.setData(moneyDataArrayList, isIncome);
        viewPager.setAdapter(listFragmentAdapter);


        showSortTab();


    }

    private void showSortTab() {
        sortTabLayout.removeAllTabs();
        ArrayList<String> sortDataArray = DataProvider.getInstance().getSortDataArray();
        for (String content : sortDataArray) {
            TabLayout.Tab tab = sortTabLayout.newTab();
            tab.setCustomView(prepareView(content));
            tab.setText(content);
            sortTabLayout.addTab(tab);
        }
        sortTabLayout.post(sorTabRunnable);
        sortTabLayout.addOnTabSelectedListener(onSortTabSelectListener);
    }

    @Override
    public void intentToCalculatorActivity() {
        Intent it = new Intent(context, CalculatorActivity.class);
        startActivity(it);
    }

    @Override
    public void showTopIncomeData(int incomeMoney, int expenditure) {
        radIncome.setText(String.format(Locale.getDefault(), "收入\n$%d", incomeMoney));
        radExpenditure.setText(String.format(Locale.getDefault(), "支出\n$%d", expenditure));
    }

    private TabLayout.OnTabSelectedListener onSortTabSelectListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int pressPosition = tab.getPosition();
            TabLayout.Tab singleTab = sortTabLayout.getTabAt(pressPosition);
            if (singleTab == null || singleTab.getCustomView() == null) {
                Log.i("Michael", "singleTab is null");
                return;
            }
            TextView tvTitle = singleTab.getCustomView().findViewById(R.id.top_tab_title);
            tvTitle.setTextColor(ContextCompat.getColor(context, R.color.green));

        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            int pressPosition = tab.getPosition();
            TabLayout.Tab singleTab = sortTabLayout.getTabAt(pressPosition);
            if (singleTab == null || singleTab.getCustomView() == null) {
                Log.i("Michael", "singleTab is null");
                return;
            }
            TextView tvTitle = singleTab.getCustomView().findViewById(R.id.top_tab_title);
            tvTitle.setTextColor(ContextCompat.getColor(context, R.color.black));
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    private Runnable sorTabRunnable = new Runnable() {
        @Override
        public void run() {
            if (sortTabLayout.getTabAt(1) == null) {
                Log.i("Michael", "secondTab is null");
                return;
            }
            TabLayout.Tab tab = sortTabLayout.getTabAt(1);
            if (tab == null || tab.getCustomView() == null) {
                Log.i("Michael", "SortItem is null ");
                return;
            }
            sortTabLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.sort_tab_layout_background));
            TextView tvTitle = tab.getCustomView().findViewById(R.id.top_tab_title);
            tvTitle.setTextColor(ContextCompat.getColor(context, R.color.green));
            tab.select();
        }
    };

    private View prepareView(String content) {
        View view = View.inflate(context, R.layout.list_top_tab_layout_custom_view, null);
        TextView tvTabTitle = view.findViewById(R.id.top_tab_title);
        tvTabTitle.setTextColor(ContextCompat.getColor(context, R.color.black));
        tvTabTitle.setTextSize(18);
        tvTabTitle.setText(content);
        return view;
    }

    private View prepare(String content) {
        View view = View.inflate(context, R.layout.list_top_tab_layout_custom_view, null);
        TextView tvTabTitle = view.findViewById(R.id.top_tab_title);
        LinearLayout itemArea = view.findViewById(R.id.item_area);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        float width = getResources().getDisplayMetrics().widthPixels;

        float singleItemSize = width / 3;

        float singleItemDp = singleItemSize / displayMetrics.density;

        int pix = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, singleItemDp, getResources().getDisplayMetrics());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pix, pix);

        itemArea.setLayoutParams(params);

        tvTabTitle.setText(content);
        return view;
    }
}