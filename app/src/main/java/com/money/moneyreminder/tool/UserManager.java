package com.money.moneyreminder.tool;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.firestore.auth.User;
import com.money.moneyreminder.R;

public class UserManager {

    private static UserManager instance = null;

    private SharedPreferences sharedPreferences;

    private static final String SORT_TYPE = "sort_type";

    private static final String DAY_RANGE = "day_range";

    private static final String SORT_ANALYSIS = "sort_analysis";

    public static UserManager getInstance(){
        if (instance == null){
            instance = new UserManager();
            return instance;
        }
        return instance;
    }
    private UserManager(){
        sharedPreferences = MoneyReminderApplication.getInstance().getApplicationContext().getSharedPreferences("userData", Context.MODE_PRIVATE);
    }

    public void saveDataSort(String sortType){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SORT_TYPE,sortType);
        editor.apply();
    }
    public String getSortType(){
        String type = sharedPreferences.getString(SORT_TYPE,"");

        if (type == null || type.isEmpty()){
            type = MoneyReminderApplication.getInstance().getApplicationContext().getString(R.string.sort_near);
        }

        return type;
    }

    public void saveDayRange(String sortType){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DAY_RANGE,sortType);
        editor.apply();
    }
    public String getDayRange(){

        String type = sharedPreferences.getString(DAY_RANGE,"");
        if (type == null || type.isEmpty()){
            type = MoneyReminderApplication.getInstance().getApplicationContext().getString(R.string.sort_one);
        }

        return type;
    }

    public void saveSortAnalysisType(String sortType){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SORT_ANALYSIS,sortType);
        editor.apply();
    }

    public String getSortAnalysisType(){

        String type = sharedPreferences.getString(SORT_ANALYSIS,"");
        if (type == null || type.isEmpty()){
            type = MoneyReminderApplication.getInstance().getApplicationContext().getString(R.string.long_analysis);
        }
        return type;
    }
}
