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
    public void onActivityCreate() {
        mView.showTabLayout();

    }

    @Override
    public void onTabSelectClickListener(int tabItemPosition) {
        mView.replaceFragment(tabItemPosition);
    }

}
