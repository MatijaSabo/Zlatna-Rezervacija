package com.foi.air.zlatnarezervacija.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foi.air.zlatnarezervacija.R;
import com.foi.webservice.responses.MenuItemDetails;

import java.util.ArrayList;

/**
 * Created by Matija on 22.11.2016..
 */

public class MenuDetailsRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<MenuItemDetails> items;

    /* Spremanje podataka dobivenih preko parametara u odgovarajuće varijeble */
    public MenuDetailsRecycleAdapter(ArrayList<MenuItemDetails> Items){
        this.items = Items;
    }

    /* Spajanje layout-a za jedan podatak sa RecyclerView-om */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_details_item, parent, false);
        MenuDetailsViewHolder item = new MenuDetailsViewHolder(view);
        return item;
    }

    /* Spajanje dobivenih podataka sa odgovarajučim elementima na layout-u */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MenuItemDetails item = items.get(position);

        /* Provjera dobivenih podataka */
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

    /* Metoda koja vraća broj podataka u RecyclerView-u */
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

            /* Spajanje odgovarajučih varijabli sa elementima iz layout-a */
            itemName = (TextView) itemView.findViewById(R.id.menu_detail_name);
            itemDescription = (TextView) itemView.findViewById(R.id.menu_detail_description);
            itemPrice = (TextView) itemView.findViewById(R.id.menu_detail_price);
        }
    }
}
