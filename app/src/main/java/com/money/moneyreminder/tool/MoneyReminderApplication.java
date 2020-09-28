package com.money.moneyreminder.tool;

import androidx.multidex.MultiDexApplication;

import com.money.moneyreminder.MainActivity;

public class MoneyReminderApplication extends MultiDexApplication {

    private static MoneyReminderApplication instance = null;

    private MainActivity mainActivity;

    public static MoneyReminderApplication getInstance(){
        if (instance == null){
            instance = new MoneyReminderApplication();
            return instance;
        }
        return  instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ImageLoaderProvider.getInstance().initImageLoader();
    }

    public void setMainActivity(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    public MainActivity getMainActivity(){
        return mainActivity;
    }
}
