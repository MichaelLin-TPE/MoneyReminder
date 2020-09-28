package com.money.moneyreminder;

import android.content.Intent;

public interface LoginHandler {
    boolean isLogin();

    void onDoLogin(OnGoogleLoginListener onGoogleLoginListener);

    void onHandleActivityResult(Intent data, int requestCode);

    void onDoLogOut(OnGoogleLogoutListener onGoogleLogoutListener);

    public interface OnGoogleLoginListener{
        void onSuccess();
        void onFail(String errorCode);
    }
    public interface OnGoogleLogoutListener{
        void onLogoutSuccess();
    }
}
