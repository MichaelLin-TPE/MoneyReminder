package com.money.moneyreminder.tool;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import androidx.core.content.ContextCompat;

import com.money.moneyreminder.R;

public class DbConvertTool {
    private static DbConvertTool instance = null;

    public static DbConvertTool getInstance(){
        if (instance == null){
            instance = new DbConvertTool();
            return instance;
        }
        return instance;
    }

    public int convertDb(){
        Context context = MoneyReminderApplication.getInstance().getApplicationContext();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        //取得螢幕的寬
        float width = (float) context.getResources().getDisplayMetrics().widthPixels;
        float singleItemSize = (float) width / 7 / metrics.density;
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,singleItemSize,context.getResources().getDisplayMetrics());
    }

}
