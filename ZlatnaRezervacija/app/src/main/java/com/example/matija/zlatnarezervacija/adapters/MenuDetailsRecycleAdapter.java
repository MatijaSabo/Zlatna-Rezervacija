package com.example.matija.zlatnarezervacija.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.matija.zlatnarezervacija.R;
import com.example.webservice.MenuItemDetails;

import java.util.ArrayList;

/**
 * Created by Matija on 22.11.2016..
 */

public class MenuDetailsRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<MenuItemDetails> items;

    public MenuDetailsRecycleAdapter(ArrayList<MenuItemDetails> Items){
        this.items = Items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_details_item, parent, false);
        MenuDetailsViewHolder item = new MenuDetailsViewHolder(view);
        return item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MenuItemDetails item = items.get(position);

        if(item.getDescription().isEmpty()){
            ((MenuDetailsViewHolder) holder).itemName.setText(item.getName());
            ((MenuDetailsViewHolder) holder).itemDescription.setText(R.string.NoDescription);
            ((MenuDetailsViewHolder) holder).itemPrice.setText(item.getPrice() + " Kn");
        }

        else{
            ((MenuDetailsViewHolder) holder).itemName.setText(item.getName());
            ((MenuDetailsViewHolder) holder).itemDescription.setText(item.getDescription());
            ((MenuDetailsViewHolder) holder).itemPrice.setText(item.getPrice() + " Kn");
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MenuDetailsViewHolder extends RecyclerView.ViewHolder{

        TextView itemName;
        TextView itemDescription;
        TextView itemPrice;

        public MenuDetailsViewHolder(View itemView) {
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.menu_detail_name);
            itemDescription = (TextView) itemView.findViewById(R.id.menu_detail_description);
            itemPrice = (TextView) itemView.findViewById(R.id.menu_detail_price);
        }
    }
}
