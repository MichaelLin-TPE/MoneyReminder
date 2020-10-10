package com.money.moneyreminder.user_fragment.view_presenter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.money.moneyreminder.R;
import com.money.moneyreminder.tool.UserManager;

import java.util.ArrayList;

public class AccountSettingAdapter extends RecyclerView.Adapter<AccountSettingAdapter.ViewHolder> {

    private ArrayList<String> secondSortContentArray;

    private  ArrayList<String> accountItemArray;


    private OnAccountItemClickListener listener;

    public void setOnAccountItemClickListener(OnAccountItemClickListener listener){
        this.listener = listener;
    }

    public AccountSettingAdapter() {

    }

    public void setSecondSortContentArray(ArrayList<String> secondSortContentArray, ArrayList<String> accountItemArray) {
        this.secondSortContentArray = secondSortContentArray;
        this.accountItemArray = accountItemArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sort_description_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final String content = secondSortContentArray.get(position);
        holder.tvItem.setText(content);


        if (position < secondSortContentArray.size()-1){
            String type = accountItemArray.get(position);
            holder.tvChoice.setText(type);
        }


        holder.itemArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener == null){
                    return;
                }
                listener.onClick(content);
            }
        });
    }

    @Override
    public int getItemCount() {
        return secondSortContentArray == null ? 0 : secondSortContentArray.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvItem,tvChoice;

        private ConstraintLayout itemArea;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.sort_description_item);
            itemArea = itemView.findViewById(R.id.sort_description_item_area);
            tvChoice = itemView.findViewById(R.id.sort_description_choice);
        }
    }

    public interface OnAccountItemClickListener{
        void onClick(String itemName);
    }
}
