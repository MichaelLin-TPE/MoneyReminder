package com.money.moneyreminder.tool;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.money.moneyreminder.R;

public class SettingBudgetDialogFragment extends MoneyReminderDialogFragment {


    private EditText editText;

    private TextView tvConfirm,tvCancel;

    private OnBudgetDialogButtonClickListener listener;

    public SettingBudgetDialogFragment setOnBudgetDialogButtonClickListener(OnBudgetDialogButtonClickListener listener){
        this.listener = listener;
        return this;
    }


    public static SettingBudgetDialogFragment newInstance() {

        Bundle args = new Bundle();

        SettingBudgetDialogFragment fragment = new SettingBudgetDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.setting_budget_dialog_layout,container,false);
        initView(view);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        //無背景設定
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setStyle(DialogFragment.STYLE_NO_FRAME,android.R.style.Theme);
        return dialog;
    }



    @Override
    public void onResume() {
        super.onResume();
        //設定DIALOG 寬度
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = DpConvertTool.getInstance().getDb(350);
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes(params);
    }

    private void initView(View view) {
        editText = view.findViewById(R.id.budget_dialog_edit);
        tvConfirm = view.findViewById(R.id.budget_dialog_confirm);
        tvCancel = view.findViewById(R.id.budget_dialog_cancel);

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onConfirmClick(editText.getText().toString());
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancelClick();
                dismiss();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public interface OnBudgetDialogButtonClickListener{
        void onConfirmClick(String content);
        void onCancelClick();
    }

}
