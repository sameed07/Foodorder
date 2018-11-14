package com.example.sameedshah.foodorder.ViewHolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sameedshah.foodorder.Interface.ItemClickListener;
import com.example.sameedshah.foodorder.R;


public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView txtMenuName;
    public ImageView itemImage;

    private ItemClickListener itemClickListener;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);

        txtMenuName = itemView.findViewById(R.id.txtMenuName);
        itemImage = itemView.findViewById(R.id.itemImage);

        itemView.setOnClickListener(this);

    }

    public ItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view,getAdapterPosition(),false);


    }
}
