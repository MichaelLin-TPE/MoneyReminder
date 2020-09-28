package com.money.moneyreminder.calculator;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.money.moneyreminder.R;

import java.util.ArrayList;

public class MathAdapter extends RecyclerView.Adapter<MathAdapter.ViewHolder> {

    private Context context;

    private ArrayList<Drawable> mathArray;

    private NumberAdapter.OnNumberItemClickListener listener;

    public static final String PLUS = "plus";

    public static final String MULTIPLY = "multiply";

    public static final String DIVIDE = "divide";

    public static final String LESS = "less";

    public static final String BACK = "back";

    public void setOnNumberItemClickListener(NumberAdapter.OnNumberItemClickListener listener){
        this.listener = listener;
    }

    public void setData(ArrayList<Drawable> mathArray){
        this.mathArray = mathArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.calculator_item_math_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Drawable icon = mathArray.get(position);
        holder.ivMath.setImageDrawable(icon);
        holder.itemArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(getMathContent(position));
            }
        });
    }

    private String getMathContent(int position) {
        switch (position){
            case 0 :
                return BACK;
            case 1:
                return DIVIDE;
            case 2:
                return MULTIPLY;
            case 3:
                return LESS;
            default:
                return PLUS;
        }
    }

    @Override
    public int getItemCount() {
        return mathArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivMath;

        private ConstraintLayout itemArea;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMath = itemView.findViewById(R.id.calculator_item_math);
            itemArea = itemView.findViewById(R.id.calculator_item_area);
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

            float width = context.getResources().getDisplayMetrics().widthPixels;

            float singleItemSize = width / 4;

            float singleItemDp = singleItemSize / displayMetrics.density;

            int pix = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,singleItemDp,context.getResources().getDisplayMetrics());
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(pix,pix);
            itemArea.setLayoutParams(params);
        }
    }

}
