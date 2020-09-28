package com.money.moneyreminder.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.money.moneyreminder.R;
import com.money.moneyreminder.sort.SortActivity;
import com.money.moneyreminder.tool.DataProvider;

import java.util.ArrayList;

public class CalculatorActivity extends AppCompatActivity implements CalculatorActivityVu{


    private CalculatorActivityPresenter presenter;

    private RecyclerView rvNumber,rvMath;

    private TextView tvContent;

    private NumberAdapter numberAdapter;

    public static final String TOTAL_MONEY = "total_money";

    public static final String IS_INCOME = "is_income";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        initPresenter();
        initView();

        presenter.onActivityCreate();
    }

    private void initView() {
        tvContent = findViewById(R.id.calculator_content);
        ImageView ivBackBtn = findViewById(R.id.calculator_back_btn);
        rvNumber = findViewById(R.id.calculator_number_recycler_view);
        rvMath = findViewById(R.id.calculator_math_recycler_view);
        rvMath.setLayoutManager(new LinearLayoutManager(this));
        final RadioButton rdInCome = findViewById(R.id.calculator_radio_in_come);
        final RadioButton rdExpenditure = findViewById(R.id.calculator_expenditure);
        rdExpenditure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                if (isCheck){
                    rdExpenditure.setBackground(ContextCompat.getDrawable(CalculatorActivity.this,R.drawable.calculator_in_come_press));
                    rdInCome.setBackground(null);
                    rdInCome.setChecked(false);
                    tvContent.setTextColor(ContextCompat.getColor(CalculatorActivity.this,R.color.red));
                    presenter.onSetIsIncome(false);
                }
            }
        });
        rdInCome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                if (isCheck){
                    rdInCome.setBackground(ContextCompat.getDrawable(CalculatorActivity.this,R.drawable.calculator_in_come_press));
                    rdExpenditure.setBackground(null);
                    rdExpenditure.setChecked(false);
                    tvContent.setTextColor(ContextCompat.getColor(CalculatorActivity.this,R.color.bule));
                    presenter.onSetIsIncome(true);
                }
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                if (position == 12){
                    return 3;
                }

                return 1;
            }
        });
        rvNumber.setLayoutManager(gridLayoutManager);

//        handleRecyclerViewWidth();

        ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBackButtonClickListener();
            }
        });



    }

    private void handleRecyclerViewWidth() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        float width = getResources().getDisplayMetrics().widthPixels;

        float singleItemSize = width / 4;

        float singleItemDp = singleItemSize / displayMetrics.density;

        int pix = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,singleItemDp,getResources().getDisplayMetrics());
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(pix,0);
        rvMath.setLayoutParams(params);
    }

    private void initPresenter() {
        presenter = new CalculatorActivityPresenterImpl(this);
    }

    @Override
    public void showRecyclerView(ArrayList<String> numberArray, ArrayList<Drawable> mathArray) {
        //number
        numberAdapter = new NumberAdapter();
        numberAdapter.setData(numberArray);
        rvNumber.setAdapter(numberAdapter);
        numberAdapter.setOnNumberItemClickListener(new NumberAdapter.OnNumberItemClickListener() {
            @Override
            public void onClick(String number) {
                presenter.onNumberItemClickListener(number);
            }
        });
        //math
        MathAdapter mathAdapter = new MathAdapter();
        mathAdapter.setData(mathArray);
        rvMath.setAdapter(mathAdapter);

        mathAdapter.setOnNumberItemClickListener(new NumberAdapter.OnNumberItemClickListener() {
            @Override
            public void onClick(String mathItem) {
                presenter.onMathItemClickListener(mathItem);
            }
        });
    }

    @Override
    public void setCalculatorContent(String number) {
        tvContent.append(number);
    }

    @Override
    public void closePage() {
        finish();
    }

    @Override
    public void clearView() {
        tvContent.setText("0");
    }

    @Override
    public String getTvContent() {
        return tvContent.getText().toString();
    }

    @Override
    public void setCalculatorContentEmpty() {
        tvContent.setText("");
    }

    @Override
    public void intentToSortActivity(String tvContent, boolean isIncome) {
        //這邊進去分類表
        Intent it = new Intent(this, SortActivity.class);
        it.putExtra(TOTAL_MONEY,tvContent);
        it.putExtra(IS_INCOME, isIncome);
        startActivity(it);
        finish();
    }

    @Override
    public void upDateNumberList() {
        ArrayList<String> numberArray = DataProvider.getInstance().getNumberArray();
        numberArray.set(numberArray.size() - 1 ,"=");
        numberAdapter.setData(numberArray);
        numberAdapter.notifyDataSetChanged();
    }

    @Override
    public void setCalculatorContentBack(String numberContent) {
        tvContent.setText(numberContent);
    }

    @Override
    public void resetNumberList() {
        numberAdapter.setData(DataProvider.getInstance().getNumberArray());
        numberAdapter.notifyDataSetChanged();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}