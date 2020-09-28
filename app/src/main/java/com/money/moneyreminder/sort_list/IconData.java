package com.money.moneyreminder.sort_list;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class IconData implements Serializable {

    @SerializedName("icon_url")
    private String iconUrl;

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
