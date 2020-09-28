package com.money.moneyreminder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.common.SignInButton;
import com.money.moneyreminder.money.MoneyActivity;
import com.money.moneyreminder.tool.ErrorDialog;
import com.money.moneyreminder.tool.MoneyReminderApplication;

public class MainActivity extends AppCompatActivity implements MainActivityVu {

    private MainActivityPresenter presenter;

    private LoginHandler loginHandler;

    private ProgressBar progressBar;

    private SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MoneyReminderApplication.getInstance().setMainActivity(MainActivity.this);
        initPresenter();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    private void initView() {
        progressBar = findViewById(R.id.main_progress_bar);
        signInButton = findViewById(R.id.main_sign_in_btn);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onSignInButtonClickListener();
            }
        });
    }

    private void initPresenter() {
        loginHandler = new LoginHandlerImpl();
        presenter = new MainActivityPresenterImpl(this,loginHandler);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginHandler.onHandleActivityResult(data,requestCode);
    }

    @Override
    public void showErrorCode(String errorCode) {
        ErrorDialog.newInstance(errorCode).show(getSupportFragmentManager(),"dialog");
    }

    @Override
    public void intentToMoneyActivity() {
        Intent it = new Intent(this, MoneyActivity.class);
        startActivity(it);
        finish();
    }

    @Override
    public void showProgressBar(boolean isShow) {
        progressBar.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void enableSignButton(boolean isEnable) {
        signInButton.setVisibility(isEnable ? View.VISIBLE : View.GONE);
    }
}