package com.foi.air.zlatnarezervacija.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foi.air.zlatnarezervacija.R;
import com.foi.air.zlatnarezervacija.RequestForCancelActivity;
import com.foi.webservice.responses.ReservationsOnHold;

import java.util.ArrayList;

/**
 * Created by Matija on 10.1.2017..
 */

public class RequestsForCancelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<ReservationsOnHold> items;
    Context context;

    public RequestsForCancelAdapter(ArrayList<ReservationsOnHold> Items, Context context){
        this.items = Items;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_request_for_cancel_card, parent, false);
        RequestForCancelViewHolder item = new RequestForCancelViewHolder(view);
        return item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ReservationsOnHold item = items.get(position);

        String user = item.getUser_first_name() + " " + item.getUser_last_name();

        ((RequestForCancelViewHolder) holder).itemId.setText(item.getId());
        ((RequestForCancelViewHolder) holder).itemUser.setText(user);
        ((RequestForCancelViewHolder) holder).itemDate.setText(item.getDate());
        ((RequestForCancelViewHolder) holder).itemTimeArrival.setText(item.getTime_arrival());
        ((RequestForCancelViewHolder) holder).itemTimeCheckout.setText(item.getTime_checkout());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RequestForCancelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView itemId;
        TextView itemUser;
        TextView itemDate;
        TextView itemTimeArrival;
        TextView itemTimeCheckout;

        public RequestForCancelViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemId = (TextView) itemView.findViewById(R.id.request_for_cancel_reservation_id);
            itemUser = (TextView) itemView.findViewById(R.id.request_for_cancel_user);
            itemDate = (TextView) itemView.findViewById(R.id.request_for_cancel_date);
            itemTimeArrival = (TextView) itemView.findViewById(R.id.request_for_cancel_time_arrival);
            itemTimeCheckout = (TextView) itemView.findViewById(R.id.request_for_cancel_time_checkout);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, RequestForCancelActivity.class);
            intent.putExtra("reservation_id", itemId.getText());
            context.startActivity(intent);
        }
    }
}
