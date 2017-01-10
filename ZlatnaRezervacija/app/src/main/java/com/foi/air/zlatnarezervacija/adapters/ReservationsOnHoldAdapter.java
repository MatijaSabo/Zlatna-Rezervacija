package com.foi.air.zlatnarezervacija.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.foi.air.zlatnarezervacija.R;
import com.foi.webservice.responses.ReservationsOnHold;

import java.util.ArrayList;

/**
 * Created by Matija on 10.1.2017..
 */

public class ReservationsOnHoldAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<ReservationsOnHold> items;

    public ReservationsOnHoldAdapter(ArrayList<ReservationsOnHold> Items){
        this.items = Items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_on_hold_card, parent, false);
        ReservationOnHoldViewHolder item = new ReservationOnHoldViewHolder(view);
        return item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ReservationsOnHold item = items.get(position);

        String user = item.getUser_first_name() + " " + item.getUser_last_name();

        ((ReservationOnHoldViewHolder) holder).itemId.setText(item.getId());
        ((ReservationOnHoldViewHolder) holder).itemUser.setText(user);
        ((ReservationOnHoldViewHolder) holder).itemDate.setText(item.getDate());
        ((ReservationOnHoldViewHolder) holder).itemTime.setText(item.getTime_arrival());
        ((ReservationOnHoldViewHolder) holder).itemPersons.setText(item.getpersons());
        ((ReservationOnHoldViewHolder) holder).itemMeals.setText(item.getMeals());
        ((ReservationOnHoldViewHolder) holder).itemRemark.setText(item.getRemark());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ReservationOnHoldViewHolder extends RecyclerView.ViewHolder{

        TextView itemId;
        TextView itemUser;
        TextView itemDate;
        TextView itemTime;
        TextView itemPersons;
        TextView itemMeals;
        TextView itemRemark;
        Button itemButton;

        public ReservationOnHoldViewHolder(View itemView) {
            super(itemView);

            itemId = (TextView) itemView.findViewById(R.id.reservation_on_hold_reservation_id);
            itemUser = (TextView) itemView.findViewById(R.id.reservation_on_hold_user);
            itemDate = (TextView) itemView.findViewById(R.id.reservation_on_hold_date);
            itemTime = (TextView) itemView.findViewById(R.id.reservation_on_hold_time);
            itemPersons = (TextView) itemView.findViewById(R.id.reservation_on_hold_persons);
            itemMeals = (TextView) itemView.findViewById(R.id.reservation_on_hold_meals);
            itemRemark = (TextView) itemView.findViewById(R.id.reservation_on_hold_remark);
            itemButton = (Button) itemView.findViewById(R.id.reservation_on_hold_button);
        }
    }
}
