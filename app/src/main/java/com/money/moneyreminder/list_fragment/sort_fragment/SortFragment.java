package com.money.moneyreminder.list_fragment.sort_fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.money.moneyreminder.R;
import com.money.moneyreminder.dialog.DetailListDialogFragment;
import com.money.moneyreminder.sort.MoneyData;
import com.money.moneyreminder.sort.MoneyObject;
import com.money.moneyreminder.tool.DataProvider;
import com.money.moneyreminder.tool.MyValueFormatter;
import com.money.moneyreminder.tool.UserManager;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class SortFragment extends Fragment implements SortFragmentVu, OnChartValueSelectedListener {

    private SortFragmentPresenter presenter;

    private RecyclerView recyclerView;

    private Context context;

    private ArrayList<MoneyObject> moneyObjectArrayList;

    private boolean isIncome;

    private TextView tvNoData;

    private PieChart pieChart;

    private FragmentActivity fragmentActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        fragmentActivity = (FragmentActivity) context;
    }

    public static SortFragment newInstance(ArrayList<MoneyObject> moneyObjectArrayList,boolean isIncome) {
        SortFragment fragment = new SortFragment();
        Bundle args = new Bundle();
        args.putSerializable("money",moneyObjectArrayList);
        args.putBoolean("is_income",isIncome);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
        initBundle();
    }

    private void initBundle() {
        if (getArguments() == null){
            Log.i("Michael","getArguments is null");
            return;
        }
        moneyObjectArrayList = (ArrayList<MoneyObject>) getArguments().getSerializable("money");
        isIncome = getArguments().getBoolean("is_income",false);
    }

    private void initPresenter() {
        presenter = new SortFragmentPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sort, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        pieChart = view.findViewById(R.id.sort_fragment_pie_chart);
        recyclerView = view.findViewById(R.id.sort_fragment_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        tvNoData = view.findViewById(R.id.sort_fragment_no_data);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.onActivityCreated(moneyObjectArrayList,isIncome);
    }

    @Override
    public void setRecyclerView(ArrayList<SortPercentData> sortTypeArray, boolean isIncome) {
        SortTypePercentAdapter adapter = new SortTypePercentAdapter(sortTypeArray,isIncome);
        recyclerView.setAdapter(adapter);
        adapter.setOnNumberOfCaseButtonClickListener(new SortTypePercentAdapter.OnNumberOfCaseButtonClickListener() {
            @Override
            public void onClick(String sortType) {
                presenter.onNumberOfCaseButtonClickListener(sortType);
            }
        });
    }

    @Override
    public void showNoDataView(boolean isShow) {
        tvNoData.setVisibility(isShow ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(isShow ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showDetailListDialog(String sortType, ArrayList<MoneyData> moneyDataArrayList) {
        DetailListDialogFragment detailListDialogFragment = DetailListDialogFragment.newInstance(sortType,moneyDataArrayList);
        detailListDialogFragment.show(fragmentActivity.getSupportFragmentManager(),"dialog");
    }

    @Override
    public String getSortAnalysisType() {
        return UserManager.getInstance().getSortAnalysisType();
    }

    @Override
    public void pieChart(ArrayList<SortPercentData> sortTypeArray) {
        pieChart.setUsePercentValues(true);
        pieChart.setBackgroundColor(Color.WHITE);
        pieChart.setRotation(0);
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);


        pieChart.setDrawEntryLabels(true);              //设置pieChart是否只显示饼图上百分比不显示文字（true：下面属性才有效果）
        pieChart.setEntryLabelColor(Color.WHITE);       //设置pieChart图表文本字体颜色
        pieChart.setEntryLabelTextSize(18f);            //设置pieChart图表文本字体大小

        // 设置 pieChart 内部圆环属性
        pieChart.setDrawHoleEnabled(true);              //是否显示PieChart内部圆环(true:下面属性才有意义)
        pieChart.setHoleRadius(28f);                    //设置PieChart内部圆的半径(这里设置28.0f)
        pieChart.setTransparentCircleRadius(31f);       //设置PieChart内部透明圆的半径(这里设置31.0f)
        pieChart.setTransparentCircleColor(Color.BLACK);//设置PieChart内部透明圆与内部圆间距(31f-28f)填充颜色
        pieChart.setTransparentCircleAlpha(50);         //设置PieChart内部透明圆与内部圆间距(31f-28f)透明度[0~255]数值越小越透明
        pieChart.setHoleColor(ContextCompat.getColor(context,R.color.grey));             //设置PieChart内部圆的颜色
        pieChart.setDrawCenterText(true);               //是否绘制PieChart内部中心文本（true：下面属性才有意义）
        pieChart.setCenterText(isIncome ? "收入" : "支出");                 //设置PieChart内部圆文字的内容
        pieChart.setCenterTextSize(18f);                //设置PieChart内部圆文字的大小
        pieChart.setCenterTextColor(Color.RED);         //设置PieChart内部圆文字的颜色

        setPieChartData(sortTypeArray);

        //監聽點擊事件
        pieChart.setOnChartValueSelectedListener(this);

//        Legend legend = pieChart.getLegend();
//
//        legend.setEnabled(true);                    //是否启用图列（true：下面属性才有意义）
//        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
//        legend.setForm(Legend.LegendForm.DEFAULT); //设置图例的形状
//        legend.setFormSize(10);					  //设置图例的大小
//        legend.setFormToTextSpace(10f);			  //设置每个图例实体中标签和形状之间的间距
//        legend.setDrawInside(false);
//        legend.setWordWrapEnabled(true);			  //设置图列换行(注意使用影响性能,仅适用legend位于图表下面)
//        legend.setXEntrySpace(10f);				  //设置图例实体之间延X轴的间距（setOrientation = HORIZONTAL有效）
//        legend.setYEntrySpace(8f);				  //设置图例实体之间延Y轴的间距（setOrientation = VERTICAL 有效）
//        legend.setYOffset(0f);					  //设置比例块Y轴偏移量
//        legend.setTextSize(14f);					  //设置图例标签文本的大小
//        legend.setTextColor(Color.parseColor("#ff9933"));//设置图例标签文本的颜色

    }

    @Override
    public void changeView(boolean isShow) {
        recyclerView.setVisibility(isShow ? View.GONE : View.VISIBLE);
        pieChart.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    private void setPieChartData(ArrayList<SortPercentData> sortTypeArray) {
        ArrayList<PieEntry> pieEntryArrayList = new ArrayList<>();
        ArrayList<Integer> colorArray = DataProvider.getInstance().getColorArray(sortTypeArray.size());

        //餅圖實體 PieEntry
        for (SortPercentData data : sortTypeArray){
            PieEntry pieEntry = new PieEntry(data.getPercent(),data.getTitle());
            pieEntryArrayList.add(pieEntry);
        }
        //最終數據 PieData
        PieDataSet pieDataSet = new PieDataSet(pieEntryArrayList,isIncome ? "收入" : "支出");
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(15f);
        pieDataSet.setColors(colorArray);

        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(true);            //设置是否显示数据实体(百分比，true:以下属性才有意义)
        pieData.setValueTextColor(Color.WHITE);  //设置所有DataSet内数据实体（百分比）的文本颜色
        pieData.setValueTextSize(20f);          //设置所有DataSet内数据实体（百分比）的文本字体大小
        DecimalFormat decimalFormat = new DecimalFormat("###,###,##0.0");
        pieData.setValueFormatter(new MyValueFormatter(decimalFormat,pieChart));//设置所有DataSet内数据实体（百分比）的文本字体格式
        pieChart.setData(pieData);
        pieChart.highlightValues(null);
        pieChart.invalidate();



    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        PieEntry pieEntry = (PieEntry) e;
        presenter.onPieChatItemClickListener(pieEntry.getLabel());
    }

    @Override
    public void onNothingSelected() {

    }
}