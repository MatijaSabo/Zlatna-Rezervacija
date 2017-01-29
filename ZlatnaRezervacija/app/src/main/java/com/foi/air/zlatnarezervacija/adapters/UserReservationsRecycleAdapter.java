package com.foi.air.zlatnarezervacija.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foi.air.zlatnarezervacija.R;
import com.foi.webservice.responses.ReservationItemDetails;
import com.foi.webservice.responses.ReservationTableItemDetails;

import java.util.ArrayList;

/**
 * Created by Lovro on 3.12.2016..
 */

public class UserReservationsRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ReservationItemDetails> reservations;

    private Integer[] status_image_id = {R.mipmap.ic_hourglass_empty_black_18dp, R.mipmap.ic_done_black_18dp, R.mipmap.ic_block_black_18dp,
            R.mipmap.ic_report_problem_black_18dp, R.mipmap.ic_clear_black_18dp};

    public UserReservationsRecycleAdapter(ArrayList<ReservationItemDetails> reservations) {
        this.reservations = reservations;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_reservations, parent, false);
        UserReservationsRecycleAdapter.MyReservationViewHolder item = new UserReservationsRecycleAdapter.MyReservationViewHolder(view);
        return item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String tables="";

        ReservationItemDetails item = reservations.get(position);

        for (ReservationTableItemDetails i : item.getTables()){
            tables=tables + i.getLabel()+ ", ";
        }

        if(tables.isEmpty())tables="-";
        tables = tables.replaceAll(", $", "");

        ((MyReservationViewHolder) holder).reservationTime_Arrival.setText(item.getTime_arrival());
        ((MyReservationViewHolder) holder).reservationId.setText("Broj rezervacije: " + item.getId());
        ((MyReservationViewHolder) holder).reservationDate.setText(item.getDate());
        ((MyReservationViewHolder) holder).reservationTime_Checkout.setText(item.getTime_checkout());
        ((MyReservationViewHolder) holder).reservationPersons.setText(item.getPersons());
        ((MyReservationViewHolder) holder).reservationMeals.setText(item.getMeals());
        ((MyReservationViewHolder) holder).reservationTable.setText(tables);
        ((MyReservationViewHolder) holder).reservationDescription.setText(item.getDescription());
        ((MyReservationViewHolder) holder).reservationRemark.setText(item.getRemark());
        ((MyReservationViewHolder) holder).status_image.setImageResource(status_image_id[item.getStatus()-1]);
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public void clearData(){
        int size=getItemCount();
        if(size>0){
            for(int i=0;i<size;i++){
                this.reservations.remove(0);
            }
            this.notifyItemRangeRemoved(0, size);
        }
    }

    public class MyReservationViewHolder extends RecyclerView.ViewHolder{

        TextView reservationDate;
        TextView reservationPersons;
        TextView reservationMeals;
        TextView reservationTime_Arrival;
        TextView reservationTime_Checkout;
        TextView reservationRemark;
        TextView reservationDescription;
        TextView reservationId;
        TextView reservationTable;
        ImageView status_image;

        public MyReservationViewHolder(View itemView) {
            super(itemView);
            reservationTime_Arrival = (TextView) itemView.findViewById(R.id.reservation_detail_time_arrival_text);
            reservationRemark = (TextView) itemView.findViewById(R.id.reservation_detail_remark_text);
            reservationDate = (TextView) itemView.findViewById(R.id.reservation_detail_date_text);
            reservationId = (TextView) itemView.findViewById(R.id.reservation_detail_id);
            reservationTime_Checkout = (TextView) itemView.findViewById(R.id.reservation_detail_time_checkout_text);
            reservationPersons = (TextView) itemView.findViewById(R.id.reservation_detail_number_persons_text);
            reservationMeals = (TextView) itemView.findViewById(R.id.reservation_detail_number_meals_text);
            reservationTable = (TextView) itemView.findViewById(R.id.reservation_detail_tables_text);
            reservationDescription = (TextView) itemView.findViewById(R.id.reservation_detail_description_text);
            status_image = (ImageView) itemView.findViewById(R.id.reservation_detail_status);
        }
    }
}
