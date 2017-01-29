package com.foi.air.zlatnarezervacija.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foi.air.zlatnarezervacija.R;
import com.foi.air.zlatnarezervacija.ReservationOnHoldActivity;
import com.foi.webservice.responses.ReservationsOnHold;

import java.util.ArrayList;

/**
 * Created by Matija on 10.1.2017..
 */

public class ReservationsOnHoldAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<ReservationsOnHold> items;
    Context context;

    /* Spremanje podataka dobivenih preko parametara u odgovarajuće varijeble */
    public ReservationsOnHoldAdapter(ArrayList<ReservationsOnHold> Items, Context context){
        this.items = Items;
        this.context = context;
    }

    /* Spajanje layout-a za jedan podatak sa RecyclerView-om */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_on_hold_card, parent, false);
        ReservationOnHoldViewHolder item = new ReservationOnHoldViewHolder(view);
        return item;
    }

    /* Spajanje dobivenih podataka sa odgovarajučim elementima na layout-u */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ReservationsOnHold item = items.get(position);

        String user = item.getUser_first_name() + " " + item.getUser_last_name();

        ((ReservationOnHoldViewHolder) holder).itemId.setText(item.getId());
        ((ReservationOnHoldViewHolder) holder).itemUser.setText(user);
        ((ReservationOnHoldViewHolder) holder).itemDate.setText(item.getDate());
        ((ReservationOnHoldViewHolder) holder).itemTime.setText(item.getTime_arrival());
    }

    /* Metoda koja vraća broj podataka u RecyclerView-u */
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ReservationOnHoldViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView itemId;
        TextView itemUser;
        TextView itemDate;
        TextView itemTime;

        public ReservationOnHoldViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            /* Spajanje odgovarajučih varijabli sa elementima iz layout-a */
            itemId = (TextView) itemView.findViewById(R.id.reservation_on_hold_reservation_id);
            itemUser = (TextView) itemView.findViewById(R.id.reservation_on_hold_user);
            itemDate = (TextView) itemView.findViewById(R.id.reservation_on_hold_date);
            itemTime = (TextView) itemView.findViewById(R.id.reservation_on_hold_time);
        }

        /* Događaj koji se poziva kada korisnik klikne na pojedini podatak */
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, ReservationOnHoldActivity.class);
            intent.putExtra("reservation_id", itemId.getText());
            context.startActivity(intent);
        }
    }
}
