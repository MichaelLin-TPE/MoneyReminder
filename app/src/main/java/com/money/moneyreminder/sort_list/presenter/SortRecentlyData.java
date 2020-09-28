package com.money.moneyreminder.sort_list.presenter;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SortRecentlyData implements Serializable {
    @SerializedName("sort_type")
    private String sortType;
    @SerializedName("icon_url")
    private String iconUrl;

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
