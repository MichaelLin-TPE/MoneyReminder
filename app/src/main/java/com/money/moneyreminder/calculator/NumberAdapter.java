package com.money.moneyreminder.calculator;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.money.moneyreminder.R;

import java.util.ArrayList;

public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.ViewHolder> {


    private ArrayList<String> numberArray;

    private Context context;

    private OnNumberItemClickListener listener;

    public void setOnNumberItemClickListener(OnNumberItemClickListener listener){
        this.listener = listener;
    }




    public void setData(ArrayList<String> numberArray){
        this.numberArray = numberArray;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calculator_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String content = numberArray.get(position);
        holder.setData(content,position);
        holder.itemArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(content);
            }
        });

    }

    @Override
    public int getItemCount() {
        return numberArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvContent;

        private ConstraintLayout itemArea;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.calculator_item_content);
            itemArea = itemView.findViewById(R.id.calculator_item_number_area);


        }

        public void setData(String content, int position) {
            tvContent.setText(content);
            if (position == numberArray.size() - 1){
                tvContent.setBackground(ContextCompat.getDrawable(context,R.drawable.calculator_ok_btn_selector));
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getScreenDp());
                int dp = convertToDp();
                itemArea.setPadding(dp,dp,dp,dp);
                itemArea.setLayoutParams(params);
            }else {
                tvContent.setBackground(null);
                itemArea.setBackground(null);

                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(getScreenDp(),getScreenDp());
                itemArea.setLayoutParams(params);
            }
        }
    }

    private int convertToDp(){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) 20.0,context.getResources().getDisplayMetrics());
    }

    private int getScreenDp(){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        float width = context.getResources().getDisplayMetrics().widthPixels;

        float singleItemSize = width / 4 ;

        float singleItemDp = singleItemSize / displayMetrics.density;
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,singleItemDp,context.getResources().getDisplayMetrics());
    }

    public interface OnNumberItemClickListener{
        void onClick(String number);
    }
}
