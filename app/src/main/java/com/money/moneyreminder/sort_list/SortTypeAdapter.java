package com.money.moneyreminder.sort_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.money.moneyreminder.R;
import com.money.moneyreminder.tool.ImageLoaderProvider;

import java.util.ArrayList;

public class SortTypeAdapter extends RecyclerView.Adapter<SortTypeAdapter.ViewHolder> {

    private ArrayList<IconData> iconDataArrayList;

    public SortTypeDialogFragment.OnSortTypeItemClickListener listener;

    private int userPressIndex;

    private Context context;

    public void setOnSortTypeItemClickListener(SortTypeDialogFragment.OnSortTypeItemClickListener listener){
        this.listener = listener;
    }

    public void setData(ArrayList<IconData> iconDataArrayList){
        this.iconDataArrayList = iconDataArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sort_type_dialog_icon_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final IconData iconData = iconDataArrayList.get(position);
        ImageLoaderProvider.getInstance().setImage(iconData.getIconUrl(),holder.ivIcon);


        if (userPressIndex == position){
            holder.itemArea.setBackground(ContextCompat.getDrawable(context,R.drawable.sort_type_item_area_background));
        }else {
            holder.itemArea.setBackground(null);
        }

        holder.ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPressIndex = position;
                listener.onClick(iconData.getIconUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return iconDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout itemArea;

        private ImageView ivIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.sort_type_icon);
            itemArea = itemView.findViewById(R.id.sort_type_item_area);
        }
    }
}
