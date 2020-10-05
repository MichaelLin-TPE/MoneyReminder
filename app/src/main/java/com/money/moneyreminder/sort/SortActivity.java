package com.money.moneyreminder.sort;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.money.moneyreminder.R;
import com.money.moneyreminder.sort_list.SortListActivity;
import com.money.moneyreminder.sort_list.presenter.SortTypeData;
import com.money.moneyreminder.tool.ImageLoaderProvider;

import java.util.ArrayList;
import java.util.Locale;

import static com.money.moneyreminder.calculator.CalculatorActivity.IS_INCOME;
import static com.money.moneyreminder.calculator.CalculatorActivity.TOTAL_MONEY;
import static com.money.moneyreminder.calendar_fragment.CalendarFragment.CURRENT_TIME;

public class SortActivity extends AppCompatActivity implements SortActivityVu{

    private SortActivityPresenter presenter;

    private int totalMoney;

    private boolean isIncome;

    private TextView tvDate,tvSort,tvNoData;

    private EditText edDescription;

    private String choiceTime , currentTime;

    private ImageView ivIcon;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        initPresenter();
        initBundle();
        initView();
        presenter.onActivityCreate(totalMoney,isIncome,currentTime);
    }

    private void initView() {
        tvNoData = findViewById(R.id.sort_recently_describe_no_data);
        ImageView ivBack = findViewById(R.id.sort_back_btn);
        ImageView ivSave = findViewById(R.id.sort_save_btn);
        ConstraintLayout sortArea = findViewById(R.id.sort_chose_area);
        ConstraintLayout dateArea = findViewById(R.id.sort_date_area);
        tvDate = findViewById(R.id.sort_date_content);
        ivIcon = findViewById(R.id.sort_chose_icon);
        tvSort = findViewById(R.id.sort_chose_content);
        edDescription = findViewById(R.id.sort_description);
        recyclerView = findViewById(R.id.sort_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sortArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.setOnSortAreaClickListener();
            }
        });
        dateArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.setOnDateAreaClickListener();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBackButtonClickListener();
            }
        });
        ivSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onSaveButtonClickListener();
            }
        });
    }

    private void initBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }
        totalMoney = Integer.parseInt(bundle.getString(TOTAL_MONEY,""));
        isIncome = bundle.getBoolean(IS_INCOME,false);
        currentTime = bundle.getString(CURRENT_TIME,"");
    }

    private void initPresenter() {
        presenter = new SortActivityPresenterImpl(this);
    }

    @Override
    public void setCurrentTime(String currentTime) {
        tvDate.setText(currentTime);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void showDatePicker() {

        DatePicker datePicker = new DatePicker(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(datePicker)
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.onDateConfirmClickListener(choiceTime);
                    }
                }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
        dialog.show();
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int date) {
                String monthStr;
                String yearStr;
                String dateStr;
                if ((month + 1) < 10) {
                    monthStr = "0" + (month + 1);
                } else {
                    monthStr = (month + 1) + "";
                }
                if ((date) < 10) {
                    dateStr = "0" + date;
                } else {
                    dateStr = date + "";
                }
                yearStr = year + "";
                choiceTime = String.format(Locale.getDefault(), "%s/%s/%s", yearStr, monthStr, dateStr);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null){
            Bundle bundle = data.getExtras();
            if (bundle == null){
                Log.i("Michael","bundle ActivityResult is null");
                return;
            }

            SortTypeData sortTypeData = (SortTypeData) bundle.getSerializable("sortType");
            if (sortTypeData == null){
                Log.i("Michael","sortTypeData is null");
                return;
            }
            String describe = bundle.getString("describe","");
            presenter.onCatchSortTypeData(sortTypeData,describe);
        }else {
            Log.i("Michael","收不到資料");
        }
    }

    @Override
    public void closePage() {
        finish();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getDescription() {
        return edDescription.getText().toString();
    }

    @Override
    public void intentToSortListActivity() {
        Intent it = new Intent(this, SortListActivity.class);
        startActivityForResult(it,100);
    }

    @Override
    public void showSortType(SortTypeData sortTypeData) {
        tvSort.setText(sortTypeData.getSortType());
        ImageLoaderProvider.getInstance().setImage(sortTypeData.getIconUrl(),ivIcon);
    }

    @Override
    public void setDescribeRecyclerView(ArrayList<String> describeArray) {
        SortAdapter adapter = new SortAdapter(describeArray);
        recyclerView.setAdapter(adapter);
        adapter.setOnDescribeItemClickListener(new SortAdapter.OnDescribeItemClickListener() {
            @Override
            public void onClick(String describe) {
                presenter.onDescribeItemClickListener(describe);
            }
        });
    }

    @Override
    public void setDescribe(String describe) {
        edDescription.setText(describe);
    }

    @Override
    public void showTvNoData(boolean isShow) {
        tvNoData.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}