package com.money.moneyreminder.list_fragment.sort_fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.money.moneyreminder.R;
import com.money.moneyreminder.sort.MoneyData;
import com.money.moneyreminder.tool.ImageLoaderProvider;

import java.util.ArrayList;
import java.util.Locale;

public class DetailChildAdapter extends RecyclerView.Adapter<DetailChildAdapter.ViewHolder> {

    private ArrayList<MoneyData> dataArray ;

    private Context context;

    private OnDetailChildItemClickListener listener;

    public void setOnDetailChildItemClickListener(OnDetailChildItemClickListener listener){
        this.listener = listener;
    }

    public DetailChildAdapter(ArrayList<MoneyData> dataArray) {
        this.dataArray = dataArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_child_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MoneyData data = dataArray.get(position);
        ImageLoaderProvider.getInstance().setImage(data.getSortTypeIcon(),holder.ivIcon);
        holder.tvContent.setText(data.getSortType());
        holder.tvDescription.setText(data.getDescription());
        if (data.isIncome()){
            holder.tvMoney.setTextColor(ContextCompat.getColor(context,R.color.bule));
        }else {
            holder.tvMoney.setTextColor(ContextCompat.getColor(context,R.color.red));
        }
        holder.tvMoney.setText(String.format(Locale.getDefault(),"$%d",data.getTotalMoney()));
        holder.itemArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataArray == null || dataArray.isEmpty() ? 0 : dataArray.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivIcon;

        private TextView tvContent,tvMoney,tvDescription;

        private ConstraintLayout itemArea;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemArea = itemView.findViewById(R.id.detail_child_click_area);
            tvDescription = itemView.findViewById(R.id.detail_child_description);
            ivIcon = itemView.findViewById(R.id.detail_child_icon);
            tvContent = itemView.findViewById(R.id.detail_child_content);
            tvMoney = itemView.findViewById(R.id.detail_child_money);
        }
    }

    public interface OnDetailChildItemClickListener{
        void onClick(MoneyData data);
    }
}
