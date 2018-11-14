package com.example.sameedshah.foodorder.ViewHolder;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.sameedshah.foodorder.Interface.ItemClickListener;
import com.example.sameedshah.foodorder.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.sameedshah.foodorderserver.Model.Order;

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txt_cart_name, txt_price;
    public ImageView img_cart_count;

    public ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_cart_name = itemView.findViewById(R.id.cart_item_name);
        txt_price = itemView.findViewById(R.id.cart_item_price);
        img_cart_count = itemView.findViewById(R.id.cart_item_count);
    }

    @Override
    public void onClick(View view) {

    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Order> listData = new ArrayList<>();
    private Context context;

    public CartAdapter(List<Order> listData, Context context)
    {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout,parent,false);

        return new CartViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i) {

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(""+ listData.get(i).getQuantity(), Color.RED);
        cartViewHolder.img_cart_count.setImageDrawable(drawable);

        Locale locale = new Locale("en","us");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt(listData.get(i).getPrice()))
                * (Integer.parseInt(listData.get(i).getQuantity()));
        cartViewHolder.txt_price.setText(fmt.format(price));
        cartViewHolder.txt_cart_name.setText(listData.get(i).getProductName());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
