package com.money.moneyreminder.sort_list;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.money.moneyreminder.R;
import com.money.moneyreminder.tool.FirebaseHandler;
import com.money.moneyreminder.tool.FirebaseHandlerImpl;
import com.money.moneyreminder.tool.MichaelLog;

import java.util.ArrayList;

public class SortTypeDialogFragment extends DialogFragment {


    private EditText edContent;

    private RecyclerView recyclerView;

    private FirebaseHandler firebaseHandler;

    private Button btnSave;

    public  OnSortTypeConfirmClickListener listener;

    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public SortTypeDialogFragment setOnSortTypeConfirmClickListener(OnSortTypeConfirmClickListener onConfirmListener){
        listener = onConfirmListener;
        return this;
    }

    private String iconUrl;


    public static SortTypeDialogFragment newInstance() {

        Bundle args = new Bundle();

        SortTypeDialogFragment fragment = new SortTypeDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseHandler = new FirebaseHandlerImpl();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sort_type_fragment_layout,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        btnSave = view.findViewById(R.id.sort_type_btn_save);
        edContent = view.findViewById(R.id.sort_type_edContent);
        recyclerView = view.findViewById(R.id.sort_type_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(context,3));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String edContentString = edContent.getText().toString();

                if (edContentString.isEmpty()){
                    Toast.makeText(context,"請輸入分類名稱",Toast.LENGTH_LONG).show();
                    return;
                }
                if (iconUrl == null || iconUrl.isEmpty()){
                    Toast.makeText(context,"請選擇一個ICON",Toast.LENGTH_LONG).show();
                    return;
                }
                listener.onClick(edContentString,iconUrl);
                dismiss();
            }
        });
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        //設定DIALOG 寬度
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes(params);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        firebaseHandler.getIconApi(onFireStoreCatchListener);
    }
    private FirebaseHandler.OnFireStoreCatchListener<ArrayList<IconData>> onFireStoreCatchListener = new FirebaseHandler.OnFireStoreCatchListener<ArrayList<IconData>>() {
        @Override
        public void onSuccess(ArrayList<IconData> dataArray) {
            MichaelLog.i("dataArray is not null");
            final SortTypeAdapter adapter = new SortTypeAdapter();
            adapter.setData(dataArray);
            recyclerView.setAdapter(adapter);
            adapter.setOnSortTypeItemClickListener(new OnSortTypeItemClickListener() {
                @Override
                public void onClick(String iconUrl) {
                    SortTypeDialogFragment.this.iconUrl = iconUrl;
                    adapter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public void onFail(String errorCode) {
            MichaelLog.i("取不到資料 : "+errorCode);
        }
    };

    public interface OnSortTypeItemClickListener{
        void onClick(String iconUrl);
    }
    public interface OnSortTypeConfirmClickListener{
        void onClick(String edContent,String iconUrl);
    }
}
