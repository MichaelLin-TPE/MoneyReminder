package com.money.moneyreminder.list_fragment.sort_fragment;

import java.util.ArrayList;

public interface SortFragmentVu {
    void setRecyclerView(ArrayList<SortPercentData> sortTypeArray, boolean isIncome);

    void showNoDataView(boolean isShow);
}
