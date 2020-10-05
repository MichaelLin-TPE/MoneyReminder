package com.money.moneyreminder.sort_list.presenter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.money.moneyreminder.R;

import java.util.ArrayList;

public class SecondSortContentAdapter extends RecyclerView.Adapter<SecondSortContentAdapter.ViewHolder> {

    private ArrayList<String> secondSortContentArray;

    private int userPressIndex = -1;

    private OnDescriptionItemClickListener listener;

    public void setOnDescriptionItemClickListener(OnDescriptionItemClickListener listener){
        this.listener = listener;
    }

    public SecondSortContentAdapter() {

    }

    public void setSecondSortContentArray(ArrayList<String> secondSortContentArray) {
        this.secondSortContentArray = secondSortContentArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sort_description_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String content = secondSortContentArray.get(position);
        holder.ivIcon.setVisibility(userPressIndex == position ? View.VISIBLE : View.GONE);
        holder.tvItem.setText(content);
        holder.itemArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPressIndex = position;
                listener.onClick(content);
            }
        });
    }

    @Override
    public int getItemCount() {
        return secondSortContentArray == null ? 0 : secondSortContentArray.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvItem;

        private ImageView ivIcon;

        private ConstraintLayout itemArea;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.sort_description_item);
            ivIcon = itemView.findViewById(R.id.sort_description_icon);
            itemArea = itemView.findViewById(R.id.sort_description_item_area);
        }
    }

    public interface OnDescriptionItemClickListener{
        void onClick(String content);
    }
}
