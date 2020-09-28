package com.money.moneyreminder.sort;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MoneyObject implements Serializable {

    @SerializedName("time_miles")
    private long timeMiles;
    @SerializedName("income_money")
    private int inComeMoney;
    @SerializedName("expenditure_money")
    private int expenditureMoney;
    @SerializedName("money_data")
    private ArrayList<MoneyData> moneyDataArrayList;

    public ArrayList<MoneyData> getMoneyDataArrayList() {
        return moneyDataArrayList;
    }

    public void setMoneyDataArrayList(ArrayList<MoneyData> moneyDataArrayList) {
        this.moneyDataArrayList = moneyDataArrayList;
    }

    public long getTimeMiles() {
        return timeMiles;
    }

    public void setTimeMiles(long timeMiles) {
        this.timeMiles = timeMiles;
    }

    public int getInComeMoney() {
        return inComeMoney;
    }

    public void setInComeMoney(int inComeMoney) {
        this.inComeMoney = inComeMoney;
    }

    public int getExpenditureMoney() {
        return expenditureMoney;
    }

    public void setExpenditureMoney(int expenditureMoney) {
        this.expenditureMoney = expenditureMoney;
    }
}
