package com.money.moneyreminder.sort;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MoneyData implements Serializable {
    @SerializedName("total_money")
    private int totalMoney;
    @SerializedName("sort_type_icon")
    private String sortTypeIcon;
    @SerializedName("sort_type")
    private String sortType;
    @SerializedName("time_miles")
    private long timeMiles;
    @SerializedName("description")
    private String description;
    @SerializedName("is_income")
    private boolean isIncome;

    public String getSortTypeIcon() {
        return sortTypeIcon;
    }

    public void setSortTypeIcon(String sortTypeIcon) {
        this.sortTypeIcon = sortTypeIcon;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public long getTimeMiles() {
        return timeMiles;
    }

    public void setTimeMiles(long timeMiles) {
        this.timeMiles = timeMiles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIncome() {
        return isIncome;
    }

    public void setIncome(boolean income) {
        isIncome = income;
    }
}
