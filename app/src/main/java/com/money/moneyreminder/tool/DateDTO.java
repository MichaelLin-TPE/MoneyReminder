package com.money.moneyreminder.tool;

import java.io.Serializable;

public class DateDTO implements Serializable {
    private String year;

    private String month;

    private String firstDateOfMonth;

    private String endDateOfMonth;

    private String tabString;

    public String getFirstDateOfMonth() {
        return firstDateOfMonth;
    }

    public void setFirstDateOfMonth(String firstDateOfMonth) {
        this.firstDateOfMonth = firstDateOfMonth;
    }

    public String getEndDateOfMonth() {
        return endDateOfMonth;
    }

    public void setEndDateOfMonth(String endDateOfMonth) {
        this.endDateOfMonth = endDateOfMonth;
    }

    public String getTabString() {
        return tabString;
    }

    public void setTabString(String tabString) {
        this.tabString = tabString;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
