package com.money.moneyreminder.money;

import com.money.moneyreminder.LoginHandler;
import com.money.moneyreminder.LoginHandlerImpl;

public class MoneyActivityPresenterImpl implements MoneyActivityPresenter {
    private MoneyActivityVu mView;

    private LoginHandler loginHandler;

    public MoneyActivityPresenterImpl(MoneyActivityVu mView) {
        this.mView = mView;
        loginHandler = new LoginHandlerImpl();
    }

    @Override
    public void onLogoutButtonClickListener() {
        mView.showLogoutConfirmDialog();

    }

    @Override
    public void onLogoutConfirmClickListener() {
        loginHandler.onDoLogOut(onGoogleLogoutListener);
    }

    @Override
    public void onActivityCreate() {
        mView.showTabLayout();

    }

    @Override
    public void onTabSelectClickListener(int tabItemPosition) {
        mView.replaceFragment(tabItemPosition);
    }

    private LoginHandler.OnGoogleLogoutListener onGoogleLogoutListener = new LoginHandler.OnGoogleLogoutListener() {
        @Override
        public void onLogoutSuccess() {
            mView.intentToMainActivity();
        }
    };
}
