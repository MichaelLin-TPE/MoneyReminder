package com.money.moneyreminder.dialog;

import android.content.Context;
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

public class SettingSortAnalysisDialogFragment extends MoneyReminderDialogFragment {

    private RadioButton radFar,radNear;

    private OnDataSortButtonClickListener listener;

    private static final String SORT_TYPE = "sort_type";

    private String sortType;

    private Context context;

    public SettingSortAnalysisDialogFragment setOnDataSortButtonClickListener(OnDataSortButtonClickListener listener){
        this.listener = listener;
        return this;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public static SettingSortAnalysisDialogFragment newInstance(String sortType) {

        Bundle args = new Bundle();

        SettingSortAnalysisDialogFragment fragment = new SettingSortAnalysisDialogFragment();
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


        radFar.setText(context.getString(R.string.long_analysis));
        radNear.setText(context.getString(R.string.pie_analysis));


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
                if (listener == null){
                    return;
                }
                listener.onChecked(sortType);
                dismiss();
            }
        });
        radFar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener == null){
                    return;
                }
                listener.onChecked(sortType);
                dismiss();
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
        if (sortType.equals(context.getString(R.string.long_analysis))){
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
