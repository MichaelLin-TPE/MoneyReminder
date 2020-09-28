package com.money.moneyreminder.sort_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.money.moneyreminder.R;
import com.money.moneyreminder.sort_list.presenter.CreateAdapter;
import com.money.moneyreminder.sort_list.presenter.CreateViewHolder;
import com.money.moneyreminder.sort_list.presenter.RecentlyAdapter;
import com.money.moneyreminder.sort_list.presenter.RecentlyViewHolder;
import com.money.moneyreminder.sort_list.presenter.SortPresenter;

import static com.money.moneyreminder.sort_list.presenter.SortPresenterImpl.CREATE_LIST;
import static com.money.moneyreminder.sort_list.presenter.SortPresenterImpl.RECENTLY;

public class SortListAdapter extends RecyclerView.Adapter {

    private SortPresenter sortPresenter;

    private CreateAdapter.OnSortTypeSelectListener createListener;

    private RecentlyAdapter.OnSortTypeRecentlySelectListener recentlyListener;

    public void setOnSortTypeRecentlyListener(RecentlyAdapter.OnSortTypeRecentlySelectListener listener){
        this.recentlyListener = listener;
    }

    public void setOnSortTypeSelectListener(CreateAdapter.OnSortTypeSelectListener createListener){
        this.createListener = createListener;
    }

    public SortListAdapter(SortPresenter sortPresenter) {
        this.sortPresenter = sortPresenter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sort_list_item,parent,false);
        switch (viewType){
            case CREATE_LIST:
                return new CreateViewHolder(view);
            case RECENTLY:
                return new RecentlyViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CreateViewHolder){
            sortPresenter.onBindCreateViewHolder((CreateViewHolder)holder,position);
            sortPresenter.setOnSortTypeSelectListener((CreateViewHolder)holder,createListener);
        }
        if (holder instanceof RecentlyViewHolder){
            sortPresenter.onBindRecentlyViewHolder((RecentlyViewHolder)holder,position);
            sortPresenter.setOnSortTypeRecentlyListener((RecentlyViewHolder)holder,recentlyListener);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return sortPresenter.getItemPosition(position);
    }

    @Override
    public int getItemCount() {
        return sortPresenter.getItemCount();
    }
}
