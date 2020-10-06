package com.money.moneyreminder.list_fragment.sort_fragment;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.money.moneyreminder.R;
import com.money.moneyreminder.tool.ImageLoaderProvider;

import java.util.ArrayList;
import java.util.Locale;

public class SortTypePercentAdapter extends RecyclerView.Adapter<SortTypePercentAdapter.ViewHolder> {

    private ArrayList<SortPercentData> sortPercentDataArrayList;

    private Context context;

    private boolean isIncome;

    public SortTypePercentAdapter(ArrayList<SortPercentData> sortPercentDataArrayList, boolean isIncome) {
        this.isIncome = isIncome;
        this.sortPercentDataArrayList = sortPercentDataArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sort_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        SortPercentData data = sortPercentDataArrayList.get(position);
        holder.tvNumber.setText(String.format(Locale.getDefault(),"%d.",(position+1)));
        holder.tvTitle.setText(data.getTitle());
        holder.tvNumberOfCase.setText(String.format(Locale.getDefault(),"%d 筆明細",data.getNumberOfCase()));
        holder.tvMoney.setText(String.format(Locale.getDefault(),"$%d",data.getTotalMoney()));
        holder.tvPercent.setText(String.format(Locale.getDefault(),"%d%%",data.getPercent()));
        holder.seekBar.setProgress(data.getPercent());
        holder.seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        ImageLoaderProvider.getInstance().setImage(data.getIconUrl(),holder.ivIcon);
        if (isIncome){
            holder.tvMoney.setTextColor(ContextCompat.getColor(context,R.color.bule));
        }else {
            holder.tvMoney.setTextColor(ContextCompat.getColor(context,R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        return sortPercentDataArrayList == null || sortPercentDataArrayList.isEmpty() ? 0 : sortPercentDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle,tvNumberOfCase,tvPercent,tvMoney,tvNumber;

        private ImageView ivIcon;

        private SeekBar seekBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.sort_item_title);
            tvNumberOfCase = itemView.findViewById(R.id.sort_item_list);
            tvPercent = itemView.findViewById(R.id.sort_item_percent);
            tvMoney = itemView.findViewById(R.id.sort_item_moeny);
            ivIcon = itemView.findViewById(R.id.sort_item_icon);
            seekBar = itemView.findViewById(R.id.sort_item_seek_bar);
            tvNumber = itemView.findViewById(R.id.sort_item_number);
        }
    }
}
