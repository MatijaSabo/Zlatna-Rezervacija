package com.foi.air.zlatnarezervacija.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.foi.air.zlatnarezervacija.R;
import com.foi.webservice.responses.ReservationTableItemDetails;
import com.foi.webservice.responses.ReservationsOnHold;

import java.util.ArrayList;

/**
 * Created by Matija on 10.1.2017..
 */

public class RequestsForCancelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<ReservationsOnHold> items;

    public RequestsForCancelAdapter(ArrayList<ReservationsOnHold> Items){
        this.items = Items;
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
        String tables = "";

        for (ReservationTableItemDetails i : item.getTables()){
            tables = tables + i.getLabel()+ ", ";
        }

        ((RequestForCancelViewHolder) holder).itemId.setText(item.getId());
        ((RequestForCancelViewHolder) holder).itemUser.setText(user);
        ((RequestForCancelViewHolder) holder).itemDate.setText(item.getDate());
        ((RequestForCancelViewHolder) holder).itemTimeArrival.setText(item.getTime_arrival());
        ((RequestForCancelViewHolder) holder).itemTimeCheckout.setText(item.getTime_checkout());
        ((RequestForCancelViewHolder) holder).itemPersons.setText(item.getpersons());
        ((RequestForCancelViewHolder) holder).itemMeals.setText(item.getMeals());
        ((RequestForCancelViewHolder) holder).itemTables.setText(tables);
        ((RequestForCancelViewHolder) holder).itemRemark.setText(item.getRemark());
        ((RequestForCancelViewHolder) holder).itemDescription.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RequestForCancelViewHolder extends RecyclerView.ViewHolder{

        TextView itemId;
        TextView itemUser;
        TextView itemDate;
        TextView itemTimeArrival;
        TextView itemTimeCheckout;
        TextView itemPersons;
        TextView itemMeals;
        TextView itemTables;
        TextView itemRemark;
        TextView itemDescription;
        Button itemButton;

        public RequestForCancelViewHolder(View itemView) {
            super(itemView);

            itemId = (TextView) itemView.findViewById(R.id.request_for_cancel_reservation_id);
            itemUser = (TextView) itemView.findViewById(R.id.request_for_cancel_user);
            itemDate = (TextView) itemView.findViewById(R.id.request_for_cancel_date);
            itemTimeArrival = (TextView) itemView.findViewById(R.id.request_for_cancel_time_arrival);
            itemTimeCheckout = (TextView) itemView.findViewById(R.id.request_for_cancel_time_checkout);
            itemPersons = (TextView) itemView.findViewById(R.id.request_for_cancel_persons);
            itemMeals = (TextView) itemView.findViewById(R.id.request_for_cancel_meals);
            itemTables = (TextView) itemView.findViewById(R.id.request_for_cancel_tables);
            itemRemark = (TextView) itemView.findViewById(R.id.request_for_cancel_remark);
            itemDescription = (TextView) itemView.findViewById(R.id.request_for_cancel_description);
            itemButton = (Button) itemView.findViewById(R.id.request_for_cancel_button);
        }
    }
}
