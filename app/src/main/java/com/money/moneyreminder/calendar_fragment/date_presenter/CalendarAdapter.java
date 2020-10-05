package com.money.moneyreminder.calendar_fragment.date_presenter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.money.moneyreminder.R;

import static com.money.moneyreminder.calendar_fragment.date_presenter.DatePresenterImpl.SHOW_DATE_LIST;
import static com.money.moneyreminder.calendar_fragment.date_presenter.DatePresenterImpl.SHOW_WEEK_DAY;

public class CalendarAdapter extends RecyclerView.Adapter {

    private DatePresenter datePresenter;

    private DateListViewHolder.OnCalendarItemClickListener listener;

    public void setOnCalendarItemClickListener(DateListViewHolder.OnCalendarItemClickListener listener){
        this.listener = listener;
    }

    public CalendarAdapter(DatePresenter datePresenter){
        this.datePresenter = datePresenter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        switch (viewType){
            case SHOW_WEEK_DAY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.week_day_item,parent,false);
                return new WeekDayViewHolder(view);
            case SHOW_DATE_LIST:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_list_item,parent,false);
                return new DateListViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof WeekDayViewHolder){
            datePresenter.onBindWeekDayHolder((WeekDayViewHolder)holder,position);
        }
        if (holder instanceof DateListViewHolder){
            datePresenter.onBindDateListHolder((DateListViewHolder)holder,position);
            Log.i("Michael","clickListener 傳入");
            datePresenter.onCalendarItemClickListener((DateListViewHolder)holder,listener);
        }
    }

    @Override
    public int getItemCount() {
        return datePresenter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return datePresenter.getItemViewType(position);
    }
}
