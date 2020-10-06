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

public class SettingDataSortDialogFragment extends MoneyReminderDialogFragment {

    private RadioButton radFar,radNear;

    private OnDataSortButtonClickListener listener;

    private static final String SORT_TYPE = "sort_type";

    public static final String FAR = MoneyReminderApplication.getInstance().getApplicationContext().getString(R.string.sort_far);

    public static final String NEAR = MoneyReminderApplication.getInstance().getApplicationContext().getString(R.string.sort_near);

    private String sortType;

    public SettingDataSortDialogFragment setOnDataSortButtonClickListener(OnDataSortButtonClickListener listener){
        this.listener = listener;
        return this;
    }

    public static SettingDataSortDialogFragment newInstance(String sortType) {

        Bundle args = new Bundle();

        SettingDataSortDialogFragment fragment = new SettingDataSortDialogFragment();
        args.putString(SORT_TYPE,sortType);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.setting_data_sort_dialog_layout,container,false);
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


        radFar = view.findViewById(R.id.data_sort_dialog_rad_far);
        radNear = view.findViewById(R.id.data_sort_dialog_rad_near);

        radNear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    radFar.setChecked(false);
                    sortType = radNear.getText().toString();
                }
            }
        });
        radFar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    radNear.setChecked(false);
                    sortType = radFar.getText().toString();
                }
            }
        });
        radNear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onChecked(sortType);
            }
        });
        radFar.setOnClickListener(new View.OnClickListener() {
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
            radNear.setChecked(true);
            radFar.setChecked(false);
            return;
        }
        if (sortType.equals(FAR)){
            radFar.setChecked(true);
            radNear.setChecked(false);
            return;
        }
        radFar.setChecked(false);
        radNear.setChecked(true);

    }

    public interface OnDataSortButtonClickListener{
        void onChecked(String sortType);
    }
}
