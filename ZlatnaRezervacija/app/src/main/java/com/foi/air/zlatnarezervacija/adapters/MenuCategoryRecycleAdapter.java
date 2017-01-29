package com.foi.air.zlatnarezervacija.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foi.air.zlatnarezervacija.MenuDetailsActivity;
import com.foi.air.zlatnarezervacija.R;

/**
 * Created by Matija on 21.11.2016..
 */

public class MenuCategoryRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    String[] category_name;
    Integer[] category_image_id;
    Context context;

    /* Spremanje podataka dobivenih preko parametara u odgovarajuće varijeble */
    public MenuCategoryRecycleAdapter(String[] category_name, Integer[] category_image_id, Context context){
        this.category_name = category_name;
        this.category_image_id = category_image_id;
        this.context = context;
    }

    /* Spajanje layout-a za jedan podatak sa RecyclerView-om */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        MenuCategoryViewHolder item = new MenuCategoryViewHolder(view);
        return item;
    }

    /* Spajanje dobivenih podataka sa odgovarajučim elementima na layout-u */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((MenuCategoryViewHolder) holder).category_name.setText(category_name[position]);
        ((MenuCategoryViewHolder) holder).category_image.setImageResource(category_image_id[position]);
    }

    /* Metoda koja vraća broj podataka u RecyclerView-u */
    @Override
    public int getItemCount() {
        return category_name.length;
    }

    public class MenuCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView category_name;
        ImageView category_image;

        public MenuCategoryViewHolder(View view){
            super(view);
            view.setOnClickListener(this);

            /* Spajanje odgovarajučih varijabli sa elementima iz layout-a */
            category_name = (TextView) view.findViewById(R.id.category_item_text);
            category_image = (ImageView) view.findViewById(R.id.category_item_icon);
        }

        /* Događaj koji se poziva kada korisnik klikne na pojedini podatak */
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, MenuDetailsActivity.class);
            intent.putExtra("kategorija", category_name.getText());
            context.startActivity(intent);
        }
    }
}
