package com.money.moneyreminder;

public class MainActivityPresenterImpl implements MainActivityPresenter {

    private MainActivityVu mView;

    private LoginHandler loginHandler;

    public MainActivityPresenterImpl(MainActivityVu mView,LoginHandler loginHandler) {
        this.mView = mView;
        this.loginHandler = loginHandler;
    }

    @Override
    public void onSignInButtonClickListener() {
        mView.enableSignButton(false);
        mView.showProgressBar(true);
        loginHandler.onDoLogin(onGoogleLoginListener);
    }

    @Override
    public void onResume() {
        if (loginHandler.isLogin()){
            mView.enableSignButton(false);
            mView.intentToMoneyActivity();
        }
    }

    private LoginHandler.OnGoogleLoginListener onGoogleLoginListener = new LoginHandler.OnGoogleLoginListener() {
        @Override
        public void onSuccess() {
            mView.showProgressBar(false);
            mView.intentToMoneyActivity();
        }

        @Override
        public void onFail(String errorCode) {
            mView.showErrorCode(errorCode);
            mView.showProgressBar(false);
            mView.enableSignButton(true);
        }
    };
}
