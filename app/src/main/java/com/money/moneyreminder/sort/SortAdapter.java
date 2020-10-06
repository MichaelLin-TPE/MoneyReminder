package com.money.moneyreminder.sort;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.money.moneyreminder.R;
import com.money.moneyreminder.tool.MoneyReminderApplication;

import java.util.ArrayList;

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.ViewHolder> {

    private ArrayList<String> describeArray;

    private OnDescribeItemClickListener listener;

    public void setOnDescribeItemClickListener(OnDescribeItemClickListener listener){
        this.listener = listener;
    }

    public SortAdapter(ArrayList<String> describeArray) {
        this.describeArray = describeArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sort_description_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String describe = describeArray.get(position);
        holder.tvItem.setText(describe);
        holder.tvItem.setTextColor(ContextCompat.getColor(MoneyReminderApplication.getInstance().getApplicationContext(),R.color.default_text_view_color));
        holder.tvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(describe);
            }
        });
    }

    @Override
    public int getItemCount() {
        return describeArray == null ? 0 : describeArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.sort_description_item);
        }
    }

    public interface OnDescribeItemClickListener{
        void onClick(String describe);
    }
}
