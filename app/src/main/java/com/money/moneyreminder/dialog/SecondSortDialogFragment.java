package com.money.moneyreminder.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.money.moneyreminder.R;
import com.money.moneyreminder.tool.DpConvertTool;

import java.util.ArrayList;

public class SecondSortDialogFragment extends MoneyReminderDialogFragment {


    private EditText edContent;

    private TextView tvIgnore;

    private Button btnAdd,btnSave;

    private RecyclerView recyclerView;

    private SecondSortAdapter adapter ;

    private String sortTitle;

    private Context context;


    private ArrayList<String> secondSortContentArray;

    private OnSecondSortTypeSaveButtonClickListener listener;

    private static final String SORT_TITLE = "sort_title";


    public static SecondSortDialogFragment newInstance(String sortType){
        Bundle args = new Bundle();
        SecondSortDialogFragment fragment = new SecondSortDialogFragment();
        args.putString(SORT_TITLE,sortType);
        fragment.setArguments(args);
        return fragment;
    }

    public  SecondSortDialogFragment setOnSecondSortSaveButtonClickListener(OnSecondSortTypeSaveButtonClickListener listener){
        this.listener = listener;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.second_sort_type_dialog_custom_view,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tvIgnore = view.findViewById(R.id.second_sort_type_ignore);
        edContent = view.findViewById(R.id.second_sort_type_edContent);
        btnAdd = view.findViewById(R.id.second_sort_type_btn_add);
        btnSave = view.findViewById(R.id.second_sort_type_btn_save);
        recyclerView = view.findViewById(R.id.second_sort_type_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));


        tvIgnore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (secondSortContentArray == null || secondSortContentArray.isEmpty()){
                    Toast.makeText(context,"請新增次分類",Toast.LENGTH_LONG).show();
                    return;
                }
                listener.onClick(secondSortContentArray,sortTitle);
                dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edContent.getText().toString();
                if (content.isEmpty()){
                    Toast.makeText(context,"請輸入次分類描述",Toast.LENGTH_LONG).show();
                    return;
                }
                edContent.setText("");
                if (secondSortContentArray == null){
                    secondSortContentArray = new ArrayList<>();
                    secondSortContentArray.add(content);
                    adapter = new SecondSortAdapter();
                    adapter.setSecondSortContentArray(secondSortContentArray);
                    recyclerView.setAdapter(adapter);
                    return;
                }
                secondSortContentArray.add(content);
                adapter.setSecondSortContentArray(secondSortContentArray);
                adapter.notifyDataSetChanged();

            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null){
            return;
        }
        sortTitle = getArguments().getString(SORT_TITLE,"");
        Log.i("Michael","主分類為："+sortTitle);
    }
    public interface OnSecondSortTypeSaveButtonClickListener{
        void onClick(ArrayList<String> secondSortContentArray,String sortTitle);
    }
}
