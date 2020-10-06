package com.money.moneyreminder.money;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.money.moneyreminder.MainActivity;
import com.money.moneyreminder.R;
import com.money.moneyreminder.tool.DataProvider;

import java.util.ArrayList;

public class MoneyActivity extends AppCompatActivity implements MoneyActivityVu{

    private MoneyActivityPresenter presenter;

    private TabLayout tabLayout;

    private ImageView ivTabIcon;

    private ArrayList<String> tabTitleArray;

    private ArrayList<Drawable> tabIconNotPressArray , tabIconPressArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        initPresenter();
        initView();
        presenter.onActivityCreate();
    }

    private void initView() {

        tabLayout = findViewById(R.id.money_tab_layout);

    }

    private void initPresenter() {
        presenter = new MoneyActivityPresenterImpl(this);
    }



    @Override
    public void showTabLayout() {
        tabTitleArray = DataProvider.getInstance().getTabTitleArray();
        tabIconNotPressArray = DataProvider.getInstance().getTabIconNotPressArray();
        tabIconPressArray = DataProvider.getInstance().getTabIconPressArray();
        tabLayout.removeAllTabs();

        for (int i = 0 ; i < tabTitleArray.size() ; i ++){
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setCustomView(prepareView(tabTitleArray.get(i),tabIconNotPressArray.get(i)));
            tab.setTag(tabTitleArray.get(i));
            tabLayout.addTab(tab);
        }

        TabLayout.Tab firstTab = tabLayout.getTabAt(0);
        if (firstTab == null || firstTab.getCustomView() == null){
            Log.i("Michael","FirstTab is null");
            return;
        }
        ivTabIcon = firstTab.getCustomView().findViewById(R.id.bottom_tab_icon);
        ivTabIcon.setImageResource(R.drawable.list_press);
        firstTab.select();
        presenter.onTabSelectClickListener(0);

        tabLayout.addOnTabSelectedListener(onTabSelectedListener);

    }

    @Override
    public void replaceFragment(int tabItemPosition) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.money_frame_layout, DataProvider.getInstance().getAllFragmentArray().get(tabItemPosition)).commit();
    }

    private TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int position = tab.getPosition();
            TabLayout.Tab singleTab = tabLayout.getTabAt(position);
            if (singleTab == null || singleTab.getCustomView() == null){
                Log.i("Michael","singleTab is null");
                return;
            }
            ivTabIcon = singleTab.getCustomView().findViewById(R.id.bottom_tab_icon);
            ivTabIcon.setImageDrawable(tabIconPressArray.get(position));
            presenter.onTabSelectClickListener(position);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            int position = tab.getPosition();
            TabLayout.Tab singleTab = tabLayout.getTabAt(position);
            if (singleTab == null || singleTab.getCustomView() == null){
                Log.i("Michael","singleTab is null");
                return;
            }
            ivTabIcon = singleTab.getCustomView().findViewById(R.id.bottom_tab_icon);
            ivTabIcon.setImageDrawable(tabIconNotPressArray.get(position));
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };


    private View prepareView(String title , Drawable icon){
        View view = View.inflate(this, R.layout.money_bottom_tab_layout_custom_view, null);
        TextView tvTabTitle = view.findViewById(R.id.bottom_tab_title);
        ivTabIcon = view.findViewById(R.id.bottom_tab_icon);
        tvTabTitle.setText(title);
        ivTabIcon.setImageDrawable(icon);
        return view;
    }
}