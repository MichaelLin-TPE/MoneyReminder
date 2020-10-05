package com.money.moneyreminder.tool;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class SecondSortData implements Serializable {

    @SerializedName("second_content_array")
    private ArrayList<String> contentArray;

    @SerializedName("title")
    private String sortTitle;

    public ArrayList<String> getContentArray() {
        return contentArray;
    }

    public void setContentArray(ArrayList<String> contentArray) {
        this.contentArray = contentArray;
    }

    public String getSortTitle() {
        return sortTitle;
    }

    public void setSortTitle(String sortTitle) {
        this.sortTitle = sortTitle;
    }
}
