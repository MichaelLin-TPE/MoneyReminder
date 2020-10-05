package com.money.moneyreminder.user_fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.money.moneyreminder.R;
import com.money.moneyreminder.list_fragment.CustomDecoration;
import com.money.moneyreminder.user_fragment.view_presenter.BudgetViewHolder;
import com.money.moneyreminder.user_fragment.view_presenter.ViewPresenter;
import com.money.moneyreminder.user_fragment.view_presenter.ViewPresenterImpl;


public class UserFragment extends Fragment implements UserFragmentVu {

    private UserFragmentPresenter presenter;

    private Context context;

    private ViewPresenter viewPresenter;

    private TextView tvName;

    private RecyclerView recyclerView;

    private UserAdapter adapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
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
        presenter = new UserFragmentPresenterImpl(this);
        viewPresenter = new ViewPresenterImpl();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tvName = view.findViewById(R.id.user_name);
        recyclerView = view.findViewById(R.id.user_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        int pix = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,20,context.getResources().getDisplayMetrics());
        recyclerView.addItemDecoration(new CustomDecoration(pix));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.onActivityCreated();
    }

    @Override
    public void setTitle(String title) {
        tvName.setText(title);
    }

    @Override
    public void showRecyclerView(long budgetMoney, long totalExpenditure, int expenditurePercent, long monthMoney) {
        viewPresenter.setData(budgetMoney,totalExpenditure,expenditurePercent,monthMoney);
        if (adapter == null){
            adapter = new UserAdapter();
            adapter.setViewPresenter(viewPresenter);
            recyclerView.setAdapter(adapter);
        }else {
            adapter.setViewPresenter(viewPresenter);
            adapter.notifyDataSetChanged();
        }
        adapter.setOnSettingButtonClickListener(new BudgetViewHolder.OnSettingButtonClickListener() {
            @Override
            public void onClick() {
                presenter.onSettingButtonClickListener();
            }
        });
    }

    @Override
    public void showBudgetDialog() {
        final EditText editText = new EditText(context);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setHint(context.getString(R.string.enter_budget));
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.setting))
                .setView(editText)
                .setPositiveButton(context.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.onSetBudgetMoneyConfirmClickListener(editText.getText().toString());
                    }
                }).setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
        dialog.show();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
}