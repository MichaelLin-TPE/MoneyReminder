package com.money.moneyreminder.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.money.moneyreminder.R;
import com.money.moneyreminder.tool.MoneyReminderApplication;

public class SettingDayRangeDialogFragment extends MoneyReminderDialogFragment {

    private RadioButton radOne,radFive,radTen;

    private OnDataSortButtonClickListener listener;

    private static final String SORT_TYPE = "sort_type";

    public static final String ONE = MoneyReminderApplication.getInstance().getApplicationContext().getString(R.string.sort_one);

    public static final String FIVE = MoneyReminderApplication.getInstance().getApplicationContext().getString(R.string.sort_five);

    public static final String TEN = MoneyReminderApplication.getInstance().getApplicationContext().getString(R.string.sort_ten);

    private String sortType;

    public SettingDayRangeDialogFragment setOnDataSortButtonClickListener(OnDataSortButtonClickListener listener){
        this.listener = listener;
        return this;
    }

    public static SettingDayRangeDialogFragment newInstance(String sortType) {

        Bundle args = new Bundle();

        SettingDayRangeDialogFragment fragment = new SettingDayRangeDialogFragment();
        args.putString(SORT_TYPE,sortType);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.setting_day_range_dialog_layout,container,false);
        initView(view);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null){
            return;
        }
        sortType = getArguments().getString(SORT_TYPE,"");
    }

    private void initView(View view) {


        radOne = view.findViewById(R.id.day_range_dialog_rad_1);
        radFive = view.findViewById(R.id.day_range_dialog_rad_5);
        radTen = view.findViewById(R.id.day_range_dialog_rad_10);

        radFive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    radOne.setChecked(false);
                    radTen.setChecked(false);
                    sortType = radFive.getText().toString();
                }
            }
        });
        radOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    radFive.setChecked(false);
                    radTen.setChecked(false);
                    sortType = radOne.getText().toString();
                }
            }
        });
        radTen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    radFive.setChecked(false);
                    radOne.setChecked(false);
                    sortType = radTen.getText().toString();
                }
            }
        });
        radFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onChecked(sortType);
            }
        });
        radOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onChecked(sortType);
            }
        });
        radTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onChecked(sortType);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (sortType.isEmpty()){
            radOne.setChecked(true);
            radFive.setChecked(false);
            radTen.setChecked(false);
            return;
        }
        if (sortType.equals(ONE)){
            radOne.setChecked(true);
            radFive.setChecked(false);
            radTen.setChecked(false);
            return;
        }
        if (sortType.equals(FIVE)){
            radOne.setChecked(false);
            radFive.setChecked(true);
            radTen.setChecked(false);
            return;
        }
        radOne.setChecked(false);
        radFive.setChecked(false);
        radTen.setChecked(true);

    }

    public interface OnDataSortButtonClickListener{
        void onChecked(String sortType);
    }
}
