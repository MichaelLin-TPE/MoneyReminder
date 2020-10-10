package com.money.moneyreminder.user_fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.money.moneyreminder.MainActivity;
import com.money.moneyreminder.R;
import com.money.moneyreminder.dialog.SettingDayRangeDialogFragment;
import com.money.moneyreminder.dialog.SettingSortAnalysisDialogFragment;
import com.money.moneyreminder.list_fragment.CustomDecoration;
import com.money.moneyreminder.dialog.SecondSortAdapter;
import com.money.moneyreminder.dialog.SettingBudgetDialogFragment;
import com.money.moneyreminder.dialog.SettingDataSortDialogFragment;
import com.money.moneyreminder.tool.UserManager;
import com.money.moneyreminder.user_fragment.view_presenter.BudgetViewHolder;
import com.money.moneyreminder.user_fragment.view_presenter.ViewPresenter;
import com.money.moneyreminder.user_fragment.view_presenter.ViewPresenterImpl;

import java.util.ArrayList;


public class UserFragment extends Fragment implements UserFragmentVu {

    private UserFragmentPresenter presenter;

    private Context context;

    private ViewPresenter viewPresenter;

    private TextView tvName;

    private RecyclerView recyclerView;

    private UserAdapter adapter;

    private ProgressBar progressBar;

    private FragmentActivity fragmentActivity;

    private long budgetMoney,  totalExpenditure, monthMoney;

    private int expenditurePercent;

    private ArrayList<String> accountItemArray;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        fragmentActivity = (FragmentActivity) context;
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
        progressBar = view.findViewById(R.id.user_progress_bar);
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
    public void showRecyclerView(long budgetMoney, long totalExpenditure, int expenditurePercent, long monthMoney, ArrayList<String> accountItemArray) {
        this.accountItemArray = accountItemArray;
        this.budgetMoney = budgetMoney;
        this.totalExpenditure = totalExpenditure;
        this.expenditurePercent = expenditurePercent;
        this.monthMoney = monthMoney;
        ArrayList<String> accountSettingArray = new ArrayList<>();
        accountSettingArray.add(UserManager.getInstance().getDayRange());
        accountSettingArray.add(UserManager.getInstance().getSortType());
        accountSettingArray.add(UserManager.getInstance().getSortAnalysisType());
        viewPresenter.setData(budgetMoney,totalExpenditure,expenditurePercent,monthMoney,accountItemArray,accountSettingArray);
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
        adapter.setOnAccountItemClickListener(new SecondSortAdapter.OnAccountItemClickListener() {
            @Override
            public void onClick(String itemName) {
                presenter.onAccountItemClickListener(itemName);
            }
        });
    }

    @Override
    public void showBudgetDialog() {

        SettingBudgetDialogFragment.newInstance().setOnBudgetDialogButtonClickListener(new SettingBudgetDialogFragment.OnBudgetDialogButtonClickListener() {
            @Override
            public void onConfirmClick(String content) {
                presenter.onSetBudgetMoneyConfirmClickListener(content);
            }

            @Override
            public void onCancelClick() {
                //這邊不做任何事情
            }
        }).show(fragmentActivity.getSupportFragmentManager(),"dialog");
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar(boolean isShow) {
        progressBar.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showLogoutDialog() {
        androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle(getString(R.string.logout_title))
                .setMessage(getString(R.string.logout_content))
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.onLogoutConfirmClickListener();
                    }
                }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
        dialog.show();
    }

    @Override
    public void intentToMainActivity() {
        Intent it = new Intent(context, MainActivity.class);
        context.startActivity(it);
        ((Activity)context).finish();
    }

    @Override
    public void showDataSortDialog() {
        final SettingDataSortDialogFragment sortDialogFragment = SettingDataSortDialogFragment.newInstance(UserManager.getInstance().getSortType());
        sortDialogFragment.setOnDataSortButtonClickListener(new SettingDataSortDialogFragment.OnDataSortButtonClickListener() {
            @Override
            public void onChecked(String sortType) {
                presenter.onDataSortClickListener(sortType);
                sortDialogFragment.dismiss();
            }
        });
        sortDialogFragment.show(fragmentActivity.getSupportFragmentManager(),"dialog");

    }

    @Override
    public void saveSortType(String sortType) {
        UserManager.getInstance().saveDataSort(sortType);
    }

    @Override
    public void showDayRangeDialog() {
        final SettingDayRangeDialogFragment dayRangeDialogFragment = SettingDayRangeDialogFragment.newInstance(UserManager.getInstance().getDayRange());
        dayRangeDialogFragment.setOnDataSortButtonClickListener(new SettingDayRangeDialogFragment.OnDataSortButtonClickListener() {
            @Override
            public void onChecked(String sortType) {
                presenter.onDayRangeButtonClickListener(sortType);
                dayRangeDialogFragment.dismiss();
            }
        });
        dayRangeDialogFragment.show(fragmentActivity.getSupportFragmentManager(),"dialog");
    }

    @Override
    public void saveDayRange(String sortType) {
        UserManager.getInstance().saveDayRange(sortType);
    }

    @Override
    public void showSortAnalysisDialog() {
        SettingSortAnalysisDialogFragment.newInstance(UserManager.getInstance().getSortAnalysisType()).setOnDataSortButtonClickListener(new SettingSortAnalysisDialogFragment.OnDataSortButtonClickListener() {
            @Override
            public void onChecked(String sortType) {
                presenter.onSortAnalysisButtonClickListener(sortType);
            }
        }).show(fragmentActivity.getSupportFragmentManager(),"dialog");
    }

    @Override
    public void saveSortAnalysis(String sortType) {
        UserManager.getInstance().saveSortAnalysisType(sortType);
    }

    @Override
    public void updateRecyclerView(ArrayList<String> accountSettingArray) {
        viewPresenter.setData(budgetMoney,totalExpenditure,expenditurePercent,monthMoney,accountItemArray,accountSettingArray);
        adapter.setViewPresenter(viewPresenter);
        adapter.notifyDataSetChanged();
    }
}