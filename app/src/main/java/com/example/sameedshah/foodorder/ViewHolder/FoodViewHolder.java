package com.example.sameedshah.foodorder.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sameedshah.foodorder.Interface.ItemClickListener;
import com.example.sameedshah.foodorder.R;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtFoodName;
    public ImageView foodImage;
    private ItemClickListener itemClickListener;

    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);

        txtFoodName = itemView.findViewById(R.id.txtFoodName);
        foodImage = itemView.findViewById(R.id.food_image);

        itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view,getAdapterPosition(),false);

    }
}
