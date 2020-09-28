package com.money.moneyreminder.list_fragment.sort_fragment;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SortPercentData implements Serializable {

    @SerializedName("title")
    private String title;
    @SerializedName("icon_url")
    private String iconUrl;
    @SerializedName("percent")
    private int percent;
    @SerializedName("number_of_case")
    private int numberOfCase;
    @SerializedName("total_money")
    private int totalMoney;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getNumberOfCase() {
        return numberOfCase;
    }

    public void setNumberOfCase(int numberOfCase) {
        this.numberOfCase = numberOfCase;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }
}
