package com.money.moneyreminder.sort_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.money.moneyreminder.R;
import com.money.moneyreminder.sort_list.presenter.CreateAdapter;
import com.money.moneyreminder.sort_list.presenter.RecentlyAdapter;
import com.money.moneyreminder.sort_list.presenter.SecondSortContentAdapter;
import com.money.moneyreminder.sort_list.presenter.SecondSortViewHolder;
import com.money.moneyreminder.sort_list.presenter.SortCreateData;
import com.money.moneyreminder.sort_list.presenter.SortData;
import com.money.moneyreminder.sort_list.presenter.SortPresenter;
import com.money.moneyreminder.sort_list.presenter.SortPresenterImpl;
import com.money.moneyreminder.sort_list.presenter.SortRecentlyData;
import com.money.moneyreminder.sort_list.presenter.SortTypeData;
import com.money.moneyreminder.dialog.SecondSortDialogFragment;

import java.util.ArrayList;

public class SortListActivity extends AppCompatActivity implements SortListActivityVu {

    private SortListActivityPresenter presenter;

    private SortPresenter sortPresenter;

    private RecyclerView recyclerView;

    private ProgressBar progressBar;

    private SortListAdapter sortListAdapter;


    public static final int RESULT_OK = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_list);
        initPresenter();
        initView();
        presenter.onActivityCreate();
    }

    private void initView() {
        recyclerView = findViewById(R.id.sort_list_recycler_view);
        progressBar = findViewById(R.id.sort_list_progress);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ImageView addBtn = findViewById(R.id.sort_list_add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onAddButtonClickListener();
            }
        });
        ImageView ivBack = findViewById(R.id.sort_list_back_btn);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBackButtonClickListener();
            }
        });
        ImageView ivSave = findViewById(R.id.sort_list_save_btn);
        ivSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onSaveButtonClickListener();
            }
        });
    }

    private void initPresenter() {
        presenter = new SortListActivityPresenterImpl(this);
        sortPresenter = new SortPresenterImpl();
    }

    @Override
    public void setRecyclerView(SortData sortData) {
        sortPresenter.setData(sortData);
        sortListAdapter = new SortListAdapter();
        sortListAdapter.setSortPresenter(sortPresenter);
        recyclerView.setAdapter(sortListAdapter);
        sortListAdapter.setOnSortTypeSelectListener(new CreateAdapter.OnSortTypeSelectListener() {
            @Override
            public void onSelected(SortCreateData data) {
                presenter.onSortTypeSelectListener(data);
            }
        });
        sortListAdapter.setOnSortTypeRecentlyListener(new RecentlyAdapter.OnSortTypeRecentlySelectListener() {
            @Override
            public void onSelected(SortRecentlyData data) {
                presenter.onSortTypeRecentlyListener(data);
            }
        });
        sortListAdapter.setOnDescriptionItemClickListener(new SecondSortContentAdapter.OnDescriptionItemClickListener() {
            @Override
            public void onClick(String content) {
                presenter.onDescriptionItemClickListener(content);
            }
        });
        sortListAdapter.setOnAddIconClickListener(new SecondSortViewHolder.OnAddIconClickListener() {
            @Override
            public void onClick() {
                presenter.onAddIconClickListener();
            }
        });
    }

    @Override
    public void closePage() {
        finish();
    }

    @Override
    public void showProgress(boolean isShow) {
        progressBar.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showAddFragmentDialog() {
        SortTypeDialogFragment.newInstance().setOnSortTypeConfirmClickListener(new SortTypeDialogFragment.OnSortTypeConfirmClickListener() {
            @Override
            public void onClick(String edContent, String iconUrl) {
                presenter.onSortTypeConfirmClickListener(edContent,iconUrl);
            }
        }).show(getSupportFragmentManager(),"dialog");
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void saveSortType(SortTypeData sortTypeData, String describe) {
        Intent it = new Intent();
        it.putExtra("sortType",sortTypeData);
        it.putExtra("describe",describe);
        this.setResult(RESULT_OK,it);
        finish();
    }

    @Override
    public void showSecondSortDialog(String edContent) {
        SecondSortDialogFragment.newInstance(edContent).setOnSecondSortSaveButtonClickListener(new SecondSortDialogFragment.OnSecondSortTypeSaveButtonClickListener() {
            @Override
            public void onClick(ArrayList<String> secondSortContentArray,String sortTitle) {
                presenter.onSecondSortSaveButtonClickListener(secondSortContentArray,sortTitle);
            }
        }).show(getSupportFragmentManager(),"dialog");
    }

    @Override
    public void showSecondSortRecyclerView(ArrayList<String> contentArray) {
        sortPresenter.setSecondSortData(contentArray);
        sortListAdapter.setSortPresenter(sortPresenter);
        sortListAdapter.notifyItemChanged(2);
    }

    @Override
    public void refreshView(int position) {
        sortListAdapter.notifyItemChanged(position);
    }
}