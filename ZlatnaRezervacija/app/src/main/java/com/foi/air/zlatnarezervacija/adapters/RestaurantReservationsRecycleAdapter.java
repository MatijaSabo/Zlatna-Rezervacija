package com.foi.air.zlatnarezervacija.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foi.air.zlatnarezervacija.R;
import com.foi.webservice.responses.ReservationItemDetails;
import com.foi.webservice.responses.ReservationTableItemDetails;

import java.util.ArrayList;

/**
 * Created by Lovro on 21.1.2017..
 */

public class RestaurantReservationsRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<ReservationItemDetails> items;

    public RestaurantReservationsRecycleAdapter(ArrayList<ReservationItemDetails> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_reservation_card, parent, false);
        RestaurantReservationsRecycleAdapter.RestaurantReservationViewHolder item = new RestaurantReservationsRecycleAdapter.RestaurantReservationViewHolder(view);
        return item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String tables="";

        ReservationItemDetails item = items.get(position);
        String first_last_name=item.getUser_first_name() + " " + item.getUser_last_name();

        for (ReservationTableItemDetails i : item.getTables()){
            tables=tables + i.getLabel()+ ", ";
        }

        if(tables.isEmpty())tables="-";
        tables = tables.replaceAll(", $", "");

        ((RestaurantReservationViewHolder) holder).itemTimeArrival.setText(item.getTime_arrival());
        ((RestaurantReservationViewHolder) holder).itemId.setText("Broj rezervacije: " + item.getId());
        ((RestaurantReservationViewHolder) holder).itemDate.setText(item.getDate());
        ((RestaurantReservationViewHolder) holder).itemTimeCheckout.setText(item.getTime_checkout());
        ((RestaurantReservationViewHolder) holder).itemTimeArrival.setText(item.getTime_arrival());
        ((RestaurantReservationViewHolder) holder).itemPersons.setText(item.getPersons());
        ((RestaurantReservationViewHolder) holder).itemMeals.setText(item.getMeals());
        ((RestaurantReservationViewHolder) holder).itemTables.setText(tables);
        ((RestaurantReservationViewHolder) holder).itemDescription.setText(item.getDescription());
        ((RestaurantReservationViewHolder) holder).itemRemark.setText(item.getRemark());
        ((RestaurantReservationViewHolder) holder).itemFirstLastName.setText(first_last_name);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RestaurantReservationViewHolder extends RecyclerView.ViewHolder{

        TextView itemId;
        TextView itemDate;
        TextView itemTimeArrival;
        TextView itemTimeCheckout;
        TextView itemFirstLastName;
        TextView itemMeals;
        TextView itemPersons;
        TextView itemRemark;
        TextView itemDescription;
        TextView itemTables;

        public RestaurantReservationViewHolder(View itemView) {
            super(itemView);
            itemId = (TextView) itemView.findViewById(R.id.restaurant_reservation_detail_id);
            itemFirstLastName = (TextView) itemView.findViewById(R.id.restaurant_reservation_user_first_last_name_text);
            itemDate = (TextView) itemView.findViewById(R.id.restaurant_reservation_detail_date_text);
            itemTimeArrival = (TextView) itemView.findViewById(R.id.restaurant_reservation_detail_time_arrival_text);
            itemTimeCheckout = (TextView) itemView.findViewById(R.id.restaurant_reservation_detail_time_checkout_text);
            itemMeals = (TextView) itemView.findViewById(R.id.restaurant_reservation_detail_number_meals_text);
            itemPersons = (TextView) itemView.findViewById(R.id.restaurant_reservation_detail_number_persons_text);
            itemRemark = (TextView) itemView.findViewById(R.id.restaurant_reservation_detail_remark_text);
            itemDescription = (TextView) itemView.findViewById(R.id.restaurant_reservation_detail_description_text);
            itemTables = (TextView) itemView.findViewById(R.id.restaurant_reservation_detail_tables_text);
        }
    }
}
