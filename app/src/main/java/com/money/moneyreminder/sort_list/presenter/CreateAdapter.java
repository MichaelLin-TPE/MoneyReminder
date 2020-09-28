package com.money.moneyreminder.sort_list.presenter;

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
import com.money.moneyreminder.tool.ImageLoaderProvider;

import java.util.ArrayList;

public class CreateAdapter extends RecyclerView.Adapter<CreateAdapter.ViewHolder> {

    private ArrayList<SortCreateData> createDataArrayList;

    private int userPressIndex;

    private OnSortTypeSelectListener listener;

    private Context context;

    public void setOnSortTypeSelectListener(OnSortTypeSelectListener listener){
        this.listener = listener;
    }

    public CreateAdapter(ArrayList<SortCreateData> createDataArrayList){
        this.createDataArrayList = createDataArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sort_type_dialog_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final SortCreateData data = createDataArrayList.get(position);
        holder.tvContent.setText(data.getSortType());
        ImageLoaderProvider.getInstance().setImage(data.getIconUrl(),holder.ivIcon);
        if (userPressIndex == position){
            holder.itemArea.setBackground(ContextCompat.getDrawable(context,R.drawable.sort_type_item_area_background));
        }else {
            holder.itemArea.setBackground(null);
        }
        holder.itemArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPressIndex = position;
                listener.onSelected(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return createDataArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvContent;

        private ImageView ivIcon;

        private ConstraintLayout itemArea;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemArea = itemView.findViewById(R.id.sort_type_dialog_area);
            tvContent = itemView.findViewById(R.id.sort_type_dialog_content);
            ivIcon = itemView.findViewById(R.id.sort_type_dialog_icon);
        }
    }
    public interface OnSortTypeSelectListener{
        void onSelected(SortCreateData data);
    }
}
