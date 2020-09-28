package com.money.moneyreminder.sort_list.presenter;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class SortData implements Serializable {

    @SerializedName("recently")
    private ArrayList<SortRecentlyData> recentlyData;
    @SerializedName("create")
    private ArrayList<SortCreateData> createData;

    public ArrayList<SortRecentlyData> getRecentlyData() {
        return recentlyData;
    }

    public void setRecentlyData(ArrayList<SortRecentlyData> recentlyData) {
        this.recentlyData = recentlyData;
    }

    public ArrayList<SortCreateData> getCreateData() {
        return createData;
    }

    public void setCreateData(ArrayList<SortCreateData> createData) {
        this.createData = createData;
    }
}
